package com.telecom.weibao.maintanceTool;

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
import com.telecom.weibao.entity.*;




public class MpImportDac {
	
	public static void main(String[] arg){

		ApplicationContext ac = new ClassPathXmlApplicationContext("com/telecom/weibao/maintanceTool/applicationContext.xml");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ac.getBean("jdbcTemplate");
		Dac.setJdbcTemplate(jdbcTemplate); 

/**	此处为增加厂家人信息的测试字段
		List<String> inpList = Arrays.asList(new String[]{"王文","15355111155","华为","录入人"});
		ManufacturerPersonnel Mp = ListToMp(inpList);
		InsertMp(Mp);
*/

/** 此处为删除指定名称厂家人信息的测试字段 	
		String del_name = "王文";
		ManufacturerPersonnel del_Mp =  GetMpByName(del_name);
		DeleteMp(del_Mp);
*/	

	}

	
	
//"名字、电话、厂家、角色"批量导入的时候4个文本段
//角色有“录入人、主管”对应1、0
	public static ManufacturerPersonnel ListToMp(List<String> list){
		if (list.size()!= 4) return null;
		
		Integer mpRole = -1;
		
		if (list.get(3).equals("录入人")) mpRole = 1;
		if (list.get(3).equals("主管")) mpRole = 0;

		
		String MpName = list.get(0);
		String MpPhone = list.get(1);
		String ManufactureName = list.get(2);

		ManufacturerPersonnel mpOutPut=new ManufacturerPersonnel();

		mpOutPut.setMpId(0);
		mpOutPut.setMpName(MpName);
		mpOutPut.setMpPassword("777777");
		mpOutPut.setMpPhone(MpPhone);
		mpOutPut.setMpRole(mpRole);
		mpOutPut.setManufacturer(GetManByName(ManufactureName));
	
		return mpOutPut;
	}

	public static ManufacturerPersonnel GetMpByName(String mpName){
		String sql="SELECT mp_id,mp_phone,mp_password,mp_name,mp_role,manufacturer_id "
				+ "FROM manufacturerpersonnels "
				+ "where mp_name=?";
		List<ManufacturerPersonnel> MpList = Dac.getJdbcTemplate().query(sql,new Object[]{mpName} ,new BeanPropertyRowMapper<ManufacturerPersonnel>(ManufacturerPersonnel.class));
		if (MpList.size()>0) return MpList.get(0);
		return null;
	}

	public static Manufacturer GetManByName(String ManufactureName){
		String sql="SELECT manufacturer_id,manufacturer_name FROM manufacturers "
				+ "where manufacturer_name=?";
		List<Manufacturer> ManList = Dac.getJdbcTemplate().query(sql,new Object[]{ManufactureName} ,new BeanPropertyRowMapper<Manufacturer>(Manufacturer.class));
		if (ManList.size()>0) return ManList.get(0);
		return null;
	}
	
	
	public static void  InsertMp(ManufacturerPersonnel mp){
		String sql="INSERT INTO manufacturerpersonnels (mp_id,mp_phone,mp_password,mp_name,mp_role,manufacturer_id)"
				+"VALUES (mpsec.nextval,?,?,?,?,?)";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				mp.getMpPhone(),mp.getMpPassword(),mp.getMpName(),
				mp.getMpRole(),mp.getManufacturer().getManufacturerId()
				});
	}
	


	public static void  DeleteMp(ManufacturerPersonnel mp){
		String sql="delete from manufacturerpersonnels "
				+"where mp_id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				mp.getMpId()
				});
	}	


}
