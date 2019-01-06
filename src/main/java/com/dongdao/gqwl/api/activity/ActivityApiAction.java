package com.dongdao.gqwl.api.activity;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.routline.activity.DdActivity;
import com.dongdao.gqwl.model.routline.activity.DdInget;
import com.dongdao.gqwl.model.routline.activity.DdRact;
import com.dongdao.gqwl.model.routline.activity.DdRank;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.model.website.RasteUser;
import com.dongdao.gqwl.service.gcolumn.RasteUserService;
import com.dongdao.gqwl.service.routline.activity.ActivityService;
import com.dongdao.gqwl.service.routline.activity.IngetService;
import com.dongdao.gqwl.service.routline.activity.RactService;
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
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    public RactService ractService;

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
                 return setSuccessMap(jsonMap, myinter+"", null);
             }else{
                 return setFailureMap(jsonMap, "操作失败！", null);
             }
           }
        }

    }

    //查询排行
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/ranks.json")
    public Map<String, Object> getrank(Long actid,Long r_uid,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            DdRank ranks=rankService.selectById(r_uid);
            List<HashMap<String,Object>> topics=rankService.selectByAct(actid);
            if(ranks!=null){

            }else{
                ranks=new DdRank();
                ranks.setR_uid(r_uid);
                ranks.setCreattime(DateUtil.getNowPlusTime());
                ranks.setIntegar(0);
                ranks.setActid(Long.parseLong(1+""));
                rankService.insertSelective(ranks);
            }
            int rank=rankService.selectRank(r_uid);
            List<HashMap<String,Object>> myrank=rankService.selectRuid(r_uid);
            List<HashMap<String,Object>> befors=rankService.selectBefor(ranks);
            ranks.setNum2(6-befors.size()-1);
            List<HashMap<String,Object>> afters=rankService.selectAfter(ranks);
            Collections.reverse(befors);

            for(int i=0;i<myrank.size();i++){
                myrank.get(i).put("ranks",rank);
            }for(int i=0;i<befors.size();i++){
                befors.get(i).put("ranks",rank-1-i);
            }for(int i=0;i<afters.size();i++){
                afters.get(i).put("ranks",rank+1+i);
            }
            List<HashMap<String,Object>> newsranks=new ArrayList<HashMap<String,Object>>();
            newsranks.addAll(befors);
            newsranks.addAll(myrank);
            newsranks.addAll(afters);
            jsonMap.put("ranks",newsranks);
            /*jsonMap.put("beforranks",befors);
            jsonMap.put("afterranks",afters);*/
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }



    //弹窗记录
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/ract.json")
    public Map<String, Object> getract(Long r_uid,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
        int num=0;
            String msg="";
        DdActivity activity=new DdActivity();
        activity.setActid(Long.parseLong(1+""));
        activity=(DdActivity) activityService.selectByPrimaryKey(activity);
        //String nowdate=DateUtil.getCurrDate();
        SimpleDateFormat timestampFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date begintime = timestampFormat.parse(activity.getBegintime());
        Date endtime=timestampFormat.parse(activity.getEndtime());
        Date nowdate=new Date();

        if(nowdate.getTime()>=begintime.getTime()&&nowdate.getTime()<=endtime.getTime()){
            DdRact ract=new DdRact();
            ract.setR_uid(r_uid);
            ract.setCreattime(DateUtil.getCurrDate());
            ract=ractService.selectByPrimaryKey(ract);
            DdInget inget=ingetService.selectByUser(r_uid);
            if(inget!=null){
                msg="今天已经领取过了！";
            }else {
                if(ract!=null){
                    if(ract.getNums()>=3){
                        msg="已经弹出三次！";
                    }else{
                        ract.setNums(ract.getNums()+1);
                        num= ractService.updateByPrimaryKeySelective(ract);
                        msg="操作成功";
                    }
                }else{
                    DdRact newract=new DdRact();
                    newract.setR_uid(r_uid);
                    newract.setCreattime(DateUtil.getCurrDate());
                    num=ractService.insertSelective(newract);
                    msg="操作成功";
                }
            }

        }else{
            ractService.deleteByPrimaryKey(1);
            msg="不在活动时间";
        }
            if(num==1){
                jsonMap.put("istrue",true);
            }else{
                jsonMap.put("istrue",false);
            }
            return setSuccessMap(jsonMap, msg, null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

}
