package com.dongdao.gqwl.service;

import com.dongdao.gqwl.bean.Order;
import com.dongdao.gqwl.bean.OrderInfo;
import com.dongdao.gqwl.mapper.FinanceMapper;
import com.dongdao.gqwl.model.IntegrationModel;
import com.dongdao.gqwl.model.OrderInfoModel;
import com.dongdao.gqwl.model.OrderModel;
import com.dongdao.gqwl.model.QuitModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FinanceService  {
    @Autowired
    private FinanceMapper financeMapper;

    public Integer countOrderlist(OrderModel model){
        return financeMapper.countOrderlist(model);
    }

    public List<Order> getOrderlist(OrderModel model){
        return financeMapper.getOrderlist(model);
    }

    public List<Order> queryByExcel(OrderModel model){
        return financeMapper.queryByExcel(model);
    }

    public List<Map> CountCPayment(OrderInfoModel model){
        return financeMapper.CountCPayment(model);
    }
    public List<Map> CountBPayment1(OrderInfoModel model){
        return financeMapper.CountBPayment1(model);
    }
    public List<OrderInfo> CountBPayment2 (OrderInfoModel model){
        return financeMapper.CountBPayment2(model);
    }
    public Integer countInye(){
        return financeMapper.countInye();
    }
    public Integer countInLog(@Param("type")Integer type){
        return financeMapper.countInLog(type);
    }
    public Integer countInteInfo(IntegrationModel model){
        return financeMapper.countInteInfo(model);
    }
    public  List<Map<String,Object>> getInteInfo(IntegrationModel model){
        return financeMapper.getInteInfo(model);
    }
    public  List<Map<String,Object>> getOrderByOrderNum(@Param("orderNum") String orderNum){
        return financeMapper.getOrderByOrderNum(orderNum);
    }
    public  List<OrderInfo> getOrdeInfoByOrderNum(@Param("orderNum") String orderNum){
        return financeMapper.getOrdeInfoByOrderNum(orderNum);
    }
    public  List<Map<String,Object>> getQuitByOrderNum(@Param("orderNum") String orderNum){
        return financeMapper.getQuitByOrderNum(orderNum);
    }
    public  List<Map<String,Object>> getJfye(@Param("uid") Integer uid){
        return financeMapper.getJfye(uid);
    }
    public List<Map> countQuit(QuitModel model){
        return financeMapper.countQuit(model);
    }
    public List<Map> countQuit1(QuitModel model){
        return financeMapper.countQuit1(model);
    }
    public Order queryOrderByOrderNum(@Param("orderNum") String orderNum){return financeMapper.queryOrderByOrderNum(orderNum);}

}
