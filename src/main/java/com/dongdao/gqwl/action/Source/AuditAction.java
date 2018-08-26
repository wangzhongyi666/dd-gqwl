package com.dongdao.gqwl.action.Source;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.source.DdAudit;
import com.dongdao.gqwl.service.source.DdAuditService;
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
@RequestMapping("/audit")
public class AuditAction extends BaseAction {

    private final static Logger log= Logger.getLogger(AuditAction.class);

    @Autowired
    public DdAuditService<DdAudit> ddAudiService;

    @RequestMapping(value = "/audit.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("source/audit", context);
    }


    @RequestMapping("/auditDataList.do")
    public void audiDataList(DdAudit model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<DdAudit> dataList = ddAudiService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/auditCount.do")
    public void roleDataCount(DdAudit model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=ddAudiService.queryByCount(model);
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
