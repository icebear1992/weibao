package com.telecom.weibao.randomSimTool;

import java.util.Random;

import cern.jet.random.Distributions;
import cern.jet.random.Normal;
import cern.jet.random.engine.RandomEngine;

// 测试正态分布

public class normalDesRandom {
	public static void main(String[] arg) {
		
		
		RandomEngine re = new cern.jet.random.engine.MersenneTwister(new java.util.Date());
		
		 for (int i=0; ++i <=1000; ) {
		    double cauchy = 0;
		    //cauchy = Math.abs(cauchy);

		    do {
		    	cauchy = new Normal(1,0.6,re).nextDouble();
		    }while (cauchy<=0);
		    
		    System.out.println(cauchy);
		    
		}

		
		
	}
}
