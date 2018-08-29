package com.dongdao.gqwl.action.Source;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.source.DdPicsource;
import com.dongdao.gqwl.model.source.DdPictype;
import com.dongdao.gqwl.service.source.PicSourceService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.HtmlUtil;
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
@RequestMapping("/pic")
public class PicSourceAction extends BaseAction {

    private final static Logger log= Logger.getLogger(PicSourceAction.class);

    @Autowired
    public PicSourceService picSourceService;
    //跳转页面
    @RequestMapping(value = "/pic.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("source/picsource", context);
    }

    //分页数据
    @RequestMapping("/picDataList.do")
    public void roleDataList(DdPicsource model, HttpServletRequest request, HttpServletResponse response) {

        List<DdPicsource> dataList = picSourceService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/piccount.do")
    public void roleDataCount(DdPicsource model, HttpServletRequest request, HttpServletResponse response) throws Exception{

        Integer count=picSourceService.queryByCount(model);
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
    //删除
    @RequestMapping("/deletepic.do")
    public void deleteType(DdPicsource model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= picSourceService.deleteByPrimaryKey(model.getPicid());
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //添加
    @RequestMapping("/addpic.do")
    public void saceType(DdPicsource model, @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String picpath=uploadFile("picsource",file,request);
        if(!"".equals(picpath)&picpath!=null){
            model.setPicpath(picpath);
        }
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= picSourceService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑
    @RequestMapping("/updatepic.do")
    public void updatetype(DdPicsource model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= picSourceService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }


    //查询图片分类
    @RequestMapping("/querytype.do")
    public void updatetype(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<DdPictype> pictypes= picSourceService.queryType();
        jsonMap.put("pictype", pictypes);

        HtmlUtil.writerJson(response, jsonMap);

    }

}
