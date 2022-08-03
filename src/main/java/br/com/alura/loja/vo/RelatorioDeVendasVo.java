package br.com.alura.loja.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RelatorioDeVendasVo {

	private String nome;
	private Long quantidadeVendida;
	private LocalDate dataUltimaVenda;

	public RelatorioDeVendasVo(String nome, Long quantidadeVendida, LocalDate dataUltimaVenda) {
		this.nome = nome;
		this.quantidadeVendida = quantidadeVendida;
		this.dataUltimaVenda = dataUltimaVenda;
	}

	public String getNome() {
		return nome;
	}

	public Long getQuantidadeVendida() {
		return quantidadeVendida;
	}

	public LocalDate getDataUltimaVenda() {
		return dataUltimaVenda;
	}

	@Override
	public String toString() {
		return "RelatorioDeVendasVo [nome=" + nome + ", quantidadeVendida=" + quantidadeVendida + ", dataUltimaVenda="
				+ dataUltimaVenda + "]";
	}


}
