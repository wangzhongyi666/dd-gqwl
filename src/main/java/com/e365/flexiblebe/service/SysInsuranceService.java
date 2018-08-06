package com.e365.flexiblebe.service;

import com.e365.flexiblebe.bean.SysInsurance;
import com.e365.flexiblebe.mapper.SysInsuranceMapper;

import com.e365.flexiblebe.model.SysInsuranceModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysInsuranceService<T> extends BaseService<T> {
    @Autowired
    private SysInsuranceMapper<T> sysInsuranceMapper;

    public SysInsuranceMapper<T> getMapper(){
        return sysInsuranceMapper;
    }

    /**
     * 添加参保信息
     * @param model
     */
    public Integer addSysIns(SysInsuranceModel model){
        return getMapper().addSysIns(model);
    }

    /**
     * 修改
     * @param model
     * @return
     */
    public Integer updateSysIns(SysInsuranceModel model){
        return getMapper().updateSysIns(model);
    }
    public Integer countMedicallist(SysInsuranceModel model){
        return getMapper().countMedicallist(model);
    }
    public List<SysInsurance> getMedical(SysInsuranceModel model){
        return getMapper().getMedical(model);
    }
    public void deleteInsurance(SysInsuranceModel model){
        getMapper().deleteInsurance(model);
    }
}
