package br.com.processofinanceiro.domain.negocio.pessoas;

import static br.com.processofinanceiro.domain.negocio.utils.Formatacao.*;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import br.com.processofinanceiro.model.pessoas.Pessoas;
import br.com.processofinanceiro.model.pessoas.RetornoPessoas;

@Component
public class PessoasValidacao {

	public RetornoPessoas validarPessoas(Pessoas pessoas) throws Exception {
		Pessoas pessoasSemEspaco = removerEspacos(pessoas);
		RetornoPessoas validarObrigatoriedade = validarObrigatoriedade(pessoasSemEspaco);
		if (validarObrigatoriedade.isStatus()) {
			RetornoPessoas validarTamanho = validarTamanho(pessoasSemEspaco, validarObrigatoriedade);

			return validarTamanho;

		}
		return validarObrigatoriedade;

	}

	private Pessoas removerEspacos(Pessoas pessoas) {
		pessoas.setCpfCnpj(removerEspacosQualquer(pessoas.getCpfCnpj()));
		pessoas.setCep(removerEspacosQualquer(pessoas.getCep()));
		return pessoas;
	}

	private RetornoPessoas validarTamanho(Pessoas pessoas, RetornoPessoas retornoPessoas) {

		LocalDate agora = LocalDate.now();

		if (verificarIntervalo(pessoas.getCpfCnpj(), 11, 14)) {
			construirRetornoFalse(retornoPessoas, " CPF/CNPJ é menor que 11 ou maior que 14!");

		}
		System.out.println(agora);
		if (pessoas.getDataNascimento() != null && pessoas.getDataNascimento().isAfter(agora)) {
			construirRetornoFalse(retornoPessoas, " Data de Nascimento não pode ser superior a data de hoje!");
			System.out.println(agora);
		}

		if (verificarIntervalo(pessoas.getNome(), 4, 25)) {
			construirRetornoFalse(retornoPessoas, " Nome é menor que 4 ou maior que 25!");

		}

		if (!isNullOrEmpty(pessoas.getCep()) && verificarIntervalo(pessoas.getCep(), 8, 8)) {
			construirRetornoFalse(retornoPessoas, " CEP é menor que 8 ou maior que 8!");

		}

		if (!isNullOrEmpty(pessoas.getLogradouro()) && verificarIntervalo(pessoas.getLogradouro(), 5, 80)) {
			construirRetornoFalse(retornoPessoas, " Logradouro é menor que 5 ou maior que 80!");

		}

		if (pessoas.getNumero() != null && pessoas.getNumero() < 1) {
			construirRetornoFalse(retornoPessoas, " Número não pode ser igual ou menor que 0!");
		}

		if (!isNullOrEmpty(pessoas.getBairro()) && verificarIntervalo(pessoas.getBairro(), 4, 50)) {
			construirRetornoFalse(retornoPessoas, " Bairro é menor que 4 ou maior que 50!");
		}
		if (!isNullOrEmpty(pessoas.getCidade()) && verificarIntervalo(pessoas.getCidade(), 4, 25)) {
			construirRetornoFalse(retornoPessoas, " Cidade é menor que 4 ou maior que 25!");
		}
		if (!isNullOrEmpty(pessoas.getEstado()) && verificarIntervalo(pessoas.getEstado(), 2, 15)) {
			construirRetornoFalse(retornoPessoas, " Estado é menor que 2 ou maior que 15!");
		}

		return retornoPessoas;
	}

	private void construirRetornoFalse(RetornoPessoas retornoPessoas, String mensagem) {
		retornoPessoas.addMensagem(mensagem);
		retornoPessoas.setStatus(false);
	}

	private RetornoPessoas validarObrigatoriedade(Pessoas pessoas) {

		RetornoPessoas retornoPessoas = new RetornoPessoas();
		retornoPessoas.setStatus(true);

		if (isNullOrEmpty(pessoas.getCpfCnpj())) {
			construirRetornoFalse(retornoPessoas, " CPF/CNPJ está vázio!");

		}

		if (isNullOrEmpty(pessoas.getNome())) {
			construirRetornoFalse(retornoPessoas, " Nome está vázio!");

		}

		return retornoPessoas;
	}
}
