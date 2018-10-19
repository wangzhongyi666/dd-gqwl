package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.websit.DdLeave;
import com.dongdao.gqwl.model.websit.RasteMassage;
import com.dongdao.gqwl.service.gcolumn.DdLeaveService;
import com.dongdao.gqwl.service.gcolumn.RasteMassageService;
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
@RequestMapping("/massage")
public class MassageApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(MassageApiAction.class);

    @Autowired
    public RasteMassageService<RasteMassage> rasteMassageService;

    //联系我们
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/massageinfo.json")
    public Map<String, Object> rasteMassageService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            RasteMassage rm = new RasteMassage();
            List<RasteMassage> rm1 = rasteMassageService.queryByList(rm);
            return setSuccessMap(jsonMap, "操作成功！", rm1);
        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }
}
