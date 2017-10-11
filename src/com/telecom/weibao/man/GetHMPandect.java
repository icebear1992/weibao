package com.telecom.weibao.man;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.telecom.weibao.dac.HMDac;
import com.telecom.weibao.tools.FileExport;
import com.telecom.weibao.tools.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@WebServlet("/man/GetHMPandect")
public class GetHMPandect extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	//获取ajax请求，返回json数据
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Map<String, Object>> hmList = getHMPandectList(request);			
			if (hmList != null) {
				PrintWriter out = response.getWriter();
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf8");
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonDateValueProcessor());
				jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor());
				JSONArray jsonarr = JSONArray.fromObject(hmList, jsonConfig);
				// new
				int page = Integer.parseInt(request.getParameter("page"));
				int limit = Integer.parseInt(request.getParameter("limit"));
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonout = new JSONArray();
				for (int i = (page - 1) * limit; i < page * limit && i < jsonarr.size(); i++) {
					jsonout.add(jsonarr.get(i));
				}
				jsonObject.put("data", jsonout);
				jsonObject.put("code", 0);
				jsonObject.put("count", jsonarr.size());
				jsonObject.put("msg", "");
				out.print(jsonObject);
				out.close();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//获取POST请求，用于导出文件
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("octets/stream");  
        String excelName = "硬件返修报表";  
        //转码防止乱码  
        response.addHeader("Content-Disposition", "attachment;filename="+new String( excelName.getBytes("gb2312"), "ISO8859-1" )+".xls");  
        String[] headers = new String[]{"事件ID","区域","子区域","专业","厂商","开始时间","结束时间","创建人","创建时间","厂商接口人","电信接口人","当前环节","及时性","满意度","网络","网元","型号","返修数量","修复数量"}; 
        String[] keys = new String[]{"hm_id","region_name","subregion_name","major_name","manufacturer_name","launch_time","completion_time","name","creat_time","mp_name","tp_name","currentprocess","timeliness","satisfaction","network_name","ne_name","nemodel_name","total_num","repair_num"};
        try {  
            OutputStream out = response.getOutputStream();  
            List<Map<String,Object>> hmList=getHMPandectList(request);
            FileExport.exportExcel(excelName, headers, hmList, keys, out, "yyyy-MM-dd"); 
            out.close();    
        } catch (FileNotFoundException e) {  
                e.printStackTrace();  
        } catch (IOException e) {  
                e.printStackTrace();  
        }  
	}
	
	
	private List<Map<String,Object>> getHMPandectList(HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String timeRange = request.getParameter("timeRange");
		System.out.println(timeRange);
		String[] timeRangeSplit=null;
		if(timeRange!=null){
			 timeRangeSplit = timeRange.split("到");
		}
		try {
			long regionId=0;
			try{
				regionId=Long.parseLong(request.getParameter("region"));
			}catch(Exception e){}
			long subregionId=0;
			try{
				subregionId=Long.parseLong(request.getParameter("subregion"));
			}catch(Exception e){}
			long majorId=0;
			try{
				majorId=Long.parseLong(request.getParameter("major"));
			}catch(Exception e){}
			long manufacturerId=0;
			try{
				manufacturerId=Long.parseLong(request.getParameter("manufacturer"));
			}catch(Exception e){}
			String comquery=request.getParameter("comquery");
			if(comquery==null)comquery="";
			String person=request.getParameter("person");
			if(person==null)person="";
			java.util.Date launchUtil = sdf.parse("1970-01-01 00:00");
			try{
				launchUtil=sdf.parse(timeRangeSplit[0].trim());
			}catch(Exception e){}
			java.util.Date completionUtil = sdf.parse("2099-01-01 00:00");
			try{
				completionUtil=sdf.parse(timeRangeSplit[1].trim());
			}catch(Exception e){}
				java.sql.Date launchTime = new java.sql.Date(launchUtil.getTime());
				java.sql.Date completionTime = new java.sql.Date(completionUtil.getTime());
				
				return HMDac.getHMAllPandect(regionId, subregionId, majorId, manufacturerId, launchTime, completionTime,comquery, person);
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
	}

}
