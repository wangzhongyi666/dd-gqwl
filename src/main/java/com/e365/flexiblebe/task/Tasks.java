package com.e365.flexiblebe.task;

import com.e365.flexiblebe.bean.RedeemCode;
import com.e365.flexiblebe.bean.SmsTemplate;
import com.e365.flexiblebe.model.OrderInfoModel;
import com.e365.flexiblebe.model.RedeemCodeModel;
import com.e365.flexiblebe.model.SmsTemplateModel;
import com.e365.flexiblebe.service.InsuranceSetService;
import com.e365.flexiblebe.service.RedeemCodeService;
import com.e365.flexiblebe.service.SmsTemplateService;
import com.e365.flexiblebe.utils.DateUtil;
import com.e365.flexiblebe.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class Tasks {

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public RedeemCodeService redeemCodeService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public SmsTemplateService smsTemplateService;

    @Autowired(required = false)
    // 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    public InsuranceSetService insuranceSetService;

    @Scheduled(cron="0 0 0 * * ?")
    public void termOfValidityEnd() throws Exception{
        List<RedeemCode> rLists = redeemCodeService.queryByTermOfValidityEnd(1);
        for (RedeemCode r : rLists){
            RedeemCodeModel model = new RedeemCodeModel();
            model.setStatus(4);
            model.setRedeem_code(r.getRedeem_code());
            redeemCodeService.updateRedmmCode(model);
        }

        List<RedeemCode> rLists1 = redeemCodeService.queryByTermOfValidityEnd(2);
        for (RedeemCode r : rLists1){
            RedeemCodeModel model = new RedeemCodeModel();
            model.setStatus(4);
            model.setRedeem_code(r.getRedeem_code());
            redeemCodeService.updateRedmmCode(model);
        }
    }

    //定时推送短信
    @Scheduled(cron="0 0 0 * * ?")
    public void sendSms() throws Exception{
        SmsTemplateModel model = new SmsTemplateModel();
        model.setState(1);
        List<SmsTemplate> lists = smsTemplateService.queryByList(model);
        if(lists!=null&&lists.size()==0){
            System.out.println("没有需要发送的用户！");
            return;
        }

        OrderInfoModel omodel = new OrderInfoModel();
        omodel.setAudit(1);
        omodel.setInseuranceCycle(DateUtil.getFomartDate(lists.get(0).getSend_time(),"yyyy/MM"));
        omodel.setInsuEnd(DateUtil.getFomartDate(lists.get(0).getSend_time(),"yyyy/MM"));
        List<OrderInfoModel> olist = insuranceSetService.insuranceList(omodel);
        for (OrderInfoModel om : olist){
            if(om.getStatus() == 1){
                if(om.getInsuranceType()==1){
                    SmsUtil.sendSms("【365灵活通】尊敬的"+om.getName()+"：您的养老保险将于"+om.getInseuranceCycle()+"到期。如您需继续办理，" +
                            "可在365灵活通小程序继续参保，如有疑问请联系电话0311-66686628",om.getTel(),null,null);
                }else{
                    SmsUtil.sendSms("【365灵活通】尊敬的"+om.getName()+"：您的医疗保险将于"+om.getInseuranceCycle()+"到期。如您需继续办理，" +
                            "可在365灵活通小程序继续参保，如有疑问请联系电话0311-66686628",om.getTel(),null,null);
                }
            }else if(om.getStatus() == 2){
                if(om.getInsuranceType()==1){
                    SmsUtil.sendSms("【365灵活通】尊敬的"+om.getName()+"：您的养老保险将于"+om.getInsuEnd()+"到期。如您需继续办理，" +
                            "可在365灵活通小程序继续参保，如有疑问请联系电话0311-66686628",om.getTel(),null,null);
                }else{
                    SmsUtil.sendSms("【365灵活通】尊敬的"+om.getName()+"：您的医疗保险将于"+om.getInsuEnd()+"到期。如您需继续办理，" +
                            "可在365灵活通小程序继续参保，如有疑问请联系电话0311-66686628",om.getTel(),null,null);
                }
            }
        }
    }
}
