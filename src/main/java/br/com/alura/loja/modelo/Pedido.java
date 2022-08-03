package br.com.alura.loja.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id // Identificador do identificar do banco de dados
	@GeneratedValue(strategy = GenerationType.IDENTITY) // banco de daddos que vai gerar o proximo id
	private Long id;
	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private LocalDate data = LocalDate.now();

	// @Enumerated(EnumType.STRING)
	@ManyToOne // Cardinalidade do relacionamento (Muitos para Um) Muitos produtos para apenas
				// uma categoria
	private Cliente cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL) // um para muitos itens
	// mappedBy = para indicar para n�o criar uma nova tabela de relacionamento alem
	// da ItemPedido
	// cascade = inserir item_pedido em pedidos novos para n�o precisar de um novo
	// ItemPedidoDao
	// isso aqui � um relacionamento bidirecional e tem que indicar para n�o ser um
	// novo relacionamento
	private List<ItemPedido> itens = new ArrayList<ItemPedido>();

	public Pedido() {
	}

	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}

	// adiciona o item no pedido e o item novo na lista de itens.
	public void adicionarItem(ItemPedido item) {
		item.setPedido(this);
		this.itens.add(item);
		this.valorTotal = this.valorTotal.add(item.getValor());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
