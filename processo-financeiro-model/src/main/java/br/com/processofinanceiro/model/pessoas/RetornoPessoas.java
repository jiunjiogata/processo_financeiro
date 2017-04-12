package br.com.processofinanceiro.model.pessoas;

import java.io.Serializable;

public class RetornoPessoas implements Serializable {

	private static final long serialVersionUID = -6924405894821488273L;
	private boolean status;
	private String mensagem = "";

	public RetornoPessoas() {

	}

	public RetornoPessoas(boolean status, String mensagem) {

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mensagem == null) ? 0 : mensagem.hashCode());
		result = prime * result + (status ? 1231 : 1237);
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
		RetornoPessoas other = (RetornoPessoas) obj;
		if (mensagem == null) {
			if (other.mensagem != null)
				return false;
		} else if (!mensagem.equals(other.mensagem))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RetornoPessoas [status=" + status + ", mensagem=" + mensagem + "]";
	}

}
