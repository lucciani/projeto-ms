package io.github.lucciani.msclientes.application.representation;

import io.github.lucciani.msclientes.domain.model.Cliente;
import lombok.Data;

@Data
public class ClienteSaveRequest {

    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel() {
        return new Cliente(cpf, nome, idade);
    }
}
