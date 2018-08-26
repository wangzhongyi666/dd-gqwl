package com.dongdao.gqwl.action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicLong;
@RequestMapping("/files")
@Controller
public class FileUploadAction extends BaseAction {

    private final static Logger logger= LoggerFactory.getLogger(FileUploadAction.class);

    private static AtomicLong counter = new AtomicLong(0L);




    @RequestMapping(value = "/uploadfile.do")
    public void uploadFile(@RequestParam("m_file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException,NoSuchAlgorithmException {
        uploadify(file,"media_File",response,request);
    }
 }
