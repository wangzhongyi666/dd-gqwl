package com.dongdao.gqwl.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SmsUtil {



    public static String sendSms(String content,String mobiles,String sendTime,String extno){
        String username = "365job";
        String pwd = "112233";
        String userid = "299";
        String timestamp = DateUtil.getFomartDate(new Date(), "yyyyMMddHHmmss");
        String sign = username+pwd+timestamp;
        sign = encryption(sign);
        String returnXml = "";
        try {
            returnXml = load("http://123.56.234.185:7878//v2sms.aspx",
                    "action=send&userid="+userid+"&timestamp="+timestamp+"&sign="+sign+"&mobile="+mobiles+"&content="+content+"&sendTime=&extno=");
            return returnXml;
        }catch (Exception e){

            e.getStackTrace();
            return returnXml;
        }
    }


    /**
     *
     * @param plainText
     *            明文
     * @return 32位密文
     */
    public static String encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
    public static String load(String url,String query) throws Exception
    {
        URL restURL = new URL(url);
        /*
         * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
         */
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        //请求方式
        conn.setRequestMethod("POST");
        //设置是否从httpUrlConnection读入，默认情况下是true; httpUrlConnection.setDoInput(true);
        conn.setDoOutput(true);
        //allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
        conn.setAllowUserInteraction(false);

        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(query);

        ps.close();

        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line,resultStr="";

        while(null != (line=bReader.readLine()))
        {
            resultStr +=line;
        }
        bReader.close();

        return resultStr;

    }

        /**
         * @description 将xml字符串转换成map
         * @param xml
         * @return Map
         */
        public static Map<String,String> readStringXmlOut(String xml) {
            Map<String,String> map = new HashMap<String,String>();
            Document doc = null;
            try {
                // 将字符串转为XML
                doc = DocumentHelper.parseText(xml);
                // 获取根节点
                Element rootElt = doc.getRootElement();
                // 拿到根节点的名称
                System.out.println("根节点=" + rootElt.getName());

                Iterator<Element> returnstatus = rootElt.elementIterator("returnstatus");
                String returnstatusText = returnstatus.next().getText();
                map.put("returnstatus", returnstatusText);


                Iterator<Element> message = rootElt.elementIterator("message");
                String messageText = message.next().getText();
                map.put("message", messageText);


                Iterator<Element> remainpoint = rootElt.elementIterator("remainpoint");
                String remainpointText = remainpoint.next().getText();
                map.put("remainpoint", remainpointText);


                Iterator<Element> taskID = rootElt.elementIterator("taskID");
                String taskIDText = taskID.next().getText();
                map.put("taskID", taskIDText);


                Iterator<Element> successCounts = rootElt.elementIterator("returnstatus");
                String successCountsText = successCounts.next().getText();
                map.put("successCounts", successCountsText);



            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return map;
        }

    public static void main(String[] args) {

        // 下面是需要解析的xml字符串例子
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
                           "<returnsms>" +
                            "<returnstatus>success</returnstatus>" +
                            "<message>message</message>" +
                            "<remainpoint> remainpoint</remainpoint>" +
                            "<taskID>taskID</taskID>" +
                            "<successCounts>successCounts</successCounts>" +
                           "</returnsms>";

        readStringXmlOut(xmlString);

        System.out.println();

        System.out.println("XML字符串xmlString中的值：");
        Map<String,String> map = readStringXmlOut(xmlString);
        Iterator<String> iters = (Iterator)map.keySet().iterator();
        while (iters.hasNext()) {
            String key = iters.next(); // 拿到键
            String val = map.get(key).toString(); // 拿到值

            System.out.println(key + "=" + val);
        }
    }

}
