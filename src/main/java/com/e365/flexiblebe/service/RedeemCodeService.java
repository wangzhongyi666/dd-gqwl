package com.e365.flexiblebe.service;

import com.e365.flexiblebe.bean.Order;
import com.e365.flexiblebe.bean.OrderInfo;
import com.e365.flexiblebe.bean.RedeemCode;
import com.e365.flexiblebe.mapper.FinanceMapper;
import com.e365.flexiblebe.mapper.RedeemCodeMapper;
import com.e365.flexiblebe.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RedeemCodeService extends BaseService{
    @Autowired
    private RedeemCodeMapper redeemCodeMapper;

    public RedeemCodeMapper getMapper(){
        return redeemCodeMapper;
    }

    public RedeemCode queryByCode(@Param("redeem_code")String redeem_code){
        return getMapper().queryByCode(redeem_code);
    }

    public Integer addRedeemCode(RedeemCodeModel model){
        return getMapper().addRedeemCode(model);
    }

    public Integer queryByCount(GoodsModel model){
        return getMapper().queryByCount(model);
    }

    public List<RedeemCode> queryByList(RedeemCodeModel model){
        return getMapper().queryByList(model);
    }

    public void updateRedmmCode(RedeemCodeModel model){
        getMapper().updateRedmmCode(model);
    }

    /**
     * 查询已兑换数量
     * @param goods_id
     * @param status
     * @return
     */
    public Integer queryByEXCount(@Param("goods_id")Integer goods_id,@Param("status")Integer status){
        return getMapper().queryByEXCount(goods_id,status);
    }

    public List<RedeemCode> queryByTermOfValidityEnd(@Param("status")Integer status){
        return getMapper().queryByTermOfValidityEnd(status);
    }

    /**
     * 查看自提数量
     * @param goods_id
     * @return
     */
    public Integer queryByGoodsCount(@Param("goods_id")Integer goods_id,@Param("status")Integer status){
        return getMapper().queryByGoodsCount(goods_id,status);
    }

    public RedeemCode getReddmCode(@Param("goods_id")Integer goods_id){
        return getMapper().getReddmCode(goods_id);
    }


    public Integer queryByGoods(@Param("goods_id")Integer goods_id){
        return getMapper().queryByGoods(goods_id);
    }

    public void deleteRedmmCode(@Param("id")Integer id){
        getMapper().deleteRedmmCode(id);
    }

    public RedeemCode getReddmCodeId(@Param("id")Integer id){
       return getMapper().getReddmCodeId(id);
    }
}
