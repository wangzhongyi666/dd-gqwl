package com.dongdao.gqwl.service;

import com.dongdao.gqwl.mapper.SmsTemplateMapper;
import com.dongdao.gqwl.bean.SmsTemplate;
import com.dongdao.gqwl.model.SmsTemplateModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
