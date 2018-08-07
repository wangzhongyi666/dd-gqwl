package com.dongdao.gqwl.service;

import com.dongdao.gqwl.bean.GoodsOrder;
import com.dongdao.gqwl.model.GoodsOrderModel;
import com.dongdao.gqwl.mapper.GoodsOrderMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsOrderService<T> extends BaseService<T> {

    @Autowired
    private GoodsOrderMapper goodsOrderMapper;

    public GoodsOrderMapper<T> getMapper() {
        return goodsOrderMapper;
    }

    /**
     * 查询商品总数
     * @param model
     * @return
     */
    public Integer queryByCount(GoodsOrderModel model){
        return getMapper().queryByCount(model);
    }

    /**
     * 商品列表
     * @param model
     * @return
     */
    public List<GoodsOrder> queryByList(GoodsOrderModel model){
        return getMapper().queryByList(model);
    }

    /**
     * 发货管理总数
     * @param model
     * @return
     */
    public Integer queryBySendCount(GoodsOrderModel model){
        return getMapper().queryBySendCount(model);
    }

    /**
     * 发货管理列表
     * @param model
     * @return
     */
    public List<GoodsOrder> queryBySendList(GoodsOrderModel model){
        return getMapper().queryBySendList(model);
    }

    /**
     *
     * @param model
     * @return
     */
    public List<GoodsOrder> queryByExcel(GoodsOrderModel model){
        return getMapper().queryByExcel(model);
    }

    /**
     * 导出核销查询列表
     * @param model
     * @return
     */
    public List<GoodsOrder> queryBySendExcel(GoodsOrderModel model){
        return getMapper().queryBySendExcel(model);
    }

    public Integer countGoodslist(GoodsOrderModel model){
        return getMapper().countGoodslist(model);
    }
    public List<GoodsOrder> getGoodlist(GoodsOrderModel model){
        return getMapper().getGoodlist(model);
    }

    public Integer addGoodsOrder(GoodsOrderModel model){
        return getMapper().addGoodsOrder(model);
    }

    /**
     * 查询订单兑换列表
     * @param model
     * @return
     */
    public List<GoodsOrder> queryBySelfList(GoodsOrderModel model){
        return getMapper().queryBySelfList(model);
    }

    /**
     * 查询订单兑换列表
     * @param model
     * @return
     */
    public List<GoodsOrder> queryByMailList(GoodsOrderModel model){
        return getMapper().queryByMailList(model);
    }

    /**
     * 订单详情
     * @param id
     * @return
     */
    public GoodsOrder queryByOrderId(@Param("id")Integer id){
        return getMapper().queryByOrderId(id);
    }

    public Integer countGoods(GoodsOrderModel model){
        return getMapper().countGoods(model);
    }
    public Integer countGoods1(GoodsOrderModel model){
        return getMapper().countGoods1(model);
    }
    public void updateSendGoods(@Param("waybill_number")String waybill_number,@Param("courier_firm")String courier_firm,@Param("order_id")Integer order_id){
        getMapper().updateSendGoods(waybill_number,courier_firm,order_id);
    }

    /**
     * 查询订单详情
     * @param order_number
     * @return
     */
    public GoodsOrder queryByOrderNumber(@Param("order_number")String order_number){
        return goodsOrderMapper.queryByOrderNumber(order_number);
    }

    /**
     * 修改支付金额
     * @param payment
     * @param order_number
     */
    public void updatePayment(@Param("payment")Double payment,@Param("order_number")String order_number){
        getMapper().updatePayment(payment,order_number);
    }
}
