package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.telecom.weibao.entity.Major;

public class MajorDac {
	
	static class MajorRowMapper implements RowMapper<Major>{
		@Override
		public Major mapRow(ResultSet rs, int rowNUm) throws SQLException {
			Major major=new Major();
			major.setMajorId(rs.getLong("major_id"));
			major.setMajorName(rs.getString("major_name"));
			return major;
		}		
	}
	
	//根据专业名获取专业
	public static Major getMajorByName(String majorName){
		String sql="SELECT major_id,major_name FROM majors "
				+"WHERE major_name=?";
		List<Major> MjList =  Dac.getJdbcTemplate().query(sql, new Object[]{majorName},new MajorRowMapper());
		if(MjList.size()>0) return MjList.get(0);
		return null;
	}
}
