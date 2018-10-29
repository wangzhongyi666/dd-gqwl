package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.websit.RasteUser;
import com.dongdao.gqwl.model.website.job.DdInformation;
import com.dongdao.gqwl.service.gcolumn.RasteUserService;
import com.dongdao.gqwl.service.website.news.InformationService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.SessionUtils;
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
            informationService.updateBySelective(info);
            if(info!=null){
                info.setUpdatetime(DateUtil.getFormattedMD(info.getUpdatetime()));
            }
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

}
