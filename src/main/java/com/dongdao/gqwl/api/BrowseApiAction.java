package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.websit.DdLeave;
import com.dongdao.gqwl.model.website.Ddbrowse;
import com.dongdao.gqwl.service.gcolumn.DdLeaveService;
import com.dongdao.gqwl.service.gcolumn.DdbrowseService;
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
import java.util.Map;

@Controller
@RequestMapping("/browse")
public class BrowseApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(BrowseApiAction.class);

    @Autowired
    public DdbrowseService<Ddbrowse> ddbrowseService;

    //提交留言
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/commitleave.json")
    public Map<String, Object> sendCode(Integer user_id,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            DdLeave ddLeave = new DdLeave();
            String today = DateUtil.getToday();
            String yesterday = DateUtil.getYesterday();
            ddLeave.setStarttime(today);
            ddLeave.setEndtime(yesterday);
            Integer todaycount = ddLeaveService.queryByCount(ddLeave);
            if(todaycount>=1000){
                return setFailureMap(jsonMap,"当前留言数量超出限制!",null);

            }
            ddLeave.setPhone(phone);
            ddLeave.setEmail(email);
            ddLeave.setLeave(leave);
            ddLeave.setCreatetime(DateUtil.getNowPlusTime());
            ddLeaveService.insertSelective(ddLeave);
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }
}
