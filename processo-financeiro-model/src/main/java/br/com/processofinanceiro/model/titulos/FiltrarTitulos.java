package br.com.processofinanceiro.model.titulos;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FiltrarTitulos implements Serializable {

	private static final long serialVersionUID = 3624756819032677472L;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataCriacaoInicial;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataCriacaoFinal;

	private Integer numero;

	private String cpfCnpj;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataVencimentoInicial;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataVencimentoFinal;

	private Double valorTituloInicial;

	private Double valorTituloFinal;

	private String tipo;

	public LocalDate getDataCriacaoInicial() {
		return dataCriacaoInicial;
	}

	public void setDataCriacaoInicial(LocalDate dataCriacaoInicial) {
		this.dataCriacaoInicial = dataCriacaoInicial;
	}

	public LocalDate getDataCriacaoFinal() {
		return dataCriacaoFinal;
	}

	public void setDataCriacaoFinal(LocalDate dataCriacaoFinal) {
		this.dataCriacaoFinal = dataCriacaoFinal;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public LocalDate getDataVencimentoInicial() {
		return dataVencimentoInicial;
	}

	public void setDataVencimentoInicial(LocalDate dataVencimentoInicial) {
		this.dataVencimentoInicial = dataVencimentoInicial;
	}

	public LocalDate getDataVencimentoFinal() {
		return dataVencimentoFinal;
	}

	public void setDataVencimentoFinal(LocalDate dataVencimentoFinal) {
		this.dataVencimentoFinal = dataVencimentoFinal;
	}

	public Double getValorTituloInicial() {
		return valorTituloInicial;
	}

	public void setValorTituloInicial(Double valorTituloInicial) {
		this.valorTituloInicial = valorTituloInicial;
	}

	public Double getValorTituloFinal() {
		return valorTituloFinal;
	}

	public void setValorTituloFinal(Double valorTituloFinal) {
		this.valorTituloFinal = valorTituloFinal;
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
		result = prime * result + ((dataCriacaoFinal == null) ? 0 : dataCriacaoFinal.hashCode());
		result = prime * result + ((dataCriacaoInicial == null) ? 0 : dataCriacaoInicial.hashCode());
		result = prime * result + ((dataVencimentoFinal == null) ? 0 : dataVencimentoFinal.hashCode());
		result = prime * result + ((dataVencimentoInicial == null) ? 0 : dataVencimentoInicial.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valorTituloFinal == null) ? 0 : valorTituloFinal.hashCode());
		result = prime * result + ((valorTituloInicial == null) ? 0 : valorTituloInicial.hashCode());
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
		FiltrarTitulos other = (FiltrarTitulos) obj;
		if (cpfCnpj == null) {
			if (other.cpfCnpj != null)
				return false;
		} else if (!cpfCnpj.equals(other.cpfCnpj))
			return false;
		if (dataCriacaoFinal == null) {
			if (other.dataCriacaoFinal != null)
				return false;
		} else if (!dataCriacaoFinal.equals(other.dataCriacaoFinal))
			return false;
		if (dataCriacaoInicial == null) {
			if (other.dataCriacaoInicial != null)
				return false;
		} else if (!dataCriacaoInicial.equals(other.dataCriacaoInicial))
			return false;
		if (dataVencimentoFinal == null) {
			if (other.dataVencimentoFinal != null)
				return false;
		} else if (!dataVencimentoFinal.equals(other.dataVencimentoFinal))
			return false;
		if (dataVencimentoInicial == null) {
			if (other.dataVencimentoInicial != null)
				return false;
		} else if (!dataVencimentoInicial.equals(other.dataVencimentoInicial))
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
		if (valorTituloFinal == null) {
			if (other.valorTituloFinal != null)
				return false;
		} else if (!valorTituloFinal.equals(other.valorTituloFinal))
			return false;
		if (valorTituloInicial == null) {
			if (other.valorTituloInicial != null)
				return false;
		} else if (!valorTituloInicial.equals(other.valorTituloInicial))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FiltrarTitulos [dataCriacaoInicial=" + dataCriacaoInicial + ", dataCriacaoFinal=" + dataCriacaoFinal
				+ ", numero=" + numero + ", cpfCnpj=" + cpfCnpj + ", dataVencimentoInicial=" + dataVencimentoInicial
				+ ", dataVencimentoFinal=" + dataVencimentoFinal + ", valorTituloInicial=" + valorTituloInicial
				+ ", valorTituloFinal=" + valorTituloFinal + ", tipo=" + tipo + "]";
	}

}
