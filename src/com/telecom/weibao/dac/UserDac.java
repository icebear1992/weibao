package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.TelecomPersonnel;

public class UserDac {
	
	static class MPRowMapper implements RowMapper<ManufacturerPersonnel>{
		@Override
		public ManufacturerPersonnel mapRow(ResultSet rs, int rowNum) throws SQLException {
			ManufacturerPersonnel mp=new ManufacturerPersonnel();
			mp.setMpId(rs.getLong("mp_id"));
			mp.setMpPhone(rs.getString("mp_phone"));
			mp.setMpName(rs.getString("mp_name"));
			mp.setMpRole(rs.getInt("mp_role"));
			mp.getManufacturer().setManufacturerId(rs.getLong("manufacturer_id"));
			mp.getManufacturer().setManufacturerName(rs.getString("manufacturer_name"));
			return mp;
		}		
	}
	
	//如果返回是null，代表厂家登陆失败
	public static ManufacturerPersonnel getManufacturerPersonnel(String account,String password){
		String sql="SELECT mp_id,mp_phone,mp_name,mp_role,mp.manufacturer_id,manufacturer_name "
				+"FROM manufacturerpersonnels mp,manufacturers m WHERE mp.manufacturer_id=m.manufacturer_id "
				+"AND mp_phone=? AND mp_password=?";
		List<ManufacturerPersonnel> mpList=Dac.getJdbcTemplate().query(sql,new Object[]{account,password},new MPRowMapper());
		if(mpList.size()>0) return mpList.get(0);
		return null;		
	}
	
	
	
	static class TPRowMapper implements RowMapper<TelecomPersonnel>{
		@Override
		public TelecomPersonnel mapRow(ResultSet rs, int rowNum) throws SQLException {
			TelecomPersonnel tp=new TelecomPersonnel();
			tp.setTpId(rs.getLong("tp_id"));
			tp.setTpPhone(rs.getString("tp_phone"));
			tp.setTpName(rs.getString("tp_name"));
			tp.setTpRole(rs.getInt("tp_role"));
			tp.getSubRegion().setSubRegionId(rs.getLong("subregion_id"));
			return tp;
		}
		
	}
	//如果返回是null，代表电信人员登陆失败
	public static TelecomPersonnel getTelecomPersonnel(String account,String password){
		String sql="SELECT tp_id,tp_phone,tp_name,tp_role,subregion_id "
				+"FROM TelecomPersonnels WHERE tp_phone=? AND tp_password=?";
		List<TelecomPersonnel> tpList=Dac.getJdbcTemplate().query(sql, new Object[]{account,password},new TPRowMapper());
		if(tpList.size()>0) return tpList.get(0);
		return null;
	}

}
