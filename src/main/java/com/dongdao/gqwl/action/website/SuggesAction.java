package com.dongdao.gqwl.action.website;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.website.DdSuggestions;
import com.dongdao.gqwl.service.website.SuggestionsService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.ExcelUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sugges")
public class SuggesAction extends BaseAction {

    private final static Logger log= Logger.getLogger(SuggesAction.class);

    @Autowired
    public SuggestionsService<DdSuggestions> suggestionsService;

    @RequestMapping(value = "/sugges.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/suggestions", context);
    }

    @RequestMapping("/suggestionsDataList.do")
    public void roleDataList(DdSuggestions model, HttpServletRequest request, HttpServletResponse response) {

        List<DdSuggestions> dataList = suggestionsService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/suggestionscount.do")
    public void roleDataCount(DdSuggestions model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=suggestionsService.queryByCount(model);
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

    @RequestMapping("/deletesugges.do")
    public void deleteType(DdSuggestions model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int num=suggestionsService.deleteByPrimaryKey(model.getSuggestion_id());
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/getsugges.do")
    public void getsugges(DdSuggestions model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        DdSuggestions sugges = suggestionsService.selectByPrimaryKey(model.getSuggestion_id());

        jsonMap.put("data",sugges);
        jsonMap.put("msg", "操作成功！");

        HtmlUtil.writerJson(response, jsonMap);

    }


    @RequestMapping("/exportSugges.do")
    public void exportOrderList(DdSuggestions model,
                                HttpServletResponse response, HttpServletRequest request) throws Exception{
        try {
//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("suggestion_id", "编号");
            fieldMap.put("wx_ident", "微信号");
            fieldMap.put("suggestion", "反馈内容");
            fieldMap.put("createtime", "创建时间");

//	        // excel的sheetName
            String sheetName = "反馈信息列表";
//	        // excel要导出的数据
            //model.setRows(1000);
            List<DdSuggestions> dataList = suggestionsService.queryByList(model);
            // 导出
            if (dataList == null || dataList.size() == 0) {
            }else {
                //将list集合转化为excle
                ExcelUtil.listToExcel(dataList, fieldMap, sheetName, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
