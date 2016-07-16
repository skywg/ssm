package com.sxl.controller;


import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jason.framework.util.FileUtil;
import com.jason.framework.util.JacksonJsonUtil;
import com.sxl.MyController;
import com.sxl.util.Uploader;

@Controller("toolFileController")
@RequestMapping(value = "/file")
/* JSP毕业设计学习	www.cq55.com */
public class FileController extends MyController {
	/**
	 * 上传文件
	 * @param request
	 * @param cmfile
	 * @param fsize
	 * @return
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Map upload(HttpServletRequest request,@RequestParam("cmfile") CommonsMultipartFile cmfile, Integer fsize) {
		System.out.println("*******uplaod**********");
		Integer filesize = fsize;
		if(fsize != null) {
			filesize = filesize * 1024 * 1024;
		}else {
			fsize = 10;
			filesize = 1024*1024*10;
		}
		
		if(cmfile!=null && !cmfile.isEmpty()){
			try {
				if(cmfile.getSize() < filesize) {
					String filename = cmfile.getOriginalFilename();  
					String suffix = filename.substring(filename.lastIndexOf(".")); 
					String newPath="";
					Uploader up = new Uploader(request);
					String folder =  up.getFolder("upload");
					
					if ((".gif.png.jpg.jpeg.bmp".indexOf(suffix.toLowerCase()) >=0)) {  
						newPath=folder+"/"+FileUtil.newFileName(cmfile.getOriginalFilename());
					}else{
						newPath=folder+"/"+FileUtil.newFileName(cmfile.getOriginalFilename());
					}
					File file=new File(request.getSession().getServletContext().getRealPath("/")+newPath);
					if(!file.exists()){
						file.createNewFile();
					}
					cmfile.getFileItem().write(file);
					return ajaxMap(true,new uploadFile(cmfile.getSize(),filename, newPath, suffix).getFilepath(), null);
				} else {
					System.out.println("===================2====================");
					return ajaxMap(false,"文件大小不能超过"+fsize+"M",null);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("======================3=================");
			}
		}
		System.out.println("=============================4==========");
		return ajaxMap(false,"上传失败",null);
	}
	
	
	
	
	/**
	 * 下载文件
	 * @param request
	 * @param response
	 * @param fileName
	 * @throws IOException
	 */
	 @RequestMapping(value = "/download")  
	 public void download(HttpServletRequest request,HttpServletResponse response,String fileName) throws IOException { 
		 String nowPath = request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"pdf"+File.separator+fileName;
	     File file = new File(nowPath); 
         response.reset(); 
         response.addHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("gbk"),"iso-8859-1"));  //转码之后下载的文件不会出现中文乱码
         response.addHeader("Content-Length", "" + file.length()); 
	     try{ 
	         //以流的形式下载文件 
	         InputStream fis = new BufferedInputStream(new FileInputStream(nowPath)); 
	         byte[] buffer = new byte[fis.available()]; 
	         fis.read(buffer); 
	         fis.close(); 
	         OutputStream toClient = new BufferedOutputStream(response.getOutputStream()); 
	         toClient.write(buffer); 
	         toClient.flush(); 
	         toClient.close(); 
	     }catch(Exception e){ 
	         e.printStackTrace(); 
	     } 
	 } 
	
	
	 class uploadFile {
			Long filesize;
			String filename;
			String suffix;
			String filepath;
			String suoImgUrl;
				
			public uploadFile(Long filesize, String filename,String filepath , String suffix) {
				super();
				this.filesize = filesize;
				this.filename = filename;
				this.filepath=filepath;
				this.suffix = suffix;
			}
			
			public uploadFile(Long filesize, String filename,String filepath,String suoImgUrl, String suffix) {
				super();
				this.filesize = filesize;
				this.filename = filename;
				this.filepath=filepath;
				this.suffix = suffix;
				this.suoImgUrl = suoImgUrl;
			}
			
			public String getFilepath() {
				return filepath;
			}
			public void setFilepath(String filepath) {
				this.filepath = filepath;
			}
			public Long getFilesize() {
				return filesize;
			}
			public void setFilesize(Long filesize) {
				this.filesize = filesize;
			}
			public String getFilename() {
				return filename;
			}
			public void setFilename(String filename) {
				this.filename = filename;
			}
			public String getSuffix() {
				return suffix;
			}
			public void setSuffix(String suffix) {
				this.suffix = suffix;
			}

			public String getSuoImgUrl() {
				return suoImgUrl;
			}

			public void setSuoImgUrl(String suoImgUrl) {
				this.suoImgUrl = suoImgUrl;
			}
			
		}
}
