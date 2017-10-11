package com.telecom.weibao.man;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.statistics.SERTypeStatistics;
import com.telecom.weibao.tools.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;


@WebServlet("/man/GetSerPercent")
public class GetSerPercent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}


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

			List<SERTypeStatistics> serTypeList=SERDac.getSERTypeCount(regionId,majorId,manufacturerId,12,1);
			
			PrintWriter out = response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor());
			JSONArray jsonarr = JSONArray.fromObject(serTypeList, jsonConfig);
			System.out.println(jsonarr.toString(4));
			out.print(jsonarr.toString(4));
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
