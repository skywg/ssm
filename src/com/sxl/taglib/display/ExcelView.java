package com.sxl.taglib.display;



/* JSP毕业设计学习	www.cq55.com */
public class ExcelView extends org.displaytag.export.ExcelView{

	@Override
	public String getMimeType() {
		 return "application/vnd.ms-excel;charset=UTF-8";
	}

	
}
