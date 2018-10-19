package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.websit.DdLeave;
import com.dongdao.gqwl.model.websit.RasteUser;
import com.dongdao.gqwl.service.gcolumn.DdLeaveService;
import com.dongdao.gqwl.service.gcolumn.RasteUserService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.SendSms;
import com.dongdao.gqwl.utils.SessionUtils;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/leave")
public class LeaveApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(LeaveApiAction.class);

    @Autowired
    public DdLeaveService<DdLeave> ddLeaveService;

    //提交留言
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/commitleave.json")
    public Map<String, Object> sendCode(String phone,String email,String leave,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            DdLeave ddLeave = new DdLeave();
            ddLeave.setPhone(phone);
            ddLeave.setEmail(email);
            ddLeave.setLeave(leave);
            ddLeaveService.insertSelective(ddLeave);
            return setSuccessMap(jsonMap, "操作成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }
}
