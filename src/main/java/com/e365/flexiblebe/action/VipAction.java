package com.e365.flexiblebe.action;

import com.e365.flexiblebe.UserConstants;
import com.e365.flexiblebe.bean.SysUser;
import com.e365.flexiblebe.bean.Vip;
import com.e365.flexiblebe.model.MessageModel;
import com.e365.flexiblebe.model.VipModel;
import com.e365.flexiblebe.service.MessageService;
import com.e365.flexiblebe.service.VipService;
import com.e365.flexiblebe.utils.DateUtil;
import com.e365.flexiblebe.utils.HtmlUtil;
import com.e365.flexiblebe.utils.SessionUtils;
import com.e365.flexiblebe.utils.SmsUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vip")
public class VipAction extends BaseAction  {
    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public VipService vipService;
    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public MessageService messageService;

    @RequestMapping(value = "/vipaudit.do")
    public String vipaudit(HttpServletRequest request, HttpServletResponse response) {
        return "vip/nameaudit";
    }
    @RequestMapping(value = "/vipmanage.do")
    public String vipmanage(HttpServletRequest request, HttpServletResponse response) {
        return "vip/vipmanage";
    }
    //获取审核列表
    @RequestMapping("/getAuditlist")
    public void getAuditlist(VipModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<Vip> dataList=vipService.getAuditlist(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    @RequestMapping("/countAuditlist")
    public void countAuditlist(VipModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        Integer count=vipService.countAuditlist(model);
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
    //vip审批
    @RequestMapping("/updateAuditById")
    public void updateAuditById(VipModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            vipService.updateById(model);
            MessageModel msmodel=new MessageModel();
            msmodel.setUser_id(model.getId());
            msmodel.setTitle("实名认证");
            msmodel.setCreate_time(new Date());
            msmodel.setFlag(0);
            msmodel.setUnread(1);
            msmodel.setType(1);
            if(model.getAudit()==2){
                msmodel.setContent("恭喜您，实名认证已通过审核，请在社保服务中继续参保");
            }else{
                msmodel.setContent("您的实名认证失败，请重新认证");
            }
            Vip vip = vipService.queryVipById(model.getId());
            SmsUtil.sendSms("【356灵活通】"+msmodel.getContent(),vip.getTel(),null,null);
            messageService.sendMsg(msmodel);
        } catch (Exception e) {
            e.printStackTrace();
            sendFailureMessage(response, "操作失败！");
        }
        sendSuccessMessage(response, "操作成功~");
    }
    //获取vip列表
    @RequestMapping("/getViplist")
    public void getViplist(VipModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<Vip> dataList=vipService.getViplist(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    @RequestMapping("/countViplist")
    public void countViplist(VipModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        Integer count=vipService.countViplist(model);
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
    @RequestMapping("/uploadImgUrl")
    public void uploadImgUrl(@RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2,
                             Integer id,HttpServletResponse response,
                             HttpServletRequest request) throws Exception {
        try {
            String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
            String type1 = file1!=null && !StringUtils.isBlank(file1.getOriginalFilename())?file1.getOriginalFilename().substring(
                    file1.getOriginalFilename().lastIndexOf(".")):"";
            String type2 = file2!=null && !StringUtils.isBlank(file2.getOriginalFilename())?file2.getOriginalFilename().substring(
                    file2.getOriginalFilename().lastIndexOf(".")):"";
            String parhstr=request.getSession().getServletContext()
                    .getRealPath(File.separator);
            parhstr=parhstr.substring(0,parhstr.length()-5);
            if(type1.equals("") && type2.equals("")){
                //sendSuccessMessage(response, "请选择图片！");
                sendSuccessMessage(response, "操作成功~");
                return;
            }
            if ((!type1.equals("") && imgeArray.indexOf(type1.toUpperCase()) < 0) || (!type2.equals("") && imgeArray.indexOf(type2.toUpperCase()) < 0)) {
                sendSuccessMessage(response, "上传文件不是图片格式,请重新上传！");
                return;
            }
            if ((file1!=null && file1.getSize() > 10485760) || (file2!=null && file2.getSize() > 10485760) ) {
                sendSuccessMessage(response, "上传图片不能大于10M,请重新上传！");
                return;
            }
            String sjc = "";
            String path = "";
            VipModel model=new VipModel();
            if(!type1.equals("")){
                sjc=DateUtil.getNowPlusTimeMill();
                path = parhstr
                        +"aptitude"
                        + File.separator
                        + sjc
                        +type1;
                File f = new File(path);
                // 创建文件夹
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                System.out.println(path);
                file1.transferTo(new File(path));
                model.setIdentPicUrl1(UserConstants.CRMURL + "aptitude/"+ sjc + type1);
            }
            if(!type2.equals("")){
                sjc=DateUtil.getNowPlusTimeMill()+2;
                path = parhstr
                        + "aptitude"
                        + File.separator
                        + sjc
                        +type2;
                File f = new File(path);
                // 创建文件夹
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                file2.transferTo(new File(path));
                model.setIdentPicUrl2(UserConstants.CRMURL + "aptitude/"+ sjc + type2);
            }
            model.setId(id);
            vipService.updateById(model);
        } catch (Exception e) {
            e.printStackTrace();
            sendFailureMessage(response, "操作失败~");
        }
        sendSuccessMessage(response, "操作成功~");
    }
}
