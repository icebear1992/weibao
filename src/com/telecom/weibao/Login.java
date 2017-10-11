package com.telecom.weibao;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.telecom.weibao.dac.UserDac;
import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.TelecomPersonnel;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("tele_login.html").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String account=request.getParameter("account");
			String password=request.getParameter("password");
			if (account != null && password != null) {
				account = account.trim();
				password = password.trim();
				ManufacturerPersonnel mp = UserDac.getManufacturerPersonnel(account, password);
				if (mp != null) {// 厂家人员登陆成功
					HttpSession session = request.getSession();
					session.setAttribute("userType", 0);
					session.setAttribute("user", mp);
					// 接下来到厂家首页
					response.sendRedirect(request.getContextPath() + "/provider/ProviderMain");
				} else {
					TelecomPersonnel tp = UserDac.getTelecomPersonnel(account, password);
					if (tp != null) {// 电信人员登陆成功
						HttpSession session = request.getSession();
						session.setAttribute("userType", 1);
						session.setAttribute("user", tp);
						int role = tp.getTpRole();
						if (role == 1) {
							// 接下来到一线维护人员页面
							response.sendRedirect(request.getContextPath() + "/user/Pendings");
						} else if (role == 2) {
							// 接下来到管理员页面
							response.sendRedirect(request.getContextPath() + "/man/Main");
						} else if (role == 3) {
							// 接下来到超级用户页面
							response.sendRedirect(request.getContextPath() + "/admin/Admin");
						}
					} else {// 登陆失败，定向到登陆页面
						request.setAttribute("error", "您输入的信息有误！");
						request.getRequestDispatcher("tele_login.html").forward(request, response);
					}
				}

			} else {// 用户名或者密码为空
				request.setAttribute("error", "用户名密码不可为空！");
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
