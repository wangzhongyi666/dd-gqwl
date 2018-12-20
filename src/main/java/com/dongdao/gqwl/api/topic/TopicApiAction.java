package com.dongdao.gqwl.api.topic;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.model.website.DdLeave;
import com.dongdao.gqwl.service.gcolumn.DdLeaveService;
import com.dongdao.gqwl.service.routline.topic.TopicService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
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
@RequestMapping("/topic")
public class TopicApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(TopicApiAction.class);

    @Autowired
    public TopicService topicService;

    //查询话题
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/topic.json")
    public Map<String, Object> sendCode(
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            List<HashMap<String,Object>> topics=topicService.selectAll();
            jsonMap.put("topics",topics);
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

    //话题点击量+1
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/addnums.json")
    public Map<String, Object> addNums(DdTopic model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            int num=topicService.updateNums(model);
            if(num==1){
                return setSuccessMap(jsonMap, "操作成功！", null);
            }else{
                return setFailureMap(jsonMap, "操作失败！", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }

}
