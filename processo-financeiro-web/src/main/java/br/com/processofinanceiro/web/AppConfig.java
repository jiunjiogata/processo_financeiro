package br.com.processofinanceiro.web;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zaxxer.hikari.HikariDataSource;
 
import liquibase.integration.spring.SpringLiquibase;

@Configuration
@ComponentScan("br.com")
@EnableWebMvc
public class AppConfig {
	
	private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

	@Bean 
	public DataSource dataSource() {
		HikariDataSource ds = new HikariDataSource();
		ds.setDriverClassName("org.postgresql.ds.PGSimpleDataSource");
		ds.setJdbcUrl("jdbc:postgresql://localhost:5432/processofinanceiro");
		ds.setUsername("postgres");
		ds.setPassword("postgres");
		ds.setConnectionTimeout(15000);
		
		return ds;
	}
	
	
	@Bean(name = "liquibase")
	public SpringLiquibase liquibase(DataSource dataSource) {
		log.debug("Configurando Liquibase");
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:master.xml");
		return liquibase;
	}
}
