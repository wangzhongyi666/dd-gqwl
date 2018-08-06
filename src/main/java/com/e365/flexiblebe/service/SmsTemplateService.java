package com.e365.flexiblebe.service;

import com.e365.flexiblebe.bean.Order;
import com.e365.flexiblebe.bean.OrderInfo;
import com.e365.flexiblebe.bean.SmsTemplate;
import com.e365.flexiblebe.mapper.FinanceMapper;
import com.e365.flexiblebe.mapper.SmsTemplateMapper;
import com.e365.flexiblebe.model.IntegrationModel;
import com.e365.flexiblebe.model.OrderInfoModel;
import com.e365.flexiblebe.model.OrderModel;
import com.e365.flexiblebe.model.SmsTemplateModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SmsTemplateService {
    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    public List<SmsTemplate> queryByList(SmsTemplateModel model){
        return smsTemplateMapper.queryByList(model);
    }

    public Integer updateEnable(@Param("id")Integer id,@Param("status")Integer status){
        return smsTemplateMapper.updateEnable(id,status);
    }

    public Integer updateSendTime(@Param("id")Integer id,@Param("sendTime")Date sendTime){
        return smsTemplateMapper.updateSendTime(id,sendTime);
    }
}
