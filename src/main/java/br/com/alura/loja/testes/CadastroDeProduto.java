package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {
		cadastrarProduto();
		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);

		Produto p = produtoDao.buscarPorId(1l);
		System.out.println(p.getPreco());
		
		List<Produto> todosProdutos = produtoDao.buscarTodos();
		todosProdutos.forEach(p3 -> System.out.println(p.getNome()));

		List<Produto> todos = produtoDao.buscarPorNomeDaCategoria("CELULARES");
		todos.forEach(p2 -> System.out.println(p.getNome()));

		BigDecimal precoDoProduto = produtoDao.buscarPrecoDoProdutoComNome("Xiaomi Redmi");
		System.out.println("Preco do Produto: " + precoDoProduto);
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);

		// inicia a transa��o
		em.getTransaction().begin();

		// da o persiste no na classe
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);

		// podemos atualizar antes de dar commit
		celular.setDescricao("CELULARES");

		// comita no banco de dadaos os dados
		em.getTransaction().commit();
		em.close();

//		celulares = em.merge(celulares);
//		em.flush();//Isso aqui insere no banco de dados porem n�o comitar

//		EntityManager emTeste = JPAUtil.getEntityManager();
//		em.getTransaction().begin();
//
//		emTeste.persist(celulares);
//		celulares.setNome("XPTO");
//
//		emTeste.flush();
//		emTeste.clear();
//
//		// sempre tem que reatribuir para recuperar a entidade antiga
//		celulares = em.merge(celulares);
//		celulares.setNome("1234");
//		emTeste.flush();
//
//		emTeste.clear();
//		celulares = em.merge(celulares);
//		em.remove(celulares);
//		em.flush();

	}

}
