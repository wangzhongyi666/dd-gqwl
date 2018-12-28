package com.dongdao.gqwl.action.routline.activity;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.routline.activity.DdRank;
import com.dongdao.gqwl.service.routline.activity.RankService;
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
@RequestMapping("/rank")
public class RankAction extends BaseAction {

    private final static Logger log= Logger.getLogger(RankAction.class);

    @Autowired
    public RankService rankService;


    @RequestMapping(value = "/rank.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response,Long actid) {
        request.getSession().setAttribute("actid",actid);
        Map<String, Object> context = getRootMap();
        return forword("routline/activity/rank", context);
    }

    

    @RequestMapping("/rankDataList.do")
    public void roleDataList(DdRank model, HttpServletRequest request, HttpServletResponse response) {

        model.setActid((Long)request.getSession().getAttribute("actid"));
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<DdRank> dataList = rankService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/rankCount.do")
    public void roleDataCount(DdRank model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=rankService.queryByCount(model);
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




}
