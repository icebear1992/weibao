package com.telecom.weibao.tools;

import java.util.ArrayList;
import java.util.List;

import com.telecom.weibao.entity.HM;
import com.telecom.weibao.entity.SER;
import com.telecom.weibao.entity.ShowThing;

public class ShowThingList {

	public static List<ShowThing> transHM(List<HM> hmList,List<ShowThing> shList) {
		if(hmList!=null&&hmList.size()>0){
			for (HM hm : hmList) {
				ShowThing st=new ShowThing();
				st.setAuditor(hm.getAuditor().getTpName());
				st.setCreatTime(hm.getCreatTime());
				st.setSubRegion(hm.getSubRegion().getSubRegionName());
				st.setType("硬件返修");
				String title = hm.getTitle();
				if(title!=null&&title.length()>12){
					title = title.substring(0, 12);
				}
				st.setTitle(title);
				st.setNumber(hm.getHmId());
				shList.add(st);
			}
		}
		return shList;
	}
	public static List<ShowThing> transSer(List<SER> serList,List<ShowThing> shList) {
		if(serList!=null&&serList.size()>0){
			for (SER ser : serList) {
				ShowThing st=new ShowThing();
				st.setAuditor(ser.getAuditor().getTpName());
				st.setCreatTime(ser.getCreatTime());
				st.setSubRegion(ser.getSubRegion().getSubRegionName());
				st.setType("服务响应");
				String title = ser.getTitle();
				if(title!=null&&title.length()>12){
					title = title.substring(0, 12);
				}
				st.setTitle(title);
				st.setNumber(ser.getSerId());
				shList.add(st);
			}
		}
		return shList;
	}

}
