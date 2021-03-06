package com.dongdao.gqwl.action.website;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.website.RasteUser;
import com.dongdao.gqwl.service.gcolumn.RasteUserService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
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
@RequestMapping("/rasteuser")
public class RasteUserAction extends BaseAction {

    private final static Logger log= Logger.getLogger(RasteUserAction.class);

    @Autowired
    public RasteUserService<RasteUser> rasteUserService;

    @RequestMapping(value = "/rasteuser.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/rasteuser", context);
    }


    @RequestMapping("/rasteuserDataList.do")
    public void audiDataList(RasteUser model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<RasteUser> dataList = rasteUserService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/rasteuserCount.do")
    public void roleDataCount(RasteUser model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=rasteUserService.queryByCount(model);
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
    @RequestMapping("/deleterasteuser.do")
    public void deleteType(RasteUser model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        RasteUser model1 = new RasteUser();
        model1.setId(model.getId());
        model1.setState(2);
        int num= rasteUserService.updateByPrimaryKeySelective(model1);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //添加
    @RequestMapping("/addrasteuser.do")
    public void saceType(RasteUser model,HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setCreatetime(DateUtil.getNowPlusTime());
        model.setLogin_type(0);
        model.setLogin_num(0);
        model.setPwd(model==null||model.getTel()==null?"123456":model.getTel().substring(7));
        model.setState(0);
        model.setLogin_type(1);
        int num= rasteUserService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    @RequestMapping(value = "/auditrasteuser.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView auditRasteuser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/auditrasteuser", context);
    }

    //审核
    @RequestMapping("/updaterasteuser.do")
    public void updateType(RasteUser model,HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        RasteUser model1 = new RasteUser();
        model1.setId(model.getId());
        model1.setState(model.getState());
        int num= rasteUserService.updateByPrimaryKeySelective(model1);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
}
