package com.dongdao.gqwl.action.websit;

import com.dongdao.gqwl.UserConstants;
import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.websit.RasteMassage;
import com.dongdao.gqwl.model.websit.RasteUser;
import com.dongdao.gqwl.service.gcolumn.RasteMassageService;
import com.dongdao.gqwl.service.gcolumn.RasteUserService;
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
@RequestMapping("/rastemassage")
public class RasteMassageAction extends BaseAction {

    private final static Logger log= Logger.getLogger(RasteMassageAction.class);

    @Autowired
    public RasteMassageService<RasteMassage> rasteMassageService;

    @RequestMapping(value = "/rastemassage.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("websit/rastemassage", context);
    }


    @RequestMapping("/rastemassageDataList.do")
    public void audiDataList(RasteMassage model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<RasteMassage> dataList = rasteMassageService.queryByList(model);
        model.setNum1(model.getPageSize() * (model.getPageNum() - 1));
        model.setNum2(model.getPageSize());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }
    //获取总数
    @RequestMapping("/rastemassageCount.do")
    public void roleDataCount(RasteMassage model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer count=rasteMassageService.queryByCount(model);
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

    //删除
    @RequestMapping("/deleterastemassage.do")
    public void deleteType(RasteMassage model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        RasteMassage model1 = new RasteMassage();
        model1.setMassage_id(model.getMassage_id());
        model1.setState(2);
        model1.setUpdatetime(DateUtil.getNowPlusTime());
        int num= rasteMassageService.updateByPrimaryKeySelective(model1);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }
    //添加
    @RequestMapping("/addrastemassage.do")
    public void saceType(RasteMassage model, @RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setCreatetime(DateUtil.getNowPlusTime());
        model.setUpdatetime(DateUtil.getNowPlusTime());
        model.setState(0);

        String parhstr=request.getSession().getServletContext()
                .getRealPath(File.separator);
        parhstr=parhstr.substring(0,parhstr.length()-5);
        String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
        String type1 = file1!=null && !org.apache.commons.lang.StringUtils.isBlank(
                file1.getOriginalFilename())?file1.getOriginalFilename().substring(
                file1.getOriginalFilename().lastIndexOf(".")):"";
        if(type1.equals("") && model.getMassage_id()==null){
            if(model.getLogo()==null){
                sendFailureMessage(response,"请选择图片！");
                return;
            }
        }
        if ((!type1.equals("") && imgeArray.indexOf(type1.toUpperCase()) < 0)) {
            sendFailureMessage(response,"文件格式错误！");
            return;
        }

        if ((file1!=null && file1.getSize() > 10485760)) {
            sendFailureMessage(response,"图片过大！");
            return;
        }
        String type2 = file2!=null && !org.apache.commons.lang.StringUtils.isBlank(
                file2.getOriginalFilename())?file2.getOriginalFilename().substring(
                file2.getOriginalFilename().lastIndexOf(".")):"";
        if(type2.equals("") && model.getMassage_id()==null){
            if(model.getTwo_bar_codes()==null){
                sendFailureMessage(response,"请选择图片！");
                return;
            }
        }
        if ((!type2.equals("") && imgeArray.indexOf(type2.toUpperCase()) < 0)) {
            sendFailureMessage(response,"文件格式错误！");
            return;
        }
        if ((file2!=null && file2.getSize() > 10485760)) {
            sendFailureMessage(response,"图片过大！");
            return;
        }
        String sjc = "";
        String path = "";
        if(!type1.equals("")){
            sjc=DateUtil.getNowPlusTimeMill();
            path = parhstr+"aptitude"
                    + java.io.File.separator
                    + sjc
                    +type1;
            File f = new File(path);
            // 创建文件夹
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            file1.transferTo(new File(path));
            model.setLogo(UserConstants.CRMURL + "aptitude/"+ sjc + type1);
        }

        if(!type2.equals("")){
            sjc=DateUtil.getNowPlusTimeMill();
            path = parhstr+"aptitude"
                    + java.io.File.separator
                    + sjc
                    +type2;
            File f = new File(path);
            // 创建文件夹
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            file2.transferTo(new File(path));
            model.setTwo_bar_codes(UserConstants.CRMURL + "aptitude/"+ sjc + type2);
        }


        int num= rasteMassageService.insertSelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }

        HtmlUtil.writerJson(response, jsonMap);

    }

    //审核
    @RequestMapping("/updaterastemassage.do")
    public void updateType(RasteMassage model,@RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2,
                           String qianinp1,String lieinp1,HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        model.setUpdatetime(DateUtil.getNowPlusTime());
        String parhstr=request.getSession().getServletContext()
                .getRealPath(File.separator);
        parhstr=parhstr.substring(0,parhstr.length()-5);
        String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
        String sjc = "";
        String path = "";
        if(qianinp1!=null&&!qianinp1.equals("0")){
            String type1 = file1!=null && !org.apache.commons.lang.StringUtils.isBlank(
                    file1.getOriginalFilename())?file1.getOriginalFilename().substring(
                    file1.getOriginalFilename().lastIndexOf(".")):"";
            if(type1.equals("") && model.getMassage_id()==null){
                if(model.getLogo()==null){
                    sendFailureMessage(response,"请选择图片！");
                    return;
                }
            }
            if ((!type1.equals("") && imgeArray.indexOf(type1.toUpperCase()) < 0)) {
                sendFailureMessage(response,"文件格式错误！");
                return;
            }

            if ((file1!=null && file1.getSize() > 10485760)) {
                sendFailureMessage(response,"图片过大！");
                return;
            }
            if(!type1.equals("")){
                sjc=DateUtil.getNowPlusTimeMill();
                path = parhstr+"aptitude"
                        + java.io.File.separator
                        + sjc
                        +type1;
                File f = new File(path);
                // 创建文件夹
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                file1.transferTo(new File(path));
                model.setLogo(UserConstants.CRMURL + "aptitude/"+ sjc + type1);
            }
        }
        //
        if(lieinp1!=null&&!lieinp1.equals("0")){
            String type2 = file2!=null && !org.apache.commons.lang.StringUtils.isBlank(
                    file2.getOriginalFilename())?file2.getOriginalFilename().substring(
                    file2.getOriginalFilename().lastIndexOf(".")):"";
            if(type2.equals("") && model.getMassage_id()==null){
                if(model.getTwo_bar_codes()==null){
                    sendFailureMessage(response,"请选择图片！");
                    return;
                }
            }
            if ((!type2.equals("") && imgeArray.indexOf(type2.toUpperCase()) < 0)) {
                sendFailureMessage(response,"文件格式错误！");
                return;
            }
            if ((file2!=null && file2.getSize() > 10485760)) {
                sendFailureMessage(response,"图片过大！");
                return;
            }
            if(!type2.equals("")){
                sjc=DateUtil.getNowPlusTimeMill();
                path = parhstr+"aptitude"
                        + java.io.File.separator
                        + sjc
                        +type2;
                File f = new File(path);
                // 创建文件夹
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                file2.transferTo(new File(path));
                model.setTwo_bar_codes(UserConstants.CRMURL + "aptitude/"+ sjc + type2);
            }
        }
        int num= rasteMassageService.updateByPrimaryKeySelective(model);
        if(num==1){
            jsonMap.put("msg", "操作成功！");
        }else{
            jsonMap.put("msg", "操作失败！");
        }
        HtmlUtil.writerJson(response, jsonMap);

    }


    //查看
    @RequestMapping("/getrastemassage.do")
    public void getastemassage(RasteMassage model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        RasteMassage model1 = rasteMassageService.selectByPrimaryKey(model.getMassage_id());
        jsonMap.put("data",model1);

        HtmlUtil.writerJson(response, jsonMap);

    }
}