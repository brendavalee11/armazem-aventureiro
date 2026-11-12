package com.rpg.armazemaventureiro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Informe o nome do item")
    @Size(max = 100, message = "Este nome é grande demais, utilize no máximo 100 caracteres")

    @Column(unique = true)
    private String nome;

    @NotNull(message = "Informe a raridade do item: COMUM, INCOMUM, RARO, ÉPICO ou LENDÁRIO")

    @Enumerated(EnumType.STRING)
    private Raridade raridade;

    @NotNull(message = "Informe o preço do item")

    @DecimalMin(value = "0.0", message = "Informe um preço maior ou igual a zero")

    private BigDecimal preco;

    @Min(value = 1, message = "O nível mínimo deve ser de 1 até 100")
    @Max(value = 1, message = "O nível mínimo deve ser de 1 até 100")

    private Integer nivelMinimo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Raridade getRaridade() {
        return raridade;
    }

    public void setRaridade(Raridade raridade) {
        this.raridade = raridade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getNivelMinimo() {
        return nivelMinimo;
    }

    public void setNivelMinimo(Integer nivelMinimo) {
        this.nivelMinimo = nivelMinimo;
    }
}