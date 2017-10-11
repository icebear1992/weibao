package com.telecom.weibao.user;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.telecom.weibao.dac.SERReviewDac;
import com.telecom.weibao.entity.SERReview;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.filter.BaseFilter;

@WebServlet("/user/FallbackSER.do")
public class FallbackSER extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getServletContext().getContextPath()+"/user/Pendings");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			long serId = Long.parseLong(request.getParameter("serId"));
			String reviewContent = request.getParameter("reviewContent");
			if(reviewContent==null)reviewContent="";
			SERReview sr = new SERReview();
			sr.getSer().setSerId(serId);
			HttpSession session = request.getSession();
			sr.setReviewer((TelecomPersonnel) session.getAttribute("user"));
			sr.setReviewContent(reviewContent);
			Timestamp reviewTime = new Timestamp(System.currentTimeMillis());
			sr.setReviewTime(reviewTime);
			SERReviewDac.FallBackSER(sr);
			response.sendRedirect(request.getServletContext().getContextPath() + "/user/Pendings");
		}catch(Exception e){
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}

}
