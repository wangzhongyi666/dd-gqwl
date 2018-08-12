package com.dongdao.gqwl.action;

import com.dongdao.gqwl.UserConstants;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.MyEditor;
import com.dongdao.gqwl.utils.URLUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BaseAction {

    public final static String SUCCESS ="success";

    public final static String MSG ="msg";

    public final static String DATA ="data";

    public final static String LOGOUT_FLAG = "logoutFlag";

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(int.class,new MyEditor());
    }

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 所有ActionMap 统一从这里获取
     * @return
     */
    public Map<String,Object> getRootMap(){
        Map<String,Object> rootMap = new HashMap<String, Object>();
        //添加url到 Map中
        rootMap.putAll(URLUtils.getUrlMap());
        return rootMap;
    }

    public ModelAndView forword(String viewName, Map context){
        return new ModelAndView(viewName,context);
    }

    public ModelAndView error(String errMsg){
        return new ModelAndView("error");
    }

    /**
     *
     * 提示成功信息
     *
     * @param message
     *
     */
    public void sendSuccessMessage(HttpServletResponse response, String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(SUCCESS, true);
        result.put(MSG, message);
        HtmlUtil.writerJson(response, result);
    }

    /**
     *
     * 提示失败信息
     *
     * @param message
     *
     */
    public void sendFailureMessage(HttpServletResponse response,String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(SUCCESS, false);
        result.put(MSG, message);
        HtmlUtil.writerJson(response, result);
    }

    public Map<String, Object> setSuccessMap(Map<String, Object> jsonMap,
                                             String msg, Object data) {
        jsonMap.put("result_code", 1);
        jsonMap.put("result_msg", msg);

        if (data != null)
            jsonMap.put("data", data);
        return jsonMap;
    }

    public Map<String, Object> setFailureMap(Map<String, Object> jsonMap,
                                             String msg, Object data) {
        jsonMap.put("result_code", 0);
        jsonMap.put("result_msg", msg);
        if (data != null)
            jsonMap.put("data", data);
        return jsonMap;
    }


    public Map<String, Object> setSuccessMap1(Map<String, Object> jsonMap,
                                             String msg, Object data) {
        jsonMap.put("code", 1);
        jsonMap.put("msg", msg);

        if (data != null)
            jsonMap.put("subCd", data);
        return jsonMap;
    }

    public Map<String, Object> setFailureMap1(Map<String, Object> jsonMap,
                                             String msg, Object data) {
        jsonMap.put("code", -1);
        jsonMap.put("msg", msg);
        if (data != null)
            jsonMap.put("subCd", data);
        return jsonMap;
    }


    public String uploadFile(String folder, MultipartFile file,HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        String imgName = "";
        String imageNamePath = "";
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (notNull(myFileName)) {
                        // 获取图片的扩展名
                        String extensionName = myFileName.substring(myFileName.lastIndexOf(".") + 1);

                        if (!(extensionName.equals("jpg") || extensionName.equals("png") || extensionName.equals("bmp") || extensionName.equals("gif")
                                || extensionName.equals("zip"))) {
                            return "格式错误";
                        }

                        // 重命名上传后的文件名
                        String fileName = EncoderByMd5(file.getOriginalFilename()+System.currentTimeMillis());
                        // 定义上传路径
                        File localFile;
                        try {
                            imageNamePath = UserConstants.UPLOADPATH + File.separator
                                    + UserConstants.UPLOADFOLDER + File.separator + folder;
							/*imageNamePath="d:\\zgyy" + File.separator
									+ "uploads" + File.separator + folder;*/
                            localFile = new File(imageNamePath);
                            // 判断文件夹
                            if (!localFile.exists()) {// 如果目录不存在
                                localFile.mkdirs();// 创建文件夹
                            }
                            localFile = new File(imageNamePath + File.separator + fileName + "." + extensionName);
                            System.err.println(imageNamePath);
                            file.transferTo(localFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // 图片名字拼接
                        imgName = folder + "/" + fileName + "." + extensionName;
                    }

        return imgName;
    }

    public static boolean notNull(String string) {
        if (string != null & !"".equals(string)) {
            return true;
        }
        return false;
    }


    public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5= MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }


}

