package br.com.processofinanceiro.domain.dao.pessoas;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.processofinanceiro.model.pessoas.Pessoas;

@Repository
public class PessoasDao {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public PessoasDao(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	private static final Logger log = LoggerFactory.getLogger(PessoasDao.class);

	public Boolean incluir(Pessoas pessoas) {

		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("INSERT INTO pessoas(cpf_cnpj, nome, data_nascimento, cep, logradouro, numero, bairro, "
					+ "cidade, estado) " + "VALUES(?,?,?,?,?,?,?,?,?)");
			jdbcTemplateObject.update(SQL.toString(), pessoas.getCpfCnpj(), pessoas.getNome(),
					pessoas.getDataNascimento(), pessoas.getCep(), pessoas.getLogradouro(), pessoas.getNumero(),
					pessoas.getBairro(), pessoas.getCidade(), pessoas.getEstado());

			log.info("Pessoa cadastrada com sucesso!");
			return true;
		} catch (Exception e) {
			log.error("Erro ao cadastrar pessoa. " + e);
			return false;

		}
	}

	public Boolean alterar(Pessoas pessoas) {

		try {
			StringBuffer SQL = new StringBuffer();

			SQL.append(
					"UPDATE pessoas SET nome = ?, data_nascimento = ?, cep = ?, logradouro = ?, numero = ?, bairro = ?,"
							+ " cidade = ?, estado = ? WHERE cpf_cnpj = ?");
			jdbcTemplateObject.update(SQL.toString(), pessoas.getNome(), pessoas.getDataNascimento(), pessoas.getCep(),
					pessoas.getLogradouro(), pessoas.getNumero(), pessoas.getBairro(), pessoas.getCidade(),
					pessoas.getEstado(), pessoas.getCpfCnpj());
			log.info("Pessoa alterada com sucesso!");
			return true;
		} catch (Exception e) {
			log.error("Erro ao alterar pessoa. " + e);
			return false;

		}
	}

	public Boolean excluir(String cpfCnpj) {

		try {

			String sql = "DELETE FROM pessoas WHERE cpf_cnpj = ?";
			jdbcTemplateObject.update(sql, cpfCnpj);
			log.info("Pessoa excluída com sucesso!");
			return true;
		} catch (Exception e) {
			log.error("Erro ao excluir pessoa. " + e);
			return false;

		}
	}

	public Pessoas consultar(String cpfCnpj) {

		try {

			String sql = "SELECT * FROM pessoas WHERE cpf_cnpj = ?";
			Pessoas pessoa = jdbcTemplateObject.queryForObject(sql, new Object[] { cpfCnpj }, new PessoasMapper());

			log.info("Pessoa consultada com sucesso!");
			return pessoa;

		} catch (Exception e) {
			log.error("Erro ao consultar pessoa. " + e);

			return new Pessoas();
		}
	}
	
	public List<Pessoas> filtrar(String filtros) {

		try {
			String sql = null;
			
			if (filtros.equals("comTitulos")) {
				 sql = "SELECT DISTINCT pessoas.nome, pessoas.cpf_cnpj, pessoas.data_nascimento, pessoas.cep, pessoas.logradouro, pessoas.numero, pessoas.bairro, "
						+ "pessoas.cidade, pessoas.estado FROM pessoas INNER JOIN titulos ON pessoas.cpf_cnpj = titulos.cpf_cnpj  ORDER BY pessoas.nome ASC";
			}
			
			if (filtros.equals("semTitulos")) {
				 sql = "SELECT * FROM pessoas WHERE cpf_cnpj NOT IN (SELECT cpf_cnpj FROM titulos)  ORDER BY pessoas.nome ASC";
			}
			
			if (filtros.equals("todas")) {
				 sql = "SELECT * FROM pessoas ORDER BY pessoas.nome ASC";
			}
			
			List<Pessoas> pessoa = jdbcTemplateObject.query(sql, new PessoasMapper());

			log.info("Pessoas filtradas com sucesso!");
			return pessoa;

		} catch (Exception e) {
			log.error("Erro ao filtrar pessoas. " + e);

			return new ArrayList<Pessoas>();
		}
	}

	public List<Pessoas> listar() {

		try {

			String sql = "SELECT * FROM pessoas ORDER BY pessoas.nome ASC";
			
			List<Pessoas> pessoa = jdbcTemplateObject.query(sql, new PessoasMapper());
		
			log.info("Pessoas consultadas com sucesso!");
			return pessoa;
		} catch (Exception e) {
			log.error("Erro ao listar pessoas. " + e);

			return new ArrayList<Pessoas>();
		}
	}

}
