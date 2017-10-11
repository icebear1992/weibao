package com.telecom.weibao.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.TelecomPersonnel;


public class BaseFilter {
	
	/* 
	 * 获取session中userType属性，
	 * -1代表该属性为空,即未登陆
	 * 0代表厂家人员
	 * 1代表电信人员
	 */
	public static int getUserType(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("userType")==null)
        	return -1;
        return (int)(session.getAttribute("userType"));
	}
	
	//获取厂商用户
	public static ManufacturerPersonnel getMpUser(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        return (ManufacturerPersonnel)session.getAttribute("user");
	}
	
	//获取电信用户
	public static TelecomPersonnel getTpUser(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        return (TelecomPersonnel)session.getAttribute("user");
	}
	
	//检查session是否过期，用于在出现异常时检测会话是否过期，如果过期则跳转到登陆页面
	public static void checkSessionTimeout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		try {
			if (session.getAttribute("user") == null) {
				response.sendRedirect(request.getServletContext().getContextPath() + "/Login");
			} 
		} catch (Exception e) {
		}		
	}
	
	//获取客户端真实IP
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		String ip = request.getRemoteAddr();

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		return ip;
	}
}
