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

import com.telecom.weibao.dac.DemandDac;
import com.telecom.weibao.dac.MainStatisticsDac;
import com.telecom.weibao.dac.RegionDac;
import com.telecom.weibao.entity.DemandCategory;
import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.Region;
import com.telecom.weibao.filter.BaseFilter;

@WebServlet("/provider/AddWork")
public class AddWork extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			ManufacturerPersonnel mp = (ManufacturerPersonnel) session.getAttribute("user");
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			input.put("name", mp.getMpName());
			input.put("manufacturer", mp.getManufacturer());
			input.put("nonDealed", MainStatisticsDac.getNonDealedCount(mp));
			input.put("nonFin", MainStatisticsDac.getNonFinCount(mp));
			// 获取Region列表，设置属性
			List<Region> regionList = RegionDac.getRegions();
			input.put("regionList", regionList);
			
			request.getRequestDispatcher("/jsp/manuJsp/addWorkManu.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			ManufacturerPersonnel mp = (ManufacturerPersonnel) session.getAttribute("user");
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			input.put("name", mp.getMpName());
			input.put("manufacturer", mp.getManufacturer());
			input.put("nonDealed", MainStatisticsDac.getNonDealedCount(mp));
			input.put("nonFin", MainStatisticsDac.getNonFinCount(mp));
			// 获取Region列表，设置属性
			List<Region> regionList = RegionDac.getRegions();
			input.put("regionList", regionList);
			String title = request.getParameter("title");
			String type = request.getParameter("type");
			String status = request.getParameter("status");
			input.put("status", status);
			input.put("title", title);
			System.out.println(title+type+status);
			if(type.equals("ser")){
				System.out.println("goto服务add");
				List<DemandCategory> demandList = DemandDac.getAllDemands();
				input.put("demandList", demandList);
				request.getRequestDispatcher("/jsp/manuJsp/addSerManu.jsp").forward(request, response);
			}else if(type.equals("HM")){
				request.getRequestDispatcher("/jsp/manuJsp/addHMManu.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}
}
