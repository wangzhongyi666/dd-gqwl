package com.dongdao.gqwl.action.Source;


import com.dongdao.gqwl.action.BaseAction;
import com.dongdao.gqwl.action.request.MultipartFileParam;
import com.dongdao.gqwl.service.source.DdScontentService;
import com.dongdao.gqwl.utils.Auth;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
@RequestMapping("/upload")
@RestController
public class FileUploadAction extends BaseAction {

    private final static Logger logger= LoggerFactory.getLogger(FileUploadAction.class);

    private static AtomicLong counter = new AtomicLong(0L);

    @Value("${web.upload-path}")
    private String uploadPath;

    @Value("${serverIP}")
    private String serviceIP;

    @Autowired
    public DdScontentService ddScontentService;
    //跳转页面
    @RequestMapping(value = "/scontent.shtml")
    @Auth(verifyLogin = false, verifyURL = false)
    public ModelAndView sysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();
        return forword("source/scontent", context);
    }



    @RequestMapping("uploadfile.do")
    public Object uploadv2(MultipartFileParam param) throws Exception {


        String path =null;

        try {


            String prefix = "req_count:" + counter.incrementAndGet() + ":";
            System.out.println(prefix + "start !!!");
            //使用 工具类解析相关参数，工具类代码见下面

            System.out.println();
            System.out.println();
            System.out.println();

            logger.info(prefix + "chunks= " + param.getChunks());
            logger.info(prefix + "chunk= " + param.getChunk());
            logger.info(prefix + "chunkSize= " + param.getSize());

            //这个必须与前端设定的值一致
            long chunkSize = 1024 * 1024;

            String finalDirPath = uploadPath + "file/";


            String tempDirPath = finalDirPath + param.getId();

            String tempFileName = param.getName() + "_tmp";

            File confFile = new File(tempDirPath, param.getName() + ".conf");

            File tmpDir = new File(tempDirPath);

            File tmpFile = new File(tempDirPath, tempFileName);

            if (!tmpDir.exists()) {
                tmpDir.mkdirs();
            }

            RandomAccessFile accessTmpFile = new RandomAccessFile(tmpFile, "rw");

            RandomAccessFile accessConfFile = new RandomAccessFile(confFile, "rw");

            long offset = chunkSize * param.getChunk();
            //定位到该分片的偏移量
            accessTmpFile.seek(offset);
            //写入该分片数据
            accessTmpFile.write(param.getFile().getBytes());

            //把该分段标记为 true 表示完成
            System.out.println(prefix + "set part " + param.getChunk() + " complete");

            accessConfFile.setLength(param.getChunks());
            accessConfFile.seek(param.getChunk());
            accessConfFile.write(Byte.MAX_VALUE);

            //completeList 检查是否全部完成,如果数组里是否全部都是(全部分片都成功上传)
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            byte isComplete = Byte.MAX_VALUE;
            for (int i = 0; i < completeList.length && isComplete==Byte.MAX_VALUE; i++) {
                //与运算, 如果有部分没有完成则 isComplete 不是 Byte.MAX_VALUE
                isComplete = (byte)(isComplete & completeList[i]);

                System.out.println(prefix + "check part " + i + " complete?:" + completeList[i]);
            }

            if (isComplete == Byte.MAX_VALUE) {

                System.out.println(prefix + "upload complete !!" +
                        "---------------------------------------------------------");
                renameFile(tempDirPath +"/"+tempFileName,tempDirPath+"/"+param.getName());

                path ="/"+param.getId()+"/"+ param.getName();

            }


            accessTmpFile.close();
            accessConfFile.close();

            System.out.println(prefix + "end !!!");

        }catch (Exception e){
            e.printStackTrace();
        }


        if (path!=null){

            String returnPath ="http://"+serviceIP+"/"+path;

            return returnPath;
        }

        return "还在上传中";
    }




    private void renameFile(String file, String toFile){
        File toBeRenamed = new File(file);
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {

            System.out.println("File does not exist: " + file);
            return;
        }

        File newFile = new File(toFile);

        //修改文件名
        if (toBeRenamed.renameTo(newFile)) {
            System.out.println("File has been renamed.");
        } else {
            System.out.println("Error renmaing file");
        }

    }
}
