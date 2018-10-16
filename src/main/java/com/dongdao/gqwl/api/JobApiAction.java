package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.website.job.DdJob;
import com.dongdao.gqwl.service.website.PictureService;
import com.dongdao.gqwl.service.website.job.JobService;
import com.dongdao.gqwl.service.website.job.JobtypeService;
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
@RequestMapping("/position")
public class JobApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(JobApiAction.class);

    @Autowired
    public JobService jobService;
    @Autowired
    public PictureService pictureService;
    @Autowired
    public JobtypeService jobtypeService;

    //获取总数
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/position.json")
    public Map<String, Object>  postion(int ptype, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
      try {
        List<Map<String, Object>> jobtype=jobtypeService.queryType();
        List<Map<String, Object>> pics=pictureService.selectByType(ptype);
        List<Map<String, Object>> areas=jobService.queryArea();
        jsonMap.put("jobtype",jobtype );
        jsonMap.put("pics", pics);
        jsonMap.put("areas",areas );
        return setSuccessMap(jsonMap, "操作成功！", null);
       }
       catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }

    }

    //分页
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/pageposition.json")
    public Map<String, Object>  pageData(DdJob model,  HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Integer count=jobService.queryByCount(model);
        if(model!=null && model.getPageNum()!=null && model.getPageSize()!=null){
            model.setNum1(model.getPageSize()*(model.getPageNum()-1));
            model.setNum2(model.getPageSize());
        }
        try {
            List<Map<String, Object>> jobs=jobService.selectByType(model);
            jsonMap.put("jobs",jobs );
            jsonMap.put("count",count);
            jsonMap.put("pageSize",model.getNum2());
            if( model.getPageSize()*(model.getPageNum())>=count){
                jsonMap.put("isNext",false);
            }else{
                jsonMap.put("isNext",true);
            }


            return setSuccessMap(jsonMap, "操作成功！", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }

    }

}
