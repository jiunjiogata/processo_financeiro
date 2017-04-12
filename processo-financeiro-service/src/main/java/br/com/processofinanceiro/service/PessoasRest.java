package br.com.processofinanceiro.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zaxxer.hikari.HikariDataSource;

import br.com.processofinanceiro.domain.negocio.pessoas.PessoasBs;
import br.com.processofinanceiro.domain.negocio.titulos.TitulosBs;
import br.com.processofinanceiro.model.pessoas.Pessoas;
import br.com.processofinanceiro.model.pessoas.RetornoPessoas;

@Service
@RestController
public class PessoasRest {

	@Autowired
	PessoasBs pessoasBs;
	@Autowired
	TitulosBs titulosBs;
	@Autowired
	HikariDataSource ds;
	

	private static final Logger log = LoggerFactory.getLogger(PessoasRest.class);
		
	
	
	
	
	@GetMapping(value = "/pessoas/{cpfCnpj}")
	public ResponseEntity<Pessoas> consultarRest(@PathVariable String cpfCnpj) {

		log.trace("REST: Consultando pessoa pelo CPF/CNPJ. ");

		try {
			return new ResponseEntity<>(pessoasBs.consultar(cpfCnpj), HttpStatus.OK);

		} catch (Exception e) {

			log.error("REST: Erro ao consultar pessoas pelo CPF/CNPJ. " + e);
			return new ResponseEntity<>(new Pessoas(), HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping(value = "/pessoas/filtros/{filtros}")
	public List<Pessoas> filtrarPessoasRest(@PathVariable String filtros) {

		log.trace("REST: Filtrando pessoas. ");

		try {
			return pessoasBs.filtrarPessoas(filtros);

		} catch (Exception e) {

			log.error("REST: Erro ao filtrar pessoas. " + e);
			return new ArrayList<Pessoas>();
		}

	}

	@GetMapping(value = "/pessoas")
	public List<Pessoas> listarRest() {

		log.trace("REST: Consultando pessoas. ");

		try {
			return pessoasBs.listar();
		} catch (Exception e) {
			log.error("REST: Erro ao consultar pessoas. " + e);

			return new ArrayList<Pessoas>();
		}

	}

	@PostMapping(value = "/pessoas")
	public ResponseEntity<RetornoPessoas> cadastrarRest(@RequestBody Pessoas pessoa) {
		RetornoPessoas status = null;

		log.trace("REST: Cadastrando pessoa. ");

		try {
			status = pessoasBs.cadastrarPessoa(pessoa);

			return retornoValidacao(status);
		} catch (Exception e) {
			log.error("REST: Erro ao cadastrar pessoa. " + e);

			return retornoValidacao(status);
		}

	}

	@PutMapping(value = "/pessoas")
	public ResponseEntity<RetornoPessoas> alterarRest(@RequestBody Pessoas pessoa) {

		RetornoPessoas status = null;

		log.trace("REST: Alterando pessoa.");

		try {

			status = pessoasBs.alterarPessoa(pessoa);

			return retornoValidacao(status);

		} catch (Exception e) {
			log.error("REST: Erro ao alterar pessoa. " + e);
			return retornoValidacao(status);
		}

	}

	@DeleteMapping(value = "/pessoas/{cpfCnpj}")
	public ResponseEntity<RetornoPessoas> restDeletar(@PathVariable String cpfCnpj) {

		log.trace("REST: Excluindo pessoa.");

		try {

			RetornoPessoas status = pessoasBs.excluir(cpfCnpj);
			return retornoValidacao(status);

		} catch (Exception e) {
			log.error("REST: Erro ao excluir pessoa." + e);

			return new ResponseEntity<>(new RetornoPessoas(false, "Erro ao excluir pessoa!"), HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<RetornoPessoas> retornoValidacao(RetornoPessoas status) {
		if (status.isStatus()) {
			return new ResponseEntity<>(status, HttpStatus.OK);
		}

		else {

			return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
		}
	}
}
