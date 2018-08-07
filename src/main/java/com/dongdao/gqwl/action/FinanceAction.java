package com.dongdao.gqwl.action;

import com.dongdao.gqwl.bean.Order;
import com.dongdao.gqwl.bean.OrderInfo;
import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.OrderInfoModel;
import com.dongdao.gqwl.model.OrderModel;
import com.dongdao.gqwl.model.QuitModel;
import com.dongdao.gqwl.utils.DateUtil;
import com.dongdao.gqwl.utils.ExcelUtil;
import com.dongdao.gqwl.utils.HtmlUtil;
import com.dongdao.gqwl.utils.SessionUtils;
import com.dongdao.gqwl.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/finance")
public class FinanceAction extends BaseAction {
    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public FinanceService financeService;

    @RequestMapping(value = "/recharge.do")
    public String recharge(HttpServletRequest request, HttpServletResponse response) {
        return "finance/recharge";
    }
    @RequestMapping(value = "/balance.do")
    public String balance(HttpServletRequest request, HttpServletResponse response) {
        return "finance/balance";
    }

    @RequestMapping("/countOrderlist")
    public void countOrderlist(OrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        Integer count=0;
        count= financeService.countOrderlist(model);
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

    @RequestMapping("/getOrderlist")
    public void getOrderlist(OrderModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
        List<Order> dataList=financeService.getOrderlist(model);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/getCountPayment")
    public void getCountPayment(HttpServletRequest request, HttpServletResponse response) throws Exception{
        OrderInfoModel model=new OrderInfoModel();
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
        /*月*/
        String date= DateUtil.getNowFormateDate();
        date=date.replace("-", "/");
        String month=date.substring(0,7);
        String year=date.substring(0,4);
        model.setInseuranceCycle(month);
        model.setStatus(1);
        //参保
        List<Map> mlist1=financeService.CountCPayment(model);
        //补缴
        List<Map> mlist2=financeService.CountBPayment1(model);
        Double ml=0.0;
        Double my=0.0;
        Double mz=0.0;
        ml=(mlist1!=null && mlist1.size()>0?Double.parseDouble(mlist1.get(0).get("payment")+""):0.0)
                +(mlist2!=null && mlist2.size()>0?Double.parseDouble(mlist2.get(0).get("unitPrice")+""):0.0);
        if(mlist1!=null && mlist1.size()>0){
            for(int i=0;i<mlist1.size();i++){
                if(mlist1.get(i).get("insuranceType").equals(1)){
                    ml+=mlist1.get(i).get("payment")==null?0.0:Double.parseDouble(mlist1.get(i).get("payment")+"");
                }else if(mlist1.get(i).get("insuranceType").equals(2)){
                    my+=mlist1.get(i).get("payment")==null?0.0:Double.parseDouble(mlist1.get(i).get("payment")+"");
                }
            }
        }
        if(mlist2!=null && mlist2.size()>0){
            for(int i=0;i<mlist2.size();i++){
                if(mlist2.get(i).get("insuranceType").equals(1)){
                    ml+=mlist2.get(i).get("unitPrice")==null?0.0:Double.parseDouble(mlist2.get(i).get("unitPrice")+"");
                }else if(mlist2.get(i).get("insuranceType").equals(2)){
                    my+=mlist2.get(i).get("unitPrice")==null?0.0:Double.parseDouble(mlist2.get(i).get("unitPrice")+"");
                }
            }
        }
        mz=ml+my;
        /*年*/
        //参保
        model=new OrderInfoModel();
        model.setStatus(1);
        model.setInsuStart(year);
        List<Map> ylist1=financeService.CountCPayment(model);
        //补缴
        Double bl=0.0;
        Double by=0.0;
        List<OrderInfo> ylist2=financeService.CountBPayment2(model);
        if(ylist2!=null && ylist2.size()>0){
            for(int i=0;i<ylist2.size();i++){
                OrderInfo f1=ylist2.get(i);
                if(f1.getInsuStart().substring(0,4).equals(year)){
                    if(f1.getInsuEnd().substring(0,4).equals(year)){
                        bl+=f1.getInsuranceType()==1?f1.getPayment():0.0;
                        by+=f1.getInsuranceType()==2?f1.getPayment():0.0;
                    }else{
                        Integer mn=Integer.parseInt(f1.getInsuStart().substring(5,7));
                        bl+=(f1.getInsuranceType()==1?f1.getUnitPrice():0.0)*(12-mn+1);
                        by+=(f1.getInsuranceType()==2?f1.getUnitPrice():0.0)*(12-mn+1);
                    }
                }else{
                    Integer mn=Integer.parseInt(f1.getInsuEnd().substring(5,7));
                    bl+=(f1.getInsuranceType()==1?f1.getUnitPrice():0.0)*mn;
                    by+=(f1.getInsuranceType()==2?f1.getUnitPrice():0.0)*mn;
                }
            }
        }
        Double yl=0.0;
        Double yy=0.0;
        Double yz=0.0;
        yl=(ylist1!=null && ylist1.size()>0?Double.parseDouble(ylist1.get(0).get("payment")+""):0.0)+bl;
        if(ylist1!=null && ylist1.size()>=2){
            yy=(ylist1!=null?Double.parseDouble(ylist1.get(1).get("payment")+""):0.0)+by;
        }else{
            yy=by;
        }
        yz=yl+yy;
        /*全*/
        model=new OrderInfoModel();
        List<Map> qlist1=financeService.CountCPayment(model);
        Double ql=0.0;
        Double qy=0.0;
        Double qz=0.0;
        ql=qlist1!=null && qlist1.size()>0?Double.parseDouble(qlist1.get(0).get("payment")+""):0.0;
        qy=qlist1!=null && qlist1.size()>1?Double.parseDouble(qlist1.get(1).get("payment")+""):0.0;
        qz=ql+qy;

        /* 积分*/
        Integer ye=0;
        Integer xf=0;
        Integer th=0;
        Integer zf=0;
        ye=financeService.countInye();
        xf=financeService.countInLog(2);
        th=financeService.countInLog(3);
        ye=ye==null?0:ye;
        xf=xf==null?0:xf;
        th=th==null?0:th;
        zf=ye+xf+th;
        /*退保金额*/
        Double mlq=0.0;
        Double myq=0.0;
        Double mzq=0.0;
        Double qlq=0.0;
        Double qyq=0.0;
        Double qzq=0.0;
        Double plq=0.0;
        Double pyq=0.0;
        Double pq=0.0;
        Double lcz=0.0;
        Double ycz=0.0;
        Double zcz=0.0;
        QuitModel qm=new QuitModel();
        String subtime1=DateUtil.thisMonth();
        String subtime2=DateUtil.thisMonthEnd();
        qm.setSubTime1(subtime1+" 00:00:00");
        qm.setSubTime2(subtime2+" 23:59:59");
        List<Map> quitlist=financeService.countQuit(qm);
        if(quitlist!=null && quitlist.size()>0){
            for(int i=0;i<quitlist.size();i++){
                if(quitlist.get(i).get("insuranceType").equals(1)){
                    mlq+=quitlist.get(i).get("pra_payment")==null?0.0:Double.parseDouble(quitlist.get(i).get("pra_payment")+"");
                }else if(quitlist.get(i).get("insuranceType").equals(2)){
                    myq+=quitlist.get(i).get("pra_payment")==null?0.0:Double.parseDouble(quitlist.get(i).get("pra_payment")+"");
                }
            }
        }
        mzq=mlq+myq;
        qm=new QuitModel();
        List<Map> quitlist1=financeService.countQuit(qm);
        if(quitlist1!=null && quitlist1.size()>0){
            for(int i=0;i<quitlist1.size();i++){
                if(quitlist1.get(i).get("insuranceType").equals(1)){
                    qlq+=quitlist1.get(i).get("pra_payment")==null?0.0:Double.parseDouble(quitlist1.get(i).get("pra_payment")+"");
                }else if(quitlist1.get(i).get("insuranceType").equals(2)){
                    qyq+=quitlist1.get(i).get("pra_payment")==null?0.0:Double.parseDouble(quitlist1.get(i).get("pra_payment")+"");
                }
            }
        }
        qzq=qlq+qyq;
        List<Map> quitlist2=financeService.countQuit1(qm);
        if(quitlist2!=null && quitlist2.size()>0){
            for(int i=0;i<quitlist2.size();i++){
                if(quitlist2.get(i).get("insuranceType").equals(1)){
                    plq+=quitlist2.get(i).get("payment_inte")==null?0.0:Double.parseDouble(quitlist2.get(i).get("payment_inte")+"");
                }else if(quitlist2.get(i).get("insuranceType").equals(2)){
                    pyq+=quitlist2.get(i).get("payment_inte")==null?0.0:Double.parseDouble(quitlist2.get(i).get("payment_inte")+"");
                }
            }
        }
        pq=plq+pyq;
        lcz=ql+qlq+plq;
        ycz=qy+qyq+pyq;
        zcz=lcz+ycz;
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("ml", ml);
        jsonMap.put("my", my);
        jsonMap.put("mz", mz);
        jsonMap.put("yl", yl);
        jsonMap.put("yy", yy);
        jsonMap.put("yz", yz);
        jsonMap.put("ql", ql);
        jsonMap.put("qy", qy);
        jsonMap.put("qz", qz);
        jsonMap.put("ye", ye);
        jsonMap.put("xf", xf);
        jsonMap.put("th", th);
        jsonMap.put("zf", zf);
        jsonMap.put("mlq", mlq);
        jsonMap.put("myq", myq);
        jsonMap.put("mzq", mzq);
        jsonMap.put("qlq", qlq);
        jsonMap.put("qyq", qyq);
        jsonMap.put("qzq", qzq);
        jsonMap.put("pq", pq);
        jsonMap.put("lcz", lcz);
        jsonMap.put("ycz", ycz);
        jsonMap.put("zcz", zcz);
        HtmlUtil.writerJson(response, jsonMap);
    }
    /**
     * 导出数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportList.do")
    public void exportList(OrderModel model, HttpServletResponse response, HttpServletRequest request) throws Exception{
        try {
            // 获取前台传来的题型和课程

//	        // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("orderNum", "参保订单");
            fieldMap.put("name", "用户姓名");
            fieldMap.put("tel", "手机号");
            fieldMap.put("inTypeStr", "参保类型");
            fieldMap.put("insuStart", "缴费区间");
            fieldMap.put("payment", "充值金额");
            fieldMap.put("poundage", "手续费");
            fieldMap.put("rechargeTime", "充值时间");

//	        // excel的sheetName
            String sheetName = "充值列表";
//	        // excel要导出的数据
            //model.setRows(1000);
            List<Order> dataList = financeService.queryByExcel(model);
            // 导出
            if (dataList == null || dataList.size() == 0) {
                sendFailureMessage(response,"暂无数据");
                return;
            }else {
                //将list集合转化为excle
                ExcelUtil.listToExcel(dataList, fieldMap, sheetName, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
