package com.telecom.weibao.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.telecom.weibao.dac.MajorContactDac;
import com.telecom.weibao.entity.MajorContact;

import net.sf.json.JSONObject;


@WebServlet("/common/GetContact")
public class GetContact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String majorIdStr=request.getParameter("majorId");
			String subRegionIdStr=request.getParameter("subRegionId");
			if(majorIdStr!=null && subRegionIdStr !=null){
				long majorId=Long.parseLong(majorIdStr.trim());
				long subRegionId=Long.parseLong(subRegionIdStr.trim());
				System.out.println(majorId+" "+subRegionId);
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf8");
				List<MajorContact> majorContactList=MajorContactDac.getMajorContacts(majorId, subRegionId);
				PrintWriter out=response.getWriter();
				out.print("[");
				for(MajorContact majorContact: majorContactList){
					JSONObject jsonobj =JSONObject.fromObject(majorContact);
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
