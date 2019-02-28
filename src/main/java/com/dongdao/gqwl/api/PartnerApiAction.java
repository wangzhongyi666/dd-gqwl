package com.dongdao.gqwl.api;

import com.dongdao.gqwl.UserConstants;
import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.routline.topic.DdCardcon;
import com.dongdao.gqwl.model.routline.topic.DdCards;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.model.routline.topic.DdZrecord;
import com.dongdao.gqwl.model.website.DdPartner;
import com.dongdao.gqwl.model.website.Ddbrowse;
import com.dongdao.gqwl.model.website.RasteUser;
import com.dongdao.gqwl.service.gcolumn.DdbrowseService;
import com.dongdao.gqwl.service.routline.topic.CardconService;
import com.dongdao.gqwl.service.routline.topic.CardsService;
import com.dongdao.gqwl.service.routline.topic.TopicService;
import com.dongdao.gqwl.service.routline.topic.ZrecordService;
import com.dongdao.gqwl.service.website.PartnerService;
import com.dongdao.gqwl.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/partner")
public class PartnerApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(PartnerApiAction.class);

    @Autowired
    public PartnerService partnerService;



    //添加合作伙伴
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/addPartner.json")
    public Map<String, Object> addCards(DdPartner model,  @RequestParam("file")MultipartFile files,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
        if(model.getPhone()!=null){
           boolean isphone= isPhone(model.getPhone());
           if(!isphone){
               return setSuccessMap(jsonMap, "手机号码格式错误！", null);
           }
        }else{
               return setSuccessMap(jsonMap, "手机号码不能为空！", null);
        }
        if(model.getEmail()!=null){
            if(!emailFormat(model.getEmail())){
                return setSuccessMap(jsonMap, "邮箱格式错误！", null);
            }
        }else{
            return setSuccessMap(jsonMap, "邮箱不能为空！", null);
        }
        if(files.getSize()<2048){
            return setSuccessMap(jsonMap, "文件大小超出限制！", null);
        }
        String myFileName = files.getOriginalFilename();
        String extensionName = myFileName.substring(myFileName.lastIndexOf(".") + 1);
        if(!extensionName.equals("doc")&&!extensionName.equals("docx")&&!extensionName.equals("pdf")&&!extensionName.equals("PDF")){
            return setSuccessMap(jsonMap, "文件格式错误！", null);
        }
        model.setIsdelete(1);
        model.setCreattime(DateUtil.getNowPlusTime());
        String filepath= uploadifys(files,"partner",response,request);
        if(filepath!=null){
            model.setFilepath(filepath);
            partnerService.insertSelective(model);
        }
            return setSuccessMap(jsonMap, "操作成功！", null);


        } catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }
    }


    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {

            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();

            if (!isMatch) {

            }
            return isMatch;
        }
    }
    public static boolean emailFormat(String email) {
        boolean tag = true;
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            tag = false;
        }
        return tag;
    }


    /*********************uploadify上传方法***************************/
    public String uploadifys(MultipartFile file, String folder, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        // 文件原名
        String fileName = file.getOriginalFilename().toLowerCase();
        // 文件扩展名
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        // 验证后缀
        if (!VerifyFormat.verifyFormat(fileType)) {
            jsonMap.put("msg", "操作失败！");
        }
        String newname = System.currentTimeMillis() + "." + fileType;
        // 将文件保存到服务器
        String filePath = uploadFiles(file.getInputStream(),newname,folder);

        return filePath;

    }


}
