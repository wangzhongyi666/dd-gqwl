package com.dongdao.gqwl.action.webinfo;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.webinfo.DdWebinfo;
import com.dongdao.gqwl.service.webinfo.WebinfoService;
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
@RequestMapping("/webinfo")
public class WebinfoAction extends BaseAction {

    private final static Logger log= Logger.getLogger(WebinfoAction.class);

    @Autowired
    public WebinfoService<DdWebinfo> webinfoService;

    @RequestMapping(value = "/webinfo.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("webinfo/webinfo", context);
    }


    @RequestMapping("/webinfoDataList.do")
    public void audiDataList(DdWebinfo model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<DdWebinfo> dataList = webinfoService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/webinfoCount.do")
    public void roleDataCount(DdWebinfo model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=webinfoService.queryByCount(model);
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
    @RequestMapping("/deletewebinfo.do")
    public void deleteType(DdWebinfo model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= webinfoService.deleteByPrimaryKey(model.getWebid());
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //添加
    @RequestMapping("/addwebinfo.do")
    public void saceType(DdWebinfo model,  HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setCreattime(DateUtil.getNowPlusTime());
        model.setG_state(1);
        SysUser user = SessionUtils.getUser(request);
        model.setW_uid(user.getEmail());
        int num= webinfoService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑
    @RequestMapping("/updatewebinfo.do")
    public void updatetype(DdWebinfo model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setUpdatetime(DateUtil.getNowPlusTime());
        int num= webinfoService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    
    
}
