package com.e365.flexiblebe.mapper;

import com.e365.flexiblebe.bean.Goods;
import com.e365.flexiblebe.model.GoodsAddressModel;
import com.e365.flexiblebe.model.GoodsModel;
import com.e365.flexiblebe.model.GoodsOrderModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper<T> extends BaseMapper {

    @Select("<script>SELECT COUNT(0) FROM goods t WHERE 1=1 "+
            "<if test=\"gname != null and gname != ''\"> AND t.gname like CONCAT('%', #{gname}, '%') </if>"+
            "<if test=\"startDate != null and startDate != ''\"> AND t.createTime >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.createTime </if> "+
            "<if test=\"state != null and state != ''\">AND t.state = #{state} </if>"+
            "<if test=\"stock != null and stock == 0\">AND t.stock = #{stock} </if>"+
            "<if test=\"take_type != null and take_type != ''\">AND t.take_type = #{take_type}</if></script>")
    Integer queryByCount(GoodsModel model);

    @Select("<script>SELECT * FROM goods t WHERE 1=1 "+
            "<if test=\"gname != null and gname != ''\"> AND t.gname like CONCAT('%', #{gname}, '%') </if>"+
            "<if test=\"startDate != null and startDate != ''\"> AND t.createTime >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.createTime </if> "+
            "<if test=\"state != null and state != ''\">AND t.state = #{state} </if>"+
            "<if test=\"stock != null and stock == 0\">AND t.stock = #{stock} </if>"+
            "<if test=\"take_type != null and take_type != ''\">AND t.take_type = #{take_type} </if>" +
            "order by " +
            "<if test=\"rankTime != null and rankTime == 1\"> integral desc, </if>" +
            "<if test=\"rankTime != null and rankTime == 2\"> integral asc, </if>" +
            "t.id desc  " +
            "<if test=\"num1 != null and num2 != null \">limit #{num1},#{num2} </if>"+
            "</script>")
    List<Goods> queryByList(GoodsModel model);

    @Select("<script>SELECT COUNT(0) FROM goods t WHERE 1=1 "+
            "<if test=\"gname != null and gname != ''\"> AND (t.gname like CONCAT('%', #{gname}, '%') or t.number like CONCAT('%', #{gname}, '%')) </if>"+
            "<if test=\"startDate != null and startDate != ''\"> AND t.createTime >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.createTime </if> "+
            "<if test=\"state != null and state != ''\">AND t.state = #{state} </if>"+
            "<if test=\"take_type != null and take_type != ''\">AND t.take_type = #{take_type}</if></script>")
    Integer queryByQCount(GoodsModel model);

    @Select("<script>SELECT * FROM goods t WHERE 1=1 "+
            "<if test=\"gname != null and gname != ''\"> AND (t.gname like CONCAT('%', #{gname}, '%') or t.number like CONCAT('%', #{gname}, '%')) </if>"+
            "<if test=\"startDate != null and startDate != ''\"> AND t.createTime >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.createTime </if> "+
            "<if test=\"state != null and state != ''\">AND t.state = #{state} </if>"+
            "<if test=\"take_type != null and take_type != ''\">AND t.take_type = #{take_type} </if>" +
            "order by t.id desc limit #{num1},#{num2}</script>")
    List<Goods> queryByQList(GoodsModel model);

    @Select("<script>SELECT * FROM goods t WHERE 1=1 "+
            "<if test=\"startDate != null and startDate != ''\"> AND t.createTime >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.createTime </if> </script>")
    List<Goods> queryByExcel(GoodsModel model);

    @Select("select * from goods where id = #{id}")
    Goods queryById(@Param("id")Integer id);

    @Select("select * from goods where state = 1 and number = #{number}")
    Goods queryByNumber(@Param("number")String number);

    @Select("select * from goods where gname = #{gname} limit 1")
    Goods queryByName(@Param("gname")String gname);

    @Select("select * from goods where number = #{number}")
    Goods queryByNum(@Param("number")String number);

    @Insert("<script>insert into goods (number,gname,integral,stock,take_type,state,exchange,imgUrl,info,statement" +
            ",createTime,mail_nums,exchanged_nums,write_off_nums,not_exchange_nums,pick_up_nums,term_of_validity_start,term_of_validity_end,deptId) " +
            " values(#{number},#{gname},#{integral},#{stock},#{take_type},#{state},#{exchange},#{imgUrl},#{info}," +
            "#{statement},now(),#{mail_nums},#{exchanged_nums},#{write_off_nums},#{not_exchange_nums},#{pick_up_nums}," +
            "#{term_of_validity_start},#{term_of_validity_end},#{deptId})</script>")
    void addGoods(GoodsModel model);

    @Update("<script>update goods set state = #{state} where id=#{id}</script>")
    void updateGoods(@Param("state")Integer state,@Param("id")Integer id);


    @Update("<script>update goods set stock = stock+#{stock} where id=#{id}</script>")
    void updateStock(@Param("stock")Integer stock,@Param("id")Integer id);


    @Update("<script>delete from goods where id=#{id}</script>")
    void delGoods(@Param("id")Integer id);


    /**
     * 不能重用
     * @param model
     */
    @Update("<script>update goods set id = id" +
            "<if test=\"state != null\"> ,state = #{state} </if>"+
            "<if test=\"imgUrl != null\"> ,imgUrl = #{imgUrl} </if>"+
            "<if test=\"stock != null\"> ,stock = #{stock} </if>"+
            "<if test=\"integral != null\"> ,integral = #{integral} </if>"+
            "<if test=\"number != null\"> ,number = #{number} </if>"+
            "<if test=\"info != null\"> ,info = #{info} </if>"+
            "<if test=\"statement != null\"> ,statement = #{statement} </if>"+
            " where id=#{id}</script>")
    void updateGs(GoodsModel model);

    /**
     * 修改到期时间
     * @param term_of_validity_end
     * @param gname
     */
    @Update("<script>update goods set term_of_validity_end = #{term_of_validity_end} where gname=#{gname}</script>")
    void updateEndTime(@Param("term_of_validity_end")String term_of_validity_end,@Param("gname")String gname);

}
