package com.dongdao.gqwl.action.gcolumn;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.website.GColumn;
import com.dongdao.gqwl.model.website.GContent;
import com.dongdao.gqwl.model.website.GSeo;
import com.dongdao.gqwl.service.gcolumn.GColumnService;
import com.dongdao.gqwl.service.gcolumn.GContentService;
import com.dongdao.gqwl.service.gcolumn.GSeoService;
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
@RequestMapping("/gcolumn")
public class GColumnAction extends BaseAction {

    private final static Logger log= Logger.getLogger(GColumnAction.class);

    @Autowired
    public GColumnService<GColumn> gColumnService;

    @Autowired
    public GSeoService<GSeo> gSeoService;

    @Autowired
    public GContentService<GContent> gContentService;

    @RequestMapping(value = "/gcolumn.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView gcolumn(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/gcolumn", context);
    }


    @RequestMapping("/gcolumnDataList.do")
    public void gcolumnDataList(GColumn model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<GColumn> dataList = gColumnService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/gcolumnCount.do")
    public void gcolumnCount(GColumn model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=gColumnService.queryByCount(model);
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
    //添加
    @RequestMapping("/addgcolumn.do")
    public void saceType(GColumn model,  HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setG_M_time(DateUtil.getNowPlusTime());
        model.setG_icon("http://");
        model.setG_R_time(DateUtil.getNowPlusTime());
        model.setG_state(1);
        SysUser user = SessionUtils.getUser(request);
        model.setW_uid(user.getId());
        model.setG_fid(1);
        int num= gColumnService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //删除
    @RequestMapping("/deletegcolumn.do")
    public void deleteType(GColumn model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= gColumnService.deleteByPrimaryKey(model.getC_id());
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //编辑
    @RequestMapping("/updategcolumn.do")
    public void updatetype(GColumn model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setG_M_time(DateUtil.getNowPlusTime());
        int num= gColumnService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }




    @RequestMapping(value = "/gseo.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/gseo", context);
    }


    @RequestMapping("/gseoDataList.do")
    public void gseoDataList(GSeo model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<GSeo> dataList = gSeoService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/gseocount.do")
    public void gseoCount(GSeo model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count = gSeoService.queryByCount(model);
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
    //添加
    @RequestMapping("/addgseo.do")
    public void saveGSeoType(GSeo model,  HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setG_M_time(DateUtil.getNowPlusTime());
        model.setG_R_time(DateUtil.getNowPlusTime());
        model.setG_state(1);
        SysUser user = SessionUtils.getUser(request);
        model.setW_uid(user.getId());
        //model.setG_fid(1);
        int num= gSeoService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //删除
    @RequestMapping("/deletegseo.do")
    public void deleteSeoType(GSeo model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= gSeoService.deleteByPrimaryKey(model.getS_id());
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //编辑
    @RequestMapping("/updategseo.do")
    public void updateSeotype(GSeo model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setG_M_time(DateUtil.getNowPlusTime());
        int num= gSeoService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }





    @RequestMapping(value = "/gcontent.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView gContent(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/gcontent", context);
    }


    @RequestMapping("/gcontentDataList.do")
    public void audiDataList(GContent model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<GContent> dataList = gContentService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/gcontentcount.do")
    public void roleDataCount(GContent model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=gContentService.queryByCount(model);
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
    //添加
    @RequestMapping("/addgcontent.do")
    public void saveContentType(GContent model,  HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setG_M_time(DateUtil.getNowPlusTime());
        //model.setG_icon("http://");
        model.setG_R_time(DateUtil.getNowPlusTime());
        model.setG_state(1);
        SysUser user = SessionUtils.getUser(request);
        model.setW_uid(user.getId());
        //model.setG_fid(1);
        int num= gContentService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //删除
    @RequestMapping("/deletegcontent.do")
    public void deleteContentType(GContent model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= gContentService.deleteByPrimaryKey(model.getCon_id());
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //编辑
    @RequestMapping("/updategcontent.do")
    public void updateContenttype(GContent model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setG_M_time(DateUtil.getNowPlusTime());
        int num= gContentService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

}

