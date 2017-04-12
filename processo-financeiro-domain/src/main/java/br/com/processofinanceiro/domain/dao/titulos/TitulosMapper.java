package br.com.processofinanceiro.domain.dao.titulos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import br.com.processofinanceiro.model.titulos.Titulos;

@Component
public class TitulosMapper implements RowMapper<Titulos> {

	@Override
	public Titulos mapRow(ResultSet rs, int rowNum) throws SQLException {

		Titulos titulos = new Titulos();

		LocalDate dataCriacao = converteParaLocalDate(rs.getTimestamp("data_criacao"));
		titulos.setDataCriacao(dataCriacao);
		LocalDate dataVencimento = converteParaLocalDate(rs.getTimestamp("data_vencimento"));
		titulos.setDataVencimento(dataVencimento);
		titulos.setCpfCnpj(rs.getString("cpf_cnpj"));
		titulos.setNumero((rs.getInt("numero")));
		titulos.setValorTitulo((rs.getDouble("valor_titulo")));
		titulos.setValorJuros((rs.getDouble("valor_juros")));
		titulos.setValorJurosCalculado((rs.getDouble("valor_juros_calculado")));
		titulos.setValorDesconto((rs.getDouble("valor_desconto")));
		if (rs.getTimestamp("data_pagamento") != null) {
			LocalDate dataPagamento = converteParaLocalDate(rs.getTimestamp("data_pagamento"));
			titulos.setDataPagamento(dataPagamento);
		}
		titulos.setValorPago((rs.getDouble("valor_pago")));
		titulos.setTipo(rs.getString("tipo"));

		return titulos;
	}

	private LocalDate converteParaLocalDate(Timestamp data) {

		LocalDate dateTime = data.toLocalDateTime().toLocalDate();

		return dateTime;
	}
}