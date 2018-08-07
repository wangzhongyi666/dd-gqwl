package com.dongdao.gqwl.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class Md5Util {
	static long seed = System.currentTimeMillis();

	static long skip = Long.parseLong("187649984473770");

	static char ch[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N',
			'O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n',
			'o','p','q','r','s','t','u','v','w','x','y','z','@','#','!','~','?',':','-','=',};

	 	public static String createPwd(){//随机生成6位数字密码
	    	Random random = new Random(); 
	        String result="";

	        for(int i=0;i<6;i++){
	            result+=random.nextInt(10);    
	        }
			return result;
	    }

	    public static String randstr(int length)  {//随机生成几位字符串
	    	if(length>62 || length<=0){
	    	throw new IllegalArgumentException();
	    	}else if(length == 62){
	    	length--;
	    	}	

	    	Random r = new Random(seed);
	    	int rNum;
	    	char temp;	
	    	for(int i=0; i<length; i++){
	    	rNum = r.nextInt(62);
	    	seed += skip;
	    	r.setSeed(seed);
	    	if(rNum < i){
	    	rNum = i+1;
	    	}
	    	temp = ch[i];
	    	ch[i] = ch[rNum];
	    	ch[rNum] = temp;	
	    	}
	    	return new String(ch,0,length);
	    }
	    
	    public static String md5(String plainText) {//php md5方法
	        byte[] secretBytes = null;
	        try {
	            secretBytes = MessageDigest.getInstance("md5").digest(
	                    plainText.getBytes());
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("没有md5这个算法！");
	        }
	        String md5code = new BigInteger(1, secretBytes).toString(16);
	        for (int i = 0; i < 32 - md5code.length(); i++) {
	            md5code = "0" + md5code;
	        }
	        return md5code;
	    }
	    
		public static String getRemortIP(HttpServletRequest request) {     
			   /* if (request.getHeader("x-forwarded-for") == null) {     
			        return request.getRemoteAddr();     
			    }     
			    return request.getHeader("x-forwarded-for");*/   
				String ip = request.getHeader("x-forwarded-for");
				if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("Proxy-Client-IP");
				}
				if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("WL-Proxy-Client-IP");
				}
				if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getRemoteAddr();
				}
				return ip;
			} 
	    
	    public static void main(String[] args) {
	    	//e_eid=e123&e_uid=u123&ident=jf&scene=my_order&timestamp=14000000
	    	String e_eid="";
	    	String e_uid="";
	    	String ident="";
	    	String scene="";
	    	
	    	String KEY = "";
	    	int timestamp = DateUtil.getSjc();
	    	String a = "e_eid="+e_eid+"&e_uid="+e_uid+"&ident="+ident+"&scene="+scene+"&timestamp="+timestamp;
	    	
	    	System.out.println(md5(a+KEY));
	    	/*String pwd = "123456";
	    	String md51=md5(pwd);
	    	System.out.println("md5第一次加密后为"+md51);
	    	String pwd_hash="J0OgCl";
	    	System.out.println("pwd_hash is "+pwd_hash);
	    	System.out.println("LP_pwdhash is "+UserConstants.LP_pwdhash);
	    	String fpwd=md5(md51+pwd_hash+UserConstants.LP_pwdhash);
	    	System.out.println("存入数据库最终密码为:"+fpwd);*/
		}
}
