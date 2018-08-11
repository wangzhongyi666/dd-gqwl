package com.dongdao.gqwl.action.Source;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.action.SysAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.SysRoleModel;
import com.dongdao.gqwl.model.source.DdPictype;
import com.dongdao.gqwl.service.source.PicTypeService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
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
@RequestMapping("/pictype")
public class PicTypeAction extends BaseAction {

    private final static Logger log= Logger.getLogger(PicTypeAction.class);

    @Autowired
    public PicTypeService PicTypeService;

    @RequestMapping(value = "/pictype.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("source/pictype", context);
    }


    @RequestMapping("/pictypeDataList.do")
    public void roleDataList(DdPictype model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<DdPictype> dataList = PicTypeService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/pictypecount.do")
    public void roleDataCount(DdPictype model, HttpServletRequest request, HttpServletResponse response) throws Exception{

        Integer count=PicTypeService.queryByCount(model);
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

    @RequestMapping("/deleteptype.do")
    public void deleteType(DdPictype model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= PicTypeService.deleteByPrimaryKey(model.getPictypeid());
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addtype.do")
    public void saceType(DdPictype model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= PicTypeService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/updatetype.do")
    public void updatetype(DdPictype model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        int num= PicTypeService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

}
