package com.dongdao.gqwl.action.routline.activity;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.routline.activity.DdActivity;
import com.dongdao.gqwl.service.routline.activity.ActivityService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/activity")
public class ActivityAction extends BaseAction {

    private final static Logger log= Logger.getLogger(ActivityAction.class);

    @Autowired
    public ActivityService activityService;

    @RequestMapping(value = "/activity.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("routline/activity/activity", context);
    }

    @RequestMapping(value = "/activity_add.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView activity_add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("routline/activity/activity_add", context);
    }


    @RequestMapping(value = "/activity_update.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView activity_update(DdActivity model, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        model=(DdActivity) activityService.selectByPrimaryKey(model.getActid());
        request.setAttribute("ACTIVITY",model);
        return forword("routline/activity/activity_update", context);
    }

    @RequestMapping("/activityDataList.do")
    public void roleDataList(DdActivity model, HttpServletRequest request, HttpServletResponse response) {

        List<DdActivity> dataList = activityService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/activityCount.do")
    public void roleDataCount(DdActivity model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=activityService.queryByCount(model);
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

    @RequestMapping("/deleteactivity.do")
    public void deleteType(DdActivity model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setIsdelete(0);
        int num= activityService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addactivity.do")
    public void saceType(DdActivity model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setA_audit(user.getEmail());
        model.setCreattime(DateUtil.getNowPlusTime());
        model.setIsdelete(1);
        int num= activityService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑信息
    @RequestMapping("/updateactivity.do")
    public void updatetype(DdActivity model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setUpdatetime(DateUtil.getNowPlusTime());
        SysUser user = SessionUtils.getUser(request);
        model.setA_audit(user.getEmail());
        int num= activityService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //审核信息
    @RequestMapping("/passactivity.do")
    public void passactivity(DdActivity model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(model.getIspass()==1){
            model.setIspass(0);
        }else{
            model.setIspass(1);
        }
        int num= activityService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }


}
