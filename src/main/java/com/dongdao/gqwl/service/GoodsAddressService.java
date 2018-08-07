package com.dongdao.gqwl.service;

import com.dongdao.gqwl.bean.GoodsAddress;
import com.dongdao.gqwl.model.GoodsAddressModel;
import com.dongdao.gqwl.mapper.GoodsAddressMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GoodsAddressService<T> extends BaseService<T> {

    @Autowired
    private GoodsAddressMapper<T> goodsAddressMapper;

    public GoodsAddressMapper<T> getMapper() {
        return goodsAddressMapper;
    }

    /**
     * 查询此用户的收货地址
     * @param vip_id
     * @return
     */
    public List<GoodsAddress> queryByList(GoodsAddressModel model){
        return getMapper().queryByList(model);
    }
    public Integer countAddresslist(GoodsAddressModel model){
        return getMapper().countAddresslist(model);
    }
    public List<Map<String,Object>> getAddresslist(GoodsAddressModel model){
        return getMapper().getAddresslist(model);
    }
    public void updateAddress(GoodsAddressModel model){
        getMapper().updateAddress(model);
    }
    public void updateDefAddressByVipId(GoodsAddressModel model){
        getMapper().updateDefAddressByVipId(model);
    }
    public void updateDefAddress(GoodsAddressModel model){
        getMapper().updateDefAddress(model);
    }
    public void deleteAddress(GoodsAddressModel model){
        getMapper().deleteAddress(model);
    }
    public void addAddress(GoodsAddressModel model){
        getMapper().addAddress(model);
    }

    public GoodsAddress queryByAddressId(@Param("id")Integer id){
        return getMapper().queryByAddressId(id);
    }
}
