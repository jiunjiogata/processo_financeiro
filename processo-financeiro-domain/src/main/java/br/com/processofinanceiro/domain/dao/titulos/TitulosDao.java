package br.com.processofinanceiro.domain.dao.titulos;

import static br.com.processofinanceiro.domain.negocio.utils.Formatacao.isNullOrEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.processofinanceiro.model.titulos.FiltrarTitulos;
import br.com.processofinanceiro.model.titulos.Titulos;

@Repository
public class TitulosDao {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public TitulosDao(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	private static final Logger log = LoggerFactory.getLogger(TitulosDao.class);

	public Boolean incluir(Titulos titulos) {

		try {
			StringBuffer SQL = new StringBuffer();
			if (titulos.getNumero() != null) {
			SQL.append("INSERT INTO titulos(data_criacao, data_vencimento, cpf_cnpj, numero, valor_titulo, "
					+ "valor_juros, valor_juros_calculado, valor_desconto, data_pagamento, valor_pago, tipo) "
					+ "VALUES(current_date,?,?,?,?,?,?,?,?,?,?)");
			jdbcTemplateObject.update(SQL.toString(), titulos.getDataVencimento(), titulos.getCpfCnpj(),
					titulos.getNumero(), titulos.getValorTitulo(), titulos.getValorJuros(),
					titulos.getValorJurosCalculado(), titulos.getValorDesconto(), titulos.getDataPagamento(),
					titulos.getValorPago(), titulos.getTipo());
			} else {
				SQL.append("INSERT INTO titulos(data_criacao, data_vencimento, cpf_cnpj, valor_titulo, "
						+ "valor_juros, valor_juros_calculado, valor_desconto, data_pagamento, valor_pago, tipo) "
						+ "VALUES(current_date,?,?,?,?,?,?,?,?,?)");
				jdbcTemplateObject.update(SQL.toString(), titulos.getDataVencimento(), titulos.getCpfCnpj(),
						 titulos.getValorTitulo(), titulos.getValorJuros(),titulos.getValorJurosCalculado(), 
						 titulos.getValorDesconto(), titulos.getDataPagamento(),
						titulos.getValorPago(), titulos.getTipo());
			}

			
			log.info("Título cadastrado com sucesso!");
			return true;
		} catch (Exception e) {
			log.error("Erro ao cadastrar título. " + e);
			return false;

		}
	}

	public Boolean alterar(Titulos titulos) {

		try {
			StringBuffer SQL = new StringBuffer();

			SQL.append("UPDATE titulos SET data_vencimento = ?, cpf_cnpj = ?, valor_titulo = ?, "
					+ "valor_juros = ?, valor_juros_calculado = ?, valor_desconto = ?, data_pagamento = ?, valor_pago = ?, tipo = ? WHERE numero = ?");

			jdbcTemplateObject.update(SQL.toString(), titulos.getDataVencimento(), titulos.getCpfCnpj(),
					titulos.getValorTitulo(), titulos.getValorJuros(), titulos.getValorJurosCalculado(),
					titulos.getValorDesconto(), titulos.getDataPagamento(), titulos.getValorPago(), titulos.getTipo(),
					titulos.getNumero());

			
			log.info("Título alterado com sucesso!");
			return true;
		} catch (Exception e) {
			log.error("Erro ao alterar título. " + e);
			return false;

		}
	}

	public Boolean excluirContaPagar(Titulos titulos) {

		try {

			String sql = "DELETE FROM titulos WHERE tipo = ? AND numero = ?";
			jdbcTemplateObject.update(sql, titulos.getTipo(), titulos.getNumero());
			
			log.info("Título excluido com sucesso!");
			return true;
		} catch (Exception e) {
			log.error("Erro ao excluir título. " + e);
			return false;

		}
	}

	public Titulos consultar(Integer numero) {

		try {

			String sql = "SELECT * FROM titulos WHERE numero = ?";
			Titulos titulos = jdbcTemplateObject.queryForObject(sql, new Object[] { numero }, new TitulosMapper());
		
			log.info("Títudo consultado com sucesso!");
			return titulos;

		} catch (Exception e) {
			log.error("Erro ao consultar título " + e);

			return new Titulos();
		}
	}

	public List<Titulos> filtrarTitulos(String filtro, String tipo) {

		try {

			String sql = null;
			
			if (filtro.equals("abertos")) {
				 sql = "SELECT * FROM titulos WHERE tipo = '"+tipo+"' AND (valor_pago = 0 OR valor_pago IS NULL)";
			}
			
			if (filtro.equals("pagos")) {
				 sql = "SELECT * FROM titulos WHERE tipo = '"+tipo+"' AND valor_pago != 0 AND valor_pago IS NOT NULL";
			}
			
			if (filtro.equals("todos")) {
				 sql = "SELECT * FROM titulos WHERE tipo = '"+tipo+"' ";
			}

			List<Titulos> titulos = jdbcTemplateObject.query(sql, new TitulosMapper());

			log.info("Títulos filtrados com sucesso!");
			return titulos;

		} catch (Exception e) {
			log.error("Erro ao listar títulos filtrados. " + e);
		
			return new ArrayList<Titulos>();
		}
	}
	
	public List<Titulos> listarPorTipo(String tipo) {

		try {

			String sql = "SELECT * FROM titulos WHERE tipo = ?";
			List<Titulos> titulos = jdbcTemplateObject.query(sql, new Object[] { tipo }, new TitulosMapper());

			log.info("Títulos consultados com sucesso!");
			return titulos;

		} catch (Exception e) {
			log.error("Erro ao listar títulos por tipo. " + e);
		
			return new ArrayList<Titulos>();
		}
	}

	public List<Titulos> listarPorFiltros(FiltrarTitulos filtros) {

		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT * FROM titulos WHERE 1=1 ");
			if (filtros.getDataCriacaoInicial() != null && filtros.getDataCriacaoFinal() != null) {
				SQL.append(" AND data_criacao BETWEEN '" + filtros.getDataCriacaoInicial() + "' AND '"
						+ filtros.getDataCriacaoFinal() + "'");
			} else {
				if (filtros.getDataCriacaoInicial() != null) {
					SQL.append(" AND data_criacao >= '" + filtros.getDataCriacaoInicial() + "'");
				}
				if (filtros.getDataCriacaoFinal() != null) {
					SQL.append(" AND data_criacao <= '" + filtros.getDataCriacaoFinal() + "'");
				}
			}
			if (filtros.getNumero() != null && filtros.getNumero() >= 1) {
				SQL.append(" AND numero = " + filtros.getNumero());
			}
			if (!isNullOrEmpty(filtros.getCpfCnpj())) {
				SQL.append(" AND cpf_cnpj = '" + filtros.getCpfCnpj() + "'");
			}
			if (filtros.getDataVencimentoInicial() != null && filtros.getDataVencimentoFinal() != null) {
				SQL.append(" AND data_vencimento BETWEEN '" + filtros.getDataVencimentoInicial() + "' AND '"
						+ filtros.getDataVencimentoFinal() + "'");
			} else {
				if (filtros.getDataVencimentoInicial() != null) {
					SQL.append(" AND data_vencimento >= '" + filtros.getDataVencimentoInicial() + "'");
				}
				if (filtros.getDataVencimentoFinal() != null) {
					SQL.append(" AND data_vencimento <= '" + filtros.getDataVencimentoFinal() + "'");
				}
			}
			if (filtros.getValorTituloInicial() != null && filtros.getValorTituloFinal() != null) {
				SQL.append(" AND valor_titulo BETWEEN " + filtros.getValorTituloInicial() + " AND "
						+ filtros.getValorTituloFinal());
			} else {
				if (filtros.getValorTituloInicial() != null) {
					SQL.append(" AND valor_titulo >= " + filtros.getValorTituloInicial());
				}
				if (filtros.getValorTituloFinal() != null) {
					SQL.append(" AND valor_titulo <= " + filtros.getValorTituloFinal());
				}
			}
			SQL.append(" AND tipo = '" + filtros.getTipo() + "'");
		

			List<Titulos> titulos = jdbcTemplateObject.query(SQL.toString(), new TitulosMapper());

			log.info("Títulos consultados com sucesso!");
			return titulos;

		} catch (Exception e) {
			log.error("Erro ao listar títulos filtrados. " + e);
			
			return new ArrayList<Titulos>();
		}
	}

	public List<Titulos> listar() {

		try {

			String sql = "SELECT * FROM titulos";
			List<Titulos> titulos = jdbcTemplateObject.query(sql, new TitulosMapper());
			log.info("Títulos consultados com sucesso!");
			return titulos;
		} catch (Exception e) {
			log.error("Erro ao listar títulos. " + e);

			return new ArrayList<Titulos>();
		}
	}

	public List<Titulos> listarPorCpfCnpj(String cpfCnpj) {

		try {

			String sql = "SELECT * FROM titulos WHERE cpf_cnpj = ?";
			List<Titulos> titulos = jdbcTemplateObject.query(sql, new Object[] { cpfCnpj }, new TitulosMapper());

			log.info("Títulos consultados com sucesso!");
			return titulos;

		} catch (Exception e) {
			log.error("Erro ao listar títulos por CPF/CNPJ. " + e);
			
			return new ArrayList<Titulos>();
		}
	}
}
