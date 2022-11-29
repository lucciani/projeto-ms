package io.github.lucciani.mscartoes.domain.service;


import io.github.lucciani.mscartoes.domain.model.ClienteCartao;
import io.github.lucciani.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository clienteCartaoRepository;

    public List<ClienteCartao> findByCpf(String cpf){
        return clienteCartaoRepository.findByCpf(cpf);
    }
}
