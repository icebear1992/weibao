package com.telecom.weibao.dac;

import java.util.List;
import java.util.Map;

import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.TelecomPersonnel;

//用于处理涉及多种服务类型的查询
public class MainStatisticsDac {
    //月度报表，按区域
	public static List<Map<String,Object>> getMonthRegionInfos(String beginTime,String endTime, String region){
		String sql = null;
		if(region==null){
			region = "0";
		}
		if(region.equals("0")){
			sql="SELECT time,region_name,sum(serpersonnel) serpersonnel,sum(totalequipment) totalequipment,sum(totalenginerroom) totalenginerroom,sum(trainingduration) trainingduration,sum(consultation) consultation,sum(completion) completion,sum(incompletion) incompletion,sum(ssum) ssum,sum(inssum) inssum,max(avgtime) avgtime,sum(sprcount) sprcount FROM monthregioninfo WHERE to_number(time)>=to_number(?) AND to_number(time)<=to_number(?) GROUP BY time,region_name ORDER BY time";
			 return Dac.getJdbcTemplate().queryForList(sql,new Object[]{beginTime,endTime});
		}else{
			sql="SELECT time,region_name,sum(serpersonnel) serpersonnel,sum(totalequipment) totalequipment,sum(totalenginerroom) totalenginerroom,sum(trainingduration) trainingduration,sum(consultation) consultation,sum(completion) completion,sum(incompletion) incompletion,sum(ssum) ssum,sum(inssum) inssum,max(avgtime) avgtime,sum(sprcount) sprcount FROM monthregioninfo WHERE to_number(time)>=to_number(?) AND to_number(time)<=to_number(?) AND region_name=? GROUP BY time,region_name ORDER BY time";
			return Dac.getJdbcTemplate().queryForList(sql,new Object[]{beginTime,endTime,region});
		}
	}
	
	//月度报表，按专业
	public static List<Map<String,Object>> getMonthMajorInfos(String beginTime,String endTime, String major){
		String sql = null;
		if(major==null){
			major="0";
		}
		if(major.equals("0")){
			sql="SELECT * FROM monthmajorinfo WHERE to_number(time)>=to_number(?) AND to_number(time)<=to_number(?) ORDER BY time";
			return Dac.getJdbcTemplate().queryForList(sql,new Object[]{beginTime,endTime});
		}else{
			sql="SELECT * FROM monthmajorinfo WHERE to_number(time)>=to_number(?) AND to_number(time)<=to_number(?) AND MAJOR_NAME=? ORDER BY time";
			return Dac.getJdbcTemplate().queryForList(sql,new Object[]{beginTime,endTime,major});
		}
	}
	
	//阅读报表，按厂商
	public static List<Map<String,Object>> getMonthManuInfos(String beginTime,String endTime, String manu){
		String sql = null;
		if(manu==null){
			manu="0";
		}
		if(manu.equals("0")){
			sql="SELECT * FROM monthmanufacturerinfo WHERE to_number(time)>=to_number(?) AND to_number(time)<=to_number(?) ORDER BY time";
			return Dac.getJdbcTemplate().queryForList(sql,new Object[]{beginTime,endTime});
		}else{
			sql="SELECT * FROM monthmanufacturerinfo WHERE to_number(time)>=to_number(?) AND to_number(time)<=to_number(?) AND MANUFACTURER_NAME=? ORDER BY time";
			return Dac.getJdbcTemplate().queryForList(sql,new Object[]{beginTime,endTime,manu});
		}
	}
	
	
	//查询所有服务的满意度占比，目前只写了硬件维修和服务响应
	public static List<Map<String,Object>> getSatisfactionPercent(){
		String sql="SELECT scorelevel,count(*) total FROM ("
				+ "(SELECT s.ser_id id,(case  when resultsatisfaction<=3 then 'LOW' when resultsatisfaction<=4 then 'MIDDLE' else 'HIGH' end) scorelevel "
				+ "FROM sers s,(SELECT s1.ser_id,resultsatisfaction FROM serreviews s1,"
				+ "(SELECT ser_id,max(review_time) review_time FROM serreviews GROUP BY ser_id) s2 "
				+ "WHERE s1.ser_id=s2.ser_id AND s1.review_time=s2.review_time) ss WHERE s.ser_id=ss.ser_id) "
				+ "union "
				+ "(SELECT h.hm_id id,(case  when satisfaction<=3 then 'LOW' when satisfaction<=4 then 'MIDDLE' else 'HIGH' end) scorelevel "
				+ "FROM hms h,(SELECT h1.hm_id,satisfaction FROM hmreviews h1,"
				+ "(SELECT hm_id,max(review_time) review_time FROM hmreviews GROUP BY hm_id) h2 "
				+ "WHERE h1.hm_id=h2.hm_id AND h1.review_time=h2.review_time) hs WHERE h.hm_id=hs.hm_id)"
				+ ") GROUP BY scorelevel";
		return Dac.getJdbcTemplate().queryForList(sql);
	}
	
	
	//获取电信人员待点评数量
	public static int getNonReviewedCount(TelecomPersonnel tp){
		int hmNon=HMDac.getNonReviewedHMCount(tp);
		int serNon=SERDac.getNonReviewedSERCount(tp);
		int ssNon=SSDac.getNonReviewedSSCount(tp);
		
		return hmNon+serNon+ssNon;
	}
	//获取电信人员待审核数量
	public static int getNonAuditedCount(TelecomPersonnel tp){
		int hmNon=HMDac.getNonAuditedHMCount(tp);
		int serNon=SERDac.getNonVerifySERCount(tp);
		int ssNon=SSDac.getNonVerifySSCount(tp);
		
		return hmNon+serNon+ssNon;
	}
	//获取电信人员退回的工单数量
	public static int getReturnCount(TelecomPersonnel tp){
		int hmNon=HMDac.getRelateReturnedHMcount(tp);
		int serNon=SERDac.getRelateReturnedSERcount(tp);
		int ssNon=SSDac.getRelateReturnedSScount(tp);
		
		return hmNon+serNon+ssNon;
	}
	//获取电信人员相关未完成的工单数量
		public static int getUncompleteCount(TelecomPersonnel tp){
			int hmNon=HMDac.getRelateUnCompletedHMcount(tp);
			int serNon=SERDac.getRelateUnCompletedSERcount(tp);
			int ssNon=SSDac.getRelateUnCompletedSScount(tp);
			
			return hmNon+serNon+ssNon;
		}
	
	//获取厂商人员被退回数量
	public static int getNonDealedCount(ManufacturerPersonnel mp){
		int hmNon=HMDac.getNonDealedHMCount(mp);
		int serNon=SERDac.getNonDealedSERCount(mp);
		int ssNon=SSDac.getNonDealedSSCount(mp);
		
		return hmNon+serNon+ssNon;
	}
	
	//获取厂商人员未完成数量
		public static int getNonFinCount(ManufacturerPersonnel mp){
			int hmNon=HMDac.getNonFinHMCount(mp);
			int serNon=SERDac.getNonFinSERCount(mp);
			int ssNon=SSDac.getNonFinSSCount(mp);
			
			return hmNon+serNon+ssNon;
		}
		//获取厂商人员待点评数量
		public static int getNonReviewCount(ManufacturerPersonnel mp){
			int hmNon=HMDac.getNonReviewedHMCount(mp);
			int serNon=SERDac.getNonReviewedSERCount(mp);
			int ssNon=SSDac.getNonReviewedSSCount(mp);
			
			return hmNon+serNon+ssNon;
		}
		//获取厂商人员待审核数量
		public static int getNonAuditCount(ManufacturerPersonnel mp){
			int hmNon=HMDac.getNonAuditedHMCount(mp);
			int serNon=SERDac.getNonAuditedSERCount(mp);
			int ssNon=SSDac.getNonAduitedSSCount(mp);
			
			return hmNon+serNon+ssNon;
		}
	
}
