package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.website.DdProfile;
import com.dongdao.gqwl.service.website.PictureService;
import com.dongdao.gqwl.service.website.ProfileService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/about")
public class ProfileApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(ProfileApiAction.class);

    @Autowired
    public ProfileService profileService;
    @Autowired
    public PictureService pictureService;


    //获取总数
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/about.json")
    public Map<String, Object>  roleDataCount(int ptype,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
      try {
        List<Map<String, Object>> mprofile=profileService.selectByType(1);
        List<Map<String, Object>> bprofile=profileService.selectByType(2);
        List<Map<String, Object>> pics=pictureService.selectByType(ptype);
        jsonMap.put("mprofile",mprofile );
        jsonMap.put("bprofile", bprofile);
        jsonMap.put("pics", pics);
        return setSuccessMap(jsonMap, "操作成功！", null);
       }
       catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }

    }



}
