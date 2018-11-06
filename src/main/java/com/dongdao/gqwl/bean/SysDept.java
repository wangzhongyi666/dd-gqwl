package com.dongdao.gqwl.bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class SysDept extends BaseBean {

    private Integer id;
    private Integer deptId;
    private String name;
    private Integer parentId;
    private Integer deleted;
    private Date createTime;
    private Date updateTime;
    private Integer rank;
    private Integer is_date;//逻辑条件
    private String subDeptNames;
    private Integer subCount;
    private Double base;
    private Double yilbase;
    private String bank;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Double getYilbase() {
        return yilbase;
    }

    public void setYilbase(Double yilbase) {
        this.yilbase = yilbase;
    }

    public Double getBase() {
        return base;
    }

    public void setBase(Double base) {
        this.base = base;
    }

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

    public String getSubDeptNames() {
        return subDeptNames;
    }

    public void setSubDeptNames(String subDeptNames) {
        this.subDeptNames = subDeptNames;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getIs_date() {
        return is_date;
    }

    public void setIs_date(Integer is_date) {
        this.is_date = is_date;
    }

    /*public static String sendSms(String content,String mobiles,String sendTime,String extno){
        String appname = "gqwl_gw_01";
        String APP_Key = "qvXJPZ6JXTrdQ247IEyygy57qTu8";
        String APP_Secret = "Vjh4hgbfb53yl4KR0Bvizg2j51EX";
        String APP_url = "https://api.rtc.huaweicloud.com:10443";
        String returnXml = "";
        try {
            returnXml = load(APP_url,
                    "from=send&userid="+userid+"&timestamp="+timestamp+"&sign="+sign+"&mobile="+mobiles+"&content="+content+"&sendTime=&extno=");
            return returnXml;
        }catch (Exception e){

            e.getStackTrace();
            return returnXml;
        }
    }*/

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

    public static void main(String[] args){
        System.out.println("2018-11-06 01:04:20".toString().substring(0,10));
    }
}
