package br.com.processofinanceiro.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.processofinanceiro.domain.negocio.titulos.TitulosBs;
import br.com.processofinanceiro.model.titulos.FiltrarTitulos;
import br.com.processofinanceiro.model.titulos.RetornoTitulos;
import br.com.processofinanceiro.model.titulos.Titulos;

@Service
@RestController
public class TitulosRest {

	@Autowired
	TitulosBs titulosBs;

	private static final Logger log = LoggerFactory.getLogger(TitulosRest.class);

	@GetMapping(value = "/titulos/{numero}")
	public ResponseEntity<Titulos> consultarRest(@PathVariable Integer numero) {

		log.trace("REST: Consultando título pelo número.");

		try {
			return new ResponseEntity<>(titulosBs.consultar(numero), HttpStatus.OK);

		} catch (Exception e) {

			log.error("REST: Erro ao consultar título por número. " + e);
			return new ResponseEntity<>(new Titulos(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping(value = "/titulos")
	public List<Titulos> listarRest() {

		log.trace("REST: Consultando Títulos.");

		try {
			return titulosBs.listar();
		} catch (Exception e) {
			log.error("REST: Erro ao listar títulos. " + e);

			return new ArrayList<Titulos>();
		}

	}

	@GetMapping(value = "/titulos/tipo/{tipo}")
	private ResponseEntity<List<Titulos>> listarPorTipoRest(@PathVariable String tipo) {

		log.trace("REST: Consultando títulos por tipo.");

		try {
			return new ResponseEntity<>(titulosBs.listarPorTipo(tipo), HttpStatus.OK);
		} catch (Exception e) {
			log.error("REST: Erro ao listar títulos por tipo. " + e);

			return new ResponseEntity<>(new ArrayList<Titulos>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/titulos/ordenar/{tipo}/{ordenador}/{modoOrdem}")
	private ResponseEntity<List<Titulos>> ordenarTitulos(@PathVariable String tipo, @PathVariable String ordenador, @PathVariable String modoOrdem, @RequestBody List<Titulos> titulos) {

		log.trace("REST: Ordenando títulos.");

		try {
			return new ResponseEntity<>(titulosBs.ordenarTitulos(tipo, ordenador, modoOrdem, titulos), HttpStatus.OK);
		} catch (Exception e) {
			log.error("REST: Erro ao ordenar títulos. " + e);

			return new ResponseEntity<>(new ArrayList<Titulos>(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/titulos/filtrar/{filtro}/{tipo}")
	private ResponseEntity<List<Titulos>> filtrarTitulos(@PathVariable String filtro, @PathVariable String tipo) {

		log.trace("REST: Consultando títulos pelos filtros.");

		try {
			return new ResponseEntity<>(titulosBs.filtrarTitulos(filtro, tipo), HttpStatus.OK);
		} catch (Exception e) {
			log.error("REST: Erro ao listar títulos por tipo. " + e);

			return new ResponseEntity<>(new ArrayList<Titulos>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/titulos/filtros")
	private ResponseEntity<List<Titulos>> restBuscar(@RequestBody FiltrarTitulos filtros) {

		log.trace("REST: Consultando títulos pelos filtros.");

		try {
			return new ResponseEntity<>(titulosBs.listarPorFiltros(filtros), HttpStatus.OK);
		} catch (Exception e) {
			log.error("REST: Erro ao listar títulos pelos filtros. " + e);

			return new ResponseEntity<>(new ArrayList<Titulos>(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/titulos")
	public ResponseEntity<RetornoTitulos> cadastrarRest(@RequestBody Titulos titulos) {
		RetornoTitulos status = null;

		log.trace("REST: Cadastrando título.");

		try {
			status = titulosBs.cadastrarTitulo(titulos);

			return retornoValidacao(status);
		} catch (Exception e) {
			log.error("REST: Erro ao cadastrar título. " + e);

			return retornoValidacao(status);
		}

	}

	@PutMapping(value = "/titulos")
	public ResponseEntity<RetornoTitulos> alterarRest(@RequestBody Titulos titulos) {

		RetornoTitulos status = null;

		log.trace("REST: Alterando título. ");

		try {

			status = titulosBs.alterarTitulo(titulos);

			return retornoValidacao(status);

		} catch (Exception e) {
			log.error("REST: Erro ao alterar título. " + e);
			return retornoValidacao(status);
		}

	}

	@PostMapping(value = "/titulos/excluir")
	public ResponseEntity<RetornoTitulos> excluirContaPagarRest(@RequestBody Titulos titulos) {

		log.trace("REST: Excluindo título.");

		try {

			RetornoTitulos status = titulosBs.excluirContaPagar(titulos);
			return retornoValidacao(status);

		} catch (Exception e) {
			log.error("REST: Erro ao excluir título. " + e);

			return new ResponseEntity<>(new RetornoTitulos(false, "Erro ao excluir título!"), HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<RetornoTitulos> retornoValidacao(RetornoTitulos status) {
		if (status.isStatus()) {
			return new ResponseEntity<>(status, HttpStatus.OK);
		}

		else {

			return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
		}
	}
}