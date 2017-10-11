package com.telecom.weibao.provider;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.filter.BaseFilter;


@WebServlet("/provider/DelSER.do")
public class DelSER extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ManufacturerPersonnel mp = (ManufacturerPersonnel) session.getAttribute("user");			
		long serId = Long.parseLong(request.getParameter("serId"));
		String currentProcess = request.getParameter("currentProcess");
		if (SERDac.isCreater(serId, mp.getMpId())) {
			SERDac.delSER(serId);
			response.sendRedirect(request.getServletContext().getContextPath()+"/provider/ProviderMain");
		}else{
			PrintWriter out = response.getWriter();
			out.print("<script language='javascript'>alert('该记录不是您创建的，您无权删除！');window.history.back();</script>");
			out.close();									
		}
		response.sendRedirect(request.getServletContext().getContextPath()+"/provider/ProviderMain");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("post");
			HttpSession session = request.getSession();
			ManufacturerPersonnel mp = (ManufacturerPersonnel) session.getAttribute("user");			
			long serId = Long.parseLong(request.getParameter("serId"));
			if (SERDac.isCreater(serId, mp.getMpId())) {
				SERDac.delSER(serId);
				response.sendRedirect(request.getServletContext().getContextPath()+"/provider/ProviderMain");
			}
			else{
				PrintWriter out = response.getWriter();
				out.print("<script language='javascript'>alert('该记录不是您创建的，您无权删除！');window.history.back();</script>");
				out.close();									
			}			
		}catch(Exception e){
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}

}
