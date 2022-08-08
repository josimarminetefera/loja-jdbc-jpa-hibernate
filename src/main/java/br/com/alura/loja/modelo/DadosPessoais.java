package br.com.alura.loja.modelo;

import javax.persistence.Embeddable;

@Embeddable
public class DadosPessoais {

	private String nome;

	public DadosPessoais(String nome) {
		super();
		this.nome = nome;
	}
	

	public DadosPessoais() {
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
