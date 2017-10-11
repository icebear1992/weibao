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

import com.telecom.weibao.dac.HMDac;
import com.telecom.weibao.entity.TelecomPersonnel;



@WebFilter("/UserChgFilter")
public class UserChgFilter implements Filter {
	private static Logger logger = Logger.getLogger(UserChgFilter.class);

	/*
	 * 这块用于防止同类型的用户修改非自身范围内的服务详情
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			TelecomPersonnel tp = BaseFilter.getTpUser(httpServletRequest);
			boolean canAccess = true;
			// 这里是修改hm
			String hmIdStr = request.getParameter("hmId");
			if (hmIdStr != null) {
				try {
					long hmId = Long.parseLong(hmIdStr.trim());
					if (!HMDac.hasChgAuthority(hmId, tp)) {
						canAccess = false;
						logger.warn("电信用户" + tp.getTpName() + ":" + tp.getTpPhone() + "尝试修改非己方资源");
					}
				} catch (Exception e) {
				}
			}
			// 下面需要依次核实其他服务
			

			if (canAccess)
				chain.doFilter(request, response);
			else{
				HttpServletResponse httpServletResponse=(HttpServletResponse)response;
				httpServletResponse.setCharacterEncoding("utf-8");
				httpServletResponse.setContentType("text/html;charset=utf8");
				PrintWriter out=httpServletResponse.getWriter();
				out.print("<script language='javascript'>alert('您无权对该资源进行访问！');window.history.back();window.location.reload();</script>");
				out.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	public void destroy() {
	}

}
