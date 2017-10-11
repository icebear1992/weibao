package com.telecom.weibao.man;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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

@WebServlet("/man/GetNonFinishHM")
public class GetNonFinishHM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Map<String, Object>> hmList = HMDac.getNonFinishedPandect();
			System.out.println(hmList.size());
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
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("octets/stream");  
        String excelName = "未完成硬件返修报表";  
        //转码防止乱码  
        response.addHeader("Content-Disposition", "attachment;filename="+new String( excelName.getBytes("gb2312"), "ISO8859-1" )+".xls");  
        String[] headers = new String[]{"事件ID","区域","子区域","专业","厂商","开始时间","结束时间","创建人","创建时间","厂商接口人","电信接口人","当前环节","及时性","满意度","网络","网元","型号","返修数量","修复数量"}; 
        String[] keys = new String[]{"hm_id","region_name","subregion_name","major_name","manufacturer_name","launch_time","completion_time","name","creat_time","mp_name","tp_name","currentprocess","timeliness","satisfaction","network_name","ne_name","nemodel_name","total_num","repair_num"};
        try {  
            OutputStream out = response.getOutputStream();  
            List<Map<String,Object>> hmList=HMDac.getNonFinishedPandect();
            FileExport.exportExcel(excelName, headers, hmList, keys, out, "yyyy-MM-dd"); 
            out.close();    
        } catch (FileNotFoundException e) {  
                e.printStackTrace();  
        } catch (IOException e) {  
                e.printStackTrace();  
        }  
	}

}
