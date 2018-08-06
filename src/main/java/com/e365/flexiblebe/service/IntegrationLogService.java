package com.e365.flexiblebe.service;

import com.e365.flexiblebe.bean.GoodsAddress;
import com.e365.flexiblebe.bean.Integration;
import com.e365.flexiblebe.mapper.GoodsAddressMapper;
import com.e365.flexiblebe.mapper.IntegrationLogMapper;
import com.e365.flexiblebe.model.GoodsAddressModel;
import com.e365.flexiblebe.model.IntegrationModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IntegrationLogService<T> extends BaseService<T> {

    @Autowired
    private IntegrationLogMapper integrationLogMapper;

    public IntegrationLogMapper getMapper() {
        return integrationLogMapper;
    }

    /**
     * 查询积分消费记录
     * @param uid
     * @return
     */
    public List<Integration> queryByIntLogList(@Param("uid")Integer uid,@Param("num1")Integer num1,@Param("num2")Integer num2){
        return getMapper().queryByIntLogList(uid,num1,num2);
    }

    /**
     * 查询积分余额
     * @param uid
     * @return
     */
    public Integration queryByIntUid(@Param("uid") Integer uid){
        return getMapper().queryByIntUid(uid);
    }

    public void updateIntegration(@Param("integration")Integer integration,@Param("uid")Integer uid,@Param("updateTime")String updateTime){
        integrationLogMapper.updateIntegration(integration,uid,updateTime);
    }
    public void addInLog(IntegrationModel model){
        integrationLogMapper.addInLog(model);
    }
    public void addIn(IntegrationModel model){
        integrationLogMapper.addIn(model);
    }

    public Integration queryByQuitId(@Param("quit_id") Integer quit_id,@Param("type") Integer type){
        return integrationLogMapper.queryByQuitId(quit_id,type);
    }

    public Integer queryByIntLogCount(@Param("uid")Integer uid){
        return integrationLogMapper.queryByIntLogCount(uid);
    }

    public List<Integration> queryIntegration(@Param("integral_s")Integer integral_s,@Param("integral_e")Integer integral_e){
        return getMapper().queryIntegration(integral_s, integral_e);
    }
}
