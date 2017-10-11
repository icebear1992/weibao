package com.telecom.weibao.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.telecom.weibao.dac.ManufacturerDac;
import com.telecom.weibao.entity.ManufacturerPersonnel;

import net.sf.json.JSONObject;


@WebServlet("/common/GetMP")
public class GetMP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String manuIdStr=request.getParameter("manuId");
			if(manuIdStr!=null){
				long manuId=Long.parseLong(manuIdStr.trim());
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf8");
				List<ManufacturerPersonnel> mpList=ManufacturerDac.getManuPersonsByManuId(manuId);
				PrintWriter out=response.getWriter();
				out.print("[");
				for(ManufacturerPersonnel mp: mpList){
					JSONObject jsonobj =JSONObject.fromObject(mp);
					String str=jsonobj.toString();
					out.print(str+",");
					out.println();
				}
				out.print("]");
				out.close();
			}					
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
