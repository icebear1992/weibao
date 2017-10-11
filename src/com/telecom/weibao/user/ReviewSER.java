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

@WebServlet("/user/ReviewSER.do")
public class ReviewSER extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int serId = Integer.parseInt(request.getParameter("serId"));
		request.setAttribute("serId", serId);
		request.getRequestDispatcher("/jsp/reviewJsp/reviewSer.jsp").forward(request, response);
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
			int serviceSatisfaction = 0;
			int resultSatisfaction = 0;
			try{serviceSatisfaction = Integer.parseInt(request.getParameter("serviceSatisfaction"));}catch(Exception e){}
			try{resultSatisfaction = Integer.parseInt(request.getParameter("resultSatisfaction"));}catch(Exception e){}
			String reviewContent = request.getParameter("reviewContent");
			if(reviewContent==null)reviewContent="";
			SERReview sr = new SERReview();
			sr.getSer().setSerId(serId);
			HttpSession session = request.getSession();
			sr.setReviewer((TelecomPersonnel) session.getAttribute("user"));
			sr.setServiceSatisfaction(serviceSatisfaction);
			sr.setResultSatisfaction(resultSatisfaction);
			sr.setReviewContent(reviewContent);
			Timestamp reviewTime = new Timestamp(System.currentTimeMillis());
			sr.setReviewTime(reviewTime);
			sr.setReviewType(1);
			System.out.println("ok");
			SERReviewDac.ReviewSER(sr,flag);			
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}

}
