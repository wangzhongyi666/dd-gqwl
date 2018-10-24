package com.dongdao.gqwl.action.routline.topic;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.service.routline.topic.TopicService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/topic")
public class TopicAction extends BaseAction {

    private final static Logger log= Logger.getLogger(TopicAction.class);

    @Autowired
    public TopicService topicService;

    @RequestMapping(value = "/topic.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("routline/topic/topic", context);
    }

    @RequestMapping(value = "/topic_add.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView topic_add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("routline/topic/topic_add", context);
    }


    @RequestMapping(value = "/topic_update.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView topic_update(DdTopic model, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        model=(DdTopic) topicService.selectByPrimaryKey(model.getTopid());
        request.setAttribute("TOPIC",model);
        return forword("routline/topic/topic_update", context);
    }

    @RequestMapping("/topicDataList.do")
    public void roleDataList(DdTopic model, HttpServletRequest request, HttpServletResponse response) {

        List<DdTopic> dataList = topicService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/topicCount.do")
    public void roleDataCount(DdTopic model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=topicService.queryByCount(model);
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

    @RequestMapping("/deletetopic.do")
    public void deleteTopic(DdTopic model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setIsdelete(0);
        int num= topicService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addtopic.do")
    public void saceTopic(@Param("file") MultipartFile file, DdTopic model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setIsdelete(1);
        model.setT_audit(user.getEmail());
        model.setCreattime(DateUtil.getNowPlusTime());
        model.setTopupdatetime(DateUtil.getNowPlusTime());
        String picpath=uploadFile("topic",file,request);
        if(!"".equals(picpath)){
            model.setPicpath(picpath);
        }
        int num= topicService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑信息
    @RequestMapping("/updatetopic.do")
    public void updateTopic(@Param("file") MultipartFile file,DdTopic model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setUpdatetime(DateUtil.getNowPlusTime());
        SysUser user = SessionUtils.getUser(request);
        model.setT_audit(user.getEmail());
        model.setIsdelete(1);
        String picpath=uploadFile("topic",file,request);
        if(!"".equals(picpath)){
            model.setPicpath(picpath);
        }
        int num= topicService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //审核信息
    @RequestMapping("/passtopic.do")
    public void passtopic(DdTopic model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(model.getIspass()==1){
            model.setIspass(0);
        }else{
            model.setIspass(1);
        }
        int num= topicService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }


}
