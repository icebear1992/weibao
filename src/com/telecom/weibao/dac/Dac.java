package com.telecom.weibao.dac;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;



public class Dac {
    private Dac(){}
	
	private static JdbcTemplate jdbcTemplate =null;
	private static TransactionTemplate transactionTemplate = null;
	
	public static JdbcTemplate getJdbcTemplate(){
		return jdbcTemplate;
	}
	
	public static void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		Dac.jdbcTemplate=jdbcTemplate;
	}

	public static TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public static void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		Dac.transactionTemplate = transactionTemplate;
	}
}
