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

@WebServlet("/user/FallbackHM.do")
public class FallbackHM extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getServletContext().getContextPath()+"/user/Pendings");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
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
            HMReviewDac.FallBackHM(hr);           
			response.sendRedirect(request.getServletContext().getContextPath()+"/user/Pendings");
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}

}
