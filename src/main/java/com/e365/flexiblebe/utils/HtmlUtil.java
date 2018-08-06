package com.e365.flexiblebe.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.e365.flexiblebe.utils.json.JSONUtil;
import com.e365.flexiblebe.utils.json.JsonMapper;
import org.json.JSONException;
import com.e365.flexiblebe.utils.json.JSONUtil;
import com.e365.flexiblebe.utils.json.JsonMapper;

/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>罗泽军<br>
 * <b>日期：</b> Dec 14, 2011 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class HtmlUtil {
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出json格式<br>
	 * <b>作者：</b>罗泽军<br>
	 * <b>日期：</b> Dec 14, 2011 <br>
	 * @param response
	 * @param jsonStr
	 * @throws Exception
	 */
	public static void writerJson(HttpServletResponse response,String jsonStr) {
			writer(response,jsonStr);
	}
	
	public static void writerJson(HttpServletResponse response,Object object){
			try {
				response.setContentType("application/json");
				String str = JSONUtil.toJSONString(object);
//				str = str.replaceAll("null", "\"\"");
				writer(response,str);
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}
	
	public static void writerAppJson(HttpServletResponse response,Object object){
		try {
			response.setContentType("application/json");
			String str = JSONUtil.toJSONString(object);
			str = str.replaceAll("null", "\"\"");
			str = str.replaceAll(" 00:00:00", "");
			writer(response,str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
}
	public static void writerNewJson(HttpServletResponse response,Object object){
		try {
			response.setContentType("application/json");
			writer(response, JsonMapper.toJsonString(object));
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出HTML代码<br>
	 * <b>作者：</b>罗泽军<br>
	 * <b>日期：</b> Dec 14, 2011 <br>
	 * @param response
	 * @param htmlStr
	 * @throws Exception
	 */
	public static void writerHtml(HttpServletResponse response,String htmlStr) {
		writer(response,htmlStr);
	}
	
	private static void writer(HttpServletResponse response,String str){
		try {
			StringBuffer result = new StringBuffer();
			//设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out= null;
			out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
