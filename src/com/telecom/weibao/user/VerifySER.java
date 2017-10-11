package com.telecom.weibao.user;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

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

@WebServlet("/user/AuditSER.do")
public class VerifySER extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int serId = Integer.parseInt(request.getParameter("serId"));
		request.setAttribute("serId", serId);
		request.getRequestDispatcher("/jsp/auditJsp/auditSer.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int flag =0;
			String pass = request.getParameter("pass");
			if(pass!=null){
				 flag = 1;
			}else{ flag =0 ;}
			System.out.println(flag);
			long serId = Long.parseLong(request.getParameter("serId"));
			String reviewContent = request.getParameter("reviewContent");
			if (reviewContent == null)
				reviewContent = "";
			SERReview sr = new SERReview();
			sr.getSer().setSerId(serId);
			HttpSession session = request.getSession();
			sr.setReviewer((TelecomPersonnel) session.getAttribute("user"));
			sr.setReviewContent(reviewContent);
			Timestamp reviewTime = new Timestamp(System.currentTimeMillis());
			sr.setReviewTime(reviewTime);
			sr.setServiceSatisfaction(0);
			sr.setResultSatisfaction(0);
			sr.setReviewType(2);
			SERReviewDac.VerifySER(sr,flag);
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}

}
