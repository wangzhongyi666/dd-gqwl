package com.dongdao.gqwl.action;

import com.alibaba.fastjson.JSON;
import com.dongdao.gqwl.UserConstants;
import com.dongdao.gqwl.model.Ueditor;
import com.dongdao.gqwl.utils.PublicMsg;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value="/ueditor")
public class UeditorController {


    @RequestMapping(value="/ueditor.do")
    @ResponseBody
    public String ueditor(@RequestParam("action") String param,MultipartFile upfile,HttpServletRequest request) {
        Ueditor ueditor = new Ueditor();

            return PublicMsg.UEDITOR_CONFIG;

    }

    @RequestMapping(value="/imgUpload.do")
    @ResponseBody
        public String imgUpload(@Param(value="file") MultipartFile upfile, HttpServletRequest request) throws IOException{
        Ueditor ueditor = new Ueditor();
        String httpImgPath = "";
        String imageNamePath = "";

        String fileName = "";
        // 原始文件名   UEDITOR创建页面元素时的alt和title属性
        String originalFileName = "";

        if(!upfile.isEmpty()) {
            // 取得文件的原始文件名称
            fileName = upfile.getOriginalFilename();
            originalFileName = fileName;
            String ext = (FilenameUtils.getExtension(upfile.getOriginalFilename())).toLowerCase();
            String storePath = "";
            if ("jpg".equals(ext) || "png".equals(ext) || "jpeg".equals(ext) || "bmp".equals(ext)) {
                storePath = "uploads/image/";
            } else {
                storePath = "uploads/file/";
            }
            //将图片和视频保存在本地服务器
            File localFile;
            try {
                imageNamePath = UserConstants.UPLOADPATH + File.separator
                        + UserConstants.UPLOADFOLDER + File.separator + "ueditorimage";

                localFile = new File(imageNamePath);
                // 判断文件夹
                if (!localFile.exists()) {// 如果目录不存在
                    localFile.mkdirs();// 创建文件夹
                }
                localFile = new File(imageNamePath + File.separator + fileName + "." + ext);
                System.err.println(imageNamePath);
                httpImgPath = File.separator
                        + UserConstants.UPLOADFOLDER + File.separator + "ueditorimage"+ File.separator +fileName;
                System.err.println("http://localhost"+httpImgPath);
                upfile.transferTo(localFile);
                ueditor.setState("Success");// UEDITOR的规则:不为SUCCESS则显示state的内容
                ueditor.setUrl(httpImgPath);         //能访问到你现在图片的路径
                ueditor.setTitle(originalFileName);
                ueditor.setOriginal(originalFileName);
            } catch (Exception e) {
                e.printStackTrace();
                ueditor.setState("文件上传失败！");// UEDITOR的规则:不为SUCCESS则显示state的内容
                ueditor.setUrl("");         //能访问到你现在图片的路径
                ueditor.setTitle("");
                ueditor.setOriginal("");
            }
        }
        return JSON.toJSONString(ueditor);
    }

    public Map<String,Object> uploadImg(MultipartFile file,
                            HttpServletRequest request) throws IOException {

        String httpImgPath = "";
        String imageNamePath = "";
        Map<String,Object> rs = new HashMap<String, Object>();

        String fileName = "";
        // 原始文件名   UEDITOR创建页面元素时的alt和title属性
        String originalFileName = "";

            if(!file.isEmpty()) {
                // 取得文件的原始文件名称
                fileName = file.getOriginalFilename();
                originalFileName = fileName;
                String ext = (FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase();
                String storePath = "";
                if ("jpg".equals(ext) || "png".equals(ext) || "jpeg".equals(ext) || "bmp".equals(ext)) {
                    storePath = "uploads/image/";
                } else {
                    storePath = "uploads/file/";
                }
                //将图片和视频保存在本地服务器
                File localFile;
                try {
                    imageNamePath = UserConstants.UPLOADPATH + File.separator
                            + UserConstants.UPLOADFOLDER + File.separator + "ueditorimage";

                    localFile = new File(imageNamePath);
                    // 判断文件夹
                    if (!localFile.exists()) {// 如果目录不存在
                        localFile.mkdirs();// 创建文件夹
                    }
                    localFile = new File(imageNamePath + File.separator + fileName + "." + ext);
                    System.err.println(imageNamePath);
                    httpImgPath = File.separator
                            + UserConstants.UPLOADFOLDER + File.separator + "ueditorimage";
                    file.transferTo(localFile);
                    rs.put("state", "SUCCESS");// UEDITOR的规则:不为SUCCESS则显示state的内容
                    rs.put("url", httpImgPath);         //能访问到你现在图片的路径
                    rs.put("title", originalFileName);
                    rs.put("original", originalFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    rs.put("state", "文件上传失败!"); //在此处写上错误提示信息，这样当错误的时候就会显示此信息
                    rs.put("url", "");
                    rs.put("title", "");
                    rs.put("original", "");
                }
            }
        return rs;

}



}
