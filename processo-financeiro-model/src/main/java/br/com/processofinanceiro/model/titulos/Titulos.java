package br.com.processofinanceiro.model.titulos;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Titulos implements Serializable {

	private static final long serialVersionUID = 7462002808827588479L;

	public Titulos() {

	}

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataCriacao;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataVencimento;

	private String cpfCnpj;

	private Integer numero;

	private Double valorTitulo;

	private Double valorJuros;

	private Double valorJurosCalculado;

	private Double valorDesconto;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataPagamento;

	private Double valorPago;

	private String tipo;

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Double getValorTitulo() {
		return valorTitulo;
	}

	public void setValorTitulo(Double valorTitulo) {
		this.valorTitulo = valorTitulo;
	}

	public Double getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(Double valorJuros) {
		this.valorJuros = valorJuros;
	}

	public Double getValorJurosCalculado() {
		return valorJurosCalculado;
	}

	public void setValorJurosCalculado(Double valorJurosCalculado) {
		this.valorJurosCalculado = valorJurosCalculado;
	}

	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpfCnpj == null) ? 0 : cpfCnpj.hashCode());
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((dataPagamento == null) ? 0 : dataPagamento.hashCode());
		result = prime * result + ((dataVencimento == null) ? 0 : dataVencimento.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valorDesconto == null) ? 0 : valorDesconto.hashCode());
		result = prime * result + ((valorJuros == null) ? 0 : valorJuros.hashCode());
		result = prime * result + ((valorJurosCalculado == null) ? 0 : valorJurosCalculado.hashCode());
		result = prime * result + ((valorPago == null) ? 0 : valorPago.hashCode());
		result = prime * result + ((valorTitulo == null) ? 0 : valorTitulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Titulos other = (Titulos) obj;
		if (cpfCnpj == null) {
			if (other.cpfCnpj != null)
				return false;
		} else if (!cpfCnpj.equals(other.cpfCnpj))
			return false;
		if (dataCriacao == null) {
			if (other.dataCriacao != null)
				return false;
		} else if (!dataCriacao.equals(other.dataCriacao))
			return false;
		if (dataPagamento == null) {
			if (other.dataPagamento != null)
				return false;
		} else if (!dataPagamento.equals(other.dataPagamento))
			return false;
		if (dataVencimento == null) {
			if (other.dataVencimento != null)
				return false;
		} else if (!dataVencimento.equals(other.dataVencimento))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valorDesconto == null) {
			if (other.valorDesconto != null)
				return false;
		} else if (!valorDesconto.equals(other.valorDesconto))
			return false;
		if (valorJuros == null) {
			if (other.valorJuros != null)
				return false;
		} else if (!valorJuros.equals(other.valorJuros))
			return false;
		if (valorJurosCalculado == null) {
			if (other.valorJurosCalculado != null)
				return false;
		} else if (!valorJurosCalculado.equals(other.valorJurosCalculado))
			return false;
		if (valorPago == null) {
			if (other.valorPago != null)
				return false;
		} else if (!valorPago.equals(other.valorPago))
			return false;
		if (valorTitulo == null) {
			if (other.valorTitulo != null)
				return false;
		} else if (!valorTitulo.equals(other.valorTitulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Titulos [dataCriacao=" + dataCriacao + ", dataVencimento=" + dataVencimento + ", cpfCnpj=" + cpfCnpj
				+ ", numero=" + numero + ", valorTitulo=" + valorTitulo + ", valorJuros=" + valorJuros
				+ ", valorJurosCalculado=" + valorJurosCalculado + ", valorDesconto=" + valorDesconto
				+ ", dataPagamento=" + dataPagamento + ", valorPago=" + valorPago + ", tipo=" + tipo + "]";
	}

}
