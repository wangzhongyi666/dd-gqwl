package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.website.news.DdNews;
import com.dongdao.gqwl.service.website.PictureService;
import com.dongdao.gqwl.service.website.ProfileService;
import com.dongdao.gqwl.service.website.news.NewService;
import com.dongdao.gqwl.service.website.news.NewstypeService;
import com.dongdao.gqwl.utils.Auth;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/infor")
public class NewsApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(NewsApiAction.class);

    @Autowired
    public NewService newsService;
    @Autowired
    public PictureService pictureService;
    @Autowired
    public NewstypeService newstypeService;

    //企业咨询
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/infor.json")
    public Map<String, Object>  roleDataCount(int newstype,int ptype,int top,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        DdNews qnews=new DdNews();
        qnews.setNewstype(newstype);
        qnews.setTop(top);
      try {
        List<Map<String, Object>> newstypes=newstypeService.queryType();
        List<Map<String, Object>> news=newsService.selectByType(qnews);
        List<Map<String, Object>> pics=pictureService.selectByType(ptype);
        jsonMap.put("news",news );
        jsonMap.put("newstype",newstypes );
        jsonMap.put("pics", pics);
        return setSuccessMap(jsonMap, "操作成功！", null);
       }
       catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }

    }

    //新闻切换
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/news.json")
    public Map<String, Object>  newschange(int newstype,int top,HttpServletRequest request, HttpServletResponse response) throws Exception{
        DdNews qnews=new DdNews();
        qnews.setNewstype(newstype);
        qnews.setTop(top);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> news=newsService.selectByType(qnews);
            jsonMap.put("news",news );
            return setSuccessMap(jsonMap, "操作成功！", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }

    }

}
