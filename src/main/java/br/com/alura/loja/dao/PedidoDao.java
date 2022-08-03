package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public BigDecimal valorTotalVendido() {
		System.out.println("valorTotalVendido");
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p ";
		return em.createQuery(jpql,BigDecimal.class).getSingleResult();
	}
	
	public List<RelatorioDeVendasVo> relatorioDeVendas(){
		String jpql = "select new br.com.alura.loja.vo.RelatorioDeVendasVo("
				+ " produto.nome,"
				+ " sum(item.quantidade),"
				+ " max(pedido.data)"
				+ " )"
				+ " from Pedido pedido"
				+ " join pedido.itens item"
				+ " join item.produto produto"
				+ " group by produto.nome"
				+ " order by item.quantidade desc";
		return em.createQuery(jpql, RelatorioDeVendasVo.class).getResultList();
	}

}
