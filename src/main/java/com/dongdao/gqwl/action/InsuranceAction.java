package com.dongdao.gqwl.action;

import com.dongdao.gqwl.bean.*;
import com.dongdao.gqwl.model.*;
import com.dongdao.gqwl.service.*;
import com.dongdao.gqwl.utils.*;
import com.dongdao.gqwl.bean.*;
import com.dongdao.gqwl.model.*;
import com.dongdao.gqwl.service.*;
import com.dongdao.gqwl.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/insurance")
public class InsuranceAction extends BaseAction {
    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public InsuranceService insuranceService;
    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public InsuranceSetService insuranceSetService;
    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public IntegrationLogService integrationLogService;

    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    @Autowired(required = false)
    public VipService vipService;
    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public MessageService messageService;
    @RequestMapping(value = "/insurance.do")
    public String insurance(HttpServletRequest request, HttpServletResponse response) {
        return "insurance/insurance";
    }
    @RequestMapping(value = "/supple.do")
    public String supple(HttpServletRequest request, HttpServletResponse response) {
        return "insurance/supple";
    }
    @RequestMapping(value = "/untread.do")
    public String untread(HttpServletRequest request, HttpServletResponse response) { return "insurance/untread";}
    @RequestMapping(value = "/insuUser.do")
    public String insuUser(HttpServletRequest request, HttpServletResponse response) {
        return "insurance/insuUser";
    }
    @RequestMapping(value = "/adjustment.do")
    public String adjustment(HttpServletRequest request, HttpServletResponse response) {
        return "insurance/adjustment";
    }

    @RequestMapping("/countadjulist")
    public void countadjulist(OrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "1！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        Integer count=insuranceService.countadjulist(model);
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
    @RequestMapping("/getadjulist")
    public void getadjulist(OrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        List<Order> dataList=insuranceService.getadjulist(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        jsonMap.put("jid", user.getJid());
        HtmlUtil.writerJson(response, jsonMap);
    }
    @RequestMapping("/upOrderAudit")
    public void upOrderAudit(OrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        model.setAuditTime(DateUtil.getNowPlusTime());
        try {
            Order order=insuranceService.queryOrderById(model.getId());
            if(model.getAudit()!=null && model.getAudit().equals(1)){
                model.setInsuStart(order.getOldStart());
                model.setInsuEnd(order.getOldEnd());
                model.setPayment(order.getOldPayment());
                model.setAudit(1);
            }
            insuranceService.upOrderAudit(model);
            if(model.getAudit()!=null && model.getAudit().equals(6)){

                if(order.getInsuStart().compareTo(order.getOldStart())>0 || order.getInsuEnd().compareTo(order.getOldEnd())<0){
                    Integer month = DateUtil.getMonth(order.getInsuStart(),order.getInsuEnd())+1;
                    Integer month1 = DateUtil.getMonth(order.getOldStart(),order.getOldEnd())+1;
                    double quitPay=0.0;
                    if(month<month1){
                        QuitModel qmodel = new QuitModel();
                        qmodel.setUid(order.getUid());
                        qmodel.setOrderNum(order.getOrderNum());
                        qmodel.setInsuranceType(order.getInsuranceType());
                        qmodel.setDeptId(order.getDeptId());
                        int monNumm = DateUtil.getMonth(order.getInsuStart(),order.getInsuEnd())+1;
                        double daypay=Double.parseDouble(order.getPayment())/monNumm;
                        String quitMonth = "";//退保
                        String extendMonth = "";//顺延
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");//格式化为年月
                        if(order.getInsuStart().compareTo(order.getOldStart())>0){//退保 1
                            int mon = DateUtil.getMonth(order.getOldStart(),order.getInsuStart());
                            quitPay+=daypay * mon;
                            Calendar min = Calendar.getInstance();
                            Calendar max = Calendar.getInstance();
                            min.setTime(sdf.parse(order.getOldStart()));
                            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
                            max.setTime(sdf.parse(order.getInsuStart()));
                            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH)-1, 2);
                            Calendar curr = min;
                            while (curr.before(max)) {
                                if(quitMonth==null || quitMonth.equals("")){
                                    quitMonth+=sdf.format(curr.getTime());
                                }else{
                                    quitMonth+=","+sdf.format(curr.getTime());
                                }
                                curr.add(Calendar.MONTH, 1);
                            }
                        }
                        if(order.getInsuStart().compareTo(order.getOldStart())<0){//退保 2
                            int mon = DateUtil.getMonth(order.getOldStart(),order.getInsuStart());

                            Calendar min = Calendar.getInstance();
                            Calendar max = Calendar.getInstance();
                            min.setTime(sdf.parse(order.getInsuStart()));
                            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
                            max.setTime(sdf.parse(order.getOldStart()));
                            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH)-1, 2);
                            Calendar curr = min;
                            while (curr.before(max)) {
                                if(extendMonth==null || extendMonth.equals("")){
                                    extendMonth+=sdf.format(curr.getTime());
                                }else{
                                    extendMonth+=","+sdf.format(curr.getTime());
                                }
                                curr.add(Calendar.MONTH, 1);
                            }
                        }

                        if(order.getInsuEnd().compareTo(order.getOldEnd())<0){//顺延 1
                            int mon1 = DateUtil.getMonth(order.getOldEnd(),order.getInsuEnd());
                            //quitPay+=daypay * mon1;
                            Calendar min = Calendar.getInstance();
                            Calendar max = Calendar.getInstance();
                            min.setTime(sdf.parse(order.getInsuEnd()));
                            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH)+1, 1);
                            max.setTime(sdf.parse(order.getOldEnd()));
                            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
                            Calendar curr = min;
                            while (curr.before(max)) {
                                if(quitMonth==null || quitMonth.equals("")){
                                    quitMonth+=sdf.format(curr.getTime());
                                }else{
                                    quitMonth+=","+sdf.format(curr.getTime());
                                }
                                curr.add(Calendar.MONTH, 1);
                            }
                        }

                        if(order.getInsuEnd().compareTo(order.getOldEnd())>0){//顺延 2
                            int mon = DateUtil.getMonth(order.getInsuEnd(),order.getOldEnd());

                            Calendar min = Calendar.getInstance();
                            Calendar max = Calendar.getInstance();
                            min.setTime(sdf.parse(order.getOldEnd()));
                            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
                            max.setTime(sdf.parse(order.getInsuEnd()));
                            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH)-1, 2);
                            Calendar curr = min;
                            while (curr.before(max)) {
                                if(extendMonth==null || extendMonth.equals("")){
                                    extendMonth+=sdf.format(curr.getTime());
                                }else{
                                    extendMonth+=","+sdf.format(curr.getTime());
                                }
                                curr.add(Calendar.MONTH, 1);
                            }
                        }

                        qmodel.setQuitMonth(quitMonth);
                        qmodel.setExtendMonth(extendMonth);
                        qmodel.setMonthNum(month1-month);
                        qmodel.setQuit_payment((month1-month)*daypay);
                        qmodel.setPra_payment((month1-month)*daypay);
                        qmodel.setSubTime(DateUtil.getNowPlusTime());
                        qmodel.setAudit(31);
                        qmodel.setQuit_integration(0);
                        qmodel.setIntegration_diff(0);
                        qmodel.setPayment_inte(0.0);
                        qmodel.setQuitType(2);
                        insuranceService.addQuit(qmodel);
                    }
                }

                InsuranceModel insModel = new InsuranceModel();
                insModel.setExpends_scale(Double.parseDouble((order.getRatio()*0.01)+""));
                insModel.setDeptId(order.getDeptId());
                insModel.setInsuranceType(order.getInsuranceType());
                Insurance ins = insuranceService.calculatePayment(insModel);
                //1 首先判单这个订单是补缴还是参保
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");//可以方便地修改日期格式
                String nowformat = dateFormat.format(c.getTime());
                Date nowDate = dateFormat.parse(nowformat);
                Date insDate = dateFormat.parse(order.getInsuStart());
                        /*
                          返回1， 说明订单开始时间小于当前时间，该订单包含补缴；
                          返回-1，说明订单开始时间大于当前时间，该订单肯定是参保；
                          返回0， 说明是订单开始时间和结束时间都是当月。*/
                int is_bujao = DateUtil.compareDate(nowDate,insDate);

                //2 如果是补缴 有两种情况

                if(is_bujao==1){
                    Date insEndDate = dateFormat.parse(order.getInsuEnd());
                    // 2a. bujaoTime>0 都是补缴
                    // 2b. bujaoTime<0 一部分补缴
                    // 2c. bujaoTime==0 说明insuEnd为当月，当月25号下午之前缴费（insuEnd）算参保，之后（insuEnd）算补缴
                    int bujaoTime = DateUtil.compareDate(nowDate,insEndDate);
                    OrderInfoModel imodel = new OrderInfoModel();
                    imodel.setOrderNum(order.getOrderNum());
                    imodel.setUid(order.getUid());
                    imodel.setInsuranceType(order.getInsuranceType());
                    imodel.setInseuranceCycle("");
                    imodel.setInsuStart(order.getInsuStart());
                    imodel.setAuditTime(DateUtil.getNowPlusTime());
                    imodel.setSubTime(order.getSubTime());
                    imodel.setBase(order.getBase());
                    imodel.setRatio(order.getRatio());
                    imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                    imodel.setStatus(2);
                    imodel.setAudit(1);
                    imodel.setDeptId(order.getDeptId());
                    imodel.setIsquit(2);
                    //2b
                    if(bujaoTime==-1||bujaoTime==0){
                        String timestr1=DateUtil.getNowPlusTime();
                        SimpleDateFormat form=new SimpleDateFormat("yyyy-MM");
                        String timestr2=form.format(new Date())+"-25 17:00:00";
                        if(timestr1.compareTo(timestr2)>0){
                            imodel.setInsuEnd(dateFormat.format(c.getTime()));
                            int monthNum = DateUtil.getMonthSpace(order.getInsuStart(),nowformat)+1;

                                    /*if(bujaoTime==0){//结束时间是当月那么这个合同是
                                        monthNum = DateUtil.getMonthSpace(order.getInsuStart(),nowformat)+1;
                                        System.out.println("当月");
                                    }*/

                            imodel.setMonthNum(monthNum);

                            imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*monthNum);
                            imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                        }else{
                            c.add(Calendar.MONTH, -1);
                            imodel.setInsuEnd(dateFormat.format(c.getTime()));

                            int monthNum = DateUtil.getMonthSpace(order.getInsuStart(),nowformat);
                            imodel.setMonthNum(monthNum);

                            imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*monthNum);
                            imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                        }
                    }else if(bujaoTime==1){
                        imodel.setInsuEnd(order.getInsuEnd());

                        int monthNum = DateUtil.getMonthSpace(order.getInsuStart(),order.getInsuEnd());
                        imodel.setMonthNum(monthNum==0?1:(monthNum+1));

                        imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                        imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*(monthNum+1));
                    }
                    //补缴录入
                    insuranceService.addInsInfo(imodel);

                    //剩下的就是参保
                    //拆分 录入
                    String timestr1=DateUtil.getNowPlusTime();
                    SimpleDateFormat form=new SimpleDateFormat("yyyy-MM");
                    String timestr2=form.format(new Date())+"-25 17:00:00";
                    int cbNum =0;
                    if(timestr1.compareTo(timestr2)>0){
                        cbNum = DateUtil.getMonthSpace1(nowformat,order.getInsuEnd());
                    }else{
                        cbNum = DateUtil.getMonthSpace1(nowformat,order.getInsuEnd())+1;
                    }
                    for(int i=0;i<cbNum;i++){
                        OrderInfoModel model1 = new OrderInfoModel();
                        Calendar c_1 = Calendar.getInstance();
                        c_1.setTime(c.getTime());
                        model1.setOrderNum(order.getOrderNum());
                        model1.setUid(order.getUid());
                        model1.setInsuranceType(order.getInsuranceType());

                        c_1.add(Calendar.MONTH, i+1);
                        if(DateUtil.getMonthSpace1(order.getInsuEnd(),dateFormat.format(c_1.getTime()))>0){
                            break;
                        }
                        model1.setInseuranceCycle(dateFormat.format(c_1.getTime()));
                        model1.setInsuStart("");
                        model1.setInsuEnd("");
                        model1.setBase(order.getBase());
                        model1.setRatio(order.getRatio());

                        model1.setMonthNum(1);

                        model1.setPayment(Double.parseDouble(ins.getExpends_amount()));

                        model1.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                        model1.setStatus(1);
                        model1.setAudit(1);
                        model1.setDeptId(order.getDeptId());
                        model1.setIsquit(2);
                        model1.setSubTime(order.getSubTime());
                        model1.setAuditTime(DateUtil.getNowPlusTime());
                        insuranceService.addInsInfo(model1);
                    }
                }else if(is_bujao==-1 || is_bujao==0){
                    Calendar c1 = Calendar.getInstance();
                    int monNum =0;
                    int monthNum1 =0;
                    int cbNum =0;
                    if(is_bujao==0){
                        OrderInfoModel imodel = new OrderInfoModel();
                        imodel.setOrderNum(order.getOrderNum());
                        imodel.setUid(order.getUid());
                        imodel.setInsuranceType(order.getInsuranceType());
                        imodel.setInseuranceCycle("");
                        imodel.setInsuStart(order.getInsuStart());
                        imodel.setAuditTime(DateUtil.getNowPlusTime());
                        imodel.setSubTime(order.getSubTime());
                        imodel.setBase(order.getBase());
                        imodel.setRatio(order.getRatio());
                        imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                        imodel.setStatus(2);
                        imodel.setAudit(1);
                        imodel.setDeptId(order.getDeptId());
                        imodel.setIsquit(2);
                        String timestr1=DateUtil.getNowPlusTime();
                        SimpleDateFormat form=new SimpleDateFormat("yyyy-MM");
                        String timestr2=form.format(new Date())+"-25 17:00:00";
                        if(timestr1.compareTo(timestr2)>0){
                            c1.add(Calendar.MONTH, 1);
                            imodel.setInsuEnd(order.getInsuStart());

                            monthNum1 = 1;

                            imodel.setMonthNum(monthNum1);

                            imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*monthNum1);
                            imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                            monNum = DateUtil.getMonthSpace(dateFormat.format(c1.getTime()),order.getInsuEnd());
                            cbNum = DateUtil.getMonthSpace1(dateFormat.format(c1.getTime()),order.getInsuEnd());
                            insuranceService.addInsInfo(imodel);
                        }else{
                            monNum = DateUtil.getMonthSpace(order.getInsuStart(),order.getInsuEnd());
                            cbNum=monNum==0?1:monNum;
                        }
                    }else{
                        monNum = DateUtil.getMonthSpace(order.getInsuStart(),order.getInsuEnd());
                        cbNum=monNum==0?1:monNum;
                    }
                    if(cbNum>0){
                        for(int i=0;i<=monNum;i++){
                            OrderInfoModel model2 = new OrderInfoModel();

                            model2.setOrderNum(order.getOrderNum());
                            model2.setUid(order.getUid());
                            model2.setInsuranceType(order.getInsuranceType());
                            model2.setInsuranceNature(order.getInsuranceNature());
                            c1.setTime(dateFormat.parse(order.getInsuStart()));
                            c1.add(Calendar.MONTH, i+monthNum1);
                            model2.setInseuranceCycle(dateFormat.format(c1.getTime()));
                            model2.setInsuStart("");
                            model2.setInsuEnd("");

                            model2.setAuditTime(DateUtil.getNowPlusTime());
                            model2.setSubTime(order.getSubTime());
                            model2.setBase(order.getBase());
                            model2.setRatio(order.getRatio());

                            model2.setMonthNum(1);

                            model2.setPayment(Double.parseDouble(ins.getExpends_amount()));

                            model2.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                            model2.setStatus(1);
                            model2.setAudit(1);
                            model2.setDeptId(order.getDeptId());
                            model2.setIsquit(2);
                            insuranceService.addInsInfo(model2);
                        }
                    }
                }

                IntegrationModel model1=new IntegrationModel();
                model1.setType(1);
                String time=DateUtil.getNowPlusTime();
                model1.setAddTime(time);
                model1.setUid(order.getUid());
                int mon = DateUtil.getMonth(order.getInsuStart(),order.getInsuEnd());
                double x = 0.0;
                if(mon>=6){
                    x = 1;
                }
                model1.setIntegration((int)(Double.parseDouble(order.getPayment())*x));
                integrationLogService.addInLog(model1);
                integrationLogService.updateIntegration((int)(Double.parseDouble(order.getPayment())*x),order.getUid(),time);
                MessageModel msmodel=new MessageModel();
                msmodel.setUser_id(order.getUid());
                msmodel.setTitle("审核通过");
                msmodel.setCreate_time(new Date());
                msmodel.setFlag(0);
                msmodel.setUnread(1);
                msmodel.setType(2);
                msmodel.setContent("【365灵活通】您参保的（"+(order.getInsuranceType()==1?"养老保险":"医疗保险")+"）已通过，参保周期为"+order.getInsuStart().replaceAll("-","/")+
                        "-"+order.getInsuEnd().replaceAll("-","/")+"，共"+(mon+1)+"个月，" +
                        "在此期间，参保为每月缴纳，补缴信息会一次性缴纳,如有问题请联系电话"+getRootMap().get("serviceTel"));
                messageService.sendMsg(msmodel);
                SmsUtil.sendSms(msmodel.getContent(),order.getTel(),null,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendFailureMessage(response, "操作失败！");
            return;
        }
        sendSuccessMessage(response, "操作成功~");
    }
    @RequestMapping("/countinsulist")
    public void countinsulist(OrderInfoModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "1！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        Integer count=insuranceService.countinsulist(model);
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
    @RequestMapping("/getinsulist")
    public void getinsulist(OrderInfoModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        List<OrderInfo> dataList=insuranceService.getinsulist(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        jsonMap.put("jid", user.getJid());
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/countinsulist1")
    public void countinsulist1(OrderInfoModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "1！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        Integer count=insuranceService.countinsulist1(model);
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
    @RequestMapping("/getinsulist1")
    public void getinsulist1(OrderInfoModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        List<OrderInfo> dataList=insuranceService.getinsulist1(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        jsonMap.put("jid", user.getJid());
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/upAudit")
    public void upAudit(OrderInfoModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        if(user.getJid().equals(4)){
            model.setAudit(20+model.getAudit());
        }else if(user.getJid().equals(3)){
            model.setAudit(30+model.getAudit());
            /*if(model.getAudit()==1){
                insuranceService.upBalance(model.getPayment(),model.getOrderNum());
            }*/
            if(model.getAudit()==31){
                OrderInfo info = insuranceService.queryByInfoId(model.getId());
                MessageModel model1=new MessageModel();
                model1.setUser_id(info.getUid());

                model1.setCreate_time(new Date());
                model1.setFlag(0);
                model1.setUnread(1);
                model1.setType(2);
                String typestr="";
                if(info.getInsuranceType()==1){
                    typestr="养老保险";
                }else{
                    typestr="医疗保险";
                }
                if(info.getStatus()==1){
                    model1.setTitle("参保缴费");
                    model1.setContent("【356灵活通】您在我平台的"+info.getInseuranceCycle()+"月"+typestr+"已经缴纳，如有问题请联系客服"+getRootMap().get("serviceTel"));
                }else{
                    model1.setTitle("参保补缴");
                    model1.setContent("【356灵活通】您在我平台的"+info.getInsuStart()+"-"+info.getInsuEnd()+"月"+typestr+"已经缴纳，如有问题请联系客服"+getRootMap().get("serviceTel"));
                }

                messageService.sendMsg(model1);
                Vip vip = vipService.queryVipById(info.getUid());
                SmsUtil.sendSms(model1.getContent(),vip.getTel(),null,null);
            }
        }
        model.setAuditTime(DateUtil.getNowPlusTime());
        try {
            insuranceService.upAudit(model);

        } catch (Exception e) {
            e.printStackTrace();
            sendFailureMessage(response, "操作失败！");
        }
        sendSuccessMessage(response, "操作成功~");
    }
    /**
     * 导出数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportList.do")
    public void exportList(OrderInfoModel model, HttpServletResponse response, HttpServletRequest request) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "1！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        try {
            // 获取前台传来的题型和课程

//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("orderNum", "参保订单");
            fieldMap.put("insuranceNum", "社保号");
            fieldMap.put("name", "用户姓名");
            fieldMap.put("identNum", "身份证号");
            fieldMap.put("tel", "手机号");
            fieldMap.put("inTypeStr", "参保类型");
            if(model.getStatus()==1){
                fieldMap.put("inseuranceCycle", "参保月份");
            }else if(model.getStatus()==2){
                fieldMap.put("insuStart", "参保月份");
            }
            fieldMap.put("base", "缴纳基数");
            fieldMap.put("ratio", "养老缴费比例");
            fieldMap.put("payment", "缴费金额");
            fieldMap.put("subTime", "申请时间");
            fieldMap.put("audit1", "财务审核");
            fieldMap.put("audit2", "城市经理审核");
//	        // excel的sheetName
            String sheetName = "充值列表";
//	        // excel要导出的数据
            //model.setRows(1000);
            List<OrderInfo> dataList = insuranceService.queryByExcel(model);
            // 导出
            if (dataList == null || dataList.size() == 0) {
            }else {
                //将list集合转化为excle
                ExcelUtil.listToExcel(dataList, fieldMap, sheetName, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/orderexportList.do")
    public void orderexportList(OrderModel model, HttpServletResponse response, HttpServletRequest request) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "1！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        try {
            // 获取前台传来的题型和课程

//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("orderNum", "参保订单");
            fieldMap.put("insuranceNum", "社保号");
            fieldMap.put("name", "用户姓名");
            fieldMap.put("identNum", "身份证号");
            fieldMap.put("tel", "手机号");
            fieldMap.put("inTypeStr", "参保类型");
            fieldMap.put("insuStart", "参保月份");
            fieldMap.put("base", "缴纳基数");
            fieldMap.put("ratio", "养老缴费比例");
            fieldMap.put("payment", "缴费金额");
            fieldMap.put("subTime", "申请时间");
//	        // excel的sheetName
            String sheetName = "审核列表";
//	        // excel要导出的数据
            //model.setRows(1000);
            List<Order> dataList = insuranceService.queryOrderByExcel(model);
            // 导出
            if (dataList == null || dataList.size() == 0) {
            }else {
                //将list集合转化为excle
                ExcelUtil.listToExcel(dataList, fieldMap, sheetName, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/countquitlist")
    public void countquitlist(QuitModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "1！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        Integer count=insuranceService.countquitlist(model);
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
    @RequestMapping("/getquitlist")
    public void getquitlist(QuitModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        List<Quit> dataList=insuranceService.getquitlist(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        jsonMap.put("jid", user.getJid());
        HtmlUtil.writerJson(response, jsonMap);
    }
    @RequestMapping("/upQuitAudit")
    public void upQuitAudit(QuitModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        if(model.getAudit()==2){

            if(model==null||model.getId()==null){
                HtmlUtil.writerJson(response, "请选择！");
                return;
            }

            //修改order_info表
            insuranceService.upisquit(2,model.getOrderNum());

            //修改order表
            OrderModel om = new OrderModel();
            om.setAudit(6);
            om.setAuditTime(DateUtil.getNowPlusTime());
            om.setOrderNum(model.getOrderNum());
            insuranceService.upAuditByOrderNum(om);

            //修改积分
            Integer qi=model.getQuit_integration()==null?0:model.getQuit_integration();
            Integer idf=model.getIntegration_diff()==null?0:model.getIntegration_diff();
            integrationLogService.updateIntegration(qi-idf,model.getUid(),DateUtil.getNowPlusTime());
            if(qi>0) {
                IntegrationModel model4 = new IntegrationModel();
                model4.setAddTime(DateUtil.getNowPlusTime());
                model4.setUid(model.getUid());
                model4.setIntegration(qi);
                model4.setType(5);
                integrationLogService.addInLog(model4);
                if (idf > 0) {
                    IntegrationModel model5 = new IntegrationModel();
                    model5.setAddTime(DateUtil.getNowPlusTime());
                    model5.setUid(model.getUid());
                    model5.setIntegration(idf);
                    model5.setType(6);
                    integrationLogService.addInLog(model5);
                }
            }

            //发送短信
            MessageModel model1=new MessageModel();
            model1.setUser_id(model.getUid());
            model1.setTitle("退保审核");
            model1.setCreate_time(new Date());
            model1.setFlag(0);
            model1.setUnread(1);
            model1.setType(2);
            model1.setContent("您的参保订单("+model.getOrderNum()+")退款已驳回,如有问题请联系电话"+getRootMap().get("serviceTel"));
            messageService.sendMsg(model1);
        }
        if(user.getJid()==4){
            model.setAudit(20+model.getAudit());
        }else if(user.getJid()==3){
            model.setAudit(30+model.getAudit());
        }
        try {
            insuranceService.upQuitAudit(model);

            if(model.getAudit()!=null && model.getAudit()==41){
                insuranceService.upisquit(1,model.getOrderNum());
                OrderModel model1=new OrderModel();
                model1.setOrderNum(model.getOrderNum());
                model1.setAudit(8);
                String time=DateUtil.getNowPlusTime();
                model1.setSubTime(time);
                //Integer qi=model.getQuit_integration()==null?0:model.getQuit_integration();
                //Integer idf=model.getIntegration_diff()==null?0:model.getIntegration_diff();
                insuranceService.upAuditByOrderNum(model1);
                //integrationLogService.updateIntegration(-(qi-idf),model.getUid(),time);
//                if(qi>0){
//                    IntegrationModel model2=new IntegrationModel();
//                    model2.setAddTime(time);
//                    model2.setUid(model.getUid());
//                    model2.setIntegration(qi);
//                    model2.setType(3);
//                    integrationLogService.addInLog(model2);
//                    if(idf>0){
//                        IntegrationModel model3=new IntegrationModel();
//                        model3.setAddTime(time);
//                        model3.setUid(model.getUid());
//                        model3.setIntegration(idf);
//                        model3.setType(4);
//                        integrationLogService.addInLog(model3);
//                    }
//                }
                MessageModel msmodel=new MessageModel();
                msmodel.setUser_id(model.getUid());
                msmodel.setTitle("退保审核");
                msmodel.setCreate_time(new Date());
                msmodel.setFlag(0);
                msmodel.setUnread(1);
                msmodel.setType(2);
                msmodel.setContent("您的参保订单("+model.getOrderNum()+")退款审核已通过,如有问题请联系电话"+getRootMap().get("serviceTel"));
                messageService.sendMsg(msmodel);
            }
//            if(model.getAudit()!=null && model.getAudit()==31){
//                Date date=new Date();
//                SimpleDateFormat format=new SimpleDateFormat("YYYY/MM");
//                String inseuranceCycle=format.format(date);
//                Integer aaa=vipService.countorderinfo(model.getUid(),inseuranceCycle,model.getInsuranceType());
//                VipModel vipmodel=new VipModel();
//                vipmodel.setId(model.getUid());
//                if(aaa==0){
//                    Integer bbb=vipService.countorderinfo(model.getUid(),"",model.getInsuranceType());
//                    if(model.getInsuranceType()==1){
//                        if(bbb==0){
//                            vipmodel.setYl_statu(1);
//                        }else{
//                            vipmodel.setYl_statu(3);
//                        }
//                    }else if(model.getInsuranceType()==2){
//                        if(bbb==0){
//                            vipmodel.setMedical_statu(1);
//                        }else{
//                            vipmodel.setMedical_statu(3);
//                        }
//                    }
//                    vipService.updateById(vipmodel);
//                }
//            }
            //加入参保退款驳回积分 记录
            /*QuitModel qModel = insuranceService.queryByQuitId(model.getId());

            if(qModel!=null){
                Integration integration1 = integrationLogService.queryByQuitId(qModel.getId(),3);
                IntegrationModel imodel = new IntegrationModel();
                imodel.setQuit_id(integration1.getQuit_id());
                imodel.setIntegration(integration1.getIntegration());
                imodel.setType(5);
                imodel.setUid(integration1.getUid());
                integrationLogService.addInLog(imodel);

            }*/
        } catch (Exception e) {
            e.printStackTrace();
            sendFailureMessage(response, "操作失败！");
        }
        sendSuccessMessage(response, "操作成功~");
    }
    @RequestMapping("/upQuitAudit1")
    public void upQuitAudit1(QuitModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        if(model.getAudit()==2){
            if(model==null||model.getId()==null){
                HtmlUtil.writerJson(response, "请选择！");
                return;
            }
            Order ord=insuranceService.queryByOrderNumber(model.getOrderNum());
            OrderModel orm=new OrderModel();
            orm.setId(ord.getId());
            orm.setInsuStart(ord.getOldStart());
            orm.setInsuEnd(ord.getOldEnd());
            orm.setPayment(ord.getOldPayment());
            orm.setAudit(1);
            insuranceSetService.updateInsurance(orm);
            //发送短信
//            MessageModel model1=new MessageModel();
//            model1.setUser_id(model.getUid());
//            model1.setTitle("退保审核");
//            model1.setCreate_time(new Date());
//            model1.setFlag(0);
//            model1.setUnread(1);
//            model1.setType(2);
//            model1.setContent("您的参保订单("+model.getOrderNum()+")退款已驳回,如有问题请联系电话"+getRootMap().get("serviceTel"));
//            messageService.sendMsg(model1);
        }
        if(user.getJid()==4){
            model.setAudit(20+model.getAudit());
        }else if(user.getJid()==3){
            model.setAudit(30+model.getAudit());
        }
        try {
            insuranceService.upQuitAudit(model);
            /*if(model.getAudit()!=null && model.getAudit()==41){
                MessageModel msmodel=new MessageModel();
                msmodel.setUser_id(model.getUid());
                msmodel.setTitle("退保审核");
                msmodel.setCreate_time(new Date());
                msmodel.setFlag(0);
                msmodel.setUnread(1);
                msmodel.setType(2);
                msmodel.setContent("您的参保订单("+model.getOrderNum()+")退款审核已通过,如有问题请联系电话"+getRootMap().get("serviceTel"));
                messageService.sendMsg(msmodel);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            sendFailureMessage(response, "操作失败！");
        }
        sendSuccessMessage(response, "操作成功~");
    }
    @RequestMapping("/exportquitList.do")
    public void exportquitList(QuitModel model,String subTime3,String subTime4,
                               HttpServletResponse response, HttpServletRequest request) throws Exception{
        try {
            // 获取前台传来的题型和课程

//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("orderNum", "参保订单");
            fieldMap.put("insuranceNum", "社保号");
            fieldMap.put("name", "用户姓名");
            fieldMap.put("identNum", "身份证号");
            fieldMap.put("inTypeStr", "参保类型");
            fieldMap.put("insuStart", "参保周期");
            fieldMap.put("quit_payment", "退保金额");
            fieldMap.put("quit_integration", "退保积分");
            fieldMap.put("integration_diff", "积分差额");
            fieldMap.put("payment_inte", "现金抵扣积分(元)");
            fieldMap.put("pra_payment", "实际退款");
            fieldMap.put("subTime", "申请时间");
            fieldMap.put("audit1", "财务状态");
            fieldMap.put("audit2", "城市经理状态");
//	        // excel的sheetName
            String sheetName = "充值列表";
//	        // excel要导出的数据
            //model.setRows(1000);
            if(subTime3!=null && !subTime3.equals("")){
                model.setSubTime1(subTime3);
            }

            if(subTime4!=null && !subTime4.equals("")){
                model.setSubTime2(subTime4);
            }
            List<Quit> dataList = insuranceService.queryquitByExcel(model);
            // 导出
            if (dataList == null || dataList.size() == 0) {
            }else {
                //将list集合转化为excle
                ExcelUtil.listToExcel(dataList, fieldMap, sheetName, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/countUserlist")
    public void countUserlist(OrderInfoModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "1！");
            return;
        }
        if(user.getJid()==1){
            model.setDeptId(null);
        }else{
            model.setDeptId(user.getDeptId());
        }
        Integer count=insuranceService.countUserlist(model);
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
    @RequestMapping("/getUserlist")
    public void getUserlist(OrderInfoModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        List<OrderInfo> dataList=insuranceService.getUserlist(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        jsonMap.put("jid", user.getJid());
        HtmlUtil.writerJson(response, jsonMap);
    }


    @RequestMapping("/exportUserList.do")
    public void exportUserList(OrderInfoModel model, HttpServletResponse response, HttpServletRequest request) throws Exception{
        try {
            // 获取前台传来的题型和课程

//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("insuranceNum", "社保号");
            fieldMap.put("name", "用户姓名");
            fieldMap.put("identNum", "身份证号");
            fieldMap.put("tel", "手机号");
            fieldMap.put("inTypeStr", "参保类型");
            fieldMap.put("inseuranceCycle", "参保月份");
            fieldMap.put("base", "缴纳基数");
            fieldMap.put("ratio", "养老缴费比例");
            fieldMap.put("payment", "缴费金额");
            fieldMap.put("subTime", "通过时间");
//	        // excel的sheetName
            String sheetName = "充值列表";
//	        // excel要导出的数据
            //model.setRows(1000);
            List<OrderInfo> dataList = insuranceService.queryByUserExcel(model);
            // 导出
            if (dataList == null || dataList.size() == 0) {
            }else {
                //将list集合转化为excle
                ExcelUtil.listToExcel(dataList, fieldMap, sheetName, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信支付（统一下单）
     */


}
