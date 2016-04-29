package com.hyun.common;

import com.google.gson.Gson;
import com.google.gson.JsonNull;

public class JasonCover {
	 private static Gson gson;
	 public JasonCover(){
	  gson=new Gson();
	 }
		public static String toJason(Object o){
			if(o==null){
				return gson.toJson(JsonNull.INSTANCE);
			}
			return gson.toJson(o);
		}
		public static <T> T fromJson(String str, Class<T> type) {  
	        Gson gson = new Gson();  
	        return gson.fromJson(str, type);  
	    }  
}
