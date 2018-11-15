package com.dongdao.gqwl.action.website.news;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.website.news.DdNewstype;
import com.dongdao.gqwl.service.website.news.NewstypeService;
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
@RequestMapping("/newstype")
public class NewstypeAction extends BaseAction {

    private final static Logger log= Logger.getLogger(NewstypeAction.class);

    @Autowired
    public NewstypeService newstypeService;

    @RequestMapping(value = "/newstype.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/news/newstype", context);
    }


    @RequestMapping("/newstypeDataList.do")
    public void roleDataList(DdNewstype model, HttpServletRequest request, HttpServletResponse response) {


        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<DdNewstype> dataList = newstypeService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/newstypecount.do")
    public void roleDataCount(DdNewstype model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=newstypeService.queryByCount(model);
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

    @RequestMapping("/deletenewstype.do")
    public void deleteType(DdNewstype model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= newstypeService.deleteByPrimaryKey(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addnewstype.do")
    public void saceType(DdNewstype model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setN_audit(user.getEmail());
        model.setCreattime(DateUtil.getNowPlusTime());

        int num= newstypeService.insertSelective(model);
        if(model.getNewstypeid()!=null&&model.getNewstypeid()!=0){
            model.setNewstype(Integer.parseInt(model.getNewstypeid()+""));
            num=newstypeService.updateByPrimaryKeySelective(model);
        }

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑信息
    @RequestMapping("/updatenewstype.do")
    public void updatetype(DdNewstype model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int num= newstypeService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

}
