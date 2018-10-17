package com.dongdao.gqwl.action.websit;

import com.dongdao.gqwl.UserConstants;
import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.websit.DdLeave;
import com.dongdao.gqwl.model.websit.RasteMassage;
import com.dongdao.gqwl.service.gcolumn.DdLeaveService;
import com.dongdao.gqwl.service.gcolumn.RasteMassageService;
import com.dongdao.gqwl.service.source.DdAuditService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ddleave")
public class DdLeaveAction extends BaseAction {

    private final static Logger log= Logger.getLogger(DdLeaveAction.class);

    @Autowired
    public DdLeaveService<DdLeave> ddLeaveService;

    @RequestMapping(value = "/ddleave.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("websit/ddleave", context);
    }


    @RequestMapping("/ddleaveDataList.do")
    public void audiDataList(DdLeave model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<DdLeave> dataList = ddLeaveService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/ddleaveCount.do")
    public void roleDataCount(RasteMassage model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=ddLeaveService.queryByCount(model);
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

    //审核
    @RequestMapping("/deleteddleave.do")
    public void updateType(DdLeave model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= ddLeaveService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }


    //查看
    @RequestMapping("/getddleave.do")
    public void getddleave(DdLeave model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        DdLeave model1 = ddLeaveService.selectByPrimaryKey(model.getLeave_id());
        jsonMap.put("data",model1);
        HtmlUtil.writerJson(response, jsonMap);

    }
}
