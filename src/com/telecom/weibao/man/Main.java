package com.telecom.weibao.man;

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

import com.telecom.weibao.dac.CruisingDac;
import com.telecom.weibao.dac.HMDac;
import com.telecom.weibao.dac.MainStatisticsDac;
import com.telecom.weibao.dac.ManufacturerDac;
import com.telecom.weibao.dac.RegionDac;
import com.telecom.weibao.dac.SERDac;
import com.telecom.weibao.dac.SSDac;
import com.telecom.weibao.dac.TrainingDac;
import com.telecom.weibao.entity.Manufacturer;
import com.telecom.weibao.entity.Region;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.filter.BaseFilter;
import com.telecom.weibao.statistics.SERTypeStatistics;

@WebServlet("/man/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			request.setAttribute("input", input);

			HttpSession session = request.getSession();
			TelecomPersonnel tp = (TelecomPersonnel) session.getAttribute("user");
			input.put("name", tp.getTpName());
			input.put("nonDealed", MainStatisticsDac.getNonReviewedCount(tp));
			
			List<Region> regionList = RegionDac.getRegions();
			input.put("regionList", regionList);
			List<Manufacturer> mfList = ManufacturerDac.getManufacturers();
			input.put("mfList", mfList);

			//维保情况，这里是各类服务支撑的名字和数量
            List<SERTypeStatistics> serTypeList=SERDac.getSERTypeCount(0, 0, 0, 12, 1);
            input.put("serTypeList", serTypeList);
            for(SERTypeStatistics ser:serTypeList){
            	System.out.println(ser.getDemandName()+" "+ser.getTotal());
            }
            
            //维保事件情况，这里是各类服务事件的名字和数量
            List<Map<String,Object>> eventList=new ArrayList<Map<String,Object>>();
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("eventName", "硬件返修");
            map.put("eventCount", HMDac.getHMCount(0, 0, 0, 12, 1));
            eventList.add(map);
            map=new HashMap<String,Object>();
            map.put("eventName", "驻点服务");
            map.put("eventCount",SSDac.getSSCount(0, 0, 0, 12, 1));
            System.out.println(SSDac.getSSCount(0, 0, 0, 12, 1));
            eventList.add(map);
            map=new HashMap<String,Object>();
            map.put("eventName", "服务响应");
            map.put("eventCount",SERDac.getSERCount(0, 0, 0, 12, 1));
            eventList.add(map);
            map=new HashMap<String,Object>();
            map.put("eventName", "巡检");
            map.put("eventCount",CruisingDac.getCruisingCount(0, 0, 0, 12, 1));
            eventList.add(map);
            map=new HashMap<String,Object>();
            map.put("eventName", "培训");
            map.put("eventCount",TrainingDac.getTrainingCount(0, 0, 0, 12, 1));
            eventList.add(map);
            input.put("eventList", eventList);
            
            //得分占比情况，分为low,middle,high
            List<Map<String,Object>> scoreLevelList=MainStatisticsDac.getSatisfactionPercent();
            input.put("scoreLevelList",scoreLevelList);
            
            //未完成事件情况、依次为故障查修、隐患排查和故障查修
            input.put("nonCompletedFault", SERDac.getNonCompletedSERCount("故障查修"));
            input.put("nonCompletedDanger", SERDac.getNonCompletedSERCount("隐患排查"));
            input.put("nonCompletedHm", HMDac.getNonCompletedHMCount());            
            System.out.println(SERDac.getNonCompletedSERCount("故障查修")+" "+SERDac.getNonCompletedSERCount("隐患排查")+" "+HMDac.getNonCompletedHMCount());
						
			request.getRequestDispatcher("/jsp/telmanJsp/indexTelMan.jsp").forward(request, response);			
		} catch (Exception e) {
			e.printStackTrace();
			BaseFilter.checkSessionTimeout(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	}

}
