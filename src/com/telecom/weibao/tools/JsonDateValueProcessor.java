package com.telecom.weibao.tools;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor{
      
    public Object processArrayValue(Object value, JsonConfig config) {     
        return process(value);     
    }     
    
    public Object processObjectValue(String key, Object value, JsonConfig config) {     
        return process(value);     
    }     
         
    private Object process(Object value){     
             
        if(value instanceof Date){     
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");     
            return sdf.format(value);     
        } 
        if(value instanceof Timestamp){
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        	return sdf.format(new java.util.Date(((Timestamp) value).getTime()));
        }
        return value == null ? "" : value.toString();     
    }     

}
