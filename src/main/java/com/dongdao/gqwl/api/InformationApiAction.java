package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.website.RasteMassage;
import com.dongdao.gqwl.model.website.RasteUser;
import com.dongdao.gqwl.model.website.job.DdInformation;
import com.dongdao.gqwl.service.gcolumn.RasteMassageService;
import com.dongdao.gqwl.service.gcolumn.RasteUserService;
import com.dongdao.gqwl.service.website.news.InformationService;
import com.dongdao.gqwl.utils.*;
import net.sf.json.JSONObject;
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
@RequestMapping("/information")
public class InformationApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(InformationApiAction.class);

    @Autowired
    public InformationService<DdInformation> informationService;

    @Autowired
    public RasteUserService<RasteUser> rasteUserService;

    @Autowired
    public RasteMassageService<RasteMassage> rasteMassageService;


    //消息通知列表
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/informationlist.json")
    public Map<String, Object> informationList(String wx_ident,
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
            DdInformation information = new DdInformation();
            information.setSendee(user0.getId());
            List<DdInformation> datalist =  informationService.queryByList(information);
            SessionUtils.removeValidateCode(request);
            return setSuccessMap(jsonMap, "操作成功！", datalist);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    //消息通知详情
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/informationinfo.json")
    public Map<String, Object> informationInfo(String information_id,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            RasteUser user = new RasteUser();
            if (information_id == null || information_id.equals("")) {
                return setFailureMap(jsonMap, "消息不能为空！", null);
            }
            DdInformation info = informationService.selectByPrimaryKey(information_id);
            if(info!=null){
                info.setUpdatetime(DateUtil.getFormattedMD(info.getUpdatetime()));
            }
            jsonMap.put("info", info);
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    //消息通知删除
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/deleteinformation.json")
    public Map<String, Object> deleteinformation(Integer information_id,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            if (information_id == null) {
                return setFailureMap(jsonMap, "消息不能为空！", null);
            }
            DdInformation info = new DdInformation();
            info.setInformation_id(information_id);
            info.setIs_see(2);

            info.setUpdatetime(DateUtil.getNowPlusTime());
            informationService.updateByPrimaryKeySelective(info);
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    //添加消息通知
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/addinformation.json")
    public Map<String, Object> addinformation(Integer sendee,Integer info_type,String title,String content,
                                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            if (sendee == null) {
                return setFailureMap(jsonMap, "消息接收人不能为空！", null);
            }
            DdInformation info = new DdInformation();
            info.setSendee(sendee);
            info.setInfo_type(info_type);
            info.setTitle(title);
            info.setContent(content);
            info.setUpdatetime(DateUtil.getNowPlusTime());
            info.setIs_see(0);
            info.setRank(0);
            informationService.insertSelective(info);
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }


    //关于我们
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/aboutus.json")
    public Map<String, Object> aboutus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            RasteMassage massage = new RasteMassage();
            List<RasteMassage> datalist = rasteMassageService.queryByList(massage);

            return setSuccessMap(jsonMap, "操作成功！", datalist.get(0).getAddress());
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/decodeUserInfo.json")
    public Map<String, Object> decodeUserInfo(String encryptedData, String iv, String code) {
        Map<String, Object> map = new HashMap<String, Object>();

        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }

        //小程序唯一标识   (在微信小程序管理后台获取)wxef2c55c20cf92aee
        //612c3524c9472ed2fab4e88a38d4b96d
        String wxspAppid = "wxef2c55c20cf92aee";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "612c3524c9472ed2fab4e88a38d4b96d";
        //授权（必填）
        String grant_type = "authorization_code";

        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");

        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = AesCbcUtil.decrypt(encryptedData.replaceAll(" ","\\+"), session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                map.put("status", 1);

                JSONObject userInfoJSON = JSONObject.fromObject(result);
                Map userInfo = new HashMap();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                return setSuccessMap(map, "解密成功！", userInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
            return setFailureMap(map, "解密失败！", null);
        }
        map.put("status", 0);
        return setFailureMap(map, "解密失败！", null);
    }
}
