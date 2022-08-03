package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDao {

	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}

	public void atualizar(Produto produto) {
		this.em.merge(produto);
	}

	public void remover(Produto produto) {
		produto = em.merge(produto);
		this.em.remove(produto);
	}

	public Produto buscarPorId(Long id) {
		// qual a entidade e qual o id
		System.out.println("buscarPorId");
		return em.find(Produto.class, id);
	}

	public List<Produto> buscarTodos() {
		// Carrega todos os produtos do banco de dados
		System.out.println("buscarTodos");
		String jpql = "SELECT p FROM Produto p";
		// createQuery tem como especificar qual o tipo de retorno
		return em.createQuery(jpql, Produto.class).getResultList();
	}

	public List<Produto> buscarPorNome(String nome) {
		System.out.println("buscarPorNome");
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, Produto.class).setParameter("nome", nome).getResultList();
	}

	public List<Produto> buscarPorNomeDaCategoria(String nome) {
		System.out.println("buscarPorNomeDaCategoria");
		//String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
		//return em.createQuery(jpql, Produto.class).setParameter("nome", nome).getResultList();
		//usando namedquery com o model
		return em.createNamedQuery("Produto.produtosPorCategoria", Produto.class).setParameter("nome", nome).getResultList();
	}

	public BigDecimal buscarPrecoDoProdutoComNome(String nome) {
		// mostrar apenas o preço de um produto
		System.out.println("buscarPrecoDoProdutoComNome");
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, BigDecimal.class).setParameter("nome", nome).getSingleResult();
	}

}
