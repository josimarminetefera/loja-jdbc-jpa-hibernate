package br.com.alura.loja.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // Anota��o de entidade
@Table(name = "categorias") // Apilido para a tabela no banco de dados
public class Categoria {

	// isso aqui � para esternar a gera��o do id caso queira juntar paramtros
	// @EmbeddedId
	// private CategoriaId id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;

	public Categoria() {
	}

	// Construtor padr�o para a categoria.
	public Categoria(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
