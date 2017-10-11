package com.telecom.weibao.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.telecom.weibao.dac.NEDac;
import com.telecom.weibao.entity.NE;

import net.sf.json.JSONObject;

@WebServlet("/common/GetNE")
public class GetNE extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String networkIdStr=request.getParameter("networkId");
			if(networkIdStr!=null){
				long networkId=Long.parseLong(networkIdStr);
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf8");
				List<NE> neList=NEDac.getNEsByNetworkId(networkId);
				PrintWriter out=response.getWriter();
				out.print("[");
				for(NE ne: neList){
					JSONObject jsonobj =JSONObject.fromObject(ne);
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
