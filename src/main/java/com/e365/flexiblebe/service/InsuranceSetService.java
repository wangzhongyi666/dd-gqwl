package com.e365.flexiblebe.service;

import com.e365.flexiblebe.bean.Insurance;
import com.e365.flexiblebe.bean.Order;
import com.e365.flexiblebe.bean.OrderInfo;
import com.e365.flexiblebe.mapper.InsuranceSetMapper;
import com.e365.flexiblebe.model.InsuranceModel;
import com.e365.flexiblebe.model.OrderInfoModel;
import com.e365.flexiblebe.model.OrderModel;
import com.e365.flexiblebe.model.VipModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceSetService<T> extends BaseService<T> {

    @Autowired
    private InsuranceSetMapper<T> insuranceMapper;

    public InsuranceSetMapper<T> getMapper() {
        return insuranceMapper;
    }

    /**
     * 修改保险内容
     * @param model
     */
    public void setInsurance(InsuranceModel model){
        getMapper().setInsurance(model);
    }

    /**
     * 查询保险类型
     * @return
     */
    public List<Insurance> allList(@Param("deptId")Integer deptId){
        return getMapper().allList(deptId);
    }


    public void updateInsBase(InsuranceModel model){
        getMapper().updateInsBase(model);
    }

/*    public void setInsurance(InsuranceModel model){
        getMapper().setInsurance(model);
    }*/

    public List<Order> insuranceDateList(OrderModel model){
        return getMapper().insuranceDateList(model);
   }

    public Integer insuranceDateCount(OrderModel model){
        return getMapper().insuranceDateCount(model);
    }

    public List<Order> insuranceDataExcel(OrderModel model){
        return getMapper().insuranceDataExcel(model);
    }

    public Integer updateInsurance(OrderModel model){
        return getMapper().updateInsurance(model);
    }

    public Order queryById(Integer id){
        return getMapper().queryById(id);
    }


    public List<Order> insuranceDataCheck(OrderModel model){
        return getMapper().insuranceDataCheck(model);
    }

    public void addQuit(OrderModel model){
        getMapper().add(model);
    }

    public List<Order> insuranceDataType(OrderModel model){
        return getMapper().insuranceDataType(model);
    }

    public List<OrderInfo> insuranceList(OrderInfoModel model){
        return getMapper().insuranceList(model);
    }
}
