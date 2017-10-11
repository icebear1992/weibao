package com.telecom.weibao.tools;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.telecom.weibao.entity.ShowThing;

public class ListSort {
	public static void ListSort(List<ShowThing> list){
		Collections.sort(list,new Comparator<ShowThing>(){

			@Override
			public int compare(ShowThing o1, ShowThing o2) {
				if(o1.getCreatTime().getTime()<o2.getCreatTime().getTime()){
					return 1;
				}else if(o1.getCreatTime().getTime()>o2.getCreatTime().getTime()){
					return -1;
				}else{
					return 0;
				}
			}
			
		});
	}
}
