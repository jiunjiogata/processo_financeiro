package br.com.processofinanceiro.domain.negocio.titulos;

import static br.com.processofinanceiro.domain.negocio.utils.Formatacao.isNullOrEmpty;
import static br.com.processofinanceiro.domain.negocio.utils.Formatacao.removerEspacosQualquer;
import static br.com.processofinanceiro.domain.negocio.utils.Formatacao.verificarIntervalo;

import org.springframework.stereotype.Component;

import br.com.processofinanceiro.model.titulos.RetornoTitulos;
import br.com.processofinanceiro.model.titulos.Titulos;

@Component
public class TitulosValidacao {

	public RetornoTitulos validarTitulos(Titulos titulos) throws Exception {
		Titulos titulosSemEspaco = removerEspacos(titulos);
		RetornoTitulos validarObrigatoriedade = validarObrigatoriedade(titulosSemEspaco);
		if (validarObrigatoriedade.isStatus()) {
			RetornoTitulos validarTamanho = validarTamanho(titulosSemEspaco, validarObrigatoriedade);

			return validarTamanho;

		}
		return validarObrigatoriedade;

	}

	private Titulos removerEspacos(Titulos titulos) {
		titulos.setCpfCnpj(removerEspacosQualquer(titulos.getCpfCnpj()));
		titulos.setTipo(removerEspacosQualquer(titulos.getTipo()));

		return titulos;
	}

	private RetornoTitulos validarTamanho(Titulos titulos, RetornoTitulos retornoTitulos) {

		if (titulos.getDataCriacao().isAfter(titulos.getDataVencimento())) {
			construirRetornoFalse(retornoTitulos, " Data de Criação não pode ser superior a Data de Vencimento!");
		}

		if (verificarIntervalo(titulos.getCpfCnpj(), 11, 14)) {
			construirRetornoFalse(retornoTitulos, " CPF/CNPJ é menor que 11" + " ou maior que 14!");

		}

		if (titulos.getNumero() != null && titulos.getNumero() < 1) {
			construirRetornoFalse(retornoTitulos, " Número não pode ser igual ou menor que 0!");
		}

		if (titulos.getValorTitulo() < 0) {
			construirRetornoFalse(retornoTitulos, " Valor do Título não pode ser negativo!");

		}
		if (titulos.getValorJuros() < 0) {
			construirRetornoFalse(retornoTitulos, " Valor de Juros não pode ser negativo!");

		}

		return retornoTitulos;
	}

	private void construirRetornoFalse(RetornoTitulos retornoTitulos, String mensagem) {
		retornoTitulos.addMensagem(mensagem);
		retornoTitulos.setStatus(false);
	}

	private RetornoTitulos validarObrigatoriedade(Titulos titulos) {

		RetornoTitulos retornoTitulos = new RetornoTitulos();
		retornoTitulos.setStatus(true);

		if (titulos.getDataCriacao() == null) {
			construirRetornoFalse(retornoTitulos, " Data de Criação está vázia!");

		}

		if (titulos.getDataVencimento() == null) {
			construirRetornoFalse(retornoTitulos, " Data de Vencimento está vázia!");

		}

		if (isNullOrEmpty(titulos.getCpfCnpj())) {
			construirRetornoFalse(retornoTitulos, " CPF/CNPJ está vázio!");

		}

		if (titulos.getValorTitulo() == null) {
			construirRetornoFalse(retornoTitulos, " Valor do Título está vázio!");

		}
		if (titulos.getValorJuros() == null) {
			construirRetornoFalse(retornoTitulos, " Valor de Juros está vázio!");

		}
		
		if (titulos.getValorPago() != null && titulos.getDataPagamento() == null) {
			construirRetornoFalse(retornoTitulos, " Data de Pagamento está vázia!");
		}
		
		if (titulos.getDataPagamento() != null && titulos.getValorPago() == null) {
			construirRetornoFalse(retornoTitulos, " Valor Pago está vázio!");
		}
		return retornoTitulos;
	}
}
