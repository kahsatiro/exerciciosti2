package br.com.exercicio3.model;

import java.math.BigDecimal;

public class Produto {
	private int id;
    private String nome;
    private BigDecimal preco;
    private int quantidade;
    // Gere os Getters e Setters aqui (Source > Generate Getters and Setters...)
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
