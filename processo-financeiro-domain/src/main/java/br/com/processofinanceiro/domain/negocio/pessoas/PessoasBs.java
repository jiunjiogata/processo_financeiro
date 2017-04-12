package br.com.processofinanceiro.domain.negocio.pessoas;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.processofinanceiro.domain.dao.pessoas.PessoasDao;
import br.com.processofinanceiro.domain.negocio.titulos.TitulosBs;
import br.com.processofinanceiro.model.pessoas.Pessoas;
import br.com.processofinanceiro.model.pessoas.RetornoPessoas;
import br.com.processofinanceiro.model.titulos.Titulos;

@Service
public class PessoasBs {
	@Autowired
	PessoasValidacao validacao;
	@Autowired
	PessoasDao dao;
	@Autowired
	TitulosBs titulosBs;

	RetornoPessoas validar = new RetornoPessoas();

	private static final Logger log = LoggerFactory.getLogger(PessoasBs.class);

	public PessoasBs() {

	}

	public PessoasBs(PessoasDao dao, PessoasValidacao validacao) {
		this.dao = dao;
		this.validacao = validacao;
	}

	public RetornoPessoas cadastrarPessoa(Pessoas pessoas) {

		log.info("Cadastrando pessoa: " + pessoas);

		validar = validar(pessoas);

		if (validar.isStatus()) {

			if (dao.incluir(pessoas)) {

				validar.setMensagem("Pessoa cadastrada com sucesso!");
				return validar;
			}
			return new RetornoPessoas(false, "Erro ao cadastrar pessoa.");

		}
		return validar;

	}

	public RetornoPessoas alterarPessoa(Pessoas pessoas) {

		log.info("Alterando pessoa: " + pessoas);

		validar = validar(pessoas);

		if (validar.isStatus()) {

			if (dao.alterar(pessoas)) {
				validar.setMensagem("Pessoa alterada com sucesso!");
				return validar;
			}
			return new RetornoPessoas(false, "Erro ao alterar pessoa.");

		}
		return validar;

	}

	public List<Pessoas> listar() {

		log.info("Consultando todas as pessoas. ");

		return dao.listar();
		
		
		
	}
	
	public List<Pessoas> filtrarPessoas(String filtros) {

		log.info("Filtrando pessoas. ");

		return dao.filtrar(filtros);
		
		
		
	}

	public Pessoas consultar(String cpfCnpj) {

		log.info("Consultando pessoa com CPF/CNPJ = " + cpfCnpj);

		return dao.consultar(cpfCnpj);

	}

	public RetornoPessoas excluir(String cpfCnpj) {

		log.info("Excluindo pessoa com CPF/CNPJ = " + cpfCnpj);

		Pessoas objetoARemover = consultar(cpfCnpj);

		if (objetoARemover != null) {
			List<Titulos> titulosPorPessoa = titulosBs.listarPorCpfCnpj(cpfCnpj);
			if (CollectionUtils.isEmpty(titulosPorPessoa)) {

				if (dao.excluir(cpfCnpj)) {
					return new RetornoPessoas(true, "Pessoa com CPF/CNPJ = " + cpfCnpj + " excluida com sucesso");
				}
				return new RetornoPessoas(false, "Erro ao excluir pessoa com CPF/CNPJ = " + cpfCnpj);
			}
			return new RetornoPessoas(false, "Não é possível excluir essa pessoa pois ela possui títulos!");
		}
		return new RetornoPessoas(false, "Não foi encontrada uma pessoa com esse CPF/CNPJ!");
	}

	public RetornoPessoas validar(Pessoas pessoas) {

		log.info("Validando pessoa: " + pessoas);

		try {

			return validacao.validarPessoas(pessoas);
		} catch (Exception e) {
			log.error("Erro ao validar pessoa. " + e);

			return new RetornoPessoas(false, "Erro ao validar pessoa. ");
		}
	}

}
