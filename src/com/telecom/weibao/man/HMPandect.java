package com.telecom.weibao.man;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.telecom.weibao.dac.MainStatisticsDac;
import com.telecom.weibao.dac.ManufacturerDac;
import com.telecom.weibao.dac.RegionDac;
import com.telecom.weibao.entity.Manufacturer;
import com.telecom.weibao.entity.Region;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.filter.BaseFilter;

@WebServlet("/man/HMPandect")
public class HMPandect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			HttpSession session = request.getSession();
			TelecomPersonnel tp = (TelecomPersonnel) session.getAttribute("user");
			input.put("name", tp.getTpName());
			input.put("nonDealed", MainStatisticsDac.getNonReviewedCount(tp));
			
			List<Region> regionList = RegionDac.getRegions();
			input.put("regionList", regionList);
			List<Manufacturer> mfList = ManufacturerDac.getManufacturers();
			input.put("mfList", mfList);
			request.getRequestDispatcher("/jsp/telmanJsp/queryTelHM.jsp").forward(request, response);
		} catch (Exception e) {
			BaseFilter.checkSessionTimeout(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
