package com.sxl;

import java.util.HashMap;
import java.util.Map;

import com.jason.framework.mvc.spring.BaseController;

/* JSP毕业设计学习	www.cq55.com */
public class MyController extends BaseController {
	
	
	
	public String initSql(String sql,Object[] list){
		
		if(sql.endsWith("?")){
			String[] aa = sql.split("\\?");
			String res="";
			System.out.println(aa.length+"***aaaa");
			System.out.println(list.length+"***list");
			for (int i = 0; i < aa.length; i++) {
				res+=(aa[i]+"'"+list[i]+"'");
			}
			return res;
		}else{
			String[] aa = sql.split("\\?");
			String res="";
			System.out.println(aa.length+"***aaaa");
			System.out.println(list.length+"***list");
			for (int i = 0; i < list.length; i++) {
				res+=(aa[i]+"'"+list[i]+"'");
			}
			res+=aa[aa.length-1];
			return res;
		}
		
		
	}
	
	
	public Map ajaxMap(boolean is,String msg,Object data){
		Map map = new HashMap();
		map.put("status", is);
		map.put("msg", msg);
		map.put("data", data);
		return map;
	}
	

	public static void main(String[] args) {
		MyController mc = new MyController();
		String sql="insert into t_customer(name,password,mobile,sex,address,insertDate,status) values(?,?,?,?,?,now(),1)";
		String aaa = mc.initSql(sql, new Object[]{"1","2","3","4","5"});
		System.out.println(aaa);
		
//		System.out.println(sql.endsWith("?"));
	}
	
	

}
