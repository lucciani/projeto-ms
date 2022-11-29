package io.github.lucciani.mscartoes.application.representation;

import io.github.lucciani.mscartoes.domain.model.BandeiraCartao;
import io.github.lucciani.mscartoes.domain.model.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBase;

    public Cartao toModel() {
        return new Cartao(nome, bandeira, renda, limiteBase);
    }
}
