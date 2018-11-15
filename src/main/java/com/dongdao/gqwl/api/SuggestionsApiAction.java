package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.website.RasteUser;
import com.dongdao.gqwl.model.website.DdSuggestions;
import com.dongdao.gqwl.service.gcolumn.RasteUserService;
import com.dongdao.gqwl.service.website.SuggestionsService;
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
@RequestMapping("/suggestions")
public class SuggestionsApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(SuggestionsApiAction.class);

    @Autowired
    public SuggestionsService<DdSuggestions> suggestionsService;


    @Autowired
    public RasteUserService<RasteUser> rasteUserService;

    //提交意见
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/addsuggestions.json")
    public Map<String, Object>  roleDataCount(String wx_ident,String suggestion,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
      try {
          if (wx_ident == null||wx_ident.equals("")) {
              return setFailureMap(jsonMap, "消息接收人不能为空！", null);
          }
          RasteUser user = new RasteUser();
          user.setWx_ident(wx_ident);
          RasteUser user0 = rasteUserService.queryByToLogin(user);
          DdSuggestions sugg = new DdSuggestions();
          sugg.setUser_id(user0.getId());
          sugg.setCreatetime(DateUtil.getNowPlusTime());
          sugg.setSuggestion(suggestion);
          suggestionsService.insertSelective(sugg);
          return setSuccessMap(jsonMap, "操作成功！", null);
       }catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }

    }
}
