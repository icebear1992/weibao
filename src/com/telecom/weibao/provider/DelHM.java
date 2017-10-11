package com.telecom.weibao.provider;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.telecom.weibao.dac.HMDac;

@WebServlet("/provider/DelHM.do")
public class DelHM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getServletContext().getContextPath()+"/provider/ProviderMain");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			long hmId = Long.parseLong(request.getParameter("hmId"));
			HMDac.delHM(hmId);
			response.sendRedirect(request.getServletContext().getContextPath()+"/provider/ProviderMain");			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
