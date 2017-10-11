package com.telecom.weibao.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.telecom.weibao.dac.HMDac;
import com.telecom.weibao.dac.MainStatisticsDac;
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.entity.HM;
import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.SER;
import com.telecom.weibao.entity.ShowThing;
import com.telecom.weibao.filter.BaseFilter;
import com.telecom.weibao.tools.ListSort;
import com.telecom.weibao.tools.ShowThingList;

@WebServlet("/provider/ProviderMain")
public class ProviderMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			ManufacturerPersonnel mp = (ManufacturerPersonnel) session.getAttribute("user");
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			//状态提示码获取
			int msg = 0;
			if(request.getParameter("msg")!=null){
				 msg = Integer.parseInt(request.getParameter("msg"));
			}
			input.put("msg",msg);
			input.put("name", mp.getMpName());
			input.put("manufacturer", mp.getManufacturer());
			input.put("nonDealed", MainStatisticsDac.getNonDealedCount(mp));
			input.put("nonFin", MainStatisticsDac.getNonFinCount(mp));
			input.put("nonReview", MainStatisticsDac.getNonReviewCount(mp));
			input.put("nonAudit", MainStatisticsDac.getNonAuditCount(mp));
			//各类服务————被退回
			List<ShowThing> returnList = new ArrayList<ShowThing>();
			List<HM> hmNonDealedList = HMDac.getNonDealedHMs(mp);
			List<SER> serNonDealedList=SERDac.getNonDealedSERs(mp);
			ShowThingList.transHM(hmNonDealedList,returnList);
			ShowThingList.transSer(serNonDealedList,returnList);
			ListSort.ListSort(returnList);
			input.put("returnList", returnList);
			
			//各类服务————未完成
			List<ShowThing> unfinList = new ArrayList<ShowThing>();
			List<HM> hmUnfinList = HMDac.getUnfinishedHMs(mp);
			List<SER> serUnfinList=SERDac.getUnfinishedSERs(mp);
			ShowThingList.transHM(hmUnfinList, unfinList);
			ShowThingList.transSer(serUnfinList, unfinList);
			ListSort.ListSort(unfinList);
			input.put("unfinList", unfinList);
			//各类服务————待点评
			List<ShowThing> noreviewList = new ArrayList<ShowThing>();
			List<HM> hmNonReviewedList = HMDac.getNonReviewedHMs(mp);
			List<SER> serNonReviewedList=SERDac.getNonReviewedSERs(mp);
			ShowThingList.transHM(hmNonReviewedList, noreviewList);
			ShowThingList.transSer(serNonReviewedList, noreviewList);
			ListSort.ListSort(noreviewList);
			input.put("noreviewList", noreviewList);
			//各类服务————待审核
			List<ShowThing> noauditList = new ArrayList<ShowThing>();
			List<HM> hmNonAuditList = HMDac.getNonAuditedHMs(mp);
			ShowThingList.transHM(hmNonAuditList, noauditList);
			List<SER> serNonAuditList = SERDac.getNonAuditedSERs(mp);
			ShowThingList.transSer(serNonAuditList, noauditList);
			ListSort.ListSort(noauditList);
			input.put("noauditList",noauditList);
            //选择页面展示
			request.getRequestDispatcher("/jsp/manuJsp/indexManu.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
