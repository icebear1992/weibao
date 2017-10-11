package com.telecom.weibao.man;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.telecom.weibao.dac.MainStatisticsDac;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.filter.BaseFilter;


@WebServlet("/man/MonthMajor")
public class MonthMajor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			HttpSession session = request.getSession();
			TelecomPersonnel tp = (TelecomPersonnel) session.getAttribute("user");
			input.put("name", tp.getTpName());
			input.put("nonDealed", MainStatisticsDac.getNonReviewedCount(tp));

			request.getRequestDispatcher("/jsp/telmanJsp/reportTelMajor.jsp").forward(request, response);
		} catch (Exception e) {
			BaseFilter.checkSessionTimeout(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
