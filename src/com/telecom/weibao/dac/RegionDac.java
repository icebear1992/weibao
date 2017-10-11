package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.telecom.weibao.entity.Region;
import com.telecom.weibao.entity.SubRegion;

public class RegionDac {
	
	static class RegionRowMapper implements RowMapper<SubRegion>{
		@Override
		public SubRegion mapRow(ResultSet rs, int rowNum) throws SQLException {
			SubRegion sr=new SubRegion();
			sr.setSubRegionId(rs.getLong("subregion_id"));
			sr.setSubRegionName(rs.getString("subregion_name"));
			sr.getRegion().setRegionId(rs.getLong("region_id"));
			sr.getRegion().setRegionName(rs.getString("region_name"));
			return sr;
		}
	}

	//获取区域、子区域对应关系，包含全区域
	public static List<Region> getAllRegionDetails(){
		String sql="SELECT r.region_id,region_name,subregion_id,subregion_name FROM regions r,subregions s WHERE r.region_id=s.region_id";
		List<SubRegion> subRegionList=Dac.getJdbcTemplate().query(sql, new RegionRowMapper());
		Map<Long,Region> regionMap=new HashMap<Long,Region>();
		List<Region> regionList=new ArrayList<Region>();
		for(SubRegion sr: subRegionList){
			long regionId=sr.getRegion().getRegionId();
			if(!regionMap.containsKey(regionId)){
				regionMap.put(regionId, sr.getRegion());
				regionList.add(sr.getRegion());
			}
			regionMap.get(regionId).getSubRegionList().add(sr);
		}		
		return regionList;		
	}
	//获取区域、子区域对应关系，不包含全区域
	public static List<Region> getRegionsDetails(){
		String sql="SELECT r.region_id,region_name,subregion_id,subregion_name FROM regions r,subregions s WHERE r.region_id=s.region_id "
				+"AND ((r.region_id>10 AND r.region_id*100<>s.subregion_id) OR (r.region_id>0 AND r.region_id<10))";
		List<SubRegion> subRegionList=Dac.getJdbcTemplate().query(sql, new RegionRowMapper());
		Map<Long,Region> regionMap=new HashMap<Long,Region>();
		List<Region> regionList=new ArrayList<Region>();
		for(SubRegion sr: subRegionList){
			long regionId=sr.getRegion().getRegionId();
			if(!regionMap.containsKey(regionId)){
				regionMap.put(regionId, sr.getRegion());
				regionList.add(sr.getRegion());
			}
			regionMap.get(regionId).getSubRegionList().add(sr);
		}		
		return regionList;		
	}
	
	//获取所有子区域,不包含全区域
	public static List<Region> getRegions(){
		String sql="SELECT region_id as regionId,region_name as regionName FROM regions WHERE region_id>0";
		return Dac.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Region>(Region.class));
	}
	
	//根据区域ID，获取子区域信息,不包含全区域
	public static List<SubRegion> getSubRegions(long regionId){
		String sql="SELECT subregion_id as subRegionId,subregion_name as subRegionName FROM subregions "
				+ "WHERE region_id=? AND ((subregion_id>1000 AND subregion_id<>region_id*100) OR subregion_id<1000)";
		return Dac.getJdbcTemplate().query(sql,new Object[]{regionId} ,new BeanPropertyRowMapper<SubRegion>(SubRegion.class));
	}
	
	//根据子区域ID获取区域NAME
	public static String getRegionNameBySubId(long subRegionId){
		String sql="select REGION_NAME from regions r,SUBREGIONS s where s.REGION_ID=r.REGION_ID and s.SUBREGION_ID=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{subRegionId},String.class);
	}
	
	//根据子区域ID获取区域ID
	public static String getRegionIdBySubId(long subRegionId){
		String sql="select s.REGION_ID from regions r,SUBREGIONS s where s.REGION_ID=r.REGION_ID and s.SUBREGION_ID=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{subRegionId},String.class);
	}
}
