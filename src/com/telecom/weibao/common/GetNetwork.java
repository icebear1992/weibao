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
import com.telecom.weibao.entity.Network;

import net.sf.json.JSONObject;


@WebServlet("/common/GetNetwork")
public class GetNetwork extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String majorIdStr=request.getParameter("majorId");
			if(majorIdStr!=null){
				long majorId=Long.parseLong(majorIdStr);
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf8");
				List<Network> networkList=NEDac.getNetworksByMajorId(majorId);
				PrintWriter out=response.getWriter();
				out.print("[");
				for(Network network: networkList){
					JSONObject jsonobj =JSONObject.fromObject(network);
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
