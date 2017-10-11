package com.telecom.weibao.dac;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.telecom.weibao.entity.Manufacturer;
import com.telecom.weibao.entity.ManufacturerPersonnel;

public class TelPersonDac {
	
	public static String getAuditorNameByReviewTpId(long tpId){
		String sql="select t2.tp_name from TELECOMPERSONNELS t1,TELECOMPERSONNELS t2 where t1.TPMANAGER_ID=t2.TP_ID and t1.tp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tpId},String.class);
	}
}
