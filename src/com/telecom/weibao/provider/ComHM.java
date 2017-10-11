package com.telecom.weibao.provider;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.telecom.weibao.dac.HMDac;
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.entity.ManufacturerPersonnel;

@WebServlet("/provider/completeHM.do")
public class ComHM extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long hmId = Long.parseLong(request.getParameter("hmId"));
		HttpSession session = request.getSession();
		ManufacturerPersonnel mp = (ManufacturerPersonnel) session.getAttribute("user");
		if (HMDac.isMpdo(hmId, mp.getMpId())){
			Timestamp comTime = new Timestamp(System.currentTimeMillis());
			HMDac.comHMtran(hmId,comTime);
			response.sendRedirect(request.getServletContext().getContextPath()+"/provider/ViewHM?HMId="+hmId+"&msg=200");
			//response.sendRedirect(request.getServletContext().getContextPath()+"/provider/ProviderMain?msg=200&msgId="+serId); 	//重新回到主页
		}else{
			//没有完成竣工
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			input.put("errorMsg", hmId+"号工单竣工未成功，可能因为您没有该工单的操作权限");
			request.getRequestDispatcher("/jsp/errorJsp/error.jsp").forward(request, response);
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
