package com.telecom.weibao.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.telecom.weibao.dac.RegionDac;
import com.telecom.weibao.entity.SubRegion;

import net.sf.json.JSONObject;


@WebServlet("/common/GetSubRegion")
public class GetSubRegion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		try{
			System.out.println("被调用");
			String regionIdStr=request.getParameter("regionId");
			System.out.println(regionIdStr);
			if(regionIdStr!=null){
				long regionId=Long.parseLong(regionIdStr);
				System.out.println(regionId);
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf8");
				try{
				List<SubRegion> subRegionList=RegionDac.getSubRegions(regionId);
				System.out.println(subRegionList.size());
				PrintWriter out=response.getWriter();
				out.print("[");
				for(SubRegion subRegion: subRegionList){
					JSONObject jsonobj =JSONObject.fromObject(subRegion);
					String str=jsonobj.toString();
					out.print(str+",");
					out.println();
				}
				out.print("]");
				out.close();
				}
				catch(Exception e){
					e.printStackTrace();
				}				
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
