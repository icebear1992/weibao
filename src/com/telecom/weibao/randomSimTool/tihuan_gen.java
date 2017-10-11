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

public class tihuan_gen {
	public static void main(String[] arg) {
		
		
		Workbook wb = null;
		Sheet ws_v2p = null;
		Sheet ws_t2p = null;
		Sheet ws_misc1 = null;
		Sheet ws_misc2 = null;
		Sheet ws_misc4 = null;
		Sheet ws_misc5 = null;
		Sheet ws_misc6 = null;
		Sheet ws_out = null;

		List<List<String>> soc_tab = new ArrayList<List<String>>();
		Map<String, List> peo_map = new HashMap<String, List>();

		try {
			wb = new HSSFWorkbook(new FileInputStream("D:\\crt_hist\\io\\test_tab_tihuan.xls"));
			ws_v2p = wb.getSheet("v2p");
			ws_t2p = wb.getSheet("t2p");
			ws_misc1 = wb.getSheet("misc1");
			ws_misc2 = wb.getSheet("misc2");
			ws_misc4 = wb.getSheet("misc4");
			ws_misc5 = wb.getSheet("misc5");//服务支撑类型
			ws_misc6 = wb.getSheet("misc6");
			ws_out = wb.getSheet("替换信息");
			
			
			List<List> list_v2p = get_tab_lst(ws_v2p,5);
			List<List> list_t2p = get_tab_lst(ws_t2p,4);
			List<List> list_misc1 = get_tab_lst(ws_misc1,2);
			List<List> list_misc2 = get_tab_lst(ws_misc2,3);
			List<List> list_misc4 = get_tab_lst(ws_misc4,2);
			List<List> list_misc5 = get_tab_lst(ws_misc5,4);
			List<List> list_misc6 = get_tab_lst(ws_misc6,3);
			
			//System.out.print(list_v2p);
			
			//录入权重和字串
			Map<String, Integer> cl_zhuanye_map = new HashMap<String, Integer>();
			for (List s:list_misc1){
				cl_zhuanye_map.put(s.get(0).toString(),Float.valueOf(s.get(1).toString()).intValue());
			}
			
			
			for(int i = 1 ; i < 800 ; i++){
			System.out.println(i);
				
			/////开始生成
			Row rowc = ws_out.createRow(i);
			
			/////生成专业后写入
			String cl_ZhuanYe_str = Rand_map(cl_zhuanye_map);//生成专业
			rowc.createCell(1).setCellValue(cl_ZhuanYe_str);
			
			/////生成对应的网络和网元后写入
			
			
			Map<List, Integer> cl_wangluo_map = new HashMap<List, Integer>();
			for(List a:list_misc2){
				if (a.get(0).equals(cl_ZhuanYe_str)){
					List tmp_list_a = new ArrayList();
					tmp_list_a.add(a.get(1));
					tmp_list_a.add(a.get(2));
					cl_wangluo_map.put(tmp_list_a, 1);
				}
			}

			List wangluo_list = Rand_map(cl_wangluo_map);
			String cl_wangluo_str = (String) wangluo_list.get(0);//网络
			String cl_wangyuan_str = (String) wangluo_list.get(1);//网元
			wangluo_list.clear();
			
			rowc.createCell(2).setCellValue(cl_wangluo_str);
			rowc.createCell(3).setCellValue(cl_wangyuan_str);
			
			
			
			//按照网络随机确认子区域,写入
			Map<String, Integer> cl_subreg_map = new HashMap<String, Integer>();
			
			for (List s:list_v2p){
				if (s.get(2).equals(cl_wangluo_str)){
					cl_subreg_map.put((String) s.get(0), 1);
				}
			}
			
			String cl_subreg_str = Rand_map(cl_subreg_map);
			rowc.createCell(0).setCellValue(cl_subreg_str);
			
			
			//更具网元确认故障硬件：
			Map<String, Integer> cl_yingjian_map = new HashMap<String, Integer>();
			for (List s:list_misc4){
				if (s.get(0).equals(cl_wangyuan_str)){
					cl_yingjian_map.put((String) s.get(1), 1);
				}
			}
			String cl_yingjian_str = Rand_map(cl_yingjian_map);
			rowc.createCell(4).setCellValue(cl_yingjian_str);		
			

			//按照网络、区域随机确认厂家人员和厂家
			Map<List, Integer> cl_changjia_map = new HashMap<List, Integer>();
			
			for (List s:list_v2p){
				if (s.get(2).equals(cl_wangluo_str)&&s.get(0).equals(cl_subreg_str)){
					List temp_list_b = new ArrayList();
					temp_list_b.add((String) s.get(3));
					temp_list_b.add((String) s.get(4));
					cl_changjia_map.put(temp_list_b, 1);
				}
			}
			List cl_changjia_list = Rand_map(cl_changjia_map);
			String cl_changjia_str = (String) cl_changjia_list.get(0);
			String cl_changjiaren_str = (String) cl_changjia_list.get(1);
			
			rowc.createCell(5).setCellValue(cl_changjia_str);//写入厂家
			rowc.createCell(8).setCellValue(cl_changjiaren_str);//写入处理人
			rowc.createCell(9).setCellValue(cl_changjiaren_str);//写入录入人
			
			
			
			//根据专业和区域确认电信人员
			Map<String, Integer> cl_telpsn_map = new HashMap<String, Integer>();
			
			for (List s:list_t2p){
				if (s.get(1).equals(cl_ZhuanYe_str) && s.get(0).equals(cl_subreg_str)){
					cl_telpsn_map.put((String) s.get(3), 1);
				}
			}
						
			String cl_telpsn_str = Rand_map(cl_telpsn_map);
			rowc.createCell(10).setCellValue(cl_telpsn_str);
			
			
			//随机开始时间、结束时间
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
			Date start_time = rand_time("2015/01/01","2016/12/31");
			String start_time_Str = sdf.format(start_time);  
			
			Date end_time = rand_time_alt(start_time,24*15);//结束时间认为有15天随机
			String end_time_Str = sdf.format(end_time);  

			rowc.createCell(6).setCellValue(start_time_Str);
			rowc.createCell(7).setCellValue(end_time_Str);			
			
			//按照网元随机生成替换前后的序列号
			
			String sn_head_str = "";
			Integer sn_len_str = 1;
			String cl_orisn_str = "";
			String cl_newsn_str = "";
			
			for(List s:list_misc6){
				if (s.get(0).equals(cl_wangyuan_str)){
					sn_head_str = (String) s.get(1);
					sn_len_str = Double.valueOf(s.get(2).toString()).intValue();
					
				}
			}
			
			cl_orisn_str = sn_head_str ;
			cl_newsn_str = sn_head_str ;
			
			for (int j = sn_len_str; --j > 0;){
				cl_orisn_str = cl_orisn_str + String.valueOf(new Random().nextInt(10));
				cl_newsn_str = cl_newsn_str + String.valueOf(new Random().nextInt(10));
			}
			
			rowc.createCell(12).setCellValue(cl_orisn_str);
			rowc.createCell(13).setCellValue(cl_newsn_str);			

			
			
			wb.write(new FileOutputStream("D:\\crt_hist\\io\\test_tab_tihuan.xls"));
			
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	
}
