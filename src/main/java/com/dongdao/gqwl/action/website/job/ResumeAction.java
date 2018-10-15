package com.dongdao.gqwl.action.website.job;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.website.job.DdJob;
import com.dongdao.gqwl.model.website.job.DdResume;
import com.dongdao.gqwl.service.website.job.ResumeService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.HtmlUtil;
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
@RequestMapping("/resume")
public class ResumeAction extends BaseAction {

    private final static Logger log= Logger.getLogger(ResumeAction.class);

    @Autowired
    public ResumeService resumeService;

    @RequestMapping(value = "/resume.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView resume(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/job/resume", context);
    }

   /* @RequestMapping(value = "/resume_add.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView resume_add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/job/resume_add", context);
    }


    @RequestMapping(value = "/resume_update.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView resume_update(DdResume model, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        model=(DdResume) resumeService.selectByPrimaryKey(model.getResumeid());
        request.setAttribute("resume",model);
        return forword("website/job/resume_update", context);
    }*/

    @RequestMapping("/resumeDataList.do")
    public void roleDataList(DdResume model, HttpServletRequest request, HttpServletResponse response) {

        List<DdResume> dataList = resumeService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/resumecount.do")
    public void roleDataCount(DdResume model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=resumeService.queryByCount(model);
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

    @RequestMapping("/deleteresume.do")
    public void deleteType(DdResume model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setIsdelete(0);
        int num= resumeService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    /*@RequestMapping("/addresume.do")
    public void saceType(DdResume model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);

        model.setCreattime(DateUtil.getNowPlusTime());
        int num= resumeService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }*/
    /*//编辑信息
    @RequestMapping("/updateresume.do")
    public void updatetype(DdResume model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setUpdatetime(DateUtil.getNowPlusTime());
        SysUser user = SessionUtils.getUser(request);
        model.setJ_audit(user.getEmail());
        int num= resumeService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
*/
    //审核信息
    /*@RequestMapping("/passresume.do")
    public void passresume(DdResume model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(model.getIspass()==1){
            model.setIspass(2);
        }else{
            model.setIspass(1);
        }
        int num= resumeService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }*/

    //查询职位分类
    @RequestMapping("/querytype.do")
    public void seetype(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<DdJob> pictypes= resumeService.queryType();
        jsonMap.put("resumetype", pictypes);

        HtmlUtil.writerJson(response, jsonMap);

    }
}
