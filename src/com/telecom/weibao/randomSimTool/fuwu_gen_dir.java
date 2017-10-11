package com.telecom.weibao.randomSimTool;
//根据xls文档中的约束生成随机服务支撑事件

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class fuwu_gen_dir {
	public static void main(String[] arg) {

		ApplicationContext ac = new ClassPathXmlApplicationContext("com/telecom/weibao/maintanceTool/applicationContext.xml");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ac.getBean("jdbcTemplate");
		Dac.setJdbcTemplate(jdbcTemplate); 
		
		
		Map<String, Integer> spe_rand_map = new HashMap<String, Integer>();
		
		List<ArrayList<String>> MaList = new ArrayList<ArrayList<String>>();
		
		MaList.add(new ArrayList(Arrays.asList(new String[]{"传输","3"})));
		MaList.add(new ArrayList(Arrays.asList(new String[]{"数据","6"})));
		MaList.add(new ArrayList(Arrays.asList(new String[]{"交换","5"})));
		MaList.add(new ArrayList(Arrays.asList(new String[]{"接入网","7"})));
		MaList.add(new ArrayList(Arrays.asList(new String[]{"C网核心网","1"})));
		MaList.add(new ArrayList(Arrays.asList(new String[]{"C网无线网","2"})));
		//MaList.add(new ArrayList(Arrays.asList(new String[]{"EPC网核心网","1"})));
		//MaList.add(new ArrayList(Arrays.asList(new String[]{"C网分组域","1"}))); 
		//MaList.add(new ArrayList(Arrays.asList(new String[]{"平台","1"}))); 

		for (List s:MaList){
			spe_rand_map.put(s.get(0).toString(),Float.valueOf(s.get(1).toString()).intValue());
		}
		List<Map<String, Object>> tmp_map;
		
		for(int i = 1 ; i <= 1500 ; i++){
		//System.out.println(i);
			
			
		String ZhuanYe = Rand_map(spe_rand_map);//生成专业
		//从内置权重生成专业
		
		String sql = "select NETWORK_ID from networks where MAJOR_ID in (select major_id from MAJORS where MAJOR_NAME = '"+ZhuanYe+"')";
		Map<Map,Integer> network_map = new HashMap<Map,Integer>();
		
		tmp_map = getSqlOut(sql);
		if (tmp_map == null) continue;
		for(Map s:tmp_map){
			network_map.put(s, 1);
		}
		Long network_id =  Long.valueOf(Rand_map(network_map).get("network_id").toString());
		
		
		//从专业随机出网络类型编号
		
		sql = "select ne_id from nes where NETWORK_ID ="+network_id.toString();
		Map<Map,Integer> ne_map = new HashMap<Map,Integer>();
		
		tmp_map = getSqlOut(sql);
		if (tmp_map == null) continue;
		for(Map s:tmp_map){
			ne_map.put(s, 1);
		}		
		Long ne_id =  Long.valueOf(Rand_map(ne_map).get("ne_id").toString());
		
		//从网络类型随即出网元类型
		sql="select nemodel_id from nemodels where ne_id = "+ne_id;
		Map<Map,Integer> nem_map = new HashMap<Map,Integer>();

		tmp_map = getSqlOut(sql);
		if (tmp_map == null) continue;
		for(Map s:tmp_map){
			nem_map.put(s, 1);
		}		
		Long nemodel_id =  Long.valueOf(Rand_map(nem_map).get("nemodel_id").toString());		
		
		//从网元类型随机出网元型号
		
		sql="select SUBREGION_ID,RESPONSREGION_ID from nenumber where nemodel_id="+nemodel_id;
		Map<Map,Integer> reg_map = new HashMap<Map,Integer>();
		
		tmp_map = getSqlOut(sql);
		if (tmp_map == null) continue;
		for(Map s:tmp_map){
			reg_map.put(s, 1);
		}		
		Long subregion_id =  Long.valueOf(Rand_map(reg_map).get("subregion_id").toString());
		Long responsregion_id =  Long.valueOf(Rand_map(reg_map).get("subregion_id").toString());
		
		
		//从网元型号选取出子区域和区域id		
		
		sql="select mp_id from mpmajor where subregion_id= "+responsregion_id+" and network_id="+network_id;
		Map<Map,Integer> mp_map = new HashMap<Map,Integer>();
		//System.out.println(sql);
		//System.out.println(sql);
		tmp_map = getSqlOut(sql);
		if (tmp_map == null) continue;
		for(Map s:tmp_map) mp_map.put(s, 1);
		Long mp_id =  Long.valueOf(Rand_map(mp_map).get("mp_id").toString());
		
		//获取厂家人员id
		
		sql="select tp_id from MAJORCONTACTS where major_id in(select major_id from majors where major_name = '"+ZhuanYe+"') and subregion_id = "+subregion_id;
		Map<Map,Integer> tp_map = new HashMap<Map,Integer>();
		tmp_map = getSqlOut(sql);
		if (tmp_map == null) continue;
		for(Map s:tmp_map) tp_map.put(s, 1);
		Long tp_id =  Long.valueOf(Rand_map(tp_map).get("tp_id").toString());
		//获取电信人员ID
		
		
		Map<String, Integer> SuKind_rand_map = new HashMap<String, Integer>();
		List<ArrayList<String>> SuKindList = new ArrayList<ArrayList<String>>();
		SuKindList.add(new ArrayList(Arrays.asList(new String[]{"远程","2"}))); 
		SuKindList.add(new ArrayList(Arrays.asList(new String[]{"现场","3"}))); 
		
		for (List s:SuKindList)	SuKind_rand_map.put(s.get(0).toString(),Float.valueOf(s.get(1).toString()).intValue());
		String supportMethod = Rand_map(SuKind_rand_map);

		//获取支撑类型远程还是现场
		
		Map<String, Integer> DmdInf_rand_map = new HashMap<String, Integer>();
		List<ArrayList<String>> DmdInfList = new ArrayList<ArrayList<String>>();
		DmdInfList.add(new ArrayList(Arrays.asList(new String[]{"方案支撑","2"}))); 
		DmdInfList.add(new ArrayList(Arrays.asList(new String[]{"故障查修","4"}))); 
		DmdInfList.add(new ArrayList(Arrays.asList(new String[]{"网络优化","1"}))); 
		DmdInfList.add(new ArrayList(Arrays.asList(new String[]{"隐患排查","1"}))); 
		
		for (List s:DmdInfList)	DmdInf_rand_map.put(s.get(0).toString(),Float.valueOf(s.get(1).toString()).intValue());
		String demandInfo = Rand_map(DmdInf_rand_map);

		//获取需求类型四种	
		
		String demandDegree = (new Integer(new Random().nextInt(3))).toString();
		
		//生成0-3三种需求等级

		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
		Date start_time = rand_time("2014/12/01","2017/06/01");
		String startTimeStr = sdf.format(start_time);  
		
		Date end_time = rand_time_alt(start_time,72);//结束时间认为有72小时随机
		String endTimeStr = sdf.format(end_time);  
		
		Date create_time = rand_time_alt(start_time,96);
		String createTime = sdf.format(create_time);
		
		String totalDuration = Long.toString(((70-new Random().nextInt(20))*(end_time.getTime() - start_time.getTime())/(1000*60*60*100)));
		//开始结束时间、创建时间和总历时
		
		Integer tmp_int = Integer.valueOf(totalDuration);
		if(tmp_int<1) tmp_int = 1;
		String TelDur = String.valueOf(new Random().nextInt(tmp_int));
		String FedDur = String.valueOf(Integer.valueOf(totalDuration) - Integer.valueOf(TelDur));
		
		/**
		System.out.println("---------------------");
		System.out.println(ZhuanYe);
		System.out.println(network_id);
		System.out.println(ne_id);
		System.out.println(nemodel_id);
		System.out.println(subregion_id);
		System.out.println(responsregion_id);
		System.out.println(mp_id);
		System.out.println(tp_id);
		System.out.println(supportMethod);
		System.out.println(demandInfo);
		System.out.println(startTimeStr+" "+endTimeStr+" "+createTime);
		System.out.println(totalDuration);
		System.out.println(TelDur+"::"+FedDur);
		**/
		System.out.println("---------------------");
		
		List out_list = new ArrayList();
		out_list.add(getSql_rst_Out("select subregion_name from subregions where subregion_id ="+subregion_id));//subregionName = list.get(0); "省NOC"              
		out_list.add(getSql_rst_Out("select nemodel_name from nemodels where nemodel_id ="+nemodel_id));//nemodelName = list.get(1);   "ONU_型号b"          
		out_list.add(endTimeStr);//startTimeStr = list.get(2);  "2015/01/02 04:17:27"
		out_list.add(startTimeStr);//endTimeStr = list.get(3);    "2015/01/04 02:05:52"
		out_list.add(totalDuration);//totalDuration = list.get(4); "16"                 
		out_list.add(getSql_rst_Out("select mp_name from MANUFACTURERPERSONNELS where mp_id="+mp_id));//MHName = list.get(5);        "王烈"               
		out_list.add(getSql_rst_Out("select mp_phone from MANUFACTURERPERSONNELS where mp_id="+mp_id));//MHphone = list.get(6);       "133805501131"       
		out_list.add(supportMethod);//supportMethod = list.get(7); "现场"               
		out_list.add(TelDur);//TelDur = list.get(8);        "8"                  
		out_list.add(FedDur);//FedDur = list.get(9);        "8"                  
		out_list.add(demandInfo);//demandInfo = list.get(10);   "故障查修"           
		out_list.add("暂空");//solution = list.get(11);     "补丁升级"           
		out_list.add(demandDegree);//demandDegree = list.get(12); "3"                  
		out_list.add("暂空");//remarkInfo = list.get(13);   "无"                 
		out_list.add(getSql_rst_Out("select mp_name from MANUFACTURERPERSONNELS where mp_id="+mp_id));//createrName = list.get(14);  "阮绍祺"             
		out_list.add(getSql_rst_Out("select mp_name from MANUFACTURERPERSONNELS where mp_id="+mp_id));//mpName = list.get(15);       "殷子昂"             
		out_list.add(getSql_rst_Out("select tp_name from TELECOMPERSONNELS where tp_id="+tp_id));//auditorName = list.get(16);  "阮绍祺"             
		out_list.add(createTime);//createTime = list.get(17);   "2015/01/02 09:17:27"
		out_list.add("归档");//curProcessStr = list.get(18);"归档"               
		
		
		SER ser = SerImportDac.ListToSER(out_list);
		System.out.println(ser);
		SERDac.addSER(ser);
		
		
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
