package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.telecom.weibao.entity.HMInfo;


public class HMInfoDac {
	
	static class HMInfoRowMapper implements RowMapper<HMInfo>{
		@Override
		public HMInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			HMInfo hmInfo=new HMInfo();
			hmInfo.getNeModel().setNeModelId(rs.getLong("nemodel_id"));
			hmInfo.getNeModel().setNeModelName(rs.getString("nemodel_name"));
			hmInfo.getNeModel().getNe().setNeId(rs.getLong("ne_id"));
			hmInfo.getNeModel().getNe().setNeName(rs.getString("ne_name"));
			hmInfo.getNeModel().getNe().getNetwork().setNetworkId(rs.getLong("network_id"));
			hmInfo.getNeModel().getNe().getNetwork().setNetworkName(rs.getString("network_name"));
			hmInfo.setTotalNumber(rs.getInt("total_num"));
			hmInfo.setRepairNumber(rs.getInt("repair_num"));
			return hmInfo;
		}		
	}
	
	//根据hmId获取HMInfo详情
	public static List<HMInfo> getHMInfosByHMId(long hmId){
		String sql="SELECT h.nemodel_id,nm.nemodel_name,nm.ne_id,ne.ne_name,ne.network_id,nw.network_name,h.total_num,h.repair_num FROM hminfos h,nemodels nm,nes ne,networks nw "
				+"WHERE h.nemodel_id=nm.nemodel_id AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id AND h.hm_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{hmId},new HMInfoRowMapper());
	}
	
	//添加一条HMInfo
	public static void addHMinfo(long hmId,HMInfo hmInfo){
		String sql="INSERT INTO hminfos (hminfo_id,hm_id,nemodel_id,serial_number,total_num,repair_num)"
				+"VALUES (recseq.nextval,?,?,?,?,?)";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				hmId,hmInfo.getNeModel().getNeModelId(),
				hmInfo.getSerialNumber(),hmInfo.getTotalNumber(),hmInfo.getRepairNumber()
				});
	}
	
	//根据hmId删除HMInfo
	public static void delHMinfos(long hmId){
		String sql="DELETE hminfos WHERE hm_id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{hmId});
	}

}
