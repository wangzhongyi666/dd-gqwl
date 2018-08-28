package com.dongdao.gqwl.utils;

import java.util.HashMap;
import java.util.Map;

public class VerifyFormat {
	public final static Map<String, Boolean> FILE_TYPE_MAP = new HashMap<String, Boolean>();
	
	static {
		getAllFileType(); // 初始化文件类型信息
	}

	public VerifyFormat() {
		super();
	}

	private static void getAllFileType() {
		//图片
		FILE_TYPE_MAP.put("jpg", true); 
        FILE_TYPE_MAP.put("png", true);      
        FILE_TYPE_MAP.put("gif", true);      
        FILE_TYPE_MAP.put("bmp", true);      
        FILE_TYPE_MAP.put("JPEG",true);
        //office ： word、ppt 和 excel 
        FILE_TYPE_MAP.put("doc", true); 
        FILE_TYPE_MAP.put("docm", true); 
        FILE_TYPE_MAP.put("docx", true);   
        FILE_TYPE_MAP.put("dotx", true);     
        FILE_TYPE_MAP.put("dotm", true); 
        FILE_TYPE_MAP.put("xlsx", true); 
        FILE_TYPE_MAP.put("xls", true);
        FILE_TYPE_MAP.put("xlsm", true); 
        FILE_TYPE_MAP.put("xltx", true); 
        FILE_TYPE_MAP.put("xltm", true); 
        FILE_TYPE_MAP.put("xlsb", true); 
        FILE_TYPE_MAP.put("xlam", true); 
        FILE_TYPE_MAP.put("ppt",  true);  
        FILE_TYPE_MAP.put("pptm", true);  
        FILE_TYPE_MAP.put("potx", true);  
        FILE_TYPE_MAP.put("potm", true);    
        FILE_TYPE_MAP.put("ppam", true);   
        FILE_TYPE_MAP.put("ppsx", true);   
        FILE_TYPE_MAP.put("ppsm", true); 
        FILE_TYPE_MAP.put("sldx", true);
        FILE_TYPE_MAP.put("sldm", true);
        FILE_TYPE_MAP.put("thmx", true);
        //音频
        FILE_TYPE_MAP.put("mp3", true);//xml文件
        FILE_TYPE_MAP.put("wma", true);//xml文件
        FILE_TYPE_MAP.put("waf", true);//java文件
        //视频
        FILE_TYPE_MAP.put("mp4", true);//bat文件
        FILE_TYPE_MAP.put("avi", true);//bat文件
        //压缩文件
        FILE_TYPE_MAP.put("zip", true);//gz文件
        FILE_TYPE_MAP.put("rar", true);//bat文件
        //pdf
        FILE_TYPE_MAP.put("pdf", true);//bat文件
	}

	public static boolean verifyFormat(String fileType) {
        return FILE_TYPE_MAP.get(fileType);
	}

}
