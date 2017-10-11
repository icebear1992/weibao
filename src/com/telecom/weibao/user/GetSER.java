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
import com.telecom.weibao.dac.MainStatisticsDac;
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.dac.SERReviewDac;
import com.telecom.weibao.dac.WbChangeDac;
import com.telecom.weibao.entity.SER;
import com.telecom.weibao.entity.SERReview;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.entity.WbChange;
import com.telecom.weibao.filter.BaseFilter;
import com.telecom.weibao.tools.DateUtil;
import com.telecom.weibao.tools.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@WebServlet("/user/GetSER")
public class GetSER extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			HttpSession session = request.getSession();
			TelecomPersonnel tp = (TelecomPersonnel) session.getAttribute("user");
			//根据前台serId查询该服务支撑信息
			long serId=Long.parseLong(request.getParameter("serId"));
			SER ser=SERDac.getSERDetailById(serId);
			input.put("ser", ser);
			input.put("major", ser.getNeModel().getNe().getNetwork().getMajor().getMajorName());
			input.put("name", tp.getTpName());
			input.put("nonDealed", MainStatisticsDac.getNonReviewedCount(tp));
			//再根据serId查询该服务支撑点评信息
			List<SERReview> reviews = SERReviewDac.getSERReviewsAllBySERId(serId);
			input.put("reviews", reviews);
			int role = tp.getTpRole();
			if (role == 1) {
				// 接下来到一线维护人员页面
				request.getRequestDispatcher("/jsp/teluserJsp/detailSerTel.jsp").forward(request, response);
			} else if (role == 2) {
				// 接下来到管理员页面
				request.getRequestDispatcher("/jsp/telmanJsp/detailSerTel.jsp").forward(request, response);
			} else if (role == 3) {
				// 接下来到超级用户页面
				request.getRequestDispatcher("/jsp/telmanJsp/detailSerTel.jsp").forward(request, response);
		} else {// 出错，定向到登陆页面
			request.setAttribute("error", "您输入的信息有误！");
			request.getRequestDispatcher("tele_login.html").forward(request, response);
		}
			
		} catch (Exception e) {
			BaseFilter.checkSessionTimeout(request, response);
		}
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//获取ser信息
			long serId=Long.parseLong(request.getParameter("serId"));
			SER ser=SERDac.getSERDetailById(serId);
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf8");
			//开始输出jason格式数据			
			PrintWriter out=response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
			jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonDateValueProcessor());
			JSONArray jsonarr=JSONArray.fromObject(ser,jsonConfig);
			out.print(jsonarr.toString(4));
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
