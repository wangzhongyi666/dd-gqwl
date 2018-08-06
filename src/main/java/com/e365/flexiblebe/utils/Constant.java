package com.e365.flexiblebe.utils;

import java.util.ResourceBundle;

public class Constant {

	private static ResourceBundle res = ResourceBundle.getBundle("sysconfig");
	//网站名称
	public static String SSI_WEBSITE_NAME = res.getString("ssi.website.name");
	//网站域名
	public static String SSI_WEBSITE_DOMAIN = res.getString("ssi.website.domain");
	//网站地址
	public static String SSI_WEBSITE_URL = res.getString("ssi.website.url");
	

	
	
	

	/**
	 * 超级管理员常量
	 * @author lu
	 *
	 */
	public static enum SuperAdmin {
		NO(0, "否"), YES(1,"是");
		public int key;
		public String value;
		private SuperAdmin(int key, String value) {
			this.key = key;
			this.value = value;
		}
		public static SuperAdmin get(int key) {
			SuperAdmin[] values = SuperAdmin.values();
			for (SuperAdmin object : values) {
				if (object.key == key) {
					return object;
				}
			}
			return null;
		}
	}

}