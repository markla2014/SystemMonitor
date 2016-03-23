package com.hyun.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GetEntityClassUtil {

	public static Class getEntityClass(Class c){
		Type type=c.getGenericSuperclass();
		// 判断是否泛型
		if(type instanceof ParameterizedType){
			//返回表示此类型的实际参数的Type 对象数组
			Type[] ptype=((ParameterizedType)type).getActualTypeArguments();
			return (Class)ptype[0];
		}else{
			return Object.class;
		}
	}
}
