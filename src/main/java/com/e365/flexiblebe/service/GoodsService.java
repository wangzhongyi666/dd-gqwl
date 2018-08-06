package com.e365.flexiblebe.service;

import com.e365.flexiblebe.bean.Goods;
import com.e365.flexiblebe.bean.SysRole;
import com.e365.flexiblebe.mapper.GoodsMapper;
import com.e365.flexiblebe.mapper.SysRoleMapper;
import com.e365.flexiblebe.model.GoodsModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Modifier;
import java.util.List;

@Service
public class GoodsService<T> extends BaseService<T> {

    @Autowired
    private GoodsMapper<T> goodsMapper;

    public GoodsMapper<T> getMapper() {
        return goodsMapper;
    }

    /**
     * 查询商品总数
     * @param model
     * @return
     */
    public Integer queryByCount(GoodsModel model){
        return getMapper().queryByCount(model);
    }

    /**
     * 商品列表
     * @param model
     * @return
     */
   public List<Goods> queryByList(GoodsModel model){
        return getMapper().queryByList(model);
    }

    /**
     * 查询商品核销数
     * @param model
     * @return
     */
    public Integer queryByQCount(GoodsModel model){
        return getMapper().queryByQCount(model);
    }

    /**
     * 商品核销列表
     * @param model
     * @return
     */
    public List<Goods> queryByQList(GoodsModel model){
        return getMapper().queryByQList(model);
    }


    /**
     * 商品核销导出
     * @param model
     * @return
     */
   public List<Goods> queryByExcel(GoodsModel model){
        return getMapper().queryByExcel(model);
    }

    /**
     * 按id查询商品
     * @param id
     * @return
     */
   public Goods queryById(@Param("id")Integer id){
       return getMapper().queryById(id);
    }

   public void addGoods(GoodsModel model){
       getMapper().addGoods(model);
   }

   public void updateGoods(@Param("state")Integer state,@Param("id")Integer id){
       getMapper().updateGoods(state,id);
    }

   public void delGoods(@Param("id")Integer id){
       getMapper().delGoods(id);
    }

   public Goods queryByNumber(@Param("number")String number){
       return getMapper().queryByNumber(number);
   }

   public Goods queryByName(@Param("gname")String gname){
       return getMapper().queryByName(gname);
   }

   public Goods queryByNum(@Param("number")String number){
       return getMapper().queryByNum(number);
   }

    /**
     * 减库存
     * @param stock 减少数量
     * @param id   商品id
     */
   public void updateStock(@Param("stock")Integer stock,@Param("id")Integer id){
        getMapper().updateStock(stock,id);
   }

   public void updateGs(GoodsModel model){
       getMapper().updateGs(model);
   }

   public void updateEndTime(@Param("term_of_validity_end")String term_of_validity_end,@Param("gname")String gname){
        getMapper().updateEndTime(term_of_validity_end, gname);
   }
}
