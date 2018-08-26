package com.dongdao.gqwl;


import com.dongdao.gqwl.utils.URLUtils;

public final class UserConstants {

  public static final String CRMURL= URLUtils.getUrlMap().get("webUrl")+"/";

  public static final String UPLOADPATH= URLUtils.getUrlMap().get("upload.path");

  public static final String UPLOADFOLDER= URLUtils.getUrlMap().get("upload.folder");

  public static final String FTPPATH= URLUtils.getUrlMap().get("FTP");

  /**
   * 系统管理员
   */
  public static final String SYS_TOP = "SYS_TOP";

}
