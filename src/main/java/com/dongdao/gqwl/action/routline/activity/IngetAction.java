package com.dongdao.gqwl.action.routline.activity;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.routline.activity.DdActivity;
import com.dongdao.gqwl.model.routline.activity.DdInget;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.service.routline.activity.IngetService;
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
@RequestMapping("/inget")
public class IngetAction extends BaseAction {

    private final static Logger log= Logger.getLogger(IngetAction.class);

    @Autowired
    public IngetService ingetService;


    @RequestMapping(value = "/inget.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("routline/activity/inget", context);
    }

    

    @RequestMapping("/ingetDataList.do")
    public void roleDataList(DdInget model, HttpServletRequest request, HttpServletResponse response) {

        List<DdInget> dataList = ingetService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/ingetCount.do")
    public void roleDataCount(DdInget model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=ingetService.queryByCount(model);
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

    //查询活动编号
    @RequestMapping("/querytype.do")
    public void seetype(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<DdActivity> pictypes= ingetService.queryType();
        jsonMap.put("actid", pictypes);

        HtmlUtil.writerJson(response, jsonMap);

    }


}
