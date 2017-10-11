package com.telecom.weibao.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.TelecomPersonnel;


@WebFilter("/UserSourceFilter")
public class UserSourceFilter implements Filter {
	private static Logger logger = Logger.getLogger(UserSourceFilter.class);
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("UserSourceFilter被使用");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setCharacterEncoding("utf-8");
		httpServletResponse.setContentType("text/html;charset=utf8");
		try{		
		int userType=BaseFilter.getUserType(httpServletRequest);
		if(userType<0){
			logger.warn("未登录用户尝试访问电信侧资源。");
			httpServletResponse.sendRedirect(request.getServletContext().getContextPath() + "/Login");
		}
		else if(userType==0){
			ManufacturerPersonnel mp=BaseFilter.getMpUser(httpServletRequest);
			logger.warn("厂商用户:"+ mp.getMpName()+":"+mp.getMpPhone()+" 尝试访问电信侧资源。");
			PrintWriter out=httpServletResponse.getWriter();
			out.print("<script language='javascript'>alert('您无权对该资源进行访问！');window.history.back();</script>");	
			out.close();
		}
		else if(userType==1){
			TelecomPersonnel tp=BaseFilter.getTpUser(httpServletRequest);
			logger.info("电信用户:"+tp.getTpName()+":"+tp.getTpPhone()+"正常访问资源。");
			chain.doFilter(request, response);
		}}catch(Exception e){
			BaseFilter.checkSessionTimeout(httpServletRequest, httpServletResponse);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	public void destroy() {
	}

}
