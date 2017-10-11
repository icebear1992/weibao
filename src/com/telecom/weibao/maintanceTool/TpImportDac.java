package com.telecom.weibao.maintanceTool;

//从字符串List生成电信人员实例{"姓名","电话","子区域名","专业名","角色","主管名"}
//ListToTp(List<String> list)
//角色固定写为初审人、复审人、管理员
//主管名可以为空，只有初审人的主管姓名有意义

//从人员实例和专业名 生成专业接口人实例
//TpToMc(TelecomPersonnel tp,String majorName)

//从电信人员实例写入数据库
//InsertTp(TelecomPersonnel tp)

//从专业接口人实例写入数据库
//InsertMc(MajorContact mc)

//按照专业接口人实例删除指定的专业接口人
//DeleteMc(MajorContact mc)

//按照电信账号id获得该人员所有的接口人实例的List
//List<MajorContact> GetMcListByTpId(long TpId)

//按照电信人员实例删除电信人员
//DeleteTp(TelecomPersonnel tp)


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mysql.jdbc.Connection;
import com.telecom.weibao.dac.Dac;
import com.telecom.weibao.dac.MajorDac;
import com.telecom.weibao.entity.*;




public class TpImportDac {
	
	public static void main(String[] arg){
	
	
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/telecom/weibao/maintanceTool/applicationContext.xml");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ac.getBean("jdbcTemplate");
		Dac.setJdbcTemplate(jdbcTemplate); 

/**	此处为增加用户和接口人信息的测试字段
		List<String> inpList = Arrays.asList(new String[]{"大刘","15305516311","省NOC","数据","初审人","文潇"});
		TelecomPersonnel tp = ListToTp(inpList);
		InsertTp(tp);//先增加电信用户
		MajorContact mc = TpToMc(GetTelecomPersonnelByName(inpList.get(0)),inpList.get(3));
		InsertMc(mc);//才能增加接口人信息
*/
		
/** 此处为删除指定名称用户所有信息的测试字段
		String del_name = "大刘";
		TelecomPersonnel del_tp =  GetTelecomPersonnelByName(del_name);
		List<MajorContact> mc_list = GetMcListByTpId(del_tp.getTpId());
		for (MajorContact s:mc_list) DeleteMc(s);
		DeleteTp(del_tp);
 */		
		
		
		
		
		
	}

	
	

	public static TelecomPersonnel ListToTp(List<String> list){
		if (list.size()!= 6) return null;
		
		Integer tpRole = -1;
		
		if (list.get(4).equals("初审人")) tpRole = 2;
		if (list.get(4).equals("复审人")) tpRole = 1;
		if (list.get(4).equals("管理员")) tpRole = 0;
		
		String tpName = list.get(0);
		String tpPhone = list.get(1);
		String subRegionName = list.get(2);
		String majorName = list.get(3);
		String tpManagerName = list.get(5);
		
		if (tpRole != 1) tpManagerName = "";//只有初审人能有上级主管
		
		TelecomPersonnel tpOutput = new TelecomPersonnel();
		
		tpOutput.setTpId(0);
		tpOutput.setTpName(tpName);
		tpOutput.setTpPassword("777777");
		tpOutput.setTpPhone(tpPhone);
		tpOutput.setTpRole(tpRole);
		tpOutput.setSubRegion(GetSubRegionByName(subRegionName));
		tpOutput.setTpManager(GetTelecomPersonnelByName(tpManagerName));
		
		return tpOutput;
	}
	
	//从人员实例和专业名 生成专业接口人条目
	public static MajorContact TpToMc(TelecomPersonnel tp,String majorName){
		MajorContact mcOutput = new MajorContact();
		mcOutput.setMajor(MajorDac.getMajorByName(majorName));
		mcOutput.setSubRegion(tp.getSubRegion());
		mcOutput.setTp(tp);

		return mcOutput;
	}
	
	
	
	public static SubRegion GetSubRegionByName(String subRegionName){
		String sql="SELECT subregion_id as subRegionId,subregion_name as subRegionName FROM subregions "
				+ "where subregion_name=?";
		List<SubRegion> SubRegList = Dac.getJdbcTemplate().query(sql,new Object[]{subRegionName} ,new BeanPropertyRowMapper<SubRegion>(SubRegion.class));
		if (SubRegList.size() > 0) return SubRegList.get(0);
		return null;	
	}

	
	
	public static TelecomPersonnel GetTelecomPersonnelByName(String tpName){
		String sql="SELECT tp_id,tp_phone,tp_name,tp_role,subregion_id,tpmanager_id "
				+"FROM TelecomPersonnels WHERE tp_name=?";
		List<TelecomPersonnel> tpList=Dac.getJdbcTemplate().query(sql, new Object[]{tpName},new TPRowMapper_alt());
		if(tpList.size()>0) return tpList.get(0);
		return null;
	}


	
	public static void  InsertTp(TelecomPersonnel tp){
		String sql="INSERT INTO TELECOMPERSONNELS (tp_id,tp_phone,tp_password,tp_name,subregion_id,tp_role,tpmanager_id)"
				+"VALUES (tpsec.nextval,?,?,?,?,?,?)";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				tp.getTpPhone(),tp.getTpPassword(),tp.getTpName(),tp.getSubRegion().getSubRegionId(),
				tp.getTpRole(),tp.getTpManager().getTpId()
				});
	}
	
	public static void  InsertMc(MajorContact mc){
		String sql="INSERT INTO MajorContacts (subregion_id,major_id,tp_id)"
				+"VALUES (?,?,?)";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				mc.getSubRegion().getSubRegionId(),mc.getMajor().getMajorId(),mc.getTp().getTpId()
				});
	}	

	public static void  DeleteMc(MajorContact mc){
		String sql="delete from MajorContacts "
				+"where subregion_id=? and major_id=? and tp_id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				mc.getSubRegion().getSubRegionId(),mc.getMajor().getMajorId(),mc.getTp().getTpId()
				});
	}	

	public static void  DeleteTp(TelecomPersonnel tp){
		String sql="delete from TELECOMPERSONNELS "
				+"where tp_id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{tp.getTpId()});
	}	
	
	
	public static List<MajorContact> GetMcListByTpId(long TpId){
		String sql="SELECT subregion_id,major_id,tp_id FROM MajorContacts "
				+ "where tp_id=?";
		List<MajorContact> McList = Dac.getJdbcTemplate().query(sql,new Object[]{TpId} ,new BeanPropertyRowMapper<MajorContact>(MajorContact.class));
		return McList;
	}
	
	
	static class TPRowMapper_alt implements RowMapper<TelecomPersonnel>{
		@Override
		public TelecomPersonnel mapRow(ResultSet rs, int rowNum) throws SQLException {
			TelecomPersonnel tp=new TelecomPersonnel();
			tp.setTpId(rs.getLong("tp_id"));
			tp.setTpPhone(rs.getString("tp_phone"));
			tp.setTpName(rs.getString("tp_name"));
			tp.setTpRole(rs.getInt("tp_role"));
			tp.setSubRegion(new SubRegion());
			tp.setTpManager(new TelecomPersonnel());
			tp.getSubRegion().setSubRegionId(rs.getLong("subregion_id"));
			tp.getTpManager().setTpId(rs.getLong("tpmanager_id"));
			return tp;
		}
		
	}
}
