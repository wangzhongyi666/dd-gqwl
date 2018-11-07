package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.websit.RasteUser;
import com.dongdao.gqwl.model.website.job.DdJob;
import com.dongdao.gqwl.service.gcolumn.RasteUserService;
import com.dongdao.gqwl.service.website.PictureService;
import com.dongdao.gqwl.service.website.job.JobService;
import com.dongdao.gqwl.service.website.job.JobtypeService;
import com.dongdao.gqwl.utils.*;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public Map<String, Object> registerWx(String wx_ident,String tel,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            RasteUser user = new RasteUser();
            if(wx_ident!=null&&!wx_ident.equals("")){
                user.setWx_ident(wx_ident);
                RasteUser user0 = rasteUserService.queryByToLogin(user);
                if(user0!=null){
                    return setFailureMap(jsonMap, "微信号已存在！", null);
                }
            }else {
                return setFailureMap(jsonMap, "微信号不能为空！", null);
            }
            if(tel!=null&&!tel.equals("")){
                user.setTel(tel);
            }
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
    public Map<String, Object> updatetel(String wx_ident,String tel,String code,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            String code1 = SessionUtils.getValidateCode(request);
            if(code==null||!code.equals(code1)){
                return setFailureMap(jsonMap, "微信号不存在！", null);
            }
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
            SessionUtils.removeValidateCode(request);
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
            return setSuccessMap(jsonMap, "注册成功！", null);
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
}
