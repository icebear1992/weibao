package com.telecom.weibao.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.jmx.snmp.Timestamp;

import com.telecom.weibao.dac.HMDac;
import com.telecom.weibao.dac.HMReviewDac;
import com.telecom.weibao.dac.MainStatisticsDac;
import com.telecom.weibao.dac.RegionDac;
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.dac.SERReviewDac;
import com.telecom.weibao.dac.TelPersonDac;
import com.telecom.weibao.entity.HM;
import com.telecom.weibao.entity.HMInfo;
import com.telecom.weibao.entity.HMReview;
import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.SER;
import com.telecom.weibao.entity.SERReview;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.filter.BaseFilter;
import com.telecom.weibao.tools.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@WebServlet("/user/ViewHM")
public class ViewHM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			TelecomPersonnel tp = (TelecomPersonnel) session.getAttribute("user");
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			input.put("name", tp.getTpName());
			input.put("region", RegionDac.getRegionNameBySubId(tp.getSubRegion().getSubRegionId()));
			input.put("nonReviewNum", MainStatisticsDac.getNonReviewedCount(tp));	//未点评
			input.put("nonAuditNum", MainStatisticsDac.getNonAuditedCount(tp));	//未审核
			input.put("nonFinNum", MainStatisticsDac.getUncompleteCount(tp));	//未完成
			input.put("returnNum", MainStatisticsDac.getReturnCount(tp));	//被退回
			//根据前台serId查询该服务支撑信息
			long hmId=Long.parseLong(request.getParameter("HMId"));
			System.out.println(hmId);
			HM hm=HMDac.getHMDetailById(hmId);
			String regionName = RegionDac.getRegionNameBySubId(hm.getSubRegion().getSubRegionId());
			input.put("hm", hm);
			input.put("auditor", TelPersonDac.getAuditorNameByReviewTpId(hm.getAuditor().getTpId()));
			input.put("regionName", regionName);
			input.put("major", hm.getMajor().getMajorName());
			//再根据serId查询该服务支撑点评信息
			List<HMReview> reviews = HMReviewDac.getHMReviewsAllByHMId(hmId);
			input.put("reviews", reviews);
			request.getRequestDispatcher("/jsp/telopJsp/detailHMTel.jsp").forward(request, response);
			
		} catch (Exception e) {
			BaseFilter.checkSessionTimeout(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//获取hm信息
			long hmId=Long.parseLong(request.getParameter("hmId"));
			HM hm=HMDac.getHMDetailById(hmId);
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf8");
			//开始输出jason格式数据			
			PrintWriter out=response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
			jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonDateValueProcessor());
			JSONArray jsonarr=JSONArray.fromObject(hm,jsonConfig);
			out.print(jsonarr.toString(4));
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
