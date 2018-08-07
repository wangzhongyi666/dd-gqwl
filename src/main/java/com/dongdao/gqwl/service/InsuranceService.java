package com.dongdao.gqwl.service;

import com.dongdao.gqwl.bean.*;
import com.dongdao.gqwl.mapper.InsuranceMapper;
import com.dongdao.gqwl.model.InsuranceModel;
import com.dongdao.gqwl.model.OrderInfoModel;
import com.dongdao.gqwl.model.OrderModel;
import com.dongdao.gqwl.model.QuitModel;
import com.dongdao.gqwl.bean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InsuranceService {
    @Autowired
    private InsuranceMapper insuranceMapper;
    public Integer countadjulist(OrderModel model){
        return insuranceMapper.countadjulist(model);
    }
    public List<Order> getadjulist(OrderModel model){
        return insuranceMapper.getadjulist(model);
    }
    public Integer countinsulist(OrderInfoModel model){
        return insuranceMapper.countinsulist(model);
    }
    public List<OrderInfo> getinsulist(OrderInfoModel model){
        return insuranceMapper.getinsulist(model);
    }


    public Integer countinsulist1(OrderInfoModel model){
        return insuranceMapper.countinsulist1(model);
    }
    public List<OrderInfo> getinsulist1(OrderInfoModel model){
        return insuranceMapper.getinsulist1(model);
    }
    public void upOrderAudit(OrderModel model){
        insuranceMapper.upOrderAudit(model);
    }
    public void upAudit(OrderInfoModel model){
        insuranceMapper.upAudit(model);
    }
    public List<OrderInfo> queryByExcel(OrderInfoModel model){
        return insuranceMapper.queryByExcel(model);
    }
    public List<Order> queryOrderByExcel(OrderModel model){
        return insuranceMapper.queryOrderByExcel(model);
    }
    public Integer countquitlist(QuitModel model){
        return insuranceMapper.countquitlist(model);
    }
    public List<Quit> getquitlist(QuitModel model){
        return insuranceMapper.getquitlist(model);
    }
    public void upQuitAudit(QuitModel model){
        insuranceMapper.upQuitAudit(model);
    }
    public List<Quit> queryquitByExcel(QuitModel model){
        return insuranceMapper.queryquitByExcel(model);
    }
    public Integer countUserlist(OrderInfoModel model){
        return insuranceMapper.countUserlist(model);
    }
    public List<OrderInfo> getUserlist(OrderInfoModel model){
        return insuranceMapper.getUserlist(model);
    }

    public List<OrderInfo> getUserlist1(OrderInfoModel model){
        return insuranceMapper.getUserlist1(model);
    }
    public List<OrderInfo> queryByUserExcel(OrderInfoModel model){
        return insuranceMapper.queryByUserExcel(model);
    }
    public void upisquit(@Param("isquit")Integer isquit,@Param("orderNum")String orderNum){
        insuranceMapper.upisquit(isquit,orderNum);
    }

    public Integer addInsInfo(OrderInfoModel model){
        return insuranceMapper.addInsInfo(model);
    }
    public void upBalance(@Param("payment")double payment,@Param("orderNum")String orderNum){
        insuranceMapper.upBalance(payment,orderNum);
    }
    public Integer addIns(OrderModel model){
        return insuranceMapper.addIns(model);
    }

    public OrderInfo queryByOrderNum(@Param("orderNum")String orderNum){
        return insuranceMapper.queryByOrderNum(orderNum);
    }
    public Insurance calculatePayment(InsuranceModel model){
        return insuranceMapper.calculatePayment(model);
    }

    /**
     * 上个月缴费月份
     * @param orderNum
     * @return
     */
    public OrderInfo queryByLastPay(@Param("orderNum")String orderNum){
        return insuranceMapper.queryByLastPay(orderNum);
    }

    public Order queryByAudit(@Param("uid")Integer uid,@Param("insuranceType")Integer InsuranceType,@Param("deptId") Integer deptId){
        return insuranceMapper.queryByAudit(uid,InsuranceType,deptId);
    }

    /**
     * 查询社保缴纳情况
     * @param id
     * @return
     */
    public OrderInfo queryByInfoId(@Param("id")Integer id){
        return insuranceMapper.queryByInfoId(id);
    }

    /**
     * 一键缴纳当月社保
     * @param model
     */
    public void payOrderAll(OrderInfoModel model){
        insuranceMapper.payOrderAll(model);
    }

    public void upAuditByOrderNum(OrderModel model){
        insuranceMapper.upAuditByOrderNum(model);
    }

    public OrderInfo queryByInfoAudit(@Param("uid") Integer uid,@Param("insuranceType") Integer insuranceType,@Param("inseuranceCycle")String inseuranceCycle){
        return insuranceMapper.queryByInfoAudit(uid,insuranceType,inseuranceCycle);
    }
    public Order queryByOrderNumber(@Param("orderNum")String orderNum){
        return insuranceMapper.queryByOrderNumber(orderNum);
    }
    public Integer addQuit(QuitModel model){
        return insuranceMapper.addQuit(model);
    }

    public List<Map<String,Object>> getbaseByDeptId( @Param("deptId") Integer deptId){
       return insuranceMapper.getbaseByDeptId(deptId);
    }
    public List<OrderInfo> queryByOrderNumList(@Param("orderNum")String orderNum){
        return insuranceMapper.queryByOrderNumList(orderNum);
    }
    public Integer countUserlist1(OrderInfoModel model){
        return insuranceMapper.countUserlist1(model);
    }

    public OrderInfo queryByInfoAudit2(@Param("uid") Integer uid,@Param("insuranceType") Integer insuranceType){
        return insuranceMapper.queryByInfoAudit2(uid, insuranceType);
    }

    public QuitModel queryByQuitId(@Param("id")Integer id){
        return insuranceMapper.queryByQuitId(id);
    }

    public OrderInfo queryByLastPayUId(@Param("uid")Integer uid){
        return insuranceMapper.queryByLastPayUId(uid);
    }

    public Integer countNoPayVipId(@Param("vipId")Integer vipId){
        return insuranceMapper.countNoPayVipId(vipId);
    }
    public void delOrderById(@Param("id")Integer id){
        insuranceMapper.delOrderById(id);
    }
    public Order queryOrderById(@Param("id")Integer id){
        return insuranceMapper.queryOrderById(id);
    }
    public List<SysInsurance> getYBKById(@Param("vipId")Integer vipId, @Param("deptId")Integer deptId){
        return insuranceMapper.getYBKById(vipId,deptId);
    }
    public void updateCodeurl(@Param("orderNum")String orderNum,@Param("code_url")String code_url){
        insuranceMapper.updateCodeurl(orderNum,code_url);
    }
    public List<Order> queryCodeurlByvipId(OrderModel model1){
        return insuranceMapper.queryCodeurlByvipId(model1);
    }
}
