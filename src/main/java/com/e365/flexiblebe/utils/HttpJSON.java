package com.e365.flexiblebe.utils;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;


import org.apache.log4j.Logger;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpJSON {
	private final static Logger log= Logger.getLogger(HttpJSON.class);
    /**
     * @param args
     */
    public static void main(String[] args) {
       /* String url = "http://192.168.1.110/lepin_pc//crm/data.php?act=apply&job_id=5029";
        Date d=new Date();
        System.out.println(d);
        JSONObject json = loadJSON(url);
        if(json!=null){
        	String data=json.getString("data").toString();
        	JSONArray jsonArray = JSONArray.fromObject(data);
        	List<Map<String,Object>> mapListJson = (List)jsonArray; //转list
        	for(Map<String,Object> map:mapListJson){
        		String datainfo=map.get("time").toString();
        		String list =map.get("list").toString();
        		if(!list.equals("null")){
        			list=list.substring(1,list.length()-1);
        			JSONObject jsonlist = JSONObject.fromObject(list);
        			System.out.println(jsonlist.getString("fullname"));
        		}
        	}
        }
        Date d1 =new Date();
        System.out.println(d1);*/
        
    	
    }
    
 
    public static  JSONObject loadJSON (String url) {
        JSONObject jsonObject = null;
        String json=loadJSONString(url);
        String json1=json.substring(json.indexOf("{"));
        System.out.println(json);
        if(json.equals("")||json==null){
        }else{
        	jsonObject = JSONObject.fromObject(json1);
        }
        return jsonObject;
    }
    
    public static String loadJSONString(String url){
    	System.out.println("借口地址=============="+url);
    	log.info("借口地址=============="+url);
    	 StringBuilder json = new StringBuilder();
         try {
             URL oracle = new URL(url);
             URLConnection yc = oracle.openConnection();
             BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(),"utf-8"));
             String inputLine = null;
             while ( (inputLine = in.readLine()) != null) {
                 json.append(inputLine);
             }
             in.close();
             System.out.println("借口返回=============="+json.toString());
         	log.info("借口返回=============="+json.toString());
             return json.toString();
         } catch (Exception e) {
        	 e.printStackTrace();
        	 return "";
         }
        
         
    }
    
}