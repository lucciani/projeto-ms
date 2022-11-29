package io.github.lucciani.mscartoes.application;

import io.github.lucciani.mscartoes.application.representation.CartaoSaveRequest;
import io.github.lucciani.mscartoes.application.representation.ClienteCartaoResponse;
import io.github.lucciani.mscartoes.domain.model.Cartao;
import io.github.lucciani.mscartoes.domain.model.ClienteCartao;
import io.github.lucciani.mscartoes.domain.service.CartaoService;
import io.github.lucciani.mscartoes.domain.service.ClienteCartaoService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "cartoes")
@RequiredArgsConstructor
public class CartaoController {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status(){
        return "MSCartoes  OK!";
    }

    @PostMapping
    public ResponseEntity<Cartao> salvar(@RequestBody CartaoSaveRequest request){
        Cartao cartao = cartaoService.save(request.toModel());

        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("id={id}")
                .buildAndExpand(cartao.getId())
                .toUri();
        return ResponseEntity
                .created(headerLocation)
                .body(cartao);
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartaoRenda(@RequestParam Long renda){
        List<Cartao> cartoes = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(cartoes);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<ClienteCartaoResponse>> findByCartaoCpf(@RequestParam String cpf){
        List<ClienteCartaoResponse> response = clienteCartaoService.findByCpf(cpf)
                .stream()
                .map(ClienteCartaoResponse::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
