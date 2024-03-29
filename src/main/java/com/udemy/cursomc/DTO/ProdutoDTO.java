package com.udemy.cursomc.DTO;

import com.udemy.cursomc.domain.Produto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ProdutoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message = "Preenchimento Obrigatorio")
    @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres!")
    private String nome;
    private Double preco;

    public ProdutoDTO() {

    }

    public ProdutoDTO(Produto obj) {
        id = obj.getId();
        nome = obj.getNome();
        preco = obj.getPreco();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
