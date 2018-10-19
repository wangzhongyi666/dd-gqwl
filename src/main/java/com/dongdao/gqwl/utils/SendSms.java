package com.dongdao.gqwl.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
//如果JDK版本是1.8,可使用原生Base64类
//import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//如果JDK版本低于1.8,请使用三方库提供Base64类
import org.apache.commons.codec.binary.Base64;

public class SendSms {

    //无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    //无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";


    public static void sendSms(String receiver,String templateParas)throws Exception{
        //必填,请参考"开发准备"获取如下数据,替换为实际值
        String url = "https://api.rtc.huaweicloud.com:10443/sms/batchSendSms/v1"; //APP接入地址+接口访问URI
        String appKey = "qvXJPZ6JXTrdQ247IEyygy57qTu8"; //APP_Key
        String appSecret = "Vjh4hgbfb53yl4KR0Bvizg2j51EX"; //APP_Secret
        String sender = "1069100121280439"; //签名通道号
        String templateId = "4496306c9e144579ad980c36edaee634"; //模板ID

        //必填,短信接收人号码,多个号码之间用英文逗号分隔

        //选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告
        String statusCallBack = "zhengshizhao0706@163.com";

        /**
         * 选填,使用无变量模板时请赋空值 String templateParas = "";
         * 单变量模板示例:模板内容为“您的验证码是${NUM_6}”时,templateParas可填写为"[\"369751\"]"
         * 双变量模板示例:模板内容为"您有${NUM_2}件快递请到${TXT_32}领取"时,templateParas可填写为"[\"3\",\"人民公园正门\"]"
         */
        templateParas = "[\""+templateParas+"\"]"; //模板变量,查看更多模板变量规则

        //请求Body
        String body = buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack);
        System.out.println("body is " + body);

        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        System.out.println("wsse header is " + wsseHeader);

        OutputStream out = null;
        InputStream in = null;

        //为防止因HTTPS证书认证失败造成API调用失败,需要先忽略证书信任问题
        HostnameVerifier hv = new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        trustAllHttpsCertificates();

        try {
            URL realUrl = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) realUrl.openConnection();

            conn.setHostnameVerifier(hv);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(true);
            //请求方法
            conn.setRequestMethod("POST");
            //请求Headers参数
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", AUTH_HEADER_VALUE);
            conn.setRequestProperty("X-WSSE", wsseHeader);

            //发送请求Body参数
            out = conn.getOutputStream();
            out.write(body.getBytes());
            out.flush();

            in = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            System.out.println(buffer.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        //必填,请参考"开发准备"获取如下数据,替换为实际值
        String url = "https://api.rtc.huaweicloud.com:10443/sms/batchSendSms/v1"; //APP接入地址+接口访问URI
        String appKey = "qvXJPZ6JXTrdQ247IEyygy57qTu8"; //APP_Key
        String appSecret = "Vjh4hgbfb53yl4KR0Bvizg2j51EX"; //APP_Secret
        String sender = "1069100121280439"; //签名通道号
        String templateId = "4496306c9e144579ad980c36edaee634"; //模板ID

        //必填,短信接收人号码,多个号码之间用英文逗号分隔
        String receiver = "13313011315";

        //选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告
        String statusCallBack = "zhengshizhao0706@163.com";

        /**
         * 选填,使用无变量模板时请赋空值 String templateParas = "";
         * 单变量模板示例:模板内容为“您的验证码是${NUM_6}”时,templateParas可填写为"[\"369751\"]"
         * 双变量模板示例:模板内容为"您有${NUM_2}件快递请到${TXT_32}领取"时,templateParas可填写为"[\"3\",\"人民公园正门\"]"
         */
        String templateParas = "[\"369751\"]"; //模板变量,查看更多模板变量规则

        //请求Body
        String body = buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack);
        System.out.println("body is " + body);

        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        System.out.println("wsse header is " + wsseHeader);

        OutputStream out = null;
        InputStream in = null;

        //为防止因HTTPS证书认证失败造成API调用失败,需要先忽略证书信任问题
        HostnameVerifier hv = new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        trustAllHttpsCertificates();

        try {
            URL realUrl = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) realUrl.openConnection();

            conn.setHostnameVerifier(hv);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(true);
            //请求方法
            conn.setRequestMethod("POST");
            //请求Headers参数
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", AUTH_HEADER_VALUE);
            conn.setRequestProperty("X-WSSE", wsseHeader);

            //发送请求Body参数
            out = conn.getOutputStream();
            out.write(body.getBytes());
            out.flush();

            in = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            System.out.println(buffer.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 构造请求Body体
     * @param sender
     * @param receiver
     * @param templateId
     * @param templateParas
     * @param statusCallBack
     * @return
     */
    static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
                                   String statusCallBack) {

        Map<String, String> map = new HashMap<String, String>();

        map.put("from", sender);
        map.put("to", receiver);
        map.put("templateId", templateId);
        map.put("templateParas", templateParas);
        map.put("statusCallback", statusCallBack);

        StringBuilder sb = new StringBuilder();
        String temp = "";

        for (String s : map.keySet()) {
            try {
                temp = URLEncoder.encode(map.get(s), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sb.append(s).append("=").append(temp).append("&");
        }

        return sb.deleteCharAt(sb.length()-1).toString();
    }

    /**
     * 构造X-WSSE参数值
     * @param appKey
     * @param appSecret
     * @return
     */
    static String buildWsseHeader(String appKey, String appSecret) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date());
        String nonce = UUID.randomUUID().toString().replace("-", "");

        MessageDigest md;
        byte[] passwordDigest = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update((nonce + time + appSecret).getBytes());
            passwordDigest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //如果JDK版本是1.8,请加载原生Base64类,并使用如下代码
        //String passwordDigestBase64Str = Base64.getEncoder().encodeToString(passwordDigest);
        //如果JDK版本低于1.8,请加载三方库提供Base64类,并使用如下代码
        String passwordDigestBase64Str = Base64.encodeBase64String(passwordDigest);
        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    /**
     * 忽略证书信任问题
     * @throws Exception
     */
    static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return;
                    }
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return;
                    }
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
}
