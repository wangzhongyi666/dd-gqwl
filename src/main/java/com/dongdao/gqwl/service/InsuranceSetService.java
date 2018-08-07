package com.dongdao.gqwl.service;

import com.dongdao.gqwl.bean.Insurance;
import com.dongdao.gqwl.bean.Order;
import com.dongdao.gqwl.bean.OrderInfo;
import com.dongdao.gqwl.model.InsuranceModel;
import com.dongdao.gqwl.model.OrderInfoModel;
import com.dongdao.gqwl.model.OrderModel;
import com.dongdao.gqwl.mapper.InsuranceSetMapper;
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
