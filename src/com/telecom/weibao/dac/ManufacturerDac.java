package com.telecom.weibao.dac;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.telecom.weibao.entity.Manufacturer;
import com.telecom.weibao.entity.ManufacturerPersonnel;

public class ManufacturerDac {
	
	public static List<Manufacturer> getManufacturers(){
		String sql="SELECT manufacturer_id manufacturerId,manufacturer_name manufacturerName FROM manufacturers";
		return Dac.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Manufacturer>(Manufacturer.class));
	}
	
	public static List<ManufacturerPersonnel> getManuPersonsByManuId(long manuId){
		String sql="SELECT mp_id mpId,mp_name mpName,mp_phone mpPhone FROM manufacturerpersonnels WHERE manufacturer_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{manuId},new BeanPropertyRowMapper<ManufacturerPersonnel>(ManufacturerPersonnel.class));
	}
}
