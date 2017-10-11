package com.telecom.weibao.user;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.telecom.weibao.dac.HMReviewDac;
import com.telecom.weibao.entity.HMReview;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.filter.BaseFilter;


@WebServlet("/user/ReviewHM.do")
public class ReviewHM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int hmId = Integer.parseInt(request.getParameter("HmId"));
		request.setAttribute("hmId", hmId);
		request.getRequestDispatcher("/jsp/reviewJsp/reviewHM.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//flag代表点评让不让此工单通过——过了审核/不过修改
			int flag =0;
			String pass = request.getParameter("pass");
			if(pass!=null){
				 flag = 1;
			}else{ flag =0 ;}
			long hmId = Long.parseLong(request.getParameter("hmId"));
			int timeliness = 0;
			int satisfaction = 0;
		    try{timeliness = Integer.parseInt(request.getParameter("timeliness"));}catch(Exception e){}
		    try{satisfaction = Integer.parseInt(request.getParameter("satisfaction"));}catch(Exception e){}
			String reviewContent = request.getParameter("reviewContent");
			if(reviewContent==null)reviewContent="";
			HMReview hr = new HMReview();
			hr.getHm().setHmId(hmId);
			HttpSession session = request.getSession();
			hr.setReviewer((TelecomPersonnel) session.getAttribute("user"));
			hr.setTimeliness(timeliness);
			hr.setSatisfaction(satisfaction);
			hr.setReviewContent(reviewContent);
			Timestamp reviewTime = new Timestamp(System.currentTimeMillis());
			hr.setReviewTime(reviewTime);
			hr.setReviewType(1);
			HMReviewDac.ReviewHM(hr,flag);
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}
}
