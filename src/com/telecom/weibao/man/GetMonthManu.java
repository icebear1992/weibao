package com.telecom.weibao.man;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jmx.snmp.Timestamp;
import com.telecom.weibao.dac.MainStatisticsDac;
import com.telecom.weibao.filter.BaseFilter;
import com.telecom.weibao.tools.FileExport;
import com.telecom.weibao.tools.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@WebServlet("/man/GetMonthManu")
public class GetMonthManu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	// 获取ajax请求，返回json数据
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Map<String, Object>> monManuInfoList = getMonthManuInfoList(request);
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf8");
			// 开始输出jason格式数据
			PrintWriter out = response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor());
			JSONArray jsonarr = JSONArray.fromObject(monManuInfoList, jsonConfig);
			// new
			int page = Integer.parseInt(request.getParameter("page"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonout = new JSONArray();
			for (int i = (page - 1) * limit; i < page * limit && i < jsonarr.size(); i++) {
				jsonout.add(jsonarr.get(i));
			}
			jsonObject.put("data", jsonout);
			jsonObject.put("code", 0);
			jsonObject.put("count", jsonarr.size());
			jsonObject.put("msg", "");
			out.print(jsonObject);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}

	// 导出数据
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("octets/stream");
		String excelName = "月度报表(按厂商)";
		// 转码防止乱码
		response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(excelName.getBytes("gb2312"), "ISO8859-1") + ".xls");
		String[] headers = new String[] { "月份", "厂商", "驻点服务(人*日)", "巡检覆盖设备数", "巡检覆盖业务节点数", "培训时长(人*时)", "方案咨询次数",
				"完结故障处理数", "未完结故障处理数", "硬件返修批次(新增)", "硬件返修批次(历史)", "硬件返修平均历时", "紧急备件服务次数" };
		String[] keys = new String[] { "time", "manufacturer_name", "serpersonnel", "totalequipment",
				"totalenginerroom", "trainingduration", "consultation", "completion", "incompletion", "ssum", "insuum",
				"avgtime", "sprcount" };
		try {
			OutputStream out = response.getOutputStream();
			List<Map<String, Object>> monManuInfoList = getMonthManuInfoList(request);
			FileExport.exportExcel(excelName, headers, monManuInfoList, keys, out, "yyyy-MM-dd");
			out.close();
			System.out.println("excel导出成功！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 工具函数，根据request获取查询结果，返回List<Map<String,Object>>
	private List<Map<String, Object>> getMonthManuInfoList(HttpServletRequest request) {
		String time = request.getParameter("time");
		String manu = request.getParameter("manu");
		System.out.println(manu);
		System.out.println(time);
		String[] timeSplit = null;
		String beginYear = null;
		String beginMonth = null;
		String endYear = null;
		String endMonth = null;
		if (time!=null&&time.length()==17) {
			timeSplit = time.split("-");
			beginYear = timeSplit[0].trim();
			beginMonth = timeSplit[1].trim();
			endYear = timeSplit[2].trim();
			endMonth = timeSplit[3].trim();
		}
		if (beginYear == null || !beginYear.matches("[0-9]{4}") || Integer.parseInt(beginYear) < 1970)
			beginYear = "1970";
		if (beginMonth == null || !beginMonth.matches("[0-9]{2}") || Integer.parseInt(beginMonth) > 12)
			beginMonth = "01";
		if (endYear == null || !endYear.matches("[0-9]{4}") || Integer.parseInt(endYear) > 2099)
			endYear = "2099";
		if (endMonth == null || !endMonth.matches("[0-9]{2}") || Integer.parseInt(endMonth) > 12)
			endMonth = "12";
		String beginTime = beginYear + beginMonth;
		String endTime = endYear + endMonth;
		System.out.println(beginTime);
		System.out.println(endTime);
		return MainStatisticsDac.getMonthManuInfos(beginTime, endTime, manu);
	}

}
