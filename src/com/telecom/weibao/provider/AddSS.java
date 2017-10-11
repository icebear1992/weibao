package com.telecom.weibao.provider;

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

import com.telecom.weibao.dac.HMDac;
import com.telecom.weibao.dac.RegionDac;
import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.Region;
import com.telecom.weibao.filter.BaseFilter;


@WebServlet("/provider/AddSS")
public class AddSS extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			ManufacturerPersonnel mp = (ManufacturerPersonnel) session.getAttribute("user");
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			int nonDealed = HMDac.getNonDealedHMCount(mp);
			input.put("name", mp.getMpName());
			input.put("nonDealed", nonDealed);
			// 获取Region列表，设置属性
			List<Region> regionList = RegionDac.getRegions();
			input.put("regionList", regionList);

			
			request.getRequestDispatcher("/WEB-INF/jsp/manu_add_yingjian2.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
