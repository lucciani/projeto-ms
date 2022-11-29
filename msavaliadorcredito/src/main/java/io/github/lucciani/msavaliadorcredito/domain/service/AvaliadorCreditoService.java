package io.github.lucciani.msavaliadorcredito.domain.service;

import feign.FeignException;
import io.github.lucciani.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.lucciani.msavaliadorcredito.application.exception.ErroComunicacaoMicroservicesException;
import io.github.lucciani.msavaliadorcredito.application.exception.ErroSolicitacaoCartaoException;
import io.github.lucciani.msavaliadorcredito.domain.model.*;
import io.github.lucciani.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.lucciani.msavaliadorcredito.infra.clients.ClienteResourceClient;
import io.github.lucciani.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteClient;
    private final CartoesResourceClient cartoesClient;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.getCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesCliente = cartoesClient.findByCartaoCpf(cpf);

            return SituacaoCliente.builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesCliente.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public AvaliacaoClienteResponse realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.getCliente(cpf);
            ResponseEntity<List<Cartao>> dadosCartaoResponse = cartoesClient.getCartaoRenda(renda);

            List<CartaoAprovado> cartoesAprovados = dadosCartaoResponse.getBody().stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBase();
                BigDecimal idadeAtual = BigDecimal.valueOf(dadosCliente.getIdade());

                BigDecimal fator = idadeAtual.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);


                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            return AvaliacaoClienteResponse.builder().cartoes(cartoesAprovados).build();

        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
        try {
            emissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }
}
