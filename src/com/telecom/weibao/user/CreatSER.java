package com.telecom.weibao.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import com.telecom.weibao.dac.ManufacturerDac;
import com.telecom.weibao.dac.RegionDac;
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.entity.DemandCategory;
import com.telecom.weibao.entity.Manufacturer;
import com.telecom.weibao.entity.Region;
import com.telecom.weibao.entity.SER;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.filter.BaseFilter;


@WebServlet("/user/CreatSER")
public class CreatSER extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			TelecomPersonnel tp = (TelecomPersonnel) session.getAttribute("user");
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			input.put("name", tp.getTpName());
			input.put("nonDealed", MainStatisticsDac.getNonReviewedCount(tp));

			List<Region> regionList = RegionDac.getRegions();
			input.put("regionList", regionList);
			List<Manufacturer> mfList = ManufacturerDac.getManufacturers();
			input.put("mfList", mfList);
			List<DemandCategory> demandList = DemandDac.getAllDemands();
			input.put("demandList", demandList);

			request.getRequestDispatcher("/WEB-INF/jsp/tele_add_ser.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			long subRegionId=Long.parseLong(request.getParameter("subRegion"));
			long neModelId=Long.parseLong(request.getParameter("neModels"));
			long mpId=Long.parseLong(request.getParameter("manuPerson"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			java.util.Date startTimeUtil=sdf.parse(request.getParameter("startTime"));
			java.util.Date endTimeUtil=sdf.parse("2010-01-01 00:00");
			long demandId=0;
			try{demandId=Long.parseLong(request.getParameter("demand"));}catch(Exception e){}
			int demandDegree=Integer.parseInt(request.getParameter("demandDegree"));
			String demandInfo=request.getParameter("demandinfo");
			
			SER ser=new SER();
			ser.getSubRegion().setSubRegionId(subRegionId);
			ser.getNeModel().setNeModelId(neModelId);
			java.sql.Timestamp startTime=new java.sql.Timestamp(startTimeUtil.getTime());
			java.sql.Timestamp endTime=new java.sql.Timestamp(endTimeUtil.getTime());
			ser.setStartTime(startTime);
			ser.setEndTime(endTime);
			ser.getCategory().setDemandId(demandId);
			ser.setDemandDegree(demandDegree);
			ser.setDemandInfo(demandInfo);
			ser.getMp().setMpId(mpId);
			HttpSession session = request.getSession();
			TelecomPersonnel tp=(TelecomPersonnel)session.getAttribute("user");
			ser.setCreaterId(tp.getTpId());
			ser.setAuditor(tp);			
			ser.setTotalDuration(0);
			ser.setTelnetServDuration(0);
			ser.setFieldServDuration(0);
			ser.setMajorHandler("");
			ser.setSupportMethod("");
			ser.setContactinfo("");
			ser.setSolution("");
			ser.setRemarksInfo("");			
			java.sql.Timestamp creatTime=new Timestamp(System.currentTimeMillis());
			ser.setCreatTime(creatTime);
		    ser.setCurrentProcess(-1);
			
			SERDac.addSER(ser);
			//重新回到主页
			response.sendRedirect(request.getServletContext().getContextPath()+"/user/Pendings");
			
		}catch(Exception e){
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf8");
			PrintWriter out=response.getWriter();
			out.print("<script language='javascript'>alert('请检查您提交的数据！');window.history.back();</script>");
			out.close();
		}
		
	}

}
