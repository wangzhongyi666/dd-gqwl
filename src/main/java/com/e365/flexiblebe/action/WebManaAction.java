package com.e365.flexiblebe.action;

import com.e365.flexiblebe.UserConstants;
import com.e365.flexiblebe.bean.SysUser;
import com.e365.flexiblebe.bean.Webinfo;
import com.e365.flexiblebe.model.VipModel;
import com.e365.flexiblebe.model.WebTextModel;
import com.e365.flexiblebe.model.WebinfoModel;
import com.e365.flexiblebe.service.WebManaService;
import com.e365.flexiblebe.utils.Auth;
import com.e365.flexiblebe.utils.DateUtil;
import com.e365.flexiblebe.utils.HtmlUtil;
import com.e365.flexiblebe.utils.SessionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.applet.Main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web")
public class WebManaAction extends BaseAction  {
    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public WebManaService webManaService;
    @RequestMapping(value = "/webinfo.do")
    public String webinfo(HttpServletRequest request, HttpServletResponse response) {
        return "webmana/webinfo";
    }
    @RequestMapping(value = "/webtext.do")
    public String webtext(HttpServletRequest request, HttpServletResponse response) {
        return "webmana/webtext";
    }
    @Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping(value = "/webadd.do")
    public ModelAndView webadd(WebinfoModel model, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        context.put("id", model.getId());
        context.put("title", model.getTitle());
        context.put("cont", model.getCont());
        context.put("picurl1", model.getPicurl1());
        context.put("picurl2", model.getPicurl2());
        context.put("picurl3", model.getPicurl3());
        return forword("webmana/web_add",context);
    }
    @RequestMapping("/countWebInfo")
    public void countWebInfo(WebinfoModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        Integer count=webManaService.countWebInfo(model);
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
    @RequestMapping("/getwebinfolist")
    public void getwebinfolist(WebinfoModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        List<Webinfo> dataList=webManaService.getwebinfolist(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    @RequestMapping("/deletinfo")
    public void deletinfo(WebinfoModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            webManaService.deletinfo(model);
        } catch (Exception e) {
            e.printStackTrace();
            sendSuccessMessage(response, "操作失败~");
        }
        sendSuccessMessage(response, "操作成功~");
    }
    @RequestMapping("/addInfo")
    public void addInfo(@RequestParam("file1") List<MultipartFile> file1,@RequestParam("file2") List<MultipartFile> file2,
                             @RequestParam("file3") List<MultipartFile> file3,String lieinp,@RequestParam("qianinp")List<String> qianinp,@RequestParam("houinp")List<String> houinp,
                             String title,String cont,Integer id, HttpServletResponse response,
                             HttpServletRequest request) throws Exception{
        WebinfoModel model=new WebinfoModel();
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
        model.setReleid(user.getId());
        model.setReleTime(DateUtil.getNowPlusTime());
        String picurl1="";
        String picurl2="";
        String picurl3="";
        if(title!=null && !title.equals("")){
            model.setTitle(title);
        }
        if(cont!=null && !cont.equals("")){
            model.setCont(cont);
        }
        if(id!=null && id>0){
            WebinfoModel model1=new WebinfoModel();
            model1.setId(id);
            List<Webinfo> wi= webManaService.getwebinfolist(model1);
            if(wi!=null && wi.size()>0){
                String p1=wi.get(0).getPicurl1();
                String p2=wi.get(0).getPicurl2();
                String p3=wi.get(0).getPicurl3();
                String[] p2s=p2.split(";");
                String[] p3s=p3.split(";");
                if(!p1.equals(lieinp)){
                    String url=uploadImg(file1.get(0),request);
                    picurl1+=url;
                }else{
                    picurl1=p1;
                }
                if(qianinp!=null && qianinp.size()>0){
                    if(p2s!=null && p2s.length>0){
                        for(int i=0;i<qianinp.size();i++){
                            String a="0";
                            for(String p2surl : p2s){
                                if (qianinp.get(i).equals(p2surl)){
                                    a="1";
                                }
                            }
                            if(a.equals("1")){
                            }else{
                                String url=uploadImg(file2.get(i),request);
                                if(url!=null && url.equals("2")){
                                    sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
                                    return;
                                }else if(url!=null && url.equals("3")){
                                    sendFailureMessage(response, "上传文件过大,请重新上传!");
                                    return;
                                }
                                qianinp.set(i,url);
                            }
                        }
                        for(int i=0;i<qianinp.size();i++){
                            if(i==0){
                                picurl2+=qianinp.get(i);
                            }else{
                                picurl2+=";"+qianinp.get(i);
                            }
                        }
                    }else{
                        if(file2!=null && file2.size()>0){
                            for(int i=0;i<file2.size();i++){
                                String url=uploadImg(file2.get(i),request);
                                if(url!=null && url.equals("2")){
                                    sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
                                    return;
                                }else if(url!=null && url.equals("3")){
                                    sendFailureMessage(response, "上传文件过大,请重新上传!");
                                    return;
                                }
                                if(i==0){
                                    picurl2+=url;
                                }else{
                                    picurl2+=";"+url;
                                }
                            }
                        }
                    }
                }
                if(houinp!=null && houinp.size()>0){
                    if(p3s!=null && p3s.length>0){
                        for(int i=0;i<houinp.size();i++){
                            String b="0";
                            for(String p3surl : p3s){
                                if(houinp.get(i).equals(p3surl)){
                                    b="1";
                                }
                            }
                            if(b.equals("1")){
                            }else{
                                String url=uploadImg(file3.get(i),request);
                                if(url!=null && url.equals("2")){
                                    sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
                                    return;
                                }else if(url!=null && url.equals("3")){
                                    sendFailureMessage(response, "上传文件过大,请重新上传!");
                                    return;
                                }
                                houinp.set(i,url);
                            }
                        }
                        for(int i=0;i<houinp.size();i++){
                            if(i==0){
                                picurl3+=houinp.get(i);
                            }else{
                                picurl3+=";"+houinp.get(i);
                            }
                        }
                    }else{
                        if(file3!=null && file3.size()>0){
                            for(int i=0;i<file3.size();i++){
                                String url=uploadImg(file3.get(i),request);
                                if(url!=null && url.equals("2")){
                                    sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
                                    return;
                                }else if(url!=null && url.equals("3")){
                                    sendFailureMessage(response, "上传文件过大,请重新上传!");
                                    return;
                                }
                                if(i==0){
                                    picurl3+=url;
                                }else{
                                    picurl3+=";"+url;
                                }
                            }
                        }
                    }
                }

            }
        }else{
            if(file1!=null && file1.size()>0){
                for(int i=0;i<file1.size();i++){
                    String url=uploadImg(file1.get(i),request);
                    picurl1+=url;
                }
            }
            if(file2!=null && file2.size()>0){
                for(int i=0;i<file2.size();i++){
                    String url=uploadImg(file2.get(i),request);
                    if(url!=null && url.equals("2")){
                        sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
                        return;
                    }else if(url!=null && url.equals("3")){
                        sendFailureMessage(response, "上传文件过大,请重新上传!");
                        return;
                    }
                    if(i==0){
                        picurl2+=url;
                    }else{
                        picurl2+=";"+url;
                    }
                }
            }
            if(file3!=null && file3.size()>0){
                for(int i=0;i<file3.size();i++){
                    String url=uploadImg(file3.get(i),request);
                    if(url!=null && url.equals("2")){
                        sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
                        return;
                    }else if(url!=null && url.equals("3")) {
                        sendFailureMessage(response, "上传文件过大,请重新上传!");
                        return;
                    }
                    if(i==0){
                        picurl3+=url;
                    }else{
                        picurl3+=";"+url;
                    }
                }
            }
        }
        model.setPicurl1(picurl1);
        model.setPicurl2(picurl2);
        model.setPicurl3(picurl3);
        if(id!=null && id>0){
            model.setId(id);
            webManaService.updateInfo(model);
        }else{
            webManaService.addInfo(model);
        }

        sendSuccessMessage(response, "保存成功~");
    }
    @RequestMapping("/addText")
    public void addText(@RequestParam("file1")MultipartFile file1, @RequestParam("file2")MultipartFile file2, @RequestParam("file3")MultipartFile file3,
                        WebTextModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        String url="";
        url=uploadImg(file1,request);
        if(url!=null && url.equals("2")){
            sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
            return;
        }else if(url!=null && url.equals("3")){
            sendFailureMessage(response, "上传文件过大,请重新上传!");
            return;
        }else if(url!=null && !url.equals("1")){
            model.setPicture1(url);
        }
        url=uploadImg(file2,request);
        if(url!=null && url.equals("2")){
            sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
            return;
        }else if(url!=null && url.equals("3")){
            sendFailureMessage(response, "上传文件过大,请重新上传!");
            return;
        }else if(url!=null && !url.equals("1")){
            model.setPicture2(url);
        }
        url=uploadImg(file3,request);
        if(url!=null && url.equals("2")){
            sendFailureMessage(response, "上传文件不是图片格式,请重新上传!");
            return;
        }else if(url!=null && url.equals("3")){
            sendFailureMessage(response, "上传文件过大,请重新上传!");
            return;
        }else if(url!=null && !url.equals("1")) {
            model.setPicture3(url);
        }
        webManaService.addText(model);
        sendSuccessMessage(response, "保存成功~");
    }
    public String uploadImg (@RequestParam("file")MultipartFile file,HttpServletRequest request ) throws Exception{
        String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
        String parhstr=request.getSession().getServletContext()
                .getRealPath(File.separator);
        parhstr=parhstr.substring(0,parhstr.length()-5);
        String type = file!=null && !StringUtils.isBlank(file.getOriginalFilename())?file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf(".")):"";
        if (type.equals("")) {
            return "1";
        }
        if ((!type.equals("") && imgeArray.indexOf(type.toUpperCase()) < 0)) {
            return "2";
        }
        if ((file!=null && file.getSize() > 10485760)) {
            return "3";
        }
        String sjc = "";
        String path = "";
        sjc=DateUtil.getNowPlusTimeMill();
        path = parhstr +"aptitude"
                    + File.separator
                    + sjc
                    +type;
            File f = new File(path);
            // 创建文件夹
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            System.out.println(path);
            file.transferTo(new File(path));
            return UserConstants.CRMURL + "aptitude/"+ sjc + type;
    }
}
