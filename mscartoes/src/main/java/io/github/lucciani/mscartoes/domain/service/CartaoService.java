package io.github.lucciani.mscartoes.domain.service;

import io.github.lucciani.mscartoes.domain.model.Cartao;
import io.github.lucciani.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    @Transactional
    public Cartao save(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
        BigDecimal valorRenda = BigDecimal.valueOf(renda);
        return cartaoRepository.findByRendaLessThanEqual(valorRenda);
    }
}
