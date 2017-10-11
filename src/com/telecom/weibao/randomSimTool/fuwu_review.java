package com.telecom.weibao.randomSimTool;
//根据xls文档中的约束生成随机服务支撑事件

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.telecom.weibao.dac.Dac;
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.entity.DemandCategory;
import com.telecom.weibao.entity.SER;
import com.telecom.weibao.maintanceTool.SerImportDac;

public class fuwu_review {
	public static void main(String[] arg) {

		ApplicationContext ac = new ClassPathXmlApplicationContext("com/telecom/weibao/maintanceTool/applicationContext.xml");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ac.getBean("jdbcTemplate");
		Dac.setJdbcTemplate(jdbcTemplate); 
		
		String sql ="select * from sers where SER_ID not in(select ser_id from SERREVIEWS)";
		List<Map<String, Object>> noRe_list = Dac.getJdbcTemplate().queryForList(sql);
		System.out.println(noRe_list.get(0).get("end_time"));
		
		for(Map<String, Object> s:noRe_list){
			Timestamp end_time = (Timestamp) s.get("end_time");
			Timestamp creat_time = (Timestamp) s.get("creat_time");
			Timestamp review_time = null;
			
			if(creat_time.getTime()>end_time.getTime()){
				review_time = creat_time;
			}else{
				review_time = end_time;
			}
			
			
			BigDecimal ser_id = (BigDecimal) s.get("ser_id");
			BigDecimal reviewer_id = (BigDecimal) s.get("auditor_id");
			
			String servicesatisfaction = String.valueOf(new Random().nextInt(2)+3);
			String resultsatisfaction = String.valueOf(new Random().nextInt(2)+3);
			String review_content = "满意";
			
			System.out.println(ser_id);
			System.out.println(reviewer_id);
			System.out.println(servicesatisfaction);
			System.out.println(resultsatisfaction);
			System.out.println(review_content);
			System.out.println(review_time);
			
			
			try {
				String sql2 = "INSERT INTO serreviews (review_id,ser_id,reviewer_id,servicesatisfaction,resultsatisfaction,review_content,review_time) "
						+ "VALUES (rewseq.nextval,?,?,?,?,?,?)";
				Dac.getJdbcTemplate().update(sql2, new Object[] {ser_id,reviewer_id,
						servicesatisfaction,resultsatisfaction,review_content,review_time
						});
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
		
		
	
	}
	
	public static List get_tab_lst(Sheet ws,Integer cl_no){
		List ret_tab = new ArrayList();
		for (int i = 1; i <= ws.getLastRowNum(); i++) {
			List tempList = new ArrayList();
			for (int j = 0; j < cl_no; j++) {
				tempList.add(ws.getRow(i).getCell(j).toString());
			}
			ret_tab.add(tempList);
		}
		
		return ret_tab;
	}
	
	public static <T> T Rand_map(Map<T,Integer> map){
		Integer rand_range = 0;
		Integer rand_imp = 0;

		for (Entry a : map.entrySet()) {
			rand_range = rand_range + (Integer)a.getValue();
				
		}	
		
		Float rand_pnt = (new Random().nextFloat())*rand_range;
		
		
		for (Entry a : map.entrySet()) {
			rand_imp = rand_imp + (Integer) a.getValue();
			
			

			if (rand_imp >= rand_pnt){
				
				return (T) a.getKey();
			}
		}
		
		return null;
		
	}
	
	public static Date rand_time(String d1,String d2){
	     DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
	     try {
			long date = random(sdf.parse(d1).getTime(),sdf.parse(d2).getTime());  
			return new Date(date);
		
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		
		return null;
	}

	public static Date rand_time_alt (Date date,long alt_hour){
	     long out_put = date.getTime() + random(0,alt_hour*60*60*1000);
	     return new Date(out_put);
	}
	
	
	private static long random(long begin,long end){ 
		long rtn = begin + (long)(Math.random() * (end - begin)); 
		if(rtn == begin || rtn == end){ 
			return random(begin,end); 
		} 
		return rtn; 
		}  
	
	public static List<Map<String, Object>> getSqlOut(String sql){
		List<Map<String, Object>> List = Dac.getJdbcTemplate().queryForList(sql);
		if (List.size() > 0) return List;
		return null;	
	}

	public static String getSql_rst_Out(String sql){
		List<Map<String, Object>> List = Dac.getJdbcTemplate().queryForList(sql);
		if (List.size() > 0) {
			Map tmp_map = List.get(0);
			if(tmp_map.size()>0){
				for(Entry s:new ArrayList<Entry>(tmp_map.entrySet())){
					return s.getValue().toString();
				};
			}
		}
		return null;	
	}
	
	
}
