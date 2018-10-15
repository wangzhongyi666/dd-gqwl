package com.dongdao.gqwl.action.website;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;

import com.dongdao.gqwl.model.website.DdProfile;
import com.dongdao.gqwl.service.website.ProfileService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileAction extends BaseAction {

    private final static Logger log= Logger.getLogger(ProfileAction.class);

    @Autowired
    public ProfileService profileService;


    @RequestMapping(value = "/profile.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/profile", context);
    }


    @RequestMapping(value = "/profile_add.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView profile_add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/profile_add", context);
    }

    @RequestMapping(value = "/profile_update.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView profile_update(DdProfile model,HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        model=(DdProfile) profileService.selectByPrimaryKey(model.getProid());
        request.setAttribute("PROFILE",model);
        return forword("website/profile_update", context);
    }

    @RequestMapping("/profileDataList.do")
    public void roleDataList(DdProfile model, HttpServletRequest request, HttpServletResponse response) {

        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<DdProfile> dataList = profileService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/profilecount.do")
    public void roleDataCount(DdProfile model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=profileService.queryByCount(model);
        Integer pageCount=0;

        if(count%model.getPageSize()>0){
            pageCount=count/model.getPageSize()+1;
        }else{
            pageCount=count/model.getPageSize();
        }

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("allNum", count);
        jsonMap.put("pageCount", pageCount);
        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/deleteprofile.do")
    public void deleteType(DdProfile model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
      
        int num= profileService.deleteByPrimaryKey(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addprofile.do")
    public void saceType(DdProfile model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setP_audit(user.getEmail());
        model.setCreattime(DateUtil.getNowPlusTime());

        int num= profileService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑素材信息
    @RequestMapping("/updateprofile.do")
    public void updatetype(DdProfile model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setP_audit(user.getEmail());
        model.setUpdatetime(DateUtil.getNowPlusTime());
        int num= profileService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }


    //审核信息
    @RequestMapping("/passprofile.do")
    public void passprofile(DdProfile model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        if(model.getIspass()==1){
            model.setIspass(0);
        }else{
            model.setIspass(1);
        }
        int num= profileService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }


}
