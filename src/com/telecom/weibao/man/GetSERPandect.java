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


import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.tools.FileExport;
import com.telecom.weibao.tools.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@WebServlet("/man/GetSERPandect")
public class GetSERPandect extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	//获取ajax请求，返回json数据
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			List<Map<String, Object>> serList = getSERPandectList(request);
			if (serList != null) {
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf8");
				PrintWriter out = response.getWriter();
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonDateValueProcessor());
				jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor());
				JSONArray jsonarr = JSONArray.fromObject(serList, jsonConfig);
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
        String excelName = "服务支撑报表";  
        //转码防止乱码  
        response.addHeader("Content-Disposition", "attachment;filename="+new String( excelName.getBytes("gb2312"), "ISO8859-1" )+".xls");  
        String[] headers = new String[]{"事件ID","区域","子区域","专业","厂商","设备型号","网元","网络","开始时间","结束时间","历时","主要处理人","支撑方式","联系方式","远程服务时间","现场服务时间","服务类型","服务信息","解决方案","需求等级","备注信息","创建人","创建时间","电信接口人","厂商接口人","当前状态","服务满意度","结果满意度"}; 
        String[] keys=new String[]{"ser_id","region_name","subregion_name","major_name","manufacturer_name","nemodel_name","ne_name","network_name","start_time","end_time","total_duration","majorhandler","supportmethod","contactinfo","telnetserv_duration","fieldserv_duration","demand_name","demand_info","solution","demand_degree","remarksinfo","name","creat_time","tp_name","mp_name","currentprocess","servicesatisfaction","resultsatisfaction"};
        try {
            OutputStream out = response.getOutputStream(); 
            List<Map<String,Object>> serList=getSERPandectList(request);
            System.out.println("len"+serList.size());
            FileExport.exportExcel(excelName, headers, serList, keys, out, "yyyy-MM-dd"); 
            out.close();  
            System.out.println("excel导出成功！");  
        } catch (FileNotFoundException e) {  
                e.printStackTrace();  
        } catch (IOException e) {  
                e.printStackTrace();  
        }  
	}
	
	
	
	//工具函数，根据request获取查询结果，返回List<Map<String,Object>>
	private List<Map<String,Object>> getSERPandectList(HttpServletRequest request){
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
			java.sql.Timestamp launchTime = new java.sql.Timestamp(launchUtil.getTime());
			java.sql.Timestamp completionTime = new java.sql.Timestamp(completionUtil.getTime());
			return SERDac.getSERPandect(regionId, subregionId, majorId, manufacturerId, launchTime, completionTime, comquery, person);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
