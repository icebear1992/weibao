package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.telecom.weibao.dac.SERReviewDac.SERReviewRowMapper;
import com.telecom.weibao.entity.WbChange;
import com.telecom.weibao.entity.WbChange;

public class WbChangeDac {
	static class WbChangeRowMapper implements RowMapper<WbChange>{

		@Override
		public WbChange mapRow(ResultSet rs, int rowNum) throws SQLException {
			WbChange WbChange=new WbChange();
			WbChange.getChanger().setManufacturerName(rs.getString("mp_name"));
			WbChange.setChangeContent(rs.getString("change_content"));
			WbChange.setChangeTime(rs.getTimestamp("change_time"));
			System.out.println(WbChange);
			return WbChange;
		}
	}

	public static List<WbChange> getChangesByWbId(long serId) {
		String sql = "select mp_name,change_content,change_time from WBCHANGES w,MANUFACTURERPERSONNELS m where w.CHANGER_ID=m.MP_ID and w.WB_ID=? order by change_time";
		System.out.println("执行数据库"+serId);
		return Dac.getJdbcTemplate().query(sql, new Object[]{serId},new WbChangeRowMapper());
	}
}
