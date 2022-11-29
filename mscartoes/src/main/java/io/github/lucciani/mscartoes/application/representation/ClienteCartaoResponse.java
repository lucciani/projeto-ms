package io.github.lucciani.mscartoes.application.representation;

import io.github.lucciani.mscartoes.domain.model.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCartaoResponse {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;


    public static ClienteCartaoResponse fromModel(ClienteCartao clienteCartao) {
        return new ClienteCartaoResponse(
                clienteCartao.getCartao().getNome(),
                clienteCartao.getCartao().getBandeira().toString(),
                clienteCartao.getLimite());
    }
}
