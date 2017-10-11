package com.telecom.weibao.dac;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import com.telecom.weibao.entity.SSInfo;

public class SSInfoDac {
	
	//根据ssId获取SSInfo详情
	public static List<SSInfo> getSSInfosBySSId(long ssId){
		String sql="SELECT sspersonnel_name as sspersonnelname,effective_duration as effectiveduration,service_content as servicecontent,service_place as serviceplace,remarksinfo "
				+"FROM ssinfos WHERE ss_id=? ORDER BY sspersonnel_name";
		return Dac.getJdbcTemplate().query(sql, new Object[]{ssId},new BeanPropertyRowMapper<SSInfo>(SSInfo.class)); 
	}

	//添加一条SSInfo
	public static void addSSInfo(long ssId,SSInfo ssInfo){
		String sql="INSERT INTO ssinfos (ssinfo_id,ss_id,sspersonnel_name,effective_duration,service_content,service_place,remarksinfo)"
				+"VALUES (recseq.nextval,?,?,?,?,?,?)";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				ssId,ssInfo.getSsPersonnelName(),ssInfo.getEffectiveduration(),
				ssInfo.getServiceContent(),ssInfo.getServicePlace(),ssInfo.getRemarksInfo()
		});
	}
	
	//根据ssID删除SSInfo记录
	public static void delSSInfos(long ssId){
		String sql="DELETE ssinfos WHERE ssId=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{ssId});
	}	
	
}
