package br.com.processofinanceiro.model.titulos;

import java.io.Serializable;

public class RetornoTitulos implements Serializable {

	private static final long serialVersionUID = 1745579689515649340L;
	private boolean status;
	private String mensagem = "";

	public RetornoTitulos() {

	}

	public RetornoTitulos(boolean status, String mensagem) {

		this.status = status;
		this.mensagem = mensagem;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		if (mensagem == null) {
			mensagem = "";
		}
		this.mensagem = mensagem;
	}

	public void addMensagem(String mensagemAdicional) {
		this.mensagem += mensagemAdicional;
	}
}
