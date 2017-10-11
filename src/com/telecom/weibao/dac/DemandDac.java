package com.telecom.weibao.dac;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.telecom.weibao.entity.DemandCategory;

public class DemandDac {
	
	public static List<DemandCategory> getAllDemands(){
		String sql="SELECT * FROM demandcategories ORDER BY demand_id";
		return Dac.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<DemandCategory>(DemandCategory.class));
	}

}
