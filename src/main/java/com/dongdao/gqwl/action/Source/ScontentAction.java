package com.dongdao.gqwl.action.Source;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.source.DdAudit;
import com.dongdao.gqwl.model.source.DdLabel;
import com.dongdao.gqwl.model.source.DdScontent;
import com.dongdao.gqwl.model.source.DdStype;
import com.dongdao.gqwl.service.source.DdAuditService;
import com.dongdao.gqwl.service.source.STypeService;
import com.dongdao.gqwl.service.source.ScontentService;
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
@RequestMapping("/scontent")
public class ScontentAction extends BaseAction {

    private final static Logger log= Logger.getLogger(ScontentAction.class);

    @Autowired
    public ScontentService scontentService;

    @Autowired
    public DdAuditService<DdAudit> ddAudiService;

    @RequestMapping(value = "/scontent.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView scontent(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("source/scontent", context);
    }


    @RequestMapping(value = "/scontent_add.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView scontent_add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("source/scontent_add", context);
    }

    @RequestMapping(value = "/scontent_update.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView scontent_update(DdScontent model,HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        model=(DdScontent) scontentService.selectByPrimaryKey(model.getS_contentid());
        request.setAttribute("SCONTENT",model);
        return forword("source/scontent_update", context);
    }

    @RequestMapping("/scontentDataList.do")
    public void roleDataList(DdScontent model, HttpServletRequest request, HttpServletResponse response) {

        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<DdScontent> dataList = scontentService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/scontentcount.do")
    public void roleDataCount(DdScontent model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=scontentService.queryByCount(model);
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

    @RequestMapping("/deletescontent.do")
    public void deleteType(DdScontent model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setS_state(0);
        int num= scontentService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addscontent.do")
    public void saceType(DdScontent model,@RequestParam("logos") MultipartFile logos,@RequestParam("pics") MultipartFile pics, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logopath=uploadFile("logos",logos,request);
        String picpath=uploadFile("pics",pics,request);
        if(!"".equals(picpath)&picpath!=null){
            model.setS_fimg(picpath);
        }
        if(!"".equals(logopath)&logopath!=null){
            model.setS_icon(logopath);
        }
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setW_uid(user.getEmail());
        model.setS_r_time(DateUtil.getNowPlusTime());
        model.setS_state(1);
        int num= scontentService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑素材信息
    @RequestMapping("/updatescontent.do")
    public void updatetype(DdScontent model, @RequestParam("logos") MultipartFile logos,@RequestParam("pics") MultipartFile pics, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if("".equals(model.getS_music())){
            model.setS_music(null);
        }
        if("".equals(model.getS_video())){
            model.setS_video(null);
        }
        if("".equals(model.getS_file())){
            model.setS_file(null);
        }
        String logopath=uploadFile("logos",logos,request);
        String picpath=uploadFile("pics",pics,request);
        if(!"".equals(picpath)&picpath!=null){
            model.setS_fimg(picpath);
        }
        if(!"".equals(logopath)&logopath!=null){
            model.setS_icon(logopath);
        }
        model.setS_m_time(DateUtil.getNowPlusTime());
        int num= scontentService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }


    //审核素材信息
    @RequestMapping("/passscontent.do")
    public void passscontent(DdScontent model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        DdAudit audit=new DdAudit();
        audit.setS_contentid(model.getS_contentid());
        audit.setS_r_time(DateUtil.getNowPlusTime());
        audit.setW_uid(user.getId()+"");

        if(model.getS_audit()==1){
            model.setS_audit(0);
            audit.setS_state(0);
            audit.setSmessage("撤销审核成功");
        }else if(model.getS_audit()==0){
            model.setS_audit(1);
            audit.setS_state(1);
            audit.setSmessage("提交审核成功");
        }
        ddAudiService.insertSelective(audit);
        int num= scontentService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //查询素材分类
    @RequestMapping("/querytype.do")
    public void seetype(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<DdStype> pictypes= scontentService.queryType();
        jsonMap.put("stype", pictypes);

        HtmlUtil.writerJson(response, jsonMap);

    }


    //查询素材标签
    @RequestMapping("/querylabel.do")
    public void seelabel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        List<DdLabel> pictypes= scontentService.queryLabel();
        jsonMap.put("label", pictypes);

        HtmlUtil.writerJson(response, jsonMap);

    }

}
