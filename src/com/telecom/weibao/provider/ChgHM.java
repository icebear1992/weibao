package com.telecom.weibao.provider;

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

import com.telecom.weibao.dac.HMDac;
import com.telecom.weibao.dac.MainStatisticsDac;
import com.telecom.weibao.dac.RegionDac;
import com.telecom.weibao.entity.HM;
import com.telecom.weibao.entity.HMInfo;
import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.Region;
import com.telecom.weibao.filter.BaseFilter;


@WebServlet("/provider/ChgHM.do")
public class ChgHM extends HttpServlet {
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

			long hmId = Long.parseLong(request.getParameter("hmId"));
			HM hm = HMDac.getHMDetailById(hmId);
			input.put("hm", hm);

			request.getRequestDispatcher("/WEB-INF/jsp/manu_chg_yingjian2.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//首先获取HM基本信息
			HttpSession session = request.getSession();
			ManufacturerPersonnel mp=(ManufacturerPersonnel)session.getAttribute("user");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			long hmId=Long.parseLong(request.getParameter("hmId"));
			long subRegionId=Long.parseLong(request.getParameter("subRegion"));
			long majorId=Long.parseLong(request.getParameter("major"));
			java.util.Date launchTimeUtil=sdf.parse(request.getParameter("launchTime"));
			java.util.Date completionTimeUtil = sdf.parse(request.getParameter("completionTime"));
			long auditorId=Long.parseLong(request.getParameter("majorContact"));
						
			HM hm=new HM();
			hm.setHmId(hmId);
			hm.getSubRegion().setSubRegionId(subRegionId);
			hm.getMajor().setMajorId(majorId);
			java.sql.Date launchTime=new java.sql.Date(launchTimeUtil.getTime());
			hm.setLaunchTime(launchTime);
			java.sql.Date completionTime = new java.sql.Date(completionTimeUtil.getTime());
			hm.setCompletionTime(completionTime);
			
			hm.getMp().setMpId(mp.getMpId());
			hm.getAuditor().setTpId(auditorId);
			Timestamp creatTime=new Timestamp(System.currentTimeMillis()); 
			hm.setCreatTime(creatTime);
			if (completionTimeUtil.getTime()>=launchTimeUtil.getTime())
				hm.setCurrentProcess(0);
			else
				hm.setCurrentProcess(-1);
			//获取HM详情
			boolean valid=true;
			String[] neModelIds=request.getParameterValues("neModels");
			String[] totalNums=request.getParameterValues("totalNum");
			String[] repairNums=request.getParameterValues("repairNum");
			for(int i=0;i<totalNums.length;i++){
				long neModelId=Long.parseLong(neModelIds[i]);
				int totalNum=Integer.parseInt(totalNums[i].trim());
				int repairNum=Integer.parseInt(repairNums[i].trim());
				if(totalNum>0 && totalNum>=repairNum){
					HMInfo hmInfo=new HMInfo();
					hmInfo.setTotalNumber(totalNum);
					hmInfo.setRepairNumber(repairNum);
					hmInfo.getNeModel().setNeModelId(neModelId);
					hmInfo.setSerialNumber("");
					hm.getHmInfoList().add(hmInfo);
				}
				else{
					valid=false;
					break;
				}
			}if (valid) {
				// 修改HM
				HMDac.chgHM(hm);
				// 重新回到主页
				response.sendRedirect(request.getServletContext().getContextPath() + "/provider/ProviderMain");
			} else {
				System.out.println("abc");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf8");
				PrintWriter out = response.getWriter();
				out.print("<script language='javascript'>alert('请检查您提交的数据！');window.history.back();</script>");
				out.close();
			}
		}
		catch(Exception e){
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
