package com.dongdao.gqwl.action;

import com.dongdao.gqwl.UserConstants;
import com.dongdao.gqwl.bean.*;
import com.dongdao.gqwl.model.*;
import com.dongdao.gqwl.service.*;
import com.dongdao.gqwl.utils.*;
import com.dongdao.gqwl.bean.*;
import com.dongdao.gqwl.model.*;
import com.dongdao.gqwl.service.*;
import com.dongdao.gqwl.utils.*;
import org.apache.commons.lang.StringUtils;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/insuranceSet")
public class InsuranceSetAction extends BaseAction {
    private final static Logger log= Logger.getLogger(InsuranceSetAction.class);

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public InsuranceSetService insuranceSetService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public VipService vipService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public SysDeptService deptService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public InsuranceService insuranceService;


    @Autowired(required = false)
    public IntegrationLogService integrationLogService;

    @Autowired(required = false)
    public SmsTemplateService smsTemplateService;

    @Autowired(required = false)
    public MessageService messageService;

    @Autowired(required = false)
    public SysInsuranceService sysInsuranceService;

    @RequestMapping(value = "/safe_back.do")
    public String safe_back(HttpServletRequest request, HttpServletResponse response) { return "insurance/safe_back";}
    @RequestMapping(value = "/insuranceSet.do")
    public ModelAndView insuranceSet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = getRootMap();

        SysUser sysUser = SessionUtils.getUser(request);
        if(sysUser==null){
            context.put("msg","登录超时,请重新登录！");
            return forword("insurance/safe_set", context);
        }
        if(sysUser.getJid()==1){
            context.put("msg","您没有保险设置权限！");
            return forword("/error403", context);
        }
        List<Insurance> insList = insuranceSetService.allList(sysUser.getDeptId());
        if(insList!=null&&insList.size()>0){
            for(Insurance ins:insList){

                if(ins!=null&&ins.getInsurance().equals("养老保险")){
                    String[] expends_scales = ins.getExpends_scale().split(",");
                    String[] expends_amounts = ins.getExpends_amount().split(",");
                    ins.setExpends_scales(expends_scales);
                    ins.setExpends_amounts(expends_amounts);
                    context.put("yaolao",ins);//养老
                }else{
                    context.put("yiliao",ins);//医疗
                }

            }

        }
        return forword("insurance/safe_set", context);
    }

    @RequestMapping(value = "/setInsurance.do")
    public void setInsurance(InsuranceModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser sysUser = SessionUtils.getUser(request);
        InsuranceModel ylaoModel = new InsuranceModel();
        ylaoModel.setExpends_base(model.getExpends_base());
        if(model!=null&&
                model.getExpends_amounts()!=null){
            String[] expends_amounts = model.getExpends_amounts().split(",");
            String[] expends_scales = model.getExpends_scales().split(",");
            for(int i = 0;i<expends_amounts.length;i++){
                ylaoModel.setId(model.getYlao_id());
                    ylaoModel.setExpends_scale(Double.parseDouble(expends_scales[i].replaceAll("%",""))*0.01);

                ylaoModel.setExpends_amount(Double.parseDouble(expends_amounts[i]));
                insuranceSetService.updateInsBase(ylaoModel);
            }
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
        String adjustment_time_end = "";
        if(model.getAdjustment_time_start()!=null){
            adjustment_time_end = DateUtil.getFomartDate(DateUtil.getDateBetweenByMonth(
                    format.parse(model.getAdjustment_time_start()),11),"yyyy/MM");

        }

        //养老保险基数设置
        ylaoModel.setEffect_start_time(model.getEffect_start_time());
        ylaoModel.setEffect_end_time(model.getEffect_end_time());
        ylaoModel.setState(model.getState());
        ylaoModel.setExpends_base(model.getExpends_base());
        ylaoModel.setAdjustment_time_start(model.getAdjustment_time_start());
        ylaoModel.setAdjustment_time_end(adjustment_time_end);

        insuranceSetService.setInsurance(ylaoModel);

        //医疗保险基数设置
        InsuranceModel yliaoModel = new InsuranceModel();
        yliaoModel.setId(model.getYiliao_id());
        yliaoModel.setExpends_scale(1.0);
        yliaoModel.setExpends_amount(model.getExpends_amount5());
        yliaoModel.setAdjustment_time_start(model.getYb_adjustment_time_start());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        Date ds = sdf.parse(model.getYb_adjustment_time_start());
        //System.out.println(DateUtil.getFomartDate(DateUtil.getDateBetweenByMonth(ds,11),"yyyy/MM"));
        yliaoModel.setAdjustment_time_end(DateUtil.getFomartDate(DateUtil.getDateBetweenByMonth(ds,11),"yyyy/MM"));

        insuranceSetService.updateInsBase(yliaoModel);

        yliaoModel.setExpends_base(model.getExpends_base1());
        insuranceSetService.setInsurance(yliaoModel);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("msg", "操作成功");
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping(value = "/insuranceInfo.do")
    public ModelAndView insuranceInfo(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        SysUser user = SessionUtils.getUser(request);
        SysDeptModel model = new SysDeptModel();


        List<SysDept> shengslist = deptService.queryByList(model);
        context.put("shengs",shengslist);
        context.put("ylshengs",shengslist);

        model.setParentId(shengslist.get(0).getDeptId());
        List<SysDept> shilist = deptService.queryByList(model);
        context.put("shis",shilist);
        context.put("ylshis",shilist);

        model.setParentId(shilist.get(0).getDeptId());
        List<SysDept> qulist = deptService.queryByList(model);
        context.put("qus",qulist);
        context.put("ylqus",qulist);

        InsuranceModel insmodel = new InsuranceModel();
        insmodel.setDeptId(user.getDeptId());
        insmodel.setInsuranceType(1);
        Insurance ins = insuranceService.calculatePayment(insmodel);
        if(ins!=null){
            context.put("yl_insuStart_S",DateUtil.getFomartDate(new Date(),"yyyy")+"-01-01");
            context.put("yl_insuStart_E",DateUtil.getFomartDate(new Date(),"yyyy-MM-dd"));
            context.put("yl_insuEnd_S",DateUtil.getFomartDate(new Date(),"yyyy")+"-01-01");
            context.put("yl_insuEnd_E",ins.getAdjustment_time_end().replaceAll("/","-")+"-30");
        }

        insmodel.setInsuranceType(2);
        ins = insuranceService.calculatePayment(insmodel);
        if(ins!=null){
            context.put("yb_insuStart_S","2010-01-01");
            context.put("yb_insuStart_E",DateUtil.getFomartDate(new Date(),"yyyy-MM-dd"));
            context.put("yb_insuEnd_S",DateUtil.getFomartDate(new Date(),"yyyy-")+"01-01");
            context.put("yb_insuEnd_E",ins.getAdjustment_time_end().replaceAll("/","-")+"-30");
        }

        return forword("insurance/safe_info", context);
    }

    @RequestMapping(value = "/safeAdd.do")
    public ModelAndView safe_add(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        SysDeptModel model = new SysDeptModel();
        List<SysDept> shengslist = deptService.queryByList(model);

        //List<SysDept> shilist = deptService.queryByList(model);
        //List<SysDept> qulist = deptService.queryByList(model);
        context.put("shengs",shengslist);
        context.put("ylshengs",shengslist);
        return forword("insurance/safe_add", context);
    }
    @RequestMapping("/countInsList")
    public void countinsulist(OrderModel model, String name, HttpServletRequest request,
                              HttpServletResponse response) throws Exception{
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
        System.out.println(name);
        if(model.getName()!=null){
            model.setTname(model.getName());
        }
        Integer count=insuranceSetService.insuranceDateCount(model);
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
    @RequestMapping("/dataInsList")
    public void getinsulist(OrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        if(model.getName()!=null){
            model.setTname(model.getName());
        }
        model.setNum1(model.getPageSize()*(model.getPageNum()-1));
        model.setNum2(model.getPageSize());
        List<Order> dataList=insuranceSetService.insuranceDateList(model);

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        jsonMap.put("jid", user.getJid());
        HtmlUtil.writerJson(response, jsonMap);
    }

    /**
     * 导出Order数据
     * @return
     * @throws Exception
     */
    //@Auth(verifyLogin=false,verifyURL=false)
    @RequestMapping("/exportOrderList.do")
    public void exportQueryList(OrderModel model,
                                HttpServletResponse response, HttpServletRequest request) throws Exception{
        try {
//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("orderNum", "参保订单");
            fieldMap.put("insuranceNum", "社保号");
            fieldMap.put("name", "姓名");
            fieldMap.put("identNum", "身份证号");
            fieldMap.put("tel", "手机号");
            fieldMap.put("insuranceTypeCh_zn", "参保类型");
            fieldMap.put("cycle", "参保周期");
            fieldMap.put("base", "缴纳基数");
            fieldMap.put("ratio", "养老缴费比例");
            fieldMap.put("payment", "缴费金额");
            fieldMap.put("insuranceNatureCh_zn", "用户参保标签");
            fieldMap.put("firstTime", "首次参保时间");
            fieldMap.put("registered", "户口所在地");
            fieldMap.put("auditCh_zn", "审核状态");
            fieldMap.put("subTime", "提交时间");
            fieldMap.put("remarks", "备注");


//	        // excel的sheetName
            String sheetName = "参保信息";
//	        // excel要导出的数据
            //model.setRows(1000);
            List<Goods> dataList = insuranceSetService.insuranceDataExcel(model);
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

    @RequestMapping("/orderInfo")
    public void orderInfo(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }


        Map<String, Object> jsonMap = new HashMap<String, Object>();
      //  jsonMap.put("rows", dataList);
        jsonMap.put("jid", user.getJid());
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/updateInsurance.do")
    public void updateInsurance(OrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }

        try {
            Order orr = insuranceSetService.queryById(model.getId());
            if(model.getUpdateType()!=null&&model.getUpdateType()==3){

                InsuranceModel insModel = new InsuranceModel();
                insModel.setInsuranceType(orr.getInsuranceType());

                insModel.setDeptId(orr.getDeptId());
                insModel.setExpends_scale(Double.parseDouble(""+(orr.getRatio()*0.01)+""));

                Insurance ins = insuranceService.calculatePayment(insModel);
                model.setInsuStart( model.getInsuStart().replaceAll("-","/"));
                model.setInsuEnd( model.getInsuEnd().replaceAll("-","/"));
                if(orr.getRatio()==60&&ins!=null
                        &&ins.getState()==1){
                    int s = DateUtil.compareDateYM(model.getInsuStart(),ins.getEffect_start_time());
                    if(s<0){
                        sendFailureMessage(response, "您的参保月份不在缴费基数60%的规定参保月份之内，请重新选择！");
                        return;
                    }

                    int e = DateUtil.compareDateYM(model.getInsuEnd(),ins.getEffect_end_time());
                    if(e>0){
                        sendFailureMessage(response, "您的参保月份不在缴费基数60%的规定参保月份之内，请重新选择！");
                        return;
                    }

                }
                if(!orr.getInsuStart().equals(model.getInsuStart()) || !orr.getInsuEnd().equals(model.getInsuEnd())){
                    model.setOldEnd(orr.getInsuEnd());
                    model.setOldStart(orr.getInsuStart());
                    Integer month = DateUtil.getMonth(model.getInsuStart(),model.getInsuEnd())+1;
                    model.setPayment((month*Double.parseDouble(ins.getExpends_amount())));
                    model.setOldPayment(Double.parseDouble(orr.getPayment()));
                    model.setAudit(2);
                    double quitPay=0.0;
                    Integer month1 = DateUtil.getMonth(orr.getInsuStart(),orr.getInsuEnd())+1;
                    if(month>month1){
                        sendFailureMessage(response, "不能多于原有周期月数！");
                        return;
                    }
                }else{
                    model.setAudit(6);
                }
                insuranceSetService.updateInsurance(model);
            }

            if(model.getUpdateType()!=null && model.getUpdateType()==3 && model.getAudit()==6){
                Order orr1 = insuranceSetService.queryById(model.getId());
                    InsuranceModel insModel = new InsuranceModel();
                    insModel.setExpends_scale(Double.parseDouble((orr1.getRatio()*0.01)+""));
                    insModel.setDeptId(orr1.getDeptId());
                    insModel.setInsuranceType(orr1.getInsuranceType());
                    Insurance ins = insuranceService.calculatePayment(insModel);
                    //1 首先判单这个订单是补缴还是参保
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");//可以方便地修改日期格式
                    String nowformat = dateFormat.format(c.getTime());
                    Date nowDate = dateFormat.parse(nowformat);
                    Date insDate = dateFormat.parse(orr1.getInsuStart());
                        /*
                          返回1， 说明订单开始时间小于当前时间，该订单包含补缴；
                          返回-1，说明订单开始时间大于当前时间，该订单肯定是参保；
                          返回0， 说明是订单开始时间和结束时间都是当月。*/
                    int is_bujao = DateUtil.compareDate(nowDate,insDate);

                    //2 如果是补缴 有两种情况

                    if(is_bujao==1){
                        Date insEndDate = dateFormat.parse(orr1.getInsuEnd());
                        // 2a. bujaoTime>0 都是补缴
                        // 2b. bujaoTime<0 一部分补缴
                        // 2c. bujaoTime==0 说明insuEnd为当月，当月25号下午之前缴费（insuEnd）算参保，之后（insuEnd）算补缴
                        int bujaoTime = DateUtil.compareDate(nowDate,insEndDate);
                        OrderInfoModel imodel = new OrderInfoModel();
                        imodel.setOrderNum(orr1.getOrderNum());
                        imodel.setUid(orr1.getUid());
                        imodel.setInsuranceType(orr1.getInsuranceType());
                        imodel.setInseuranceCycle("");
                        imodel.setInsuStart(orr1.getInsuStart());
                        imodel.setAuditTime(DateUtil.getNowPlusTime());
                        imodel.setSubTime(orr1.getSubTime());
                        imodel.setBase(orr1.getBase());
                        imodel.setRatio(orr1.getRatio());
                        imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                        imodel.setStatus(2);
                        imodel.setAudit(1);
                        imodel.setDeptId(orr1.getDeptId());
                        imodel.setIsquit(2);
                        //2b
                        if(bujaoTime==-1||bujaoTime==0){
                            String timestr1=DateUtil.getNowPlusTime();
                            SimpleDateFormat form=new SimpleDateFormat("yyyy-MM");
                            String timestr2=form.format(new Date())+"-25 17:00:00";
                            if(timestr1.compareTo(timestr2)>0){
                                imodel.setInsuEnd(dateFormat.format(c.getTime()));
                                int monthNum = DateUtil.getMonthSpace(orr1.getInsuStart(),nowformat)+1;

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

                                int monthNum = DateUtil.getMonthSpace(orr1.getInsuStart(),nowformat);
                                imodel.setMonthNum(monthNum);

                                imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*monthNum);
                                imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                            }
                        }else if(bujaoTime==1){
                            imodel.setInsuEnd(orr1.getInsuEnd());

                            int monthNum = DateUtil.getMonthSpace(orr1.getInsuStart(),orr1.getInsuEnd());
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
                            cbNum = DateUtil.getMonthSpace1(nowformat,orr1.getInsuEnd());
                        }else{
                            cbNum = DateUtil.getMonthSpace1(nowformat,orr1.getInsuEnd())+1;
                        }
                        for(int i=0;i<cbNum;i++){
                            OrderInfoModel model1 = new OrderInfoModel();
                            Calendar c_1 = Calendar.getInstance();
                            c_1.setTime(c.getTime());
                            model1.setOrderNum(orr1.getOrderNum());
                            model1.setUid(orr1.getUid());
                            model1.setInsuranceType(orr1.getInsuranceType());

                            c_1.add(Calendar.MONTH, i+1);
                            if(DateUtil.getMonthSpace1(orr1.getInsuEnd(),dateFormat.format(c_1.getTime()))>0){
                                break;
                            }
                            model1.setInseuranceCycle(dateFormat.format(c_1.getTime()));
                            model1.setInsuStart("");
                            model1.setInsuEnd("");
                            model1.setBase(orr1.getBase());
                            model1.setRatio(orr1.getRatio());

                            model1.setMonthNum(1);

                            model1.setPayment(Double.parseDouble(ins.getExpends_amount()));

                            model1.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                            model1.setStatus(1);
                            model1.setAudit(1);
                            model1.setDeptId(orr1.getDeptId());
                            model1.setIsquit(2);
                            model1.setSubTime(orr1.getSubTime());
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
                            imodel.setOrderNum(orr1.getOrderNum());
                            imodel.setUid(orr1.getUid());
                            imodel.setInsuranceType(orr1.getInsuranceType());
                            imodel.setInseuranceCycle("");
                            imodel.setInsuStart(orr1.getInsuStart());
                            imodel.setAuditTime(DateUtil.getNowPlusTime());
                            imodel.setSubTime(orr1.getSubTime());
                            imodel.setBase(orr1.getBase());
                            imodel.setRatio(orr1.getRatio());
                            imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                            imodel.setStatus(2);
                            imodel.setAudit(1);
                            imodel.setDeptId(orr1.getDeptId());
                            imodel.setIsquit(2);
                            String timestr1=DateUtil.getNowPlusTime();
                            SimpleDateFormat form=new SimpleDateFormat("yyyy-MM");
                            String timestr2=form.format(new Date())+"-25 17:00:00";
                            if(timestr1.compareTo(timestr2)>0){
                                c1.add(Calendar.MONTH, 1);
                                imodel.setInsuEnd(orr1.getInsuStart());

                                monthNum1 = 1;

                                imodel.setMonthNum(monthNum1);

                                imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*monthNum1);
                                imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                monNum = DateUtil.getMonthSpace(dateFormat.format(c1.getTime()),orr1.getInsuEnd());
                                cbNum = DateUtil.getMonthSpace1(dateFormat.format(c1.getTime()),orr1.getInsuEnd());
                                insuranceService.addInsInfo(imodel);
                            }else{
                                monNum = DateUtil.getMonthSpace(orr1.getInsuStart(),orr1.getInsuEnd());
                                cbNum=monNum==0?1:monNum;
                            }
                        }else{
                            monNum = DateUtil.getMonthSpace(orr1.getInsuStart(),orr1.getInsuEnd());
                            cbNum=monNum==0?1:monNum;
                        }
                        if(cbNum>0){
                            for(int i=0;i<=monNum;i++){
                                OrderInfoModel model2 = new OrderInfoModel();

                                model2.setOrderNum(orr1.getOrderNum());
                                model2.setUid(orr1.getUid());
                                model2.setInsuranceType(orr1.getInsuranceType());
                                model2.setInsuranceNature(orr1.getInsuranceNature());
                                c1.setTime(dateFormat.parse(orr1.getInsuStart()));
                                c1.add(Calendar.MONTH, i+monthNum1);
                                model2.setInseuranceCycle(dateFormat.format(c1.getTime()));
                                model2.setInsuStart("");
                                model2.setInsuEnd("");

                                model2.setAuditTime(DateUtil.getNowPlusTime());
                                model2.setSubTime(orr1.getSubTime());
                                model2.setBase(orr1.getBase());
                                model2.setRatio(orr1.getRatio());

                                model2.setMonthNum(1);

                                model2.setPayment(Double.parseDouble(ins.getExpends_amount()));

                                model2.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                model2.setStatus(1);
                                model2.setAudit(1);
                                model2.setDeptId(orr1.getDeptId());
                                model2.setIsquit(2);
                                insuranceService.addInsInfo(model2);
                            }
                        }
                    }

                    IntegrationModel model1=new IntegrationModel();
                    model1.setType(1);
                    String time=DateUtil.getNowPlusTime();
                    model1.setAddTime(time);
                    model1.setUid(orr1.getUid());
                    int mon = DateUtil.getMonth(orr1.getInsuStart(),orr1.getInsuEnd());
                    double x = 0.0;
                    if(mon>=6){
                        x = 1;
                    }
                    model1.setIntegration((int)(Double.parseDouble(orr1.getPayment())*x));
                    integrationLogService.addInLog(model1);
                    integrationLogService.updateIntegration((int)(Double.parseDouble(orr1.getPayment())*x),orr1.getUid(),time);


                String typestr="";
                if(orr1.getInsuranceType()==1){
                    typestr="养老保险";
                }else{
                    typestr="医疗保险";
                }
                Integer month = DateUtil.getMonth(model.getInsuStart().replaceAll("-","/"),model.getInsuEnd().replaceAll("-","/"))+1;
                MessageModel msmodel=new MessageModel();
                msmodel.setUser_id(model.getUid());
                msmodel.setTitle("审核通过");
                msmodel.setCreate_time(new Date());
                msmodel.setFlag(0);
                msmodel.setUnread(1);
                msmodel.setType(2);
                msmodel.setContent("【365灵活通】您参保的（"+typestr+"）已通过，参保周期为"+model.getInsuStart().replaceAll("-","/")+
                        "-"+model.getInsuEnd().replaceAll("-","/")+"，共"+month+"个月，" +
                        "在此期间，参保为每月缴纳，补缴信息会一次性缴纳,如有问题请联系电话"+getRootMap().get("serviceTel"));
                messageService.sendMsg(msmodel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendFailureMessage(response, "操作失败！");
        }
        sendSuccessMessage(response, "保单调整成功，请等待审核~");
    }

    @RequestMapping("/auditPassIns.do")
    public void auditIns(OrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }
        try {
                Order orr1 = insuranceSetService.queryById(model.getId());
                if(model!=null&&model.getAudit()!=null&&model.getAudit()==6){
                        InsuranceModel insModel = new InsuranceModel();
                        insModel.setExpends_scale(Double.parseDouble((orr1.getRatio()*0.01)+""));
                        insModel.setDeptId(orr1.getDeptId());
                        insModel.setInsuranceType(orr1.getInsuranceType());
                        Insurance ins = insuranceService.calculatePayment(insModel);
                        //1 首先判单这个订单是补缴还是参保
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");//可以方便地修改日期格式
                        String nowformat = dateFormat.format(c.getTime());
                        Date nowDate = dateFormat.parse(nowformat);
                        Date insDate = dateFormat.parse(orr1.getInsuStart());
                        /*
                          返回1， 说明订单开始时间小于当前时间，该订单包含补缴；
                          返回-1，说明订单开始时间大于当前时间，该订单肯定是参保；
                          返回0， 说明是订单开始时间和结束时间都是当月。*/
                        int is_bujao = DateUtil.compareDate(nowDate,insDate);

                        //2 如果是补缴 有两种情况

                        if(is_bujao==1){
                            Date insEndDate = dateFormat.parse(orr1.getInsuEnd());
                            // 2a. bujaoTime>0 都是补缴
                            // 2b. bujaoTime<0 一部分补缴
                            // 2c. bujaoTime==0 说明insuEnd为当月，当月25号下午之前缴费（insuEnd）算参保，之后（insuEnd）算补缴
                            int bujaoTime = DateUtil.compareDate(nowDate,insEndDate);
                            OrderInfoModel imodel = new OrderInfoModel();
                            imodel.setOrderNum(orr1.getOrderNum());
                            imodel.setUid(orr1.getUid());
                            imodel.setInsuranceType(orr1.getInsuranceType());
                            imodel.setInseuranceCycle("");
                            imodel.setInsuStart(orr1.getInsuStart());
                            imodel.setAuditTime(DateUtil.getNowPlusTime());
                            imodel.setSubTime(orr1.getSubTime());
                            imodel.setBase(orr1.getBase());
                            imodel.setRatio(orr1.getRatio());
                            imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                            imodel.setStatus(2);
                            imodel.setAudit(1);
                            imodel.setDeptId(orr1.getDeptId());
                            imodel.setIsquit(2);
                            //2b
                            if(bujaoTime==-1||bujaoTime==0){
                                String timestr1=DateUtil.getNowPlusTime();
                                SimpleDateFormat form=new SimpleDateFormat("yyyy-MM");
                                String timestr2=form.format(new Date())+"-25 17:00:00";
                                if(timestr1.compareTo(timestr2)>0){
                                    imodel.setInsuEnd(dateFormat.format(c.getTime()));
                                    int monthNum = DateUtil.getMonthSpace(orr1.getInsuStart(),nowformat)+1;

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

                                    int monthNum = DateUtil.getMonthSpace(orr1.getInsuStart(),nowformat);
                                    imodel.setMonthNum(monthNum);

                                    imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*monthNum);
                                    imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                }
                            }else if(bujaoTime==1){
                                imodel.setInsuEnd(orr1.getInsuEnd());

                                int monthNum = DateUtil.getMonthSpace(orr1.getInsuStart(),orr1.getInsuEnd());
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
                                cbNum = DateUtil.getMonthSpace1(nowformat,orr1.getInsuEnd());
                            }else{
                                cbNum = DateUtil.getMonthSpace1(nowformat,orr1.getInsuEnd())+1;
                            }
                            for(int i=0;i<cbNum;i++){
                                OrderInfoModel model1 = new OrderInfoModel();
                                Calendar c_1 = Calendar.getInstance();
                                c_1.setTime(c.getTime());
                                model1.setOrderNum(orr1.getOrderNum());
                                model1.setUid(orr1.getUid());
                                model1.setInsuranceType(orr1.getInsuranceType());

                                c_1.add(Calendar.MONTH, i+1);
                                if(DateUtil.getMonthSpace1(orr1.getInsuEnd(),dateFormat.format(c_1.getTime()))>0){
                                    break;
                                }
                                model1.setInseuranceCycle(dateFormat.format(c_1.getTime()));
                                model1.setInsuStart("");
                                model1.setInsuEnd("");
                                model1.setBase(orr1.getBase());
                                model1.setRatio(orr1.getRatio());

                                model1.setMonthNum(1);

                                model1.setPayment(Double.parseDouble(ins.getExpends_amount()));

                                model1.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                model1.setStatus(1);
                                model1.setAudit(1);
                                model1.setDeptId(orr1.getDeptId());
                                model1.setIsquit(2);
                                model1.setSubTime(orr1.getSubTime());
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
                                imodel.setOrderNum(orr1.getOrderNum());
                                imodel.setUid(orr1.getUid());
                                imodel.setInsuranceType(orr1.getInsuranceType());
                                imodel.setInseuranceCycle("");
                                imodel.setInsuStart(orr1.getInsuStart());
                                imodel.setAuditTime(DateUtil.getNowPlusTime());
                                imodel.setSubTime(orr1.getSubTime());
                                imodel.setBase(orr1.getBase());
                                imodel.setRatio(orr1.getRatio());
                                imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                imodel.setStatus(2);
                                imodel.setAudit(1);
                                imodel.setDeptId(orr1.getDeptId());
                                imodel.setIsquit(2);
                                String timestr1=DateUtil.getNowPlusTime();
                                SimpleDateFormat form=new SimpleDateFormat("yyyy-MM");
                                String timestr2=form.format(new Date())+"-25 17:00:00";
                                if(timestr1.compareTo(timestr2)>0){
                                    c1.add(Calendar.MONTH, 1);
                                    imodel.setInsuEnd(orr1.getInsuStart());

                                    monthNum1 = 1;

                                    imodel.setMonthNum(monthNum1);

                                    imodel.setPayment(Double.parseDouble(ins.getExpends_amount())*monthNum1);
                                    imodel.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                    monNum = DateUtil.getMonthSpace(dateFormat.format(c1.getTime()),orr1.getInsuEnd());
                                    cbNum = DateUtil.getMonthSpace1(dateFormat.format(c1.getTime()),orr1.getInsuEnd());
                                    insuranceService.addInsInfo(imodel);
                                }else{
                                    monNum = DateUtil.getMonthSpace(orr1.getInsuStart(),orr1.getInsuEnd());
                                    cbNum=monNum==0?1:monNum;
                                }
                            }else{
                                monNum = DateUtil.getMonthSpace(orr1.getInsuStart(),orr1.getInsuEnd());
                                cbNum=monNum==0?1:monNum;
                            }
                            if(cbNum>0){
                                for(int i=0;i<=monNum;i++){
                                    OrderInfoModel model2 = new OrderInfoModel();

                                    model2.setOrderNum(orr1.getOrderNum());
                                    model2.setUid(orr1.getUid());
                                    model2.setInsuranceType(orr1.getInsuranceType());
                                    model2.setInsuranceNature(orr1.getInsuranceNature());
                                    c1.setTime(dateFormat.parse(orr1.getInsuStart()));
                                    c1.add(Calendar.MONTH, i+monthNum1);
                                    model2.setInseuranceCycle(dateFormat.format(c1.getTime()));
                                    model2.setInsuStart("");
                                    model2.setInsuEnd("");

                                    model2.setAuditTime(DateUtil.getNowPlusTime());
                                    model2.setSubTime(orr1.getSubTime());
                                    model2.setBase(orr1.getBase());
                                    model2.setRatio(orr1.getRatio());

                                    model2.setMonthNum(1);

                                    model2.setPayment(Double.parseDouble(ins.getExpends_amount()));

                                    model2.setUnitPrice(Double.parseDouble(ins.getExpends_amount()));
                                    model2.setStatus(1);
                                    model2.setAudit(1);
                                    model2.setDeptId(orr1.getDeptId());
                                    model2.setIsquit(2);
                                    insuranceService.addInsInfo(model2);
                                }
                            }
                        }

                        IntegrationModel model1=new IntegrationModel();
                        model1.setType(1);
                        String time=DateUtil.getNowPlusTime();
                        model1.setAddTime(time);
                        model1.setUid(orr1.getUid());
                        int mon = DateUtil.getMonth(orr1.getInsuStart(),orr1.getInsuEnd());
                        double x = 0.0;
                        if(mon>=6){
                            x = 1;
                        }
                        model1.setIntegration((int)(Double.parseDouble(orr1.getPayment())*x));
                        insuranceSetService.updateInsurance(model);
                        integrationLogService.addInLog(model1);
                        integrationLogService.updateIntegration((int)(Double.parseDouble(orr1.getPayment())*x),orr1.getUid(),time);


                        String typestr="";
                        if(orr1.getInsuranceType()==1){
                            typestr="养老保险";
                        }else{
                            typestr="医疗保险";
                        }
                        Integer month = DateUtil.getMonth(orr1.getInsuStart().replaceAll("-","/"),orr1.getInsuEnd().replaceAll("-","/"))+1;
                        MessageModel msmodel=new MessageModel();
                        msmodel.setUser_id(orr1.getUid());
                        msmodel.setTitle("审核通过");
                        msmodel.setCreate_time(new Date());
                        msmodel.setFlag(0);
                        msmodel.setUnread(1);
                        msmodel.setType(2);
                        msmodel.setContent("【365灵活通】您参保的（"+typestr+"）已通过，参保周期为"+orr1.getInsuStart()+"-"+orr1.getInsuEnd()+"，共"+month+"个月，" +
                                "在此期间，参保为每月缴纳，补缴信息会一次性缴纳,如有问题请联系电话"+getRootMap().get("serviceTel"));
                        messageService.sendMsg(msmodel);
                        SmsUtil.sendSms("【365灵活通】您参保的（"+typestr+"）已通过，参保周期为"+orr1.getInsuStart()+"-"+orr1.getInsuEnd()+"，共"+month+"个月，" +
                                "在此期间，参保为每月缴纳，补缴信息会一次性缴纳,如有问题请联系电话"+getRootMap().get("serviceTel"),orr1.getTel(),null,null);

                    }else if(model.getAudit()==7){
                        QuitModel qmodel = new QuitModel();
                        qmodel.setUid(orr1.getUid());
                        qmodel.setOrderNum(orr1.getOrderNum());
                        qmodel.setInsuranceType(orr1.getInsuranceType());
                        qmodel.setDeptId(orr1.getDeptId());
                        int monNumm = DateUtil.getMonth(orr1.getInsuStart(),orr1.getInsuEnd())+1;

                        String quitMonth="";
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");//格式化为年月
                        Calendar min = Calendar.getInstance();
                        Calendar max = Calendar.getInstance();
                        min.setTime(sdf.parse(orr1.getInsuStart()));
                        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
                        max.setTime(sdf.parse(orr1.getInsuEnd()));
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
                        qmodel.setQuitMonth(quitMonth);
                        qmodel.setMonthNum(monNumm);
                        qmodel.setQuit_payment(Double.parseDouble(orr1.getPayment()));
                        qmodel.setPra_payment(Double.parseDouble(orr1.getPayment()));
                        qmodel.setSubTime(DateUtil.getNowPlusTime());
                        qmodel.setAudit(11);
                        qmodel.setQuit_integration(0);
                        qmodel.setIntegration_diff(0);
                        qmodel.setPayment_inte(0.0);
                        qmodel.setQuitType(3);
                        insuranceService.addQuit(qmodel);
                        model.setOrderNum(orr1.getOrderNum());
                        insuranceService.upAuditByOrderNum(model);
//                        MessageModel msmodel=new MessageModel();
//                        msmodel.setUser_id(order.getUid());
//                        msmodel.setTitle("退保成功");
//                        msmodel.setCreate_time(new Date());
//                        msmodel.setFlag(0);
//                        msmodel.setUnread(1);
//                        msmodel.setType(3);
//                        msmodel.setContent("【365灵活通】您的参保订单（"+order.getOrderNum()+"）资料已驳回，接下来请您在参保订单中进行缴费，有问题请联系客服0311-66686628");
//                        messageService.sendMsg(msmodel);
//                        String viptel=vipService.getTelById(order.getUid());
//                        SmsUtil.sendSms(msmodel.getContent(),viptel,null,null);
//                        sendFailureMessage(response, "驳回成功！");
                    }
        } catch (Exception e) {
            e.printStackTrace();
            sendFailureMessage(response, "操作失败！");
            return;
        }
        sendSuccessMessage(response, "操作成功~");
    }

    @RequestMapping("/auditRefuseIns.do")
    public void auditRefuseIns(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            HtmlUtil.writerJson(response, "登录超时！");
            return;
        }

        try {
            OrderModel model = new OrderModel();
            model.setId(id);
            model.setAudit(3);
            insuranceSetService.updateInsurance(model);
            Order order = insuranceSetService.queryById(model.getId());
            MessageModel msmodel=new MessageModel();
            msmodel.setUser_id(order.getUid());
            msmodel.setTitle("参保信息审核失败");
            msmodel.setCreate_time(new Date());
            msmodel.setFlag(0);
            msmodel.setUnread(1);
            msmodel.setType(3);
            msmodel.setContent("【365灵活通】您的参保订单（"+order.getOrderNum()+"）资料已驳回，接下来请您在参保订单中进行缴费，有问题请联系客服0311-66686628");
            messageService.sendMsg(msmodel);
            String viptel=vipService.getTelById(order.getUid());
            SmsUtil.sendSms(msmodel.getContent(),viptel,null,null);
            sendFailureMessage(response, "驳回成功！");
        } catch (Exception e) {
            e.printStackTrace();
            sendFailureMessage(response, "操作失败！");
        }
        sendSuccessMessage(response, "操作成功~");
    }

    @RequestMapping(value = "/insorder.do")
    public ModelAndView insorder(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        SysDeptModel model = new SysDeptModel();
        return forword("insurance/safe_order", context);
    }



    @RequestMapping("/exportSafeOrder.do")
    public void exportOrderList(OrderModel model,
                                HttpServletResponse response, HttpServletRequest request) throws Exception{
        try {
//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("orderNum", "参保订单");
            fieldMap.put("insuranceNum", "社保号");
            fieldMap.put("name", "姓名");
            fieldMap.put("identNum", "身份证号");
            fieldMap.put("tel", "手机号");
            fieldMap.put("insuranceTypeCh_zn", "参保类型");
            fieldMap.put("cycle", "参保周期");
            fieldMap.put("base", "缴纳基数");
            fieldMap.put("ratio", "养老缴费比例");
            fieldMap.put("payment", "缴费金额");
            fieldMap.put("insuranceNatureCh_zn", "用户参保标签");
            fieldMap.put("firstTime", "首次参保时间");
            fieldMap.put("registered", "户口所在地");
            fieldMap.put("auditCh_zn", "审核状态");


//	        // excel的sheetName
            String sheetName = "参保订单";
//	        // excel要导出的数据
            //model.setRows(1000);
            List<Order> dataList = insuranceSetService.insuranceDataExcel(model);
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

    @RequestMapping(value = "/safeServe.do")
    public ModelAndView safeServe(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        SysDeptModel model = new SysDeptModel();
        return forword("insurance/safe_serve", context);
    }

    @RequestMapping("/exportOrderServe.do")
    public void exportOrderServe(OrderInfoModel model,
                                HttpServletResponse response, HttpServletRequest request) throws Exception{
        try {
//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("orderNum", "参保订单");
            fieldMap.put("insuranceNum", "社保号");
            fieldMap.put("name", "姓名");
            fieldMap.put("identNum", "身份证号");
            fieldMap.put("tel", "手机号");
            fieldMap.put("inseuranceCycle", "参保周期");
            fieldMap.put("base", "缴纳基数");
            fieldMap.put("ratio", "养老缴费比例");
            fieldMap.put("unitPrice", "缴费金额");
            fieldMap.put("audit3", "状态");


//	        // excel的sheetName
            String sheetName = "参保服务";
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

    @RequestMapping(value = "/safeNote.do")
    public ModelAndView safeNote(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> context = getRootMap();
        List<Integration> list1 = integrationLogService.queryIntegration(1,1000);
        List<Integration> list2 = integrationLogService.queryIntegration(1001,10000);
        List<Integration> list3 = integrationLogService.queryIntegration(10001,20000);
        List<Integration> list4 = integrationLogService.queryIntegration(20001,100000000);
        context.put("integral1",list1==null?0:list1.size());
        context.put("integral2",list2==null?0:list2.size());
        context.put("integral3",list3==null?0:list3.size());
        context.put("integral4",list4==null?0:list4.size());
        return forword("insurance/safe_note", context);
    }

    @RequestMapping("/getSmsTempalelist.do")
    public void getSmsTempalelist(SmsTemplateModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        List<SmsTemplate> sList = smsTemplateService.queryByList(model);

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", sList);
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/enableSms.do")
    public void enableSms(Integer id,Integer status, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg","操作失败！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        smsTemplateService.updateEnable(id,status);

        jsonMap.put("msg","操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/payOrder.do")
    public void payOrder(String info_ids,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            setSuccessMap(jsonMap, "登录超时！", null);
            return;
        }
        if(info_ids==null||info_ids.equals("")){
            setSuccessMap(jsonMap, "不能为空！", null);
            return;
        }
        try {
            String[] info_idss = info_ids.split(",");

            for (String info_id : info_idss){
                OrderInfoModel infoModel = new OrderInfoModel();
                infoModel.setId(Integer.parseInt(info_id));

                //判断是否可退保
                OrderInfo info = insuranceService.queryByInfoId(Integer.parseInt(info_id));
                if(info!=null&&info.getOrderNum()!=null){
                    Order order = insuranceService.queryByOrderNumber(info.getOrderNum());
                    if(order!=null&&order.getAudit()!=null&&order.getAudit()!=6){
                        jsonMap.put("msg","不能缴纳退保中的保单！！");
                        HtmlUtil.writerJson(response, jsonMap);
                        return;
                    }
                }
                if(user.getJid()==2){
                    if(info!=null){
                        if(info.getIsquit()==1){
                            jsonMap.put("msg","退保之后不能缴纳！");
                            HtmlUtil.writerJson(response, jsonMap);
                            return;
                        }
                        String now = DateUtil.getFomartDate(new Date(),"yyyy/MM");
                        if(info.getStatus()==1){

                            int compre = DateUtil.compareDateYM(info.getInseuranceCycle(),now);
                            if(compre>0){
                                jsonMap.put("msg","不能缴纳当月之后的保单！！");
                                HtmlUtil.writerJson(response, jsonMap);
                                return;
                            }
                        }else if(info.getStatus()==2){
                            int compre = DateUtil.compareDateYM(info.getInsuEnd(),now);
                            if(compre>0){
                                jsonMap.put("msg","不能缴纳当月之后的保单！！");
                                HtmlUtil.writerJson(response, jsonMap);
                                return;
                            }
                        }
                    }
                //不能重复提交
               /* if(user.getJid()==2){
                    if(info!=null&&info.getAudit()==31){
                        jsonMap.put("msg","该月社保已缴纳，不能重复缴纳！！");
                        HtmlUtil.writerJson(response, jsonMap);
                        return;
                    }
                }else */
                    if(info!=null&&info.getAudit()==11){

                        jsonMap.put("msg","该月社保已缴纳，不能重复缴纳！！");
                        HtmlUtil.writerJson(response, jsonMap);
                        return;
                    }
                }else{
                    jsonMap.put("msg","您没有操作权限！");
                    HtmlUtil.writerJson(response, jsonMap);
                    return;
                }
                //只能缴纳当月的社保
                /*if(info!=null&&info.getInseuranceCycle()!=null&&info.getInseuranceCycle().equals("")){
                    String inscycle = info.getInseuranceCycle();

                    int bmonth = DateUtil.getMonth(inscycle,DateUtil.getFomartDate(new Date(),"yyyy-MM"));
                    if(bmonth!=0){

                        jsonMap.put("msg","包含非当月社保,请重新选择！");
                        HtmlUtil.writerJson(response, jsonMap);
                        return;
                    }
                }*/

            }
            for (String info_id : info_idss){
                OrderInfoModel model = new OrderInfoModel();
                model.setId(Integer.parseInt(info_id));
                /*if(user.getJid()==4){
                    model.setAudit(11);
                    model.setAudit1(21);
                }else */if(user.getJid()==2) {
                    model.setAudit(11);
                    //model.setAudit1(41);
                }
                model.setAuditTime(DateUtil.getCurrDateTime());
                insuranceService.upAudit(model);
            }
            jsonMap.put("msg","申请缴纳已提交，请等待审核！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }catch (Exception e){
            e.printStackTrace();
            jsonMap.put("msg","申请缴纳失败！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }

    }

    @RequestMapping("/payOrderAll.do")
    public void payOrderAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            setSuccessMap(jsonMap, "登录超时！", null);
            return;
        }

        try {

            OrderInfoModel model = new OrderInfoModel();
            model.setInseuranceCycle(DateUtil.getFomartDate(new Date(),"yyyy/MM"));
            model.setDeptId(user.getDeptId());
            /*if(user.getJid()==4){
                model.setAudit(11);
                model.setAudit1(21);
            }else*/ if(user.getJid()==2){
                model.setAudit1(6);
                model.setAudit(103);
                //model.setAudit1(31);
            }else{
                jsonMap.put("msg","您没有操作权限！");
                HtmlUtil.writerJson(response, jsonMap);
                return;
            }
            List<OrderInfo> infoList = insuranceService.getinsulist1(model);
            if(infoList==null||infoList.size()==0){
                jsonMap.put("msg","当月没有需要缴纳的订单！");
                HtmlUtil.writerJson(response, jsonMap);
                return;
            }

            for (OrderInfo info : infoList){

                if(info!=null){
                    if(user.getJid()==2){
                        if(info.getIsquit()==1){
                            jsonMap.put("msg",info.getOrderNum()+" 退保之后不能缴纳！");
                            HtmlUtil.writerJson(response, jsonMap);
                            return;
                        }
                        String now = DateUtil.getFomartDate(new Date(),"yyyy/MM");
                        if(info.getStatus()==1){

                            int compre = DateUtil.compareDateYM(info.getInseuranceCycle(),now);
                            if(compre>0){
                                jsonMap.put("msg","不能缴纳当月之后的保单！！");
                                HtmlUtil.writerJson(response, jsonMap);
                                return;
                            }
                        }else if(info.getStatus()==2){
                            int compre = DateUtil.compareDateYM(info.getInsuEnd(),now);
                            if(compre>0){
                                jsonMap.put("msg","不能缴纳当月之后的保单！！");
                                HtmlUtil.writerJson(response, jsonMap);
                                return;
                            }
                        }

                        if(info!=null&&info.getAudit()==11){

                            jsonMap.put("msg","该月社保已缴纳，不能重复缴纳！！");
                            HtmlUtil.writerJson(response, jsonMap);
                            return;
                        }
                    }else{
                        jsonMap.put("msg","您没有操作权限！");
                        HtmlUtil.writerJson(response, jsonMap);
                        return;
                    }
                }
            }

            for (OrderInfo info : infoList){

                OrderInfoModel infoModel = new OrderInfoModel();
                infoModel.setId(info.getId());
                /*if(user.getJid()==2){
                    infoModel.setAudit(11);
                    infoModel.setAudit1(21);
                }else*/ if(user.getJid()==2) {
                    infoModel.setAudit(11);
                    //infoModel.setAudit1(41);
                }
                infoModel.setAuditTime(DateUtil.getCurrDateTime());
                insuranceService.upAudit(infoModel);

            }

            jsonMap.put("msg","申请缴纳已提交，请等待审核！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }catch (Exception e){
            e.printStackTrace();
            jsonMap.put("msg","申请缴纳失败！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }

    }
    @RequestMapping("/payOrderAllShow.do")
    public void payOrderAllShow(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg","登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        try {
            OrderInfoModel model = new OrderInfoModel();
            model.setInseuranceCycle(DateUtil.getFomartDate(new Date(),"yyyy/MM"));
            model.setDeptId(user.getDeptId());

            /*if(user.getJid()==4){
                model.setAudit(21);
                model.setAudit1(31);
            }else*/ if(user.getJid()==2){
                model.setAudit(103);
                //model.setAudit1(41);
            }else{
                jsonMap.put("msg","您没有操作权限！");
                jsonMap.put("result",1);
                HtmlUtil.writerJson(response, jsonMap);
                return;
            }
            Integer count = insuranceService.countinsulist1(model);
            model.setInsuranceType(1);
            Integer ylcount = insuranceService.countinsulist1(model);
            model.setInsuranceType(2);
            Integer zlcount = insuranceService.countinsulist1(model);
            jsonMap.put("count",count);
            jsonMap.put("ylcount",ylcount);
            jsonMap.put("ybcount",zlcount);
            jsonMap.put("ny",DateUtil.getFomartDate(new Date(),"yyyy年MM月"));
            jsonMap.put("ny1",DateUtil.getFomartDate(new Date(),"yyyy年MM月"));
            jsonMap.put("msg","操作失败！");
            HtmlUtil.writerJson(response, jsonMap);
        }catch (Exception e){
            e.printStackTrace();
            jsonMap.put("msg","操作失败！");
            HtmlUtil.writerJson(response, jsonMap);
        }

    }
    @RequestMapping("/uploadImgUrl")
    public void uploadImgUrl(@RequestParam("file1") MultipartFile file1,
                             Integer uid,Integer id, HttpServletResponse response,
                             HttpServletRequest request) throws Exception {
        try {
            String imgeArray = ".BMP,.DIB,.GIF,.JFIF,.JPE,.JPEG,.JPG,.PNG,.TIF,.TIFF,.ICO";
            String type1 = file1!=null && !StringUtils.isBlank(file1.getOriginalFilename())?file1.getOriginalFilename().substring(
                    file1.getOriginalFilename().lastIndexOf(".")):"";
            String parhstr=request.getSession().getServletContext()
                    .getRealPath(File.separator);
            parhstr=parhstr.substring(0,parhstr.length()-5);
            if(type1.equals("")){
                sendSuccessMessage(response, "请选择图片！");
                return;
            }
            if ((!type1.equals("") && imgeArray.indexOf(type1.toUpperCase()) < 0)) {
                sendSuccessMessage(response, "上传文件不是图片格式,请重新上传！");
                return;
            }
            if ((file1!=null && file1.getSize() > 10485760)) {
                sendSuccessMessage(response, "上传图片不能大于10M,请重新上传！");
                return;
            }
            String sjc = "";
            String path = "";
            SysInsuranceModel model=new SysInsuranceModel();
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
                model.setMedicalUrl(UserConstants.CRMURL + "aptitude/"+ sjc + type1);
            }

            model.setVip_id(uid);
            Order o = insuranceSetService.queryById(id);
            model.setDeptId(o.getDeptId());
            sysInsuranceService.updateSysIns(model);
        } catch (Exception e) {
            e.printStackTrace();
            sendSuccessMessage(response, "上传失败~");
        }
        sendSuccessMessage(response, "上传成功~");
    }

    @RequestMapping("/quitOrder.do")
    public void quitOrder(String orderNum,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg","登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        try {
            Order order = insuranceService.queryByOrderNumber(orderNum);
            if(order!=null){/*
                                <li><label>应退金额</label><em id="ytje"></em></li>
                <li><label>应退积分</label><em id="ytjf"></em></li>
                <li class="text-danger">该用户的积分余额不足已本次扣除，所以剩余积分需要用现金抵扣</li>
                <li><label>用户可退积分</label><em id="syjf"></em></li>
                <li><label>差额积分</label><em id="cejf"></em></li>
                <li><label>现金抵扣积分</label><em id="tkjf"></em></li>
                <li><label>实退金额</label><em id="stjf"></em></li>*/

                List<OrderInfo> infoList = insuranceService.queryByOrderNumList(order.getOrderNum());
                int month = 0;// DateUtil.getMonth(DateUtil.getFomartDate(new Date(),"yyyy/MM"),order.getInsuStart());
                //int sumMonth = DateUtil.getMonth(order.getInsuStart(),order.getInsuEnd())+1;
                if(infoList!=null&&infoList.size()>0){
                    for (OrderInfo info : infoList){
                        if(info.getStatus()==1){
                            month++;
                        }else if(info.getStatus()==2){
                            month = month + DateUtil.getMonth(info.getInsuStart(),info.getInsuEnd())+1;
                        }
                    }
                }

                Integration ing = integrationLogService.queryByIntUid(order.getUid());

                int mon = DateUtil.getMonth(order.getInsuStart(),order.getInsuEnd())+1;


                int x = 0;
                if(mon>=6){
                    x = 1;
                }

                Double ytje_ = month*Double.parseDouble(order.getPayment())/mon;
                BigDecimal bgytje = new BigDecimal(ytje_);
                double ytje = bgytje.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                jsonMap.put("ytje",ytje);

                int ytjf = (int)(ytje*x);
                jsonMap.put("ytjf",ytjf);

                jsonMap.put("syjf",ing.getIntegration());

                int cejf_ = order.getIntegration()-ytjf;
                if(cejf_<0){
                    int cejf = ytjf-order.getIntegration();
                    jsonMap.put("is_have",1);
                    jsonMap.put("cejf",cejf);

                    BigDecimal tkjf_ = new BigDecimal(cejf*0.01);
                    double tkjf = tkjf_.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    jsonMap.put("tkjf",tkjf);

                    jsonMap.put("stjf",ytje-tkjf);
                }else{
                    jsonMap.put("is_have",0);
                }

            }
            jsonMap.put("msg","操作成功！");
            HtmlUtil.writerJson(response, jsonMap);
        }catch (Exception e){
            e.printStackTrace();
            jsonMap.put("msg","操作失败！");
            HtmlUtil.writerJson(response, jsonMap);
        }

    }

    @RequestMapping("/tuibao.do")
    public void tuibao1(String orderNum,Integer uid,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg","登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        try {
            Order order =  insuranceService.queryByOrderNumber(orderNum);
            if(order!=null&&order.getAudit()!=6){
                jsonMap.put("msg","该保单不能退保！");
                HtmlUtil.writerJson(response, jsonMap);
                return;
            }
            //1.订单退保
            OrderModel model = new OrderModel();
            model.setAudit(7);
            model.setOrderNum(orderNum);
            model.setUid(order.getUid());

            QuitModel qmodel = new QuitModel();
            qmodel.setUid(order.getUid());
            qmodel.setOrderNum(orderNum);
            qmodel.setInsuranceType(order.getInsuranceType());
            qmodel.setQuitType(1);
            List<OrderInfo> infoList = insuranceService.queryByOrderNumList(order.getOrderNum());
            int month = 0;
            String quitMonth="";
            if(infoList!=null&&infoList.size()>0){
                for (OrderInfo info : infoList){
                    if(info.getAudit()==11||info.getAudit()==21){
                        jsonMap.put("msg","该保单有月份缴费审核中，不能退保！");
                        HtmlUtil.writerJson(response, jsonMap);
                        return;
                    }
                    if(info.getStatus()==1){
                        month++;
                        if(quitMonth==null || quitMonth.equals("")){
                            quitMonth+=info.getInseuranceCycle();
                        }else{
                            quitMonth+=","+info.getInseuranceCycle();
                        }
                    }else if(info.getStatus()==2){
                        month = month + DateUtil.getMonth(info.getInsuStart(),info.getInsuEnd())+1;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");//格式化为年月
                        Calendar min = Calendar.getInstance();
                        Calendar max = Calendar.getInstance();
                        min.setTime(sdf.parse(info.getInsuStart()));
                        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
                        max.setTime(sdf.parse(info.getInsuEnd()));
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
                }
            }
            qmodel.setQuitMonth(quitMonth);


            Integration ing = integrationLogService.queryByIntUid(order.getUid());

            int mon = DateUtil.getMonth(order.getInsuStart(),order.getInsuEnd())+1;

            int x = 0;
            if(mon>=6){
                x = 1;
            }

            Double ytje_ = month*Double.parseDouble(order.getPayment())/mon;
            BigDecimal bgytje = new BigDecimal(ytje_);
            double ytje = bgytje.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            qmodel.setQuit_payment(ytje);

            int ytjf = (int)(ytje*x);
            qmodel.setQuit_integration(ytjf);
            qmodel.setMonthNum(month);

            int cejf_ = ing.getIntegration()-ytjf;

            if(cejf_<0){//如果积分不够
                int cejf = ytjf-order.getIntegration();
                qmodel.setIntegration_diff(cejf);
                //积分差额  单位（元）
                BigDecimal tkjf_ = new BigDecimal(cejf*0.01);
                double tkjf = tkjf_.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                qmodel.setPayment_inte(tkjf);
                qmodel.setPra_payment(ytje-tkjf);

            }else{
                qmodel.setIntegration_diff(0);
                qmodel.setPayment_inte(0.0);
                qmodel.setPra_payment(ytje);
            }

            qmodel.setSubTime(DateUtil.getCurrDateTime());
            qmodel.setAudit(11);
            qmodel.setDeptId(order.getDeptId());

            Integer quit_id = insuranceService.addQuit(qmodel);
            insuranceService.upAuditByOrderNum(model);

            //修改积分
            String time=DateUtil.getNowPlusTime();
            Integer qi=qmodel.getQuit_integration()==null?0:qmodel.getQuit_integration();
            Integer idf=qmodel.getIntegration_diff()==null?0:qmodel.getIntegration_diff();
            integrationLogService.updateIntegration(-(qi-idf),model.getUid(),time);
            if(qi>0){
                IntegrationModel model2=new IntegrationModel();
                model2.setAddTime(time);
                model2.setUid(model.getUid());
                model2.setIntegration(qi);
                model2.setType(3);
                model2.setQuit_id(quit_id);
                integrationLogService.addInLog(model2);
                if(idf>0){
                    IntegrationModel model3=new IntegrationModel();
                    model3.setAddTime(time);
                    model3.setUid(model.getUid());
                    model3.setIntegration(idf);
                    model3.setType(4);
                    model3.setQuit_id(quit_id);
                    integrationLogService.addInLog(model3);
                }
            }

            jsonMap.put("msg","操作成功！");
            HtmlUtil.writerJson(response, jsonMap);
        }catch (Exception e){
            e.printStackTrace();
            jsonMap.put("msg","操作失败！");
            HtmlUtil.writerJson(response, jsonMap);
        }
    }

   /* @RequestMapping("/.do")
    public void payOrderAllShow(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg","登录超时！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        try {
            OrderInfoModel model = new OrderInfoModel();
            model.setInseuranceCycle(DateUtil.getFomartDate(new Date(),"yyyy/MM"));
            model.setDeptId(user.getDeptId());

            *//*if(user.getJid()==4){
                model.setAudit(21);
                model.setAudit1(31);
            }else*//* if(user.getJid()==2){
                model.setAudit(103);
                //model.setAudit1(41);
            }else{
                jsonMap.put("msg","您没有操作权限！");
                jsonMap.put("result",1);
                HtmlUtil.writerJson(response, jsonMap);
                return;
            }
            Integer count = insuranceService.countinsulist1(model);
            model.setInsuranceType(1);
            Integer ylcount = insuranceService.countinsulist1(model);
            model.setInsuranceType(2);
            Integer zlcount = insuranceService.countinsulist1(model);
            jsonMap.put("count",count);
            jsonMap.put("ylcount",ylcount);
            jsonMap.put("ybcount",zlcount);
            jsonMap.put("ny",DateUtil.getFomartDate(new Date(),"yyyy年MM月"));
            jsonMap.put("ny1",DateUtil.getFomartDate(new Date(),"yyyy年MM月"));
            jsonMap.put("msg","操作失败！");
            HtmlUtil.writerJson(response, jsonMap);
        }catch (Exception e){
            e.printStackTrace();
            jsonMap.put("msg","操作失败！");
            HtmlUtil.writerJson(response, jsonMap);
        }

    }*/

    @RequestMapping("/sendSms1.do")
    public void sendSms1(Integer sendMonth,String sendDay,String sendTime,
                         HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg","操作失败！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        OrderInfoModel model = new OrderInfoModel();
        model.setAudit(1);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, sendMonth);
        smsTemplateService.updateSendTime(8,DateUtil.fromStringToDate("yyyy-MM-dd",
                DateUtil.getFomartDate(cal.getTime(),"yyyy-MM")+"-"+sendDay+" "+sendTime));
/*        model.setInseuranceCycle(DateUtil.getFomartDate(cal.getTime(),"yyyy/MM"));
        model.setInsuEnd(DateUtil.getFomartDate(cal.getTime(),"yyyy/MM"));
        List<OrderInfoModel> olist = insuranceSetService.insuranceList(model);
        for (OrderInfoModel om : olist){
            if(om.getStatus() == 1){
                SmsUtil.sendSms("【365灵活通】尊敬的"+om.getName()+"：您的养老保险将于"+om.getInseuranceCycle()+"到期。如您需继续办理，" +
                        "可在365灵活通小程序继续参保，如有疑问请联系电话0311-66686628",om.getTel(),null,null);
            }else if(om.getStatus() == 2){
                SmsUtil.sendSms("【365灵活通】尊敬的"+om.getName()+"：您的养老保险将于"+om.getInsuEnd()+"到期。如您需继续办理，" +
                        "可在365灵活通小程序继续参保，如有疑问请联系电话0311-66686628",om.getTel(),null,null);
            }
        }*/

        jsonMap.put("msg","操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }
    @RequestMapping("/sendSms2.do")
    public void sendSms2(Integer integral,
                         HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object>  jsonMap = new HashMap<String ,Object>();
        SysUser user = SessionUtils.getUser(request);
        if(user==null){
            jsonMap.put("msg","操作失败！");
            HtmlUtil.writerJson(response, jsonMap);
            return;
        }
        List<Integration> list = new ArrayList<Integration>();
        String tels = "";
        if(integral>=1&&integral<=1000){
            list = integrationLogService.queryIntegration(1,integral);
            for(Integration intg : list){
                tels += intg.getTel();
            }
        }else if(integral>=1001&&integral<=10000){
            list = integrationLogService.queryIntegration(1001,integral);
            for(Integration intg : list){
                tels += intg.getTel();
            }
        }else if(integral>=10001&&integral<=20000){
            list = integrationLogService.queryIntegration(10001,integral);

            for(Integration intg : list){
                tels += intg.getTel();
            }

        }else if(integral>=20001){
            list = integrationLogService.queryIntegration(20001,100000000);

            for(Integration intg : list){
                tels += intg.getTel();
            }
        }
        SmsUtil.sendSms("【365灵活通】尊敬的用户，最近上架了很多新的商品，快去兑换吧。",tels,null,null);
        jsonMap.put("msg","操作成功！");
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/updateInsInfo")
    public void updateInsInfo(Integer id,Integer uid,VipModel vipModel,
                              HttpServletRequest request,HttpServletResponse response)throws Exception{
        try {
            Order orr = null;
            if(id!=null){
                orr = insuranceSetService.queryById(id);
            }
            vipModel.setId(orr.getUid());
            if(vipModel.getDeptId()==null){
                if(vipModel.getCity()!=null){
                    if(vipModel.getProvince()!=null){
                        vipModel.setDeptId(vipModel.getProvince());
                    }
                }else{
                    vipModel.setDeptId(vipModel.getCity());
                }
            }
            vipService.updateVip(vipModel);
            //修改养老医疗
            //if(type!=3){
                List<SysInsurance> si = insuranceService.getYBKById(vipModel.getId(),orr.getDeptId());
                SysInsuranceModel sysInsModel = new SysInsuranceModel();
                if(si!=null&&si.size()>0){
                    if(orr.getInsuranceType()==1){
                        sysInsModel.setVip_id(orr.getUid());
                        sysInsModel.setInsuranceNum(vipModel.getInsuranceNum());
                        sysInsModel.setDeptId(orr.getDeptId());
                    }else if(orr.getInsuranceType()==2){
                        sysInsModel.setVip_id(orr.getUid());
                        sysInsModel.setBank(vipModel.getBank());
                        sysInsModel.setBankNum(vipModel.getBankNum());
                        sysInsModel.setDeptId(orr.getDeptId());
                    }
                    sysInsuranceService.updateSysIns(sysInsModel);
                }else{
                    if(orr.getInsuranceType()==1){
                        sysInsModel.setVip_id(orr.getUid());
                        sysInsModel.setInsuranceNum(vipModel.getInsuranceNum());
                        sysInsModel.setDeptId(orr.getDeptId());
                    }else if(orr.getInsuranceType()==2){
                        sysInsModel.setVip_id(orr.getUid());
                        sysInsModel.setBank(vipModel.getBank());
                        sysInsModel.setBankNum(vipModel.getBankNum());
                        sysInsModel.setDeptId(orr.getDeptId());
                    }
                    sysInsuranceService.addSysIns(sysInsModel);
                }
            //}
        }catch (Exception e){
            e.printStackTrace();
            sendFailureMessage(response,"操作失败！");
        }

        sendSuccessMessage(response,"操作失败！");
    }

    @RequestMapping("/updateRemarks")
    public void updateRemarks(Integer id,String remarks,
                              HttpServletRequest request,HttpServletResponse response)throws Exception{
        try {
            OrderModel model = new OrderModel();
            model.setId(id);
            model.setRemarks(remarks);
            insuranceSetService.updateInsurance(model);
        }catch (Exception e){
            e.printStackTrace();
            sendFailureMessage(response,"操作失败！");
        }

        sendSuccessMessage(response,"操作成功！");
    }
}
