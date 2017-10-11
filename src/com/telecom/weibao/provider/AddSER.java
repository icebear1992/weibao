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

import com.telecom.weibao.dac.DemandDac;
import com.telecom.weibao.dac.MainStatisticsDac;
import com.telecom.weibao.dac.RegionDac;
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.entity.DemandCategory;
import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.Region;
import com.telecom.weibao.entity.SER;
import com.telecom.weibao.filter.BaseFilter;

@WebServlet("/provider/AddSER")
public class AddSER extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    //获取信息，设置属性，选择视图
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	//获取表单信息，添加新的服务
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			java.util.Date startTimeUtil = null;
			java.util.Date endTimeUtil = null;
			String title =request.getParameter("title");
			String status = request.getParameter("status");
			long subRegionId=Long.parseLong(request.getParameter("subRegion"));
			long neModelId=Long.parseLong(request.getParameter("neModels"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String date = request.getParameter("date");
			if(status.equals("1")){
				String[] timeRangeSplit=null;
				if(date!=null){
					timeRangeSplit = date.split("到");
				}
				 startTimeUtil=sdf.parse(timeRangeSplit[0].trim());
				 endTimeUtil=sdf.parse(timeRangeSplit[1].trim());
			}else if(status.equals("0")){
				 startTimeUtil=sdf.parse(date);
			}
			String majorHandler=request.getParameter("majorHandler");
			String supportMethod=request.getParameter("supportMethod");
			String contactinfo=request.getParameter("contactinfo");
			double telnetServDuration=0;
			try{telnetServDuration=Double.parseDouble(request.getParameter("telnetservDuration"));}catch(Exception e){}
			double fieldServDuration=0;
			try{fieldServDuration=Double.parseDouble(request.getParameter("fieldservDuration"));}catch(Exception e){}
			double totalDuration=telnetServDuration+fieldServDuration;
			long demandId=Long.parseLong(request.getParameter("demand"));
			String demandInfo=request.getParameter("demandinfo");
			String solution=request.getParameter("solution");	
			String remarksInfo=request.getParameter("memoinfo");
			long auditorId=Long.parseLong(request.getParameter("majorContact"));
			int demandDegree=0;
			try{demandDegree=Integer.parseInt(request.getParameter("demandDegree"));}
			catch(Exception e){}
			
			SER ser=new SER();
			ser.getSubRegion().setSubRegionId(subRegionId);
			ser.getNeModel().setNeModelId(neModelId);
			java.sql.Timestamp startTime=new java.sql.Timestamp(startTimeUtil.getTime());
			if(status.equals("1")){
				java.sql.Timestamp endTime=new java.sql.Timestamp(endTimeUtil.getTime());
				ser.setEndTime(endTime);
			}
			ser.setStartTime(startTime);
			ser.setTotalDuration(totalDuration);
			ser.setMajorHandler(majorHandler);
			ser.setSupportMethod(supportMethod);
			ser.setContactinfo(contactinfo);
			ser.setTelnetServDuration(telnetServDuration);
			ser.setFieldServDuration(fieldServDuration);
			ser.getCategory().setDemandId(demandId);
			ser.setDemandDegree(demandDegree);
			ser.setDemandInfo(demandInfo);
			ser.setSolution(solution);
			ser.setRemarksInfo(remarksInfo);
			HttpSession session = request.getSession();
			ManufacturerPersonnel mp=(ManufacturerPersonnel)session.getAttribute("user");
			ser.setCreaterId(mp.getMpId());
			ser.getMp().setMpId(mp.getMpId());
			ser.getAuditor().setTpId(auditorId);
			java.sql.Timestamp creatTime=new Timestamp(System.currentTimeMillis());
			ser.setCreatTime(creatTime);
			ser.setTitle(title);
			if(status.equals("1")){
				ser.setCurrentProcess(0);
			}else if(status.equals("0")){
				ser.setCurrentProcess(-2);
			}
			
			SERDac.addSER(ser);
			//重新回到主页
			response.sendRedirect(request.getServletContext().getContextPath()+"/provider/ProviderMain?msg=201");
			
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
