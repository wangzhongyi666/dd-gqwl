package com.dongdao.gqwl.action.website.news;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.website.DdProfile;
import com.dongdao.gqwl.model.website.news.DdNews;
import com.dongdao.gqwl.model.website.news.DdNewstype;
import com.dongdao.gqwl.service.website.news.NewService;
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
@RequestMapping("/news")
public class NewAction extends BaseAction {

    private final static Logger log= Logger.getLogger(NewAction.class);

    @Autowired
    public NewService newsService;

    @RequestMapping(value = "/news.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/news/news", context);
    }

    @RequestMapping(value = "/news_add.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView news_add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/news/news_add", context);
    }


    @RequestMapping(value = "/news_update.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView news_update(DdNews model,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        model=(DdNews) newsService.selectByPrimaryKey(model.getNewsid());
        request.setAttribute("NEWS",model);
        return forword("website/news/news_update", context);
    }

    @RequestMapping("/newsDataList.do")
    public void roleDataList(DdNews model, HttpServletRequest request, HttpServletResponse response) {


        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<DdNews> dataList = newsService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/newscount.do")
    public void roleDataCount(DdNews model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=newsService.queryByCount(model);
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

    @RequestMapping("/deletenews.do")
    public void deleteType(DdNews model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setIsdelete(0);
        int num= newsService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addnews.do")
    public void saceType(@RequestParam("file") MultipartFile file,@RequestParam("file2") MultipartFile file2, DdNews model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setN_audit(user.getEmail());
        model.setCreattime(DateUtil.getNowPlusTime());
        String newspic=uploadFile("news",file,request);
        if(!"".equals(newspic)&newspic!=null){
            model.setNewspic(newspic);
        }
        String newsbanner=uploadFile("news",file2,request);
        if(!"".equals(newsbanner)&newsbanner!=null){
            model.setNewsbanner(newsbanner);
        }
        int num= newsService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑信息
    @RequestMapping("/updatenews.do")
    public void updatetype(@RequestParam("file") MultipartFile file,@RequestParam("file2") MultipartFile file2,DdNews model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setUpdatetime(DateUtil.getNowPlusTime());
        SysUser user = SessionUtils.getUser(request);
        model.setN_audit(user.getEmail());
        String newspic=uploadFile("news",file,request);
        if(!"".equals(newspic)&newspic!=null){
            model.setNewspic(newspic);
        }
        String newsbanner=uploadFile("news",file2,request);
        if(!"".equals(newsbanner)&newsbanner!=null){
            model.setNewsbanner(newsbanner);
        }
        int num= newsService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //审核信息
    @RequestMapping("/passnews.do")
    public void passnews(DdNews model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(model.getIsdelete()==1){
            model.setIsdelete(2);
        }else{
            model.setIsdelete(1);
        }
        int num= newsService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //查询新闻分类
    @RequestMapping("/querytype.do")
    public void seetype(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<DdNewstype> pictypes= newsService.queryType();
        jsonMap.put("newstype", pictypes);

        HtmlUtil.writerJson(response, jsonMap);

    }
}
