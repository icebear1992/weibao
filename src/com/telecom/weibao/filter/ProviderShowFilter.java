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
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.dac.SSDac;
import com.telecom.weibao.entity.ManufacturerPersonnel;


@WebFilter("/ProviderShowFilter")
public class ProviderShowFilter implements Filter {
	private static Logger logger = Logger.getLogger(ProviderShowFilter.class);
	
	/*
	 * 这块用于防止同类型的用户查看非自身范围内的服务详情
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			if (BaseFilter.getUserType(httpServletRequest) != 0) {
				logger.warn("非厂商用户尝试查看厂商资源");
			} 
			else {
				ManufacturerPersonnel mp = BaseFilter.getMpUser(httpServletRequest);
				boolean canAccess = true;
				//这里是查看hm
				String hmIdStr = request.getParameter("hmId");
				if (hmIdStr != null) {
					try {
						long hmId = Long.parseLong(hmIdStr.trim());
						if (!HMDac.hasShowAuthority(hmId, mp.getMpId())){
							canAccess = false;
							logger.warn("厂商用户"+mp.getMpName()+":"+mp.getMpPhone()+"尝试查看非己方资源");
						}
					} catch (Exception e) {
					}
				}
				//这里查看的是ss
				String ssIdStr = request.getParameter("ssId");
				if (ssIdStr != null) {
					try {
						long ssId = Long.parseLong(ssIdStr.trim());
						if (!SSDac.hasShowAuthority(ssId, mp.getMpId())){
							canAccess = false;
							logger.warn("厂商用户"+mp.getMpName()+":"+mp.getMpPhone()+"尝试查看非己方资源");
						}
					} catch (Exception e) {
					}
				}
				//这里查看的是ser
				String serIdStr=request.getParameter("serId");
				if(serIdStr!=null){
					try{
						long serId=Long.parseLong(serIdStr.trim());
						if(!SERDac.hasShowAuthority(serId, mp.getMpId())){
							canAccess = false;
							logger.warn("厂商用户"+mp.getMpName()+":"+mp.getMpPhone()+"尝试查看非己方资源");
						}
					}catch(Exception e){						
					}
				}
				//下面需要依次核实其他服务
				
				
				if(canAccess) chain.doFilter(request, response);
				else{
					HttpServletResponse httpServletResponse=(HttpServletResponse)response;
					httpServletResponse.setCharacterEncoding("utf-8");
					httpServletResponse.setContentType("text/html;charset=utf8");
					PrintWriter out=httpServletResponse.getWriter();
					out.print("<script language='javascript'>alert('您无权对该资源进行访问！');window.history.back();</script>");
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	public void destroy() {
	}
}
