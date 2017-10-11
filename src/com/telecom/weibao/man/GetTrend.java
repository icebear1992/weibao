package com.telecom.weibao.man;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.telecom.weibao.dac.HMDac;
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.statistics.SERTypeStatistics;
import com.telecom.weibao.tools.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;


@WebServlet("/man/GetTrend")
public class GetTrend extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	//获取ajax请求，返回几个月的趋势数据
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			long regionId=0;
			try{
				regionId=Long.parseLong(request.getParameter("region"));
			}catch(Exception e){}
			long majorId=0;
			try{
				majorId=Long.parseLong(request.getParameter("network"));
			}catch(Exception e){}
			long manufacturerId=0;
			try{
				manufacturerId=Long.parseLong(request.getParameter("manu"));
			}catch(Exception e){}

			int n = 6;// 需要显示的月份数
			Calendar now = Calendar.getInstance();
			int year=now.get(Calendar.YEAR);
			int month=now.get(Calendar.MONTH);
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < n; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				String time;
				if(month-n+i<0){
					time=Integer.toString(year-1).substring(2)+"年"+Integer.toString(month-n+i+13)+"月";
				}else{
					time=Integer.toString(year).substring(2)+"年"+Integer.toString(month-n+i+1)+"月";
				}
				map.put("time",time);
				List<SERTypeStatistics> serList = SERDac.getSERTypeCount(regionId, majorId, manufacturerId, n - i,n - i);
				map.put("faultCount", 0);
				map.put("planCount", 0);
				for (SERTypeStatistics ser : serList) {
					if (ser.getDemandName().equals("故障查修")){
						map.put("faultCount", ser.getTotal());
					}
					if (ser.getDemandName().equals("方案支撑"))
						map.put("planCount", ser.getTotal());
				}
				int hmCount = HMDac.getHMCount(regionId, majorId, manufacturerId, n - i, n - i);
				map.put("hmCount", hmCount);
				double hmSatisfaction = HMDac.getHMSatisfaction(regionId, majorId, manufacturerId, n - i, n - i);
				map.put("hmSatisfaction", hmSatisfaction);
				double serResultSatisfaction=SERDac.getSERResultSatisfaction(regionId, majorId, manufacturerId, n - i, n - i);
				map.put("serResultSatisfaction", serResultSatisfaction);
				mapList.add(map);
			}
			PrintWriter out = response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor());
			JSONArray jsonarr = JSONArray.fromObject(mapList, jsonConfig);
			System.out.println(jsonarr.toString(4));
			out.print(jsonarr.toString(4));
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
