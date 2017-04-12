package br.com.processofinanceiro.domain.negocio.titulos;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.processofinanceiro.domain.dao.titulos.TitulosDao;
import br.com.processofinanceiro.model.titulos.FiltrarTitulos;
import br.com.processofinanceiro.model.titulos.RetornoTitulos;
import br.com.processofinanceiro.model.titulos.Titulos;

@Service
public class TitulosBs {

	@Autowired
	TitulosValidacao validacao;
	@Autowired
	TitulosDao dao;

	RetornoTitulos validar = new RetornoTitulos();

	private static final Logger log = LoggerFactory.getLogger(TitulosBs.class);

	public TitulosBs() {

	}

	public TitulosBs(TitulosDao dao, TitulosValidacao validacao) {
		this.dao = dao;
		this.validacao = validacao;
	}

	public RetornoTitulos cadastrarTitulo(Titulos titulos) {

		log.info("Cadastrando título: " + titulos);

		validar = validar(titulos);

		if (validar.isStatus()) {

			if (dao.incluir(titulos)) {

				validar.setMensagem("Título cadastrado com sucesso!");
				return validar;
			}
			return new RetornoTitulos(false, "Erro ao cadastrar título.");

		}
		return validar;

	}

	public RetornoTitulos alterarTitulo(Titulos titulos) {

		log.info("Alterando título: " + titulos);

		validar = validar(titulos);

		if (validar.isStatus()) {

			if (dao.alterar(titulos)) {
				validar.setMensagem("Título alterado com sucesso!");
				return validar;
			}
			return new RetornoTitulos(false, "Erro ao alterar título.");

		}
		return validar;

	}

	public List<Titulos> listar() {

		log.info("Consultando todos os títulos. ");

		return dao.listar();

	}
	
	public List<Titulos> ordenarTitulos(String tipo, String ordenador, String modo, List<Titulos> titulos) {

		log.trace("Ordenando títulos");
		
		List<Titulos> ordenada = titulos;
		
		if (modo.equals("DESC")) {
			if (ordenador.equals("numero")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t2.getNumero().compareTo(t1.getNumero()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("cpfCnpj")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t2.getCpfCnpj().compareTo(t1.getCpfCnpj()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("dataCriacao")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t2.getDataCriacao().compareTo(t1.getDataCriacao()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("dataVencimento")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t2.getDataVencimento().compareTo(t1.getDataVencimento()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("valorTitulo")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t2.getValorTitulo().compareTo(t1.getValorTitulo()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("valorJuros")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t2.getValorJuros().compareTo(t1.getValorJuros()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("valorDesconto")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t2.getValorDesconto().compareTo(t1.getValorDesconto()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("valorPago")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t2.getValorPago().compareTo(t1.getValorPago()))
				         .collect(Collectors.toList());
			}
			
		} else {
			
			if (ordenador.equals("numero")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t1.getNumero().compareTo(t2.getNumero()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("cpfCnpj")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t1.getCpfCnpj().compareTo(t2.getCpfCnpj()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("dataCriacao")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t1.getDataCriacao().compareTo(t2.getDataCriacao()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("dataVencimento")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t1.getDataVencimento().compareTo(t2.getDataVencimento()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("valorTitulo")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t1.getValorTitulo().compareTo(t2.getValorTitulo()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("valorJuros")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t1.getValorJuros().compareTo(t2.getValorJuros()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("valorDesconto")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t1.getValorDesconto().compareTo(t2.getValorDesconto()))
				         .collect(Collectors.toList());
			}
			if (ordenador.equals("valorPago")) {
				return ordenada.stream()
				         .sorted((t1, t2) -> t1.getValorPago().compareTo(t2.getValorPago()))
				         .collect(Collectors.toList());
			}
			
			
		}
		
		return ordenada;

		

	}
	
	public List<Titulos> filtrarTitulos(String filtro, String tipo) {

		log.trace("Consultando títulos pelos filtro: " + filtro);

		return dao.filtrarTitulos(filtro, tipo);

	}

	public List<Titulos> listarPorFiltros(FiltrarTitulos filtros) {

		log.trace("Consultando títulos pelos filtros: " + filtros);

		return dao.listarPorFiltros(filtros);

	}

	public List<Titulos> listarPorTipo(String tipo) {

		log.trace("Consultando títulos pelo tipo: " + tipo);

		return dao.listarPorTipo(tipo);

	}

	public List<Titulos> listarPorCpfCnpj(String cpfCnpj) {

		log.trace("Consultando títulos pelo CPF/CNPJ: " + cpfCnpj);

		return dao.listarPorCpfCnpj(cpfCnpj);

	}

	public Titulos consultar(Integer numero) {

		log.info("Consultando título com número = " + numero);

		return dao.consultar(numero);

	}

	public RetornoTitulos excluirContaPagar(Titulos titulos) {

		log.info("Excluíndo título com numero = " + titulos.getNumero());

		Titulos objetoARemover = consultar(titulos.getNumero());

		if (objetoARemover != null && (objetoARemover.getValorPago() == null || objetoARemover.getValorPago() == 0)) {

			if (dao.excluirContaPagar(titulos)) {
				return new RetornoTitulos(true,
						"Título com numero = " + titulos.getNumero() + " excluído com sucesso!");
			}

			return new RetornoTitulos(false, "Erro ao excluir título com numero = " + titulos.getNumero());
		}
		return new RetornoTitulos(false, "Não é possível excluir esse título pois ele já está pago!");

	}

	public RetornoTitulos validar(Titulos titulos) {

		log.info("Validando título: " + titulos);

		try {

			return validacao.validarTitulos(titulos);
		} catch (Exception e) {
			log.error("Erro ao validar título." + e);

			return new RetornoTitulos(false, "Erro ao validar título.");
		}
	}

}
