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

public class fuwu_gen {
	public static void main(String[] arg) {
		
		
		Workbook wb = null;
		Sheet ws_v2p = null;
		Sheet ws_t2p = null;
		Sheet ws_misc1 = null;
		Sheet ws_misc2 = null;
		Sheet ws_misc3 = null;
		Sheet ws_out = null;

		List<List<String>> soc_tab = new ArrayList<List<String>>();
		Map<String, List> peo_map = new HashMap<String, List>();

		try {
			wb = new HSSFWorkbook(new FileInputStream("D:\\crt_hist\\io\\test_tab.xls"));
			ws_v2p = wb.getSheet("v2p");
			ws_t2p = wb.getSheet("t2p");
			ws_misc1 = wb.getSheet("misc1");
			ws_misc2 = wb.getSheet("misc2");
			ws_misc3 = wb.getSheet("misc3");//服务支撑类型
			ws_out = wb.getSheet("服务支撑");
			
			
			List<List> list_v2p = get_tab_lst(ws_v2p,5);
			List<List> list_t2p = get_tab_lst(ws_t2p,4);
			List<List> list_misc1 = get_tab_lst(ws_misc1,2);
			List<List> list_misc2 = get_tab_lst(ws_misc2,3);
			List<List> list_misc3 = get_tab_lst(ws_misc3,2);
			
			//System.out.print(list_v2p);
			
			//录入权重和字串
			Map<String, Integer> spe_rand_map = new HashMap<String, Integer>();
			for (List s:list_misc1){
				spe_rand_map.put(s.get(0).toString(),Float.valueOf(s.get(1).toString()).intValue());
			}
			
			for(int i = 1 ; i < 100 ; i++){
			/////开始生成
			String ZhuanYe = Rand_map(spe_rand_map);//生成专业
			
			
			Map<List, Integer> fin_rand_map = new HashMap<List, Integer>();
			for(List a:list_misc2){
				if (a.get(0).equals(ZhuanYe)){
					List tmp_list_a = new ArrayList();
					tmp_list_a.add(a.get(1));
					tmp_list_a.add(a.get(2));
					fin_rand_map.put(tmp_list_a, 1);
				}
			}
			List ZhuanYe_list = Rand_map(fin_rand_map);
			
			String cl_c = (String) ZhuanYe_list.get(0);//网络
			String cl_d = (String) ZhuanYe_list.get(1);//网元
			
			//随机确认子区域
			Map<String, Integer> cl1_rand_map = new HashMap<String, Integer>();
			
			for (List s:list_v2p){
				if (s.get(2).equals(cl_c)){
					cl1_rand_map.put((String) s.get(0), 1);
				}
			}
			
			String cl_a = Rand_map(cl1_rand_map);

			//随机确认厂家人员
			Map<List, Integer> cl8_rand_map = new HashMap<List, Integer>();
			
			for (List s:list_v2p){
				if (s.get(2).equals(cl_c)){
					List c18_t_list = new ArrayList();
					c18_t_list.add((String) s.get(3));
					c18_t_list.add((String) s.get(4));
					cl8_rand_map.put(c18_t_list, 1);
				}
			}
			List cl_h_list = Rand_map(cl8_rand_map);
			String cl_h_1 = (String) cl_h_list.get(0);
			String cl_h = (String) cl_h_list.get(1);
			
			//随机确认电信人员
			Map<String, Integer> cl17_rand_map = new HashMap<String, Integer>();
			
			for (List s:list_t2p){
				if (s.get(1).equals(ZhuanYe) && s.get(0).equals(cl_a)){
					cl17_rand_map.put((String) s.get(3), 1);
				}
			}
						
			String cl_17 = Rand_map(cl17_rand_map);
			
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
			Date start_time = rand_time("2015/01/01","2016/12/31");
			String start_time_Str = sdf.format(start_time);  
			
			Date end_time = rand_time_alt(start_time,72);//结束时间认为有72小时随机
			String end_time_Str = sdf.format(end_time);  
			
			//按照misc3表的类型和权重 随机出随机维保类型 准备写入11列
			Map<String, Integer> misc3_rand_map = new HashMap<String, Integer>();
			for (List s:list_misc3){
				misc3_rand_map.put(s.get(0).toString(),Float.valueOf(s.get(1).toString()).intValue());
			}
			String misc3_rand_rsut = Rand_map(misc3_rand_map);
			
			//填入excel单元格
			Row rowc = ws_out.createRow(i);
			rowc.createCell(0).setCellValue(cl_a);
			rowc.createCell(1).setCellValue(ZhuanYe);
			rowc.createCell(2).setCellValue(cl_c);
			rowc.createCell(3).setCellValue(cl_d);
			rowc.createCell(4).setCellValue(cl_h_1);			
			rowc.createCell(8).setCellValue(cl_h);
			rowc.createCell(17).setCellValue(cl_h);
			rowc.createCell(18).setCellValue(cl_17);
			rowc.createCell(5).setCellValue(start_time_Str);
			rowc.createCell(6).setCellValue(end_time_Str);
			rowc.createCell(12).setCellValue(misc3_rand_rsut);
			
			System.out.println(cl_a + ":"+ ZhuanYe + ":"+ cl_c + ":"+ cl_d + ":"+ cl_h + ":"+ cl_17  +":"+ start_time_Str + " : "+  end_time_Str);
			
			wb.write(new FileOutputStream("D:\\crt_hist\\io\\test_tab.xls"));
			
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
