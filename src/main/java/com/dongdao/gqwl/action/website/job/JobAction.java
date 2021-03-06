package com.dongdao.gqwl.action.website.job;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.website.job.DdJob;
import com.dongdao.gqwl.model.website.job.DdJob_from;
import com.dongdao.gqwl.model.website.job.DdJobfrom;
import com.dongdao.gqwl.model.website.job.DdJobtype;
import com.dongdao.gqwl.service.website.job.JobService;
import com.dongdao.gqwl.service.website.job.Job_fromService;
import com.dongdao.gqwl.service.website.job.JobfromService;
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
@RequestMapping("/job")
public class JobAction extends BaseAction {

    private final static Logger log= Logger.getLogger(JobAction.class);

    @Autowired
    public JobService jobService;
    @Autowired
    public JobfromService jobfromService;
    @Autowired
    public Job_fromService job_fromService;

    @RequestMapping(value = "/job.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/job/job", context);
    }

    @RequestMapping(value = "/job_add.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView job_add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/job/job_add", context);
    }


    @RequestMapping(value = "/job_update.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView job_update(DdJob model,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        model=(DdJob) jobService.selectByPrimaryKey(model.getJobid());
        request.setAttribute("JOB",model);
        return forword("website/job/job_update", context);
    }

    @RequestMapping("/jobDataList.do")
    public void roleDataList(DdJob model, HttpServletRequest request, HttpServletResponse response) {


        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<DdJob> dataList = jobService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/jobcount.do")
    public void roleDataCount(DdJob model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=jobService.queryByCount(model);
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

    @RequestMapping("/deletejob.do")
    public void deleteType(DdJob model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setIsdelete(0);
        int num= jobService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addjob.do")
    public void saceType(DdJob model,Long[] jobfromid,String[] j_link, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setJ_audit(user.getEmail());
        model.setCreattime(DateUtil.getNowPlusTime());
        model.setIsdelete(1);
        int num= jobService.insertSelective(model);
        if(num==1){
            for(int i=0;i<j_link.length;i++){
                if(j_link[i]!=null&&!"".equals(j_link[i])){
                    DdJob_from job_from=new DdJob_from();
                    job_from.setJobid(model.getJobid());
                    job_from.setJobfromid(jobfromid[i]);
                    job_from.setJ_link(j_link[i]);
                    job_fromService.insertSelective(job_from);
                }


            }
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑信息
    @RequestMapping("/updatejob.do")
    public void updatetype(DdJob model,Long[] jobfromid,String[] j_link, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setUpdatetime(DateUtil.getNowPlusTime());
        SysUser user = SessionUtils.getUser(request);
        model.setJ_audit(user.getEmail());
        int num= jobService.updateByPrimaryKeySelective(model);
        //jobfromService.deleteByPrimaryKey(model.getJobid());
        if(num==1){
            int ishas=0;
            for(int i=0;i<j_link.length;i++){
                if(j_link[i]!=null&&!"".equals(j_link[i])){
                    ishas=1;
                }
            }
            if(ishas==1){
                job_fromService.deleteByPrimaryKey(model.getJobid());
            }
            for(int i=0;i<j_link.length;i++){
                if(j_link[i]!=null&&!"".equals(j_link[i])){
                    DdJob_from job_from=new DdJob_from();
                    job_from.setJobid(model.getJobid());
                    job_from.setJobfromid(jobfromid[i]);
                    job_from.setJ_link(j_link[i]);

                    job_fromService.insertSelective(job_from);
                }


            }
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //审核信息
    @RequestMapping("/passjob.do")
    public void passjob(DdJob model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(model.getIspass()==1){
            model.setIspass(0);
        }else{
            model.setIspass(1);
        }
        int num= jobService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //查询职位分类
    @RequestMapping("/querytype.do")
    public void seetype(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<DdJobtype> pictypes= jobService.queryType();
        jsonMap.put("jobtype", pictypes);

        HtmlUtil.writerJson(response, jsonMap);

    }


    //编辑超链接
    @RequestMapping("/updatejobfrom.do")
    public void updatejobfrom(DdJobfrom model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= jobfromService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //添加超链接
    @RequestMapping("/addjobfrom.do")
    public void addjobfrom(DdJobfrom model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= jobfromService.insertSelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //查询超链接
    @RequestMapping("/jobfrom.do")
    public void jobfrom(DdJobfrom model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<DdJobfrom> jobfroms= jobfromService.selectByJob(model.getJobid());
        jsonMap.put("jobfroms",jobfroms);

        HtmlUtil.writerJson(response, jsonMap);

    }

    //查询超链接
    @RequestMapping("/myjobfrom.do")
    public void myjobfrom(DdJobfrom model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<DdJobfrom> jobfroms= jobfromService.selectByJob(model.getJobid());
        jsonMap.put("jobfroms",jobfroms);
        List<DdJobfrom> myjobfroms= job_fromService.selectByJob(model.getJobid());
        jsonMap.put("myjobfroms",myjobfroms);

        HtmlUtil.writerJson(response, jsonMap);

    }

}
