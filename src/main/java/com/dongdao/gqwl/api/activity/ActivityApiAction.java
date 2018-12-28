package com.dongdao.gqwl.api.activity;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.routline.activity.DdActivity;
import com.dongdao.gqwl.model.routline.activity.DdInget;
import com.dongdao.gqwl.model.routline.activity.DdRank;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.model.website.RasteUser;
import com.dongdao.gqwl.service.gcolumn.RasteUserService;
import com.dongdao.gqwl.service.routline.activity.ActivityService;
import com.dongdao.gqwl.service.routline.activity.IngetService;
import com.dongdao.gqwl.service.routline.activity.RankService;
import com.dongdao.gqwl.service.routline.topic.TopicService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/activity")
public class ActivityApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(ActivityApiAction.class);

    @Autowired
    public ActivityService activityService;
    @Autowired
    public IngetService ingetService;
    @Autowired
    public RasteUserService rasteUserService;
    @Autowired
    public RankService rankService;


    //查询话题
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/activity.json")
    public Map<String, Object> sendCode(Long type,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            List<HashMap<String,Object>> topics=activityService.selectById(type);
            jsonMap.put("activity",topics);
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    //话题点击量+1
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/addactivity.json")
    public Map<String, Object> addNums(DdInget model,HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        RasteUser user= SessionUtils.getRasteUser(request);
        if(user!=null){
            model.setR_uid(Long.parseLong(user.getId()+"") );
        }
        DdInget inget=ingetService.selectByUser(model.getR_uid());
        if(inget!=null){
            return setFailureMap(jsonMap, "每天只能领取一次呦！", null);
        }else{
            int maxinter=0;
           List<DdInget> ingets=ingetService.selectByToday();
           for(int i=0;i<ingets.size();i++){
               maxinter+=ingets.get(i).getIntegar();
           }
           DdActivity activity=(DdActivity)activityService.selectByPrimaryKey(model.getActid());
           if(maxinter>=activity.getMaxinter()){
               return setSuccessMap(jsonMap, "来晚啦，积分已被抢空！明天再来！！", null);
           }else{
             int myinter=  (int)(1+Math.random()*(10-1+1));
             user=(RasteUser) rasteUserService.selectByPrimaryKey(Integer.parseInt(model.getR_uid()+""));
             user.setIntegral(user.getIntegral()+myinter);
             int num=rasteUserService.updateByPrimaryKeySelective(user);
             if(num==1){
                 model.setIntegar(myinter);
                 model.setCreattime(DateUtil.getDateLong(new Date()));
                 ingetService.insertSelective(model);
                 DdRank ranks=rankService.selectById(model.getR_uid());
                 if(ranks!=null){
                     ranks.setIntegar(ranks.getIntegar()+myinter);
                     rankService.updateByPrimaryKeySelective(ranks);
                 }else{
                     ranks=new DdRank();
                     ranks.setR_uid(model.getR_uid());
                     ranks.setCreattime(DateUtil.getNowPlusTime());
                     ranks.setIntegar(myinter);
                     ranks.setActid(activity.getActid());
                     rankService.insertSelective(ranks);
                 }
                 return setSuccessMap(jsonMap, "获得"+myinter+"积分！", null);
             }else{
                 return setFailureMap(jsonMap, "操作失败！", null);
             }
           }
        }

    }

    //查询话题
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/ranks.json")
    public Map<String, Object> getrank(Long actid,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            List<HashMap<String,Object>> topics=rankService.selectByAct(actid);
            jsonMap.put("ranks",topics);
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

}
