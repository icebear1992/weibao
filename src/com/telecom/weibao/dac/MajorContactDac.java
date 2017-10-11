package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.telecom.weibao.entity.MajorContact;

public class MajorContactDac {
	
	static class MajorContactRowMapper implements RowMapper<MajorContact>{
		@Override
		public MajorContact mapRow(ResultSet rs, int rowNUm) throws SQLException {
			MajorContact mc=new MajorContact();
			mc.getTp().setTpId(rs.getLong("tp_id"));
			mc.getTp().setTpName(rs.getString("tp_name"));
			return mc;
		}		
	}
	
	//根据专业ID和子区域ID获取专业接口人
	public static List<MajorContact> getMajorContacts(long majorId,long subRegionId){
		String sql="SELECT m.tp_id,t.tp_name FROM majorcontacts m,telecompersonnels t "
				+"WHERE m.tp_id=t.tp_id AND m.major_id=? AND m.subregion_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{majorId,subRegionId},new MajorContactRowMapper());
	}
}
