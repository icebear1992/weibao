package com.telecom.weibao.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jmx.snmp.Timestamp;
import com.telecom.weibao.dac.HMDac;
import com.telecom.weibao.entity.HM;
import com.telecom.weibao.filter.BaseFilter;
import com.telecom.weibao.tools.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;


@WebServlet("/user/GetHM.get")
public class GetHM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getServletContext().getContextPath()+"/user/Pendings");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//获取hm信息
			long hmId=Long.parseLong(request.getParameter("hmId"));
			HM hm=HMDac.getHMDetailById(hmId);
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf8");
			//开始输出jason格式数据			
			PrintWriter out=response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
			jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonDateValueProcessor());
			JSONArray jsonarr=JSONArray.fromObject(hm,jsonConfig);
			out.print(jsonarr.toString(4));
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}

}
