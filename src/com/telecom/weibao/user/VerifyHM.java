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

import com.telecom.weibao.dac.HMReviewDac;
import com.telecom.weibao.dac.SERReviewDac;
import com.telecom.weibao.entity.HMReview;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.filter.BaseFilter;

@WebServlet("/user/AuditHM.do")
public class VerifyHM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int hmId = Integer.parseInt(request.getParameter("hmId"));
		request.setAttribute("hmId", hmId);
		request.getRequestDispatcher("/jsp/auditJsp/auditHM.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int flag =0;
			String pass = request.getParameter("pass");
			if(pass!=null){
				 flag = 1;
			}else{ flag =0 ;}
			long hmId = Long.parseLong(request.getParameter("hmId"));
			String reviewContent = request.getParameter("reviewContent");
			if(reviewContent==null)reviewContent="";
			HMReview hr = new HMReview();
			hr.getHm().setHmId(hmId);
			HttpSession session = request.getSession();
			hr.setReviewer((TelecomPersonnel) session.getAttribute("user"));
			hr.setReviewContent(reviewContent);
			Timestamp reviewTime = new Timestamp(System.currentTimeMillis());
			hr.setReviewTime(reviewTime);
			hr.setTimeliness(0);
			hr.setSatisfaction(0);
			hr.setReviewType(2);
			HMReviewDac.VerifyHM(hr,flag);
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}

}
