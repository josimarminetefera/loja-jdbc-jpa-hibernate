package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
		// String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
		// return em.createQuery(jpql, Produto.class).setParameter("nome",
		// nome).getResultList();
		// usando namedquery com o model
		return em.createNamedQuery("Produto.produtosPorCategoria", Produto.class).setParameter("nome", nome)
				.getResultList();
	}

	public BigDecimal buscarPrecoDoProdutoComNome(String nome) {
		// mostrar apenas o preço de um produto
		System.out.println("buscarPrecoDoProdutoComNome");
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, BigDecimal.class).setParameter("nome", nome).getSingleResult();
	}

	// Este metodo de consulta vem com parametros opcionais
	public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
		String jpql = "SELECT p FROM Produto p WHERE 1=1 ";
		// tem que verificar cada um dos parametros para adicionar o and específico
		if (nome != null && !nome.trim().isEmpty()) {
			jpql += " AND p.nome = :nome ";
		}
		if (preco != null) {
			jpql += " AND p.preco = :preco ";
		}
		if (dataCadastro != null) {
			jpql += " AND p.dataCadastro = :dataCadastro ";
		}

		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
		// tem que criar validação também para passar parametros
		if (nome != null && !nome.trim().isEmpty()) {
			query.setParameter("nome", nome);
		}
		if (preco != null) {
			query.setParameter("preco", preco);
		}
		if (dataCadastro != null) {
			query.setParameter("dataCadastro", dataCadastro);
		}

		return query.getResultList();
	}

	// Este método faz consulta usando objetos sem nenhuma linha de select
	public List<Produto> buscarPorParametrosComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		// adiciona a classe de criterio
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		// de onde ele vai disparar para quem from = Produto
		Root<Produto> from = query.from(Produto.class);

		// para filtrar os parametros tem que adicionar os filtros
		Predicate filtros = builder.and();// este é o objeto que vai adicionando os parametros do and
		if (nome != null && !nome.trim().isEmpty()) {
			// builder.and passando o paramtro
			filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
		}
		if (preco != null) {
			filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
		}
		if (dataCadastro != null) {
			filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
		}
		query.where(filtros);

		// para disparar a query
		return em.createQuery(query).getResultList();
	}

}
