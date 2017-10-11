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

@WebFilter("/ProviderSourceFilter")
public class ProviderSourceFilter implements Filter {
	private static Logger logger = Logger.getLogger(ProviderSourceFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("ProviderSourceFilter被使用");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setCharacterEncoding("utf-8");
		httpServletResponse.setContentType("text/html;charset=utf8");
		try {
			int userType = BaseFilter.getUserType(httpServletRequest);
			if (userType < 0) {
				logger.warn("未登录用户尝试访问管理员资源。");
				httpServletResponse.sendRedirect(request.getServletContext().getContextPath() + "/Login");
			} else if (userType == 0) {
				ManufacturerPersonnel mp=BaseFilter.getMpUser(httpServletRequest);
				logger.info("厂商用户:" + mp.getMpName()+":"+mp.getMpPhone() + "正常访问厂商资源。");
				chain.doFilter(request, response);
			} else {
				PrintWriter out = httpServletResponse.getWriter();
				out.print("<script language='javascript'>alert('您无权对该资源进行访问！');window.history.back();</script>");
				out.close();
			}
		} catch (Exception e) {
			BaseFilter.checkSessionTimeout(httpServletRequest, httpServletResponse);
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	public void destroy() {
	}

}
