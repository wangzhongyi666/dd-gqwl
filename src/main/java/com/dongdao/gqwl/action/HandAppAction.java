package com.dongdao.gqwl.action;

import com.dongdao.gqwl.UserConstants;
import com.dongdao.gqwl.bean.*;
import com.dongdao.gqwl.model.*;
import com.dongdao.gqwl.service.*;
import com.dongdao.gqwl.utils.*;
import com.dongdao.gqwl.bean.*;
import com.dongdao.gqwl.model.*;
import com.dongdao.gqwl.service.*;
import com.dongdao.gqwl.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/handApp")
public class HandAppAction extends BaseAction {
    private final static Logger log = Logger.getLogger(MainAction.class);


    /**
     * 支付成功页面
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL = false)
    @RequestMapping("/success.shtml")
    public ModelAndView success(HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("h5/success", context);
    }

    /**
     * 关于我们（完成）
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL = false)
    @RequestMapping("/aboutus.shtml")
    public ModelAndView aboutus(HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("h5/aboutus", context);
    }

    /**
     * 小程序关于我们
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL = false)
    @RequestMapping("/ush5.shtml")
    public ModelAndView ush5(HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        return forword("h5/ush5", context);
    }

    /**
     * 支付成功
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyURL = false)
    @RequestMapping("/paySuccess.shtml")
    public ModelAndView paySuccess(String order_number, HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        context.put("order_number", order_number);
        return forword("h5/pay_success", context);
    }

    /**
     * 上传身份证
     *
     * @param file1
     * @param name
     * @param identNum
     * @param tel
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @Auth(verifyLogin = false, verifyURL = false)
    @RequestMapping("/uploadImgUrl")
    public Map<String, Object> uploadImgUrl(@RequestParam("file1") MultipartFile file1, Integer picType,
                                            String name, String identNum, String tel, HttpServletResponse response,
                                            HttpServletRequest request) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            String parhstr = request.getSession().getServletContext()
                    .getRealPath(File.separator);
            parhstr = parhstr.substring(0, parhstr.length() - 5);
            String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
            String type1 = file1 != null && !org.apache.commons.lang.StringUtils.isBlank(file1.getOriginalFilename()) ? file1.getOriginalFilename().substring(
                    file1.getOriginalFilename().lastIndexOf(".")) : "";
            if (type1.equals("")) {
                return setFailureMap(jsonMap, "请选择图片！", null);
            }
            if ((!type1.equals("") && imgeArray.indexOf(type1.toUpperCase()) < 0)) {
                return setFailureMap(jsonMap, "文件格式错误！", null);
            }
            if ((file1 != null && file1.getSize() > 10485760)) {
                return setFailureMap(jsonMap, "图片过大！", null);
            }
            String sjc = "";
            String path = "";
            /*VipModel model=new VipModel();
            if(!type1.equals("")){
                sjc=DateUtil.getNowPlusTimeMill();
                path = parhstr+"aptitude"
                        + File.separator
                        + sjc
                        +type1;
                File f = new File(path);
                // 创建文件夹
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                file1.transferTo(new File(path));
                if(picType.equals(1)){
                    model.setIdentPicUrl1(UserConstants.CRMURL + "aptitude/"+ sjc + type1);
                }else{
                    model.setIdentPicUrl2(UserConstants.CRMURL + "aptitude/"+ sjc + type1);
                }
            }
            model.setName(name);
            model.setIdentNum(identNum);
            model.setTel(tel);
            model.setAudit(1);
            model.setProvince(130000);
            model.setCity(130100);
            model.setSubmitTime(DateUtil.getNowPlusTime());
            vipService.updateBytel(model);*/
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        return setSuccessMap(jsonMap, "操作成功！", null);
    }


    /**
     * 微信小程序支付获取openid
     *
     * @param code
     * @throws Exception
     */
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/wxXPay.do")
    public JSONObject wxXPay(String code, HttpServletRequest request) throws Exception {
        String path = "https://api.weixin.qq.com/sns/jscode2session?appid=" + ConfigUtil.APPID + "&secret=" + ConfigUtil.APPSECRET + "&js_code=" + code + "&grant_type=authorization_code";
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(path);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod("GET");

            httpUrlConn.connect();

            // 当有数据需要提交时
            /*if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }*/

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }

}