package br.com.processofinanceiro.domain.dao.pessoas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import br.com.processofinanceiro.model.pessoas.Pessoas;

@Component
public class PessoasMapper implements RowMapper<Pessoas> {

	@Override
	public Pessoas mapRow(ResultSet rs, int rowNum) throws SQLException {

		Pessoas pessoas = new Pessoas();

		pessoas.setCpfCnpj(rs.getString("cpf_cnpj"));
		pessoas.setNome(rs.getString("nome"));
		if (rs.getTimestamp("data_nascimento") != null) {
			LocalDate dataLocalDate = converteParaLocalDate(rs.getTimestamp("data_nascimento"));
			pessoas.setDataNascimento(dataLocalDate);
		}
		pessoas.setCep((rs.getString("cep")));
		pessoas.setLogradouro((rs.getString("logradouro")));
		pessoas.setNumero((rs.getInt("numero")));
		pessoas.setBairro((rs.getString("bairro")));
		pessoas.setCidade((rs.getString("cidade")));
		pessoas.setEstado((rs.getString("estado")));

		return pessoas;
	}

	private LocalDate converteParaLocalDate(Timestamp data) {
		LocalDate dateTime = data.toLocalDateTime().toLocalDate();

		return dateTime;
	}
}
