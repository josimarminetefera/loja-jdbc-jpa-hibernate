package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class CadastroDePedido {

	public static void main(String[] args) {
		cadastrarProduto();

		EntityManager em = JPAUtil.getEntityManager();
		// buscar um produto para usar de teste
		ProdutoDao produtoDao = new ProdutoDao(em);
		System.out.println("Iniciando a busca do produto");
		Produto produto = produtoDao.buscarPorId(1l);
		Produto produto2 = produtoDao.buscarPorId(2l);
		Produto produto3 = produtoDao.buscarPorId(3l);

		// buscar o cliente
		ClienteDao clienteDao = new ClienteDao(em);
		System.out.println("Iniciando a busca do cliente");
		Cliente cliente = clienteDao.buscarPorId(1l);

		// abrir a transação
		em.getTransaction().begin();
		System.out.println("Abrir a transação");

		// criar pedido para o cliente
		Pedido pedido = new Pedido(cliente);
		Pedido pedido2 = new Pedido(cliente);

		System.out.println("Inserir o pedido");

		// adicionar o tem ao pedido
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		pedido.adicionarItem(new ItemPedido(40, pedido, produto2));
		pedido.adicionarItem(new ItemPedido(2, pedido2, produto3));

		// salvar o pedido no banco de dados
		PedidoDao pedidoDao = new PedidoDao(em);
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);

		// commitar a transação
		em.getTransaction().commit();

		//
		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		System.out.println("totalVendido: " + totalVendido);

		List<RelatorioDeVendasVo> relatorioDeVendas = pedidoDao.relatorioDeVendas();
		relatorioDeVendas.forEach(System.out::println);

	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("8000"), videogames);
		Produto mac = new Produto("MacBosta", "Merda", new BigDecimal("10000"), informatica);

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);

		// criar cliente
		Cliente cliente = new Cliente("Josimar", "13395154742");
		ClienteDao clienteDao = new ClienteDao(em);
		clienteDao.cadastrar(cliente);

		// inicia a transação
		em.getTransaction().begin();

		// da o persiste no na classe
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		categoriaDao.cadastrar(informatica);
		produtoDao.cadastrar(mac);
		categoriaDao.cadastrar(videogames);
		produtoDao.cadastrar(videogame);

		// podemos atualizar antes de dar commit
		celular.setDescricao("CELULARES");

		// comita no banco de dadaos os dados
		em.getTransaction().commit();
		em.close();

//		celulares = em.merge(celulares);
//		em.flush();//Isso aqui insere no banco de dados porem não comitar

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
