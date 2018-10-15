package com.dongdao.gqwl.action.website.job;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.website.job.DdJobtype;
import com.dongdao.gqwl.service.website.job.JobtypeService;
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
@RequestMapping("/jobtype")
public class JobtypeAction extends BaseAction {

    private final static Logger log= Logger.getLogger(JobtypeAction.class);

    @Autowired
    public JobtypeService jobtypeService;

    @RequestMapping(value = "/jobtype.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView jobtype(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/job/jobtype", context);
    }


    @RequestMapping("/jobtypeDataList.do")
    public void jobtypeDataList(DdJobtype model, HttpServletRequest request, HttpServletResponse response) {

        List<DdJobtype> dataList = jobtypeService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/jobtypecount.do")
    public void jobtypeDataCount(DdJobtype model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=jobtypeService.queryByCount(model);
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

    @RequestMapping("/deletejobtype.do")
    public void deleteType(DdJobtype model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= jobtypeService.deleteByPrimaryKey(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addjobtype.do")
    public void saceType(DdJobtype model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setJ_audit(user.getEmail());
        model.setCreattime(DateUtil.getNowPlusTime());

        int num= jobtypeService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑信息
    @RequestMapping("/updatejobtype.do")
    public void updatetype(DdJobtype model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setUpdatetime(DateUtil.getNowPlusTime());
        int num= jobtypeService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

}
