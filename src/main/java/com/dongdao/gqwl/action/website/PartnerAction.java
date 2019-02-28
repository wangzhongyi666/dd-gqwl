package com.dongdao.gqwl.action.website;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;

import com.dongdao.gqwl.model.website.DdPartner;
import com.dongdao.gqwl.service.website.PartnerService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/partner")
public class PartnerAction extends BaseAction {

    private final static Logger log= Logger.getLogger(PartnerAction.class);

    @Autowired
    public PartnerService partnerService;

    @RequestMapping(value = "/partner.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/partner", context);
    }

    @RequestMapping(value = "/partner_add.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView partner_add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/partner_add", context);
    }


    @RequestMapping(value = "/partner_update.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView partner_update(DdPartner model, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        model=(DdPartner) partnerService.selectByPrimaryKey(model.getPartner());
        request.setAttribute("PARTNER",model);
        return forword("website/partner_update", context);
    }

    @RequestMapping("/partnerDataList.do")
    public void roleDataList(DdPartner model, HttpServletRequest request, HttpServletResponse response) {


        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<DdPartner> dataList = partnerService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/partnercount.do")
    public void roleDataCount(DdPartner model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=partnerService.queryByCount(model);
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

    @RequestMapping("/deletepartner.do")
    public void deleteType(DdPartner model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setIsdelete(0);
        int num= partnerService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }


    //编辑信息
    @RequestMapping("/updatepartner.do")
    public void updatetype(DdPartner model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= partnerService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }


}
