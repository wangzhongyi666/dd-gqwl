package com.dongdao.gqwl.action.website.job;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.website.job.DdJobfrom;
import com.dongdao.gqwl.service.website.job.JobfromService;
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
@RequestMapping("/jobfrom")
public class JobfromAction extends BaseAction {

    private final static Logger log= Logger.getLogger(JobfromAction.class);

    @Autowired
    public JobfromService jobfromService;


    @RequestMapping(value = "/jobfrom.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/job/jobfrom", context);
    }

    @RequestMapping(value = "/jobfrom_add.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView jobfrom_add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/job/jobfrom_add", context);
    }


    @RequestMapping(value = "/jobfrom_update.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView jobfrom_update(DdJobfrom model, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        model=(DdJobfrom) jobfromService.selectByPrimaryKey(model.getJobfromid());
        request.setAttribute("JOBFROM",model);
        return forword("website/job/jobfrom_update", context);
    }

    @RequestMapping("/jobfromDataList.do")
    public void roleDataList(DdJobfrom model, HttpServletRequest request, HttpServletResponse response) {


        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<DdJobfrom> dataList = jobfromService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/jobfromcount.do")
    public void roleDataCount(DdJobfrom model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=jobfromService.queryByCount(model);
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

    @RequestMapping("/deletejobfrom.do")
    public void deleteType(DdJobfrom model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int num= jobfromService.deleteByPrimaryKey(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addjobfrom.do")
    public void saceType(DdJobfrom model, @RequestParam("file") MultipartFile file,@RequestParam("g_file") MultipartFile gfile, String[] title, String[] j_link, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String picpath=uploadFile("jobfrom",file,request);
        String gpicpath=uploadFile("jobfrom",gfile,request);
        if(!"".equals(picpath)&picpath!=null){
            model.setLogo(picpath);
        }
        if(!"".equals(gpicpath)&gpicpath!=null){
            model.setG_logo(gpicpath);
        }
        SysUser user = SessionUtils.getUser(request);
        model.setJ_audit(user.getEmail());
        model.setCreattime(DateUtil.getNowPlusTime());
        int num= jobfromService.insertSelective(model);
        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑信息
    @RequestMapping("/updatejobfrom.do")
    public void updatetype(DdJobfrom model, @RequestParam("file") MultipartFile file,@RequestParam("g_file") MultipartFile gfile, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String picpath=uploadFile("jobfrom",file,request);
        String gpicpath=uploadFile("jobfrom",gfile,request);
        if(!"".equals(picpath)&picpath!=null){
            model.setLogo(picpath);
        }
        if(!"".equals(gpicpath)&gpicpath!=null){
            model.setG_logo(gpicpath);
        }
        SysUser user = SessionUtils.getUser(request);
        model.setJ_audit(user.getEmail());
        int num= jobfromService.updateByPrimaryKeySelective(model);
        jsonMap.put("msg", "操作成功！");
        HtmlUtil.writerJson(response, jsonMap);

    }



}
