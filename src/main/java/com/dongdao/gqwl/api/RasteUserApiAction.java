package com.dongdao.gqwl.api;

import com.alibaba.fastjson.JSONObject;
import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.website.RasteUser;
import com.dongdao.gqwl.model.website.job.DdInformation;
import com.dongdao.gqwl.service.gcolumn.RasteUserService;
import com.dongdao.gqwl.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rasteuser")
public class RasteUserApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(RasteUserApiAction.class);

    @Autowired
    public RasteUserService<RasteUser> rasteUserService;

    //账号密码登录
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/tologinPwd.json")
    public Map<String, Object> tologinPwd(String tel,String pwd,Integer login_type,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            if(StringUtils.isBlank(tel)){
                //sendFailureMessage(response, "账号不能为空.");
                return setFailureMap(jsonMap, "账号不能为空", null);
            }
            if(StringUtils.isBlank(pwd)){
                //sendFailureMessage(response, "密码不能为空.");
                return setFailureMap(jsonMap, "密码不能为空", null);
            }
            if(login_type==null){
                jsonMap.put("code",1003);
                return setFailureMap(jsonMap, "请输入登录方式！", null);
            }
            RasteUser user = new RasteUser();
            user.setTel(tel);
            user.setPwd(pwd);
            RasteUser user1 = rasteUserService.queryByToLogin(user);
            String msg = "用户登录日志:";
            if(user1==null){
                //记录错误登录日志
                log.debug(msg+"["+tel+"]"+"账号或者密码输入错误.");
                // sendFailureMessage(response, "账号或者密码输入错误.");
                return setFailureMap(jsonMap, "账号密码错误", null);
            }else{
                int loginCount = 0;
                if(user1.getLogin_num() != null){
                    loginCount = user1.getLogin_num();
                }

                loginCount++;
                RasteUser user2 = new RasteUser();
                user2.setId(user1.getId());
                user2.setLogin_num(loginCount);
                user2.setLasttime(DateUtil.getNowPlusTime());
                rasteUserService.updateByPrimaryKeySelective(user2);
                //用户信息放入session
                SessionUtils.setRasteUser(request,user1);

            }
            return setSuccessMap(jsonMap, "登录成功！", null);
        }catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    //验证码登录
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/tologinCode.json")
    public Map<String, Object> tologinCode(String tel,String code,Integer login_type,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            if (StringUtils.isBlank(tel)) {
                //sendFailureMessage(response, "账号不能为空.");
                return setFailureMap(jsonMap, "账号不能为空", null);
            }
            if (StringUtils.isBlank(code)) {
                //sendFailureMessage(response, "密码不能为空.");
                return setFailureMap(jsonMap, "密码不能为空", null);
            }
            if (login_type == null) {
                jsonMap.put("code", 1003);
                return setFailureMap(jsonMap, "请输入登录方式！", null);
            }
            String code1 =  SessionUtils.getValidateCode(request);
            if(code1==null||!code.equals(code1)){
                jsonMap.put("code", 1005);
                return setFailureMap(jsonMap, "验证码错误！", null);
            }
            RasteUser user = new RasteUser();
            user.setTel(tel);
            RasteUser user1 = rasteUserService.queryByToLogin(user);
            String msg = "用户登录日志:";
            if (user1 == null) {
                //记录错误登录日志
                log.debug(msg + "[" + tel + "]" + "账号输入错误.");
                // sendFailureMessage(response, "账号或者密码输入错误.");
                return setFailureMap(jsonMap, "账号输入错误", null);
            } else {
                int loginCount = 0;
                if (user1.getLogin_num() != null) {
                    loginCount = user1.getLogin_num();
                }

                loginCount++;
                RasteUser user2 = new RasteUser();
                user2.setId(user1.getId());
                user2.setLogin_num(loginCount);
                user2.setLasttime(DateUtil.getNowPlusTime());
                rasteUserService.updateByPrimaryKeySelective(user2);
                //用户信息放入session
                SessionUtils.setRasteUser(request, user1);
                SessionUtils.removeValidateCode(request);
            }
            return setSuccessMap(jsonMap, "登录成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }
    //发送验证码
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/sendCode.json")
    public Map<String, Object> sendCode(String tel,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            String code = Md5Util.createPwd();
            SendSms.sendSms(tel,code);
            SessionUtils.setValidateCode(request,code);
            return setSuccessMap(jsonMap, "发送成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "发送失败！", null);
        }
    }

    //用户注册
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/register.json")
    public Map<String, Object> register(String tel,String pwd,String code,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            String code1 = SessionUtils.getValidateCode(request);
            if(code==null||code1==null||!code.equals(code1)){
                return setFailureMap(jsonMap, "验证码错误！", null);
            }
            RasteUser user = new RasteUser();
            user.setTel(tel);
            RasteUser user1 = rasteUserService.queryByToLogin(user);
            if(user1!=null){
                return setFailureMap(jsonMap, "手机号已存在！", null);
            }
            user.setPwd(pwd);
            user.setState(1);
            user.setCreatetime(DateUtil.getNowPlusTime());
            rasteUserService.insertSelective(user);
            SessionUtils.removeValidateCode(request);
            return setSuccessMap(jsonMap, "注册成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "注册失败！", null);
        }
    }

    //忘记密码
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/forgetpwd.json")
    public Map<String, Object> forgetpwd(String tel,String newpwd,String code,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            String code1 = SessionUtils.getValidateCode(request);
            if(code==null||code1==null||!code.equals(code1)){
                return setFailureMap(jsonMap, "验证码错误！", null);
            }
            RasteUser user = new RasteUser();
            user.setTel(tel);
            RasteUser user1 = rasteUserService.queryByToLogin(user);
            if(user1==null){
                return setFailureMap(jsonMap, "手机号不存在！", null);
            }
            user.setPwd(newpwd);
            rasteUserService.insertSelective(user);
            SessionUtils.removeValidateCode(request);
            return setSuccessMap(jsonMap, "注册成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "注册失败！", null);
        }
    }



    //验证微信号是否存在
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/is_exist.json")
    public Map<String, Object> isExist(String wx_ident,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            if(StringUtils.isBlank(wx_ident)){
                return setFailureMap(jsonMap, "账号不能为空", null);
            }
            RasteUser user = new RasteUser();
            user.setWx_ident(wx_ident);
            RasteUser user1 = rasteUserService.queryByToLogin(user);
            if(user1==null){

                jsonMap.put("is_exist",0);
                return setSuccessMap(jsonMap, "操作成功", null);
            }else{
                jsonMap.put("is_exist",1);
                return setSuccessMap(jsonMap, "操作成功！", null);
            }

        }catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    //小程序授权
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/registerWx.json")
    public Map<String, Object> registerWx(String wx_ident,String tel,String imgurl,String name,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map<String,Object> data = new HashMap<String,Object>();
        name=name.replaceAll(" ", "");
        if("".equals(name)){
            name="无昵称";
        }else{
            name= URLDecoder.decode(name, "UTF-8");
        }

        //访问微信接口得到openid
        try {
            RasteUser user = new RasteUser();
            if(wx_ident!=null&&!wx_ident.equals("")){
                user.setWx_ident(wx_ident);
                user = rasteUserService.queryByToLogin(user);
                if(user!=null){
                    jsonMap.put("is_logged",1);//是否登陆 0 否 ；1 是
                    jsonMap.put("r_uid",user.getId());
                    data.put("name",user.getName()==null?"":user.getName());//姓名
                    data.put("wx_ident",user.getWx_ident()==null?"":user.getWx_ident());//微信唯一标识 openid
                    data.put("sex",user.getSex()==null?0:user.getSex());
                    data.put("birthday",user.getBirthday()==null?"":user.getBirthday());
                    data.put("integral",user.getIntegral()==null?0:user.getIntegral());//积分
                    data.put("tel",user.getTel()==null?"":user.getTel());//手机号
                    data.put("email",user.getEmail()==null?"":user.getEmail());//邮箱
                    data.put("createtime",user.getCreatetime()==null?"":user.getCreatetime());//注册时间
                    data.put("lasttime",user.getLasttime()==null?"":user.getLasttime());//最后一次登陆时间
                    data.put("login_num",user.getLogin_num()==null?0:user.getLogin_num());//登陆次数
                    data.put("state",user.getState()==null?0:user.getState());//是否可以登陆  1可以  2不可以
                    data.put("login_type",user.getLogin_type()==null?0:user.getLogin_type());//登陆方式 1 网站登陆 2小程序登陆
                    data.put("lasttime",user.getLasttime()==null?"":user.getLasttime());
                    return setFailureMap(jsonMap, "已登陆！", data);
                }else{
                    user = new RasteUser();
                    jsonMap.put("is_logged",0);
                }
            }else{
                user = new RasteUser();
                jsonMap.put("is_logged",0);
            }

            if(imgurl!=null&&!imgurl.equals("")){
                //user.setWx_ident(DateUtil.getNowPlusTime());
                user.setPicurl(imgurl);
                user = rasteUserService.queryByToLogin(user);
                if(user!=null){
                    jsonMap.put("is_logged",1);
                    jsonMap.put("r_uid",user.getId());
                    data.put("name",user.getName()==null?"":user.getName());//姓名
                    data.put("wx_ident",user.getWx_ident()==null?"":user.getWx_ident());//微信唯一标识 openid
                    data.put("sex",user.getSex()==null?0:user.getSex());
                    data.put("birthday",user.getBirthday()==null?"":user.getBirthday());
                    data.put("integral",user.getIntegral()==null?0:user.getIntegral());//积分
                    data.put("tel",user.getTel()==null?"":user.getTel());//手机号
                    data.put("email",user.getEmail()==null?"":user.getEmail());//邮箱
                    data.put("createtime",user.getCreatetime()==null?"":user.getCreatetime());//注册时间
                    data.put("lasttime",user.getLasttime()==null?"":user.getLasttime());//最后一次登陆时间
                    data.put("login_num",user.getLogin_num()==null?0:user.getLogin_num());//登陆次数
                    data.put("state",user.getState()==null?0:user.getState());//是否可以登陆  1可以  2不可以
                    data.put("login_type",user.getLogin_type()==null?0:user.getLogin_type());//登陆方式 1 网站登陆 2小程序登陆
                    data.put("lasttime",user.getLasttime()==null?"":user.getLasttime());
                    return setSuccessMap(jsonMap, "已登陆！", data);
                }else{
                    user = new RasteUser();
                    user.setPicurl(imgurl);
                }
            }else {
                return setFailureMap(jsonMap, "微信号不能为空！", null);
            }
            if(tel!=null&&!tel.equals("")){
                user.setTel(tel);
            }
            user.setState(1);
            String openId = System.currentTimeMillis()+"";
            user.setCreatetime(DateUtil.getNowPlusTime());
            user.setWx_ident(openId);
            user.setName(name==null?"":name);
            rasteUserService.insertSelective(user);
            SessionUtils.removeValidateCode(request);

            jsonMap.put("r_uid",user.getId());
            data.put("name",user.getName()==null?"":user.getName());//姓名
            data.put("wx_ident",user.getWx_ident()==null?"":user.getWx_ident());//微信唯一标识 openid
            data.put("sex",user.getSex()==null?0:user.getSex());
            data.put("birthday",user.getBirthday()==null?"":user.getBirthday());
            data.put("integral",user.getIntegral()==null?0:user.getIntegral());//积分
            data.put("tel",user.getTel()==null?"":user.getTel());//手机号
            data.put("email",user.getEmail()==null?"":user.getEmail());//邮箱
            data.put("createtime",user.getCreatetime()==null?"":user.getCreatetime());//注册时间
            data.put("lasttime",user.getLasttime()==null?"":user.getLasttime());//最后一次登陆时间
            data.put("login_num",user.getLogin_num()==null?0:user.getLogin_num());//登陆次数
            data.put("state",user.getState()==null?0:user.getState());//是否可以登陆  1可以  2不可以
            data.put("login_type",user.getLogin_type()==null?0:user.getLogin_type());//登陆方式 1 网站登陆 2小程序登陆
            return setSuccessMap(jsonMap, "注册成功！", data);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "注册失败！", null);
        }
    }
    //小程序登录
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/tologinWx.json")
    public Map<String, Object> tologinWx(String wx_ident,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            if(StringUtils.isBlank(wx_ident)){
                //sendFailureMessage(response, "账号不能为空.");
                return setFailureMap(jsonMap, "账号不能为空", null);
            }
            RasteUser user = new RasteUser();
            user.setWx_ident(wx_ident);
            RasteUser user1 = rasteUserService.queryByToLogin(user);
            String msg = "用户登录日志:";
            if(user1==null){
                //记录错误登录日志
                log.debug(msg+"["+wx_ident+"]"+"微信用户不存在.");
                return setFailureMap(jsonMap, "微信用户不存在", null);
            }else{

                int loginCount = 0;
                if(user1.getLogin_num() != null){
                    loginCount = user1.getLogin_num();
                }

                loginCount++;
                RasteUser user2 = new RasteUser();
                user2.setId(user1.getId());
                user2.setLogin_num(loginCount);
                user2.setLasttime(DateUtil.getNowPlusTime());
                rasteUserService.updateByPrimaryKeySelective(user2);
                //用户信息放入session
                SessionUtils.setRasteUser(request,user1);

            }
            return setSuccessMap(jsonMap, "登录成功！", null);
        }catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }



    //修改手机号
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/updatetel.json")
    public Map<String, Object> updatetel(String wx_ident,String tel,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            // String code1 = SessionUtils.getValidateCode(request);
//            if(code==null||!code.equals(code1)){
//                return setFailureMap(jsonMap, "微信号不存在！", null);
//            }
            RasteUser user = new RasteUser();
            if(wx_ident!=null&&!wx_ident.equals("")){
                user.setWx_ident(wx_ident);
                RasteUser user0 = rasteUserService.queryByToLogin(user);
                if(user0==null){
                    return setFailureMap(jsonMap, "微信号不存在！", null);
                }
            }else {
                return setFailureMap(jsonMap, "微信号不能为空！", null);
            }
            if(tel!=null){
                user.setTel(tel);
            }else{
                return setFailureMap(jsonMap, "手机号不能为空！", null);
            }
            rasteUserService.updateByWxIdent(user);
            //SessionUtils.removeValidateCode(request);
            return setSuccessMap(jsonMap, "注册成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "注册失败！", null);
        }
    }


    //修改性别
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/updatesex.json")
    public Map<String, Object> updatesex(String wx_ident,Integer sex,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            RasteUser user = new RasteUser();
            if(wx_ident!=null&&!wx_ident.equals("")){
                user.setWx_ident(wx_ident);
                RasteUser user0 = rasteUserService.queryByToLogin(user);
                if(user0==null){
                    return setFailureMap(jsonMap, "微信号不存在！", null);
                }
            }else {
                return setFailureMap(jsonMap, "微信号不能为空！", null);
            }
            if(sex!=null){
                user.setSex(sex);
            }else{
                return setFailureMap(jsonMap, "性别不能为空！", null);
            }
            rasteUserService.updateByWxIdent(user);
            SessionUtils.removeValidateCode(request);
            jsonMap.put("sex",user.getSex());
            return setSuccessMap(jsonMap, "修改成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "注册失败！", null);
        }
    }


    //修改生日
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/updatebirthday.json")
    public Map<String, Object> updatebirthday(String wx_ident,String birthday,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            RasteUser user = new RasteUser();
            if(wx_ident!=null&&!wx_ident.equals("")){
                user.setWx_ident(wx_ident);
                RasteUser user0 = rasteUserService.queryByToLogin(user);
                if(user0==null){
                    return setFailureMap(jsonMap, "微信号不存在！", null);
                }
            }else {
                return setFailureMap(jsonMap, "微信号不能为空！", null);
            }
            if(birthday!=null){
                user.setBirthday(birthday);
            }else{
                return setFailureMap(jsonMap, "性别不能为空！", null);
            }
            rasteUserService.updateByWxIdent(user);
            SessionUtils.removeValidateCode(request);
            return setSuccessMap(jsonMap, "注册成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "注册失败！", null);
        }
    }


    //消息通知列表
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/rasteuserinfo.json")
    public Map<String, Object> rasteuserinfo(String wx_ident,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {

            RasteUser user = new RasteUser();
            RasteUser user0 = null;
            if(wx_ident!=null&&!wx_ident.equals("")){
                user.setWx_ident(wx_ident);
                user0 = rasteUserService.queryByToLogin(user);
                if(user0==null){
                    return setFailureMap(jsonMap, "微信号不存在！", null);
                }
            }else {
                return setFailureMap(jsonMap, "微信号不能为空！", null);
            }
            Map<String, Object> data = new HashMap<String, Object>();
            SessionUtils.removeValidateCode(request);
            data.put("name",user0.getName()==null?"":user0.getName());//姓名
            data.put("wx_ident",user0.getWx_ident()==null?"":user0.getWx_ident());//微信唯一标识 openid
            data.put("sex",user0.getSex()==null?0:user0.getSex());
            data.put("birthday",user0.getBirthday()==null?"":user0.getBirthday());
            data.put("integral",user0.getIntegral()==null?0:user0.getIntegral());//积分
            data.put("tel",user0.getTel()==null?"":user0.getTel());//手机号
            data.put("email",user0.getEmail()==null?"":user0.getEmail());//邮箱
            data.put("createtime",user0.getCreatetime()==null?"":user0.getCreatetime());//注册时间
            data.put("lasttime",user0.getLasttime()==null?"":user0.getLasttime());//最后一次登陆时间
            data.put("login_num",user0.getLogin_num()==null?0:user0.getLogin_num());//登陆次数
            data.put("state",user0.getState()==null?0:user0.getState());//是否可以登陆  1可以  2不可以
            data.put("login_type",user0.getLogin_type()==null?0:user0.getLogin_type());//登陆方式 1 网站登陆 2小程序登陆
            data.put("lasttime",user0.getLasttime()==null?"":user0.getLasttime());
            return setSuccessMap(jsonMap, "操作成功！", data);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

//    @ResponseBody
//    @RequestMapping(value = "/decodeUserInfo")
//    public Map decodeUserInfo(String encryptedData, String iv, String code) {
//
//        Map map = new HashMap();
//        //登录凭证不能为空
//        if (code == null || code.length() == 0) {
//            map.put("status", 0);
//            map.put("msg", "code 不能为空");
//            return map;
//        }
//
//        //小程序唯一标识   (在微信小程序管理后台获取)
//        String wxspAppid = "***********************";
//        //小程序的 app secret (在微信小程序管理后台获取)
//        String wxspSecret = "************************";
//        //授权（必填）
//        String grant_type = "***************************";
//
//
//        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
//        //请求参数
//        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
//        //发送请求
//        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
//        //解析相应内容（转换成json对象）
//        JSONObject json = JSONObject.fromObject(sr);
//        //获取会话密钥（session_key）
//        String session_key = json.get("session_key").toString();
//        //用户的唯一标识（openid）
//        String openid = (String) json.get("openid");
//
//        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
//        try {
//            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
//            if (null != result && result.length() > 0) {
//                map.put("status", 1);
//                map.put("msg", "解密成功");
//
//                JSONObject userInfoJSON = JSONObject.fromObject(result);
//                Map userInfo = new HashMap();
//                userInfo.put("openId", userInfoJSON.get("openId"));
//                userInfo.put("nickName", userInfoJSON.get("nickName"));
//                userInfo.put("gender", userInfoJSON.get("gender"));
//                userInfo.put("city", userInfoJSON.get("city"));
//                userInfo.put("province", userInfoJSON.get("province"));
//                userInfo.put("country", userInfoJSON.get("country"));
//                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
//                userInfo.put("unionId", userInfoJSON.get("unionId"));
//                map.put("userInfo", userInfo);
//                return map;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        map.put("status", 0);
//        map.put("msg", "解密失败");
//        return map;
//    }
}
