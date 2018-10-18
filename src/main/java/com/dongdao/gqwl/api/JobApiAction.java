package com.dongdao.gqwl.api;

import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.model.website.job.DdJob;
import com.dongdao.gqwl.model.website.job.DdResume;
import com.dongdao.gqwl.service.website.PictureService;
import com.dongdao.gqwl.service.website.job.JobService;
import com.dongdao.gqwl.service.website.job.JobtypeService;
import com.dongdao.gqwl.service.website.job.ResumeService;
import com.dongdao.gqwl.utils.Auth;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.VerifyFormat;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/position")
public class JobApiAction extends BaseAction {

    private final static Logger log= Logger.getLogger(JobApiAction.class);

    @Autowired
    public JobService jobService;
    @Autowired
    public PictureService pictureService;
    @Autowired
    public JobtypeService jobtypeService;
    @Autowired
    public ResumeService resumeService;

    //获取总数
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/position.json")
    public Map<String, Object>  postion(int ptype, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
      try {
        List<Map<String, Object>> jobtype=jobtypeService.queryType();
        List<Map<String, Object>> pics=pictureService.selectByType(ptype);
        List<Map<String, Object>> areas=jobService.queryArea();
        jsonMap.put("jobtype",jobtype );
        jsonMap.put("pics", pics);
        jsonMap.put("areas",areas );
        return setSuccessMap(jsonMap, "操作成功！", null);
       }
       catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }

    }

    //分页
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/pageposition.json")
    public Map<String, Object>  pageData(DdJob model,  HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Integer count=jobService.queryByCount(model);
        if(model!=null && model.getPageNum()!=null && model.getPageSize()!=null){
            model.setNum1(model.getPageSize()*(model.getPageNum()-1));
            model.setNum2(model.getPageSize());
        }
        try {
            List<Map<String, Object>> jobs=jobService.selectByType(model);
            jsonMap.put("jobs",jobs );
            jsonMap.put("count",count);
            jsonMap.put("pageSize",model.getNum2());
            if( model.getPageSize()*(model.getPageNum())>=count){
                jsonMap.put("isNext",false);
            }else{
                jsonMap.put("isNext",true);
            }


            return setSuccessMap(jsonMap, "操作成功！", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }

    }


    //获取简历
    @Auth(verifyURL = false)
    @ResponseBody
    @RequestMapping("/addresume.json")
    public Map<String, Object>  adresume(DdResume ddResume,  @RequestParam("resume") MultipartFile resume, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int num=0;
        try {
            String filepath=uploadifys(resume,"resume",response,request);
            if(!"".equals(filepath)){
                ddResume.setFilepath(filepath);
                ddResume.setIsdelete(1);
                ddResume.setCreattime(DateUtil.getNowPlusTime());
                ddResume.setReip(getIpAddr(request));
                num=resumeService.insertSelective(ddResume);
                if(num==1){
                    jsonMap.put("msg", "操作成功！");
                }else{
                    jsonMap.put("msg", "操作失败！");
                }
                return setSuccessMap(jsonMap, "操作成功！", null);
            }
            return setFailureMap(jsonMap, "操作失败！", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return setFailureMap(jsonMap, "操作失败！", null);
        }

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
