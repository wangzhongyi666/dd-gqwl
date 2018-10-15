package com.dongdao.gqwl.action.website;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.website.DdPicture;
import com.dongdao.gqwl.service.website.PictureService;
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
@RequestMapping("/picture")
public class PictureAction extends BaseAction {

    private final static Logger log= Logger.getLogger(PictureAction.class);

    @Autowired
    public PictureService pictureService;


    @RequestMapping(value = "/picture.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView picture(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/picture", context);
    }


    @RequestMapping(value = "/picture_add.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView picture_add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("website/picture_add", context);
    }

    @RequestMapping(value = "/picture_update.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView picture_update(DdPicture model, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        model=(DdPicture) pictureService.selectByPrimaryKey(model.getPicid());
        request.setAttribute("PICTURE",model);
        return forword("website/picture_update", context);
    }

    @RequestMapping("/pictureDataList.do")
    public void roleDataList(DdPicture model, HttpServletRequest request, HttpServletResponse response) {

        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        List<DdPicture> dataList = pictureService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/picturecount.do")
    public void roleDataCount(DdPicture model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=pictureService.queryByCount(model);
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

    @RequestMapping("/deletepicture.do")
    public void deleteType(DdPicture model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
      
        int num= pictureService.deleteByPrimaryKey(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    @RequestMapping("/addpicture.do")
    public void saceType(DdPicture model, @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setP_audit(user.getEmail());
        model.setCreattime(DateUtil.getNowPlusTime());
        String picpath=uploadFile("picture",file,request);
        if(!"".equals(picpath)&picpath!=null){
            model.setPicpath(picpath);
        }
        int num= pictureService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //编辑素材信息
    @RequestMapping("/updatepicture.do")
    public void updatetype(DdPicture model, @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        model.setP_audit(user.getEmail());
        model.setUpdatetime(DateUtil.getNowPlusTime());
        String picpath=uploadFile("picture",file,request);
        if(!"".equals(picpath)&picpath!=null){
            model.setPicpath(picpath);
        }
        int num= pictureService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }


    //审核信息
    @RequestMapping("/passpicture.do")
    public void passpicture(DdPicture model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SysUser user = SessionUtils.getUser(request);
        if(model.getIspass()==1){
            model.setIspass(0);
        }else{
            model.setIspass(1);
        }
        int num= pictureService.updateByPrimaryKeySelective(model);

        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }


}
