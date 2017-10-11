package com.telecom.weibao.user;

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
import com.telecom.weibao.dac.RegionDac;
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.dac.SSDac;
import com.telecom.weibao.entity.HM;
import com.telecom.weibao.entity.SER;
import com.telecom.weibao.entity.SS;
import com.telecom.weibao.entity.ShowThing;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.filter.BaseFilter;
import com.telecom.weibao.tools.ListSort;
import com.telecom.weibao.tools.ShowThingList;

@WebServlet("/user/Pendings")
public class Pendings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			TelecomPersonnel tp = (TelecomPersonnel) session.getAttribute("user");
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);
			input.put("region",RegionDac.getRegionNameBySubId(tp.getSubRegion().getSubRegionId()));
			input.put("name", tp.getTpName());
			input.put("nonReviewNum", MainStatisticsDac.getNonReviewedCount(tp));	//未点评
			input.put("nonAuditNum", MainStatisticsDac.getNonAuditedCount(tp));	//未审核
			input.put("nonFinNum", MainStatisticsDac.getUncompleteCount(tp));	//未完成
			input.put("returnNum", MainStatisticsDac.getReturnCount(tp));	//被退回
			//下面依次获取各类服务的待办清单和自身提交或者退回的
			List<ShowThing> noreviewList = new ArrayList<ShowThing>();
			List<HM> hmNonReviewedList = HMDac.getNonReviewedHMs(tp);
			List<SER> serNonReviewedList=SERDac.getNonReviewedSERs(tp);
			ShowThingList.transHM(hmNonReviewedList, noreviewList);
			ShowThingList.transSer(serNonReviewedList, noreviewList);
			ListSort.ListSort(noreviewList);
			input.put("noreviewList", noreviewList);			//未点评list
			
			List<ShowThing> noauditList = new ArrayList<ShowThing>();
			List<HM> hmNonAuditList = HMDac.getNonAuditedHMs(tp);
			List<SER> serNonAuditList=SERDac.getNonAuditedSERs(tp);
			ShowThingList.transHM(hmNonAuditList, noauditList);
			ShowThingList.transSer(serNonAuditList, noauditList);
			ListSort.ListSort(noauditList);
			input.put("noauditList",noauditList);				//未审核list
			
			List<ShowThing> returnList = new ArrayList<ShowThing>();
			List<HM> hmRelatedList=HMDac.getRelateReturnedHMs(tp);
			List<SER> serRelatedList=SERDac.getRelateReturnedSERs(tp);
			ShowThingList.transHM(hmRelatedList,returnList);
			ShowThingList.transSer(serRelatedList,returnList);
			ListSort.ListSort(returnList);
			input.put("returnList", returnList);				//被退回list
			
			List<ShowThing> unCompletedList = new ArrayList<ShowThing>();
			List<HM> hmUnCompletedList = HMDac.getRelateUnCompletedHMs(tp);
			List<SER> serUnCompletedList=SERDac.getRelateUnCompletedSERs(tp);
			ShowThingList.transHM(hmUnCompletedList, unCompletedList);
			ShowThingList.transSer(serUnCompletedList, unCompletedList);
			ListSort.ListSort(unCompletedList);
			input.put("unCompletedList", unCompletedList);					//未完成list
			
//			List<SS> ssNonReviewedList=SSDac.getNonReviewedSSs(tp);
//			List<SS> ssNonVerifyList=SSDac.getNonVerifySSs(tp);

			//选择页面展示
			request.getRequestDispatcher("/jsp/telopJsp/indexTelop.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
