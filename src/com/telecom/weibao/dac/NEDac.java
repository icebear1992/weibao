package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.telecom.weibao.entity.Major;
import com.telecom.weibao.entity.NE;
import com.telecom.weibao.entity.NEModel;
import com.telecom.weibao.entity.Network;

public class NEDac {
	
	static class NERowMapper implements RowMapper<NEModel>{
		@Override
		public NEModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			NEModel neModel=new NEModel();
			neModel.setNeModelId(rs.getLong("neModel_id"));
			neModel.setNeModelName(rs.getString("neModel_name"));
			
			neModel.getManufacturer().setManufacturerId(rs.getLong("manufacturer_id"));
			neModel.getManufacturer().setManufacturerName(rs.getString("manufacturer_name"));
			
			neModel.getNe().setNeId(rs.getLong("ne_id"));
			neModel.getNe().setNeName(rs.getString("ne_name"));
			
			neModel.getNe().getNetwork().setNetworkId(rs.getLong("network_id"));
			neModel.getNe().getNetwork().setNetworkName(rs.getString("network_name"));
			
			neModel.getNe().getNetwork().getMajor().setMajorId(rs.getLong("major_id"));
			neModel.getNe().getNetwork().getMajor().setMajorName(rs.getString("major_name"));			
			
			return neModel;
		}		
	}
	
	//获取从专业到网元型号的具体信息
	public List<Major> getAllMajorDetails(){
		String sql = "SELECT nm.nemodel_id,nm.nemodel_name,mf.manufacturer_id,mf.manufacturer_name,n.ne_id,n.ne_name,nw.network_id,nw.network_name,m.major_id,m.major_name "
				+ "FROM nemodels nm,manufacturers mf,nes n,networks nw,majors m "
				+ "WHERE nm.manufacturer_id=mf.manufacturer_id " 
				+ "AND nm.ne_id=n.ne_id "
				+ "AND n.network_id=nw.network_id " 
				+ "AND mw.major_id=m.major_id";
		List<NEModel> nemList=Dac.getJdbcTemplate().query(sql,new NERowMapper());
		
		Map<Long,NE> neMap=new HashMap<Long,NE>();
		List<NE> neList=new ArrayList<NE>();
		for(NEModel nem: nemList){
			long neId=nem.getNe().getNeId();
			if(!neMap.containsKey(neId)){
				neMap.put(neId, nem.getNe());
				neList.add(nem.getNe());
			}
			neMap.get(neId).getNeModelList().add(nem);
		}
		Map<Long,Network> nwMap=new HashMap<Long,Network>();
		List<Network> nwList=new ArrayList<Network>();
		for(NE ne: neList){
			long nwId=ne.getNetwork().getNetworkId();
			if(!nwMap.containsKey(nwId)){
				nwMap.put(nwId, ne.getNetwork());
				nwList.add(ne.getNetwork());
			}
			nwMap.get(nwId).getNeList().add(ne);
		}
		Map<Long,Major> majorMap=new HashMap<Long,Major>();
		List<Major> majorList=new ArrayList<Major>();
		for(Network nw: nwList){
			long majorId=nw.getMajor().getMajorId();
			if(!majorMap.containsKey(majorId)){
				majorMap.put(majorId, nw.getMajor());
				majorList.add(nw.getMajor());
			}
			majorMap.get(majorId).getNetworkList().add(nw);
		}		
		return majorList;
	}
	
	//根据专业ID获取NetWork
	public static List<Network> getNetworksByMajorId(long majorId){
		String sql="SELECT network_id networkId,network_name networkName FROM networks WHERE major_id=?";
		return Dac.getJdbcTemplate().query(sql,new Object[]{majorId},new BeanPropertyRowMapper<Network>(Network.class));
	}
	
	//根据NetWorkID获取NE
	public static List<NE> getNEsByNetworkId(long netWorkId){
		String sql="SELECT ne_id neId,ne_name neName FROM nes WHERE network_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{netWorkId},new BeanPropertyRowMapper<NE>(NE.class));
	}
	
	//根据NEID获取NEModel
	public static List<NEModel> getNEModelsByNEId(long neId){
		String sql="SELECT nemodel_id neModelId,nemodel_name neModelName FROM nemodels WHERE ne_id=?";
		return Dac.getJdbcTemplate().query(sql,new Object[]{neId},new BeanPropertyRowMapper<NEModel>(NEModel.class));
	}
	
	//判断nemodel是不是某个专业的
	public static boolean checkNeModelBelongMajor(long neModelId,long majorId){
		String sql="SELECT count(*) FROM nemodels nm,nes ne,networks nw WHERE nm.nemodel_id=? AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id AND nw.major_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{neModelId,majorId},Long.class)==0)
			return false;
		return true;
	}
	
	
}
