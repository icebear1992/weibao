package com.telecom.weibao.provider;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.telecom.weibao.dac.MajorContactDac;
import com.telecom.weibao.dac.NEDac;
import com.telecom.weibao.dac.RegionDac;
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.entity.DemandCategory;
import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.Region;
import com.telecom.weibao.entity.SER;
import com.telecom.weibao.filter.BaseFilter;


@WebServlet("/provider/ChgSER.do")
public class ChgSER extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			ManufacturerPersonnel mp = (ManufacturerPersonnel) session.getAttribute("user");
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			input.put("name", mp.getMpName());
			input.put("nonDealed", MainStatisticsDac.getNonDealedCount(mp));
			// 获取Region列表，设置属性
			List<Region> regionList = RegionDac.getRegions();
			input.put("regionList", regionList);
			// 获取服务支撑类别列表，设置属性
			List<DemandCategory> demandList = DemandDac.getAllDemands();
			input.put("demandList", demandList);
			long serId = Long.parseLong(request.getParameter("serId"));
			SER ser = SERDac.getSERDetailById(serId);
			
			
			input.put("name", mp.getMpName());
			input.put("manufacturer", mp.getManufacturer());
			input.put("nonDealed", MainStatisticsDac.getNonDealedCount(mp));
			input.put("nonFin", MainStatisticsDac.getNonFinCount(mp));
			//根据前台serId查询该服务支撑信息
			long regionId = Long.parseLong(RegionDac.getRegionIdBySubId(ser.getSubRegion().getSubRegionId()));
			long majorId = ser.getNeModel().getNe().getNetwork().getMajor().getMajorId();
			long networkId = ser.getNeModel().getNe().getNetwork().getNetworkId();
			System.out.println(networkId+"3");
			long neId = ser.getNeModel().getNe().getNeId();
			input.put("ser", ser);
			input.put("majorName", ser.getNeModel().getNe().getNetwork().getMajor().getMajorName());
			input.put("majorId",majorId );
			input.put("regionName", RegionDac.getRegionNameBySubId(ser.getSubRegion().getSubRegionId()));
			input.put("regionId",regionId );
			input.put("networkId",networkId);
			input.put("networkName", ser.getNeModel().getNe().getNetwork().getNetworkName());
			input.put("subRegionList", RegionDac.getSubRegions(regionId));
			input.put("majorContactList", MajorContactDac.getMajorContacts(majorId, ser.getSubRegion().getSubRegionId()));
			input.put("networkList", NEDac.getNetworksByMajorId(majorId));
			input.put("neList", NEDac.getNEsByNetworkId(networkId));
			input.put("neModelList", NEDac.getNEModelsByNEId(neId));
			input.put("neId", neId);
			input.put("neName", ser.getNeModel().getNe().getNeName());
			
			
			request.getRequestDispatcher("/jsp/manuJsp/chgSerManu.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			ManufacturerPersonnel mp = (ManufacturerPersonnel) session.getAttribute("user");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			long serId = Long.parseLong(request.getParameter("serId"));
			SER ser = new SER();
			//判断厂商是不是创建者
			if (SERDac.isCreater(serId, mp.getMpId())) {
				System.out.println("creater chg");
				long subRegionId2 = Long.parseLong(request.getParameter("subRegion"));
				long neModelId2 = Long.parseLong(request.getParameter("neModels"));
				java.util.Date startTimeUtil2 = sdf.parse(request.getParameter("startTime"));
				java.sql.Timestamp startTime2 = new java.sql.Timestamp(startTimeUtil2.getTime());
				long demandId2 = Long.parseLong(request.getParameter("demand"));
				String demandInfo2 = request.getParameter("demandinfo");
				int demandDegree2 = 0;
				try {
					demandDegree2 = Integer.parseInt(request.getParameter("demandDegree"));
				} catch (Exception e) {
				}
				long auditorId2 = Long.parseLong(request.getParameter("majorContact"));
				ser.setSerId(serId);
				ser.getSubRegion().setSubRegionId(subRegionId2);
				ser.getNeModel().setNeModelId(neModelId2);

				ser.setStartTime(startTime2);
				ser.getCategory().setDemandId(demandId2);
				ser.setDemandInfo(demandInfo2);
				ser.setDemandDegree(demandDegree2);
				ser.getAuditor().setTpId(auditorId2);
				ser.getMp().setMpId(mp.getMpId());
			} else {// 即创建人是电信方，则以上字段不允许修改
				ser = SERDac.getSERDetailById(serId);
				System.out.println("i am not creater chg");
			}

			java.util.Date endTimeUtil = sdf.parse(request.getParameter("endTime"));
			java.sql.Timestamp endTime = new java.sql.Timestamp(endTimeUtil.getTime());
			double telnetServDuration = 0;
			try {
				telnetServDuration = Double.parseDouble(request.getParameter("telnetservDuration"));
			} catch (Exception e) {
			}
			double fieldServDuration = 0;
			try {
				fieldServDuration = Double.parseDouble(request.getParameter("fieldservDuration"));
			} catch (Exception e) {
			}
			double totalDuration = telnetServDuration + fieldServDuration;
			String majorHandler = request.getParameter("majorHandler");
			String supportMethod = request.getParameter("supportMethod");
			String contactinfo = request.getParameter("contactinfo");
			String solution = request.getParameter("solution");
			String remarksInfo = request.getParameter("memoinfo");
			ser.setEndTime(endTime);
			ser.setTotalDuration(totalDuration);
			ser.setMajorHandler(majorHandler);
			ser.setSupportMethod(supportMethod);
			ser.setContactinfo(contactinfo);
			ser.setTelnetServDuration(telnetServDuration);
			ser.setFieldServDuration(fieldServDuration);
			ser.setSolution(solution);
			ser.setRemarksInfo(remarksInfo);

			if (ser.getStartTime().getTime() <= ser.getEndTime().getTime()) {
				ser.setCurrentProcess(0);
			} else {
				ser.setCurrentProcess(-1);
			}
			
						
			SERDac.chgSER(ser);
			//重新回到主页
			response.sendRedirect(request.getServletContext().getContextPath()+"/provider/ProviderMain");			
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
