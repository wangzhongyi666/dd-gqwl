package com.e365.flexiblebe.action;

import com.e365.flexiblebe.utils.HtmlUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/print")
public class PrintAction extends BaseAction{
    @RequestMapping(value = "/setInsurance.do")
    public void drawImage(String fileName, int count, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            DocFlavor dof = null;
            if (fileName.endsWith(".gif")) {
                dof = DocFlavor.INPUT_STREAM.GIF;
            } else if (fileName.endsWith(".jpg")) {
                dof = DocFlavor.INPUT_STREAM.JPEG;
            } else if (fileName.endsWith(".png")) {
                dof = DocFlavor.INPUT_STREAM.PNG;
            }
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            pras.add(OrientationRequested.PORTRAIT);

            pras.add(new Copies(count));
            pras.add(PrintQuality.HIGH);
            PrintService pss[] = PrintServiceLookup.lookupPrintServices(dof,
                    pras);
            // 如果没有获取打印机
            if (pss.length == 0) {
                // 终止程序
                sendFailureMessage(response, "未能连接打印机！");
                return;
            }
            PrintService ps = PrintServiceLookup.lookupDefaultPrintService();

            DocAttributeSet das = new HashDocAttributeSet();
            // 设置打印纸张的大小（以毫米为单位）
            das.add(new MediaPrintableArea(0, 0, 210, 296, MediaPrintableArea.MM));
            FileInputStream fin = new FileInputStream(fileName);

            Doc doc = new SimpleDoc(fin, dof, das);

            DocPrintJob job = ps.createPrintJob();

            job.print(doc, pras);
            fin.close();
        } catch (IOException ie) {
            ie.printStackTrace();
            sendFailureMessage(response, "操作失败！");
        } catch (PrintException pe) {
            pe.printStackTrace();
            sendFailureMessage(response, "操作失败！");
        }
        sendSuccessMessage(response, "操作成功！");
    }
}
