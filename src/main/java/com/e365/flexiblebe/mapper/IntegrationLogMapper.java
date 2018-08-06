package com.e365.flexiblebe.mapper;

import com.e365.flexiblebe.bean.Integration;
import com.e365.flexiblebe.bean.Order;
import com.e365.flexiblebe.bean.OrderInfo;
import com.e365.flexiblebe.model.IntegrationModel;
import com.e365.flexiblebe.model.OrderInfoModel;
import com.e365.flexiblebe.model.OrderModel;
import com.e365.flexiblebe.model.QuitModel;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface IntegrationLogMapper extends  BaseMapper{


    @Select("<script>select *," +
            "CASE t.type WHEN 1 THEN '参保获得' WHEN 2 THEN '商城消费' WHEN 3 THEN '参保退款扣除' WHEN 4 THEN '现金抵扣积分' " +
            "WHEN 5 THEN '参保退款失败积分退回' ELSE '其他' END AS type_zh  " +
            ",CASE WHEN t.integration > 0 THEN concat('+',t.integration) ELSE t.integration END AS integration_zh  " +
            ",CASE WHEN t.integration > 0 THEN 1 ELSE 0 END AS numType  " +
            " from sys_integration_log t where uid = #{uid} order by t.addTime desc " +
            "<if test=\"num1 != null and num2 != null \">limit #{num1},#{num2} </if></script>")
    List<Integration> queryByIntLogList(@Param("uid")Integer uid,@Param("num1")Integer num1,@Param("num2")Integer num2);

    @Select("select * from sys_integration t where uid = #{uid}")
    Integration queryByIntUid(@Param("uid") Integer uid);

    @Update("update sys_integration set integration=integration+#{integration},updateTime=#{updateTime} where uid = #{uid} ")
    void updateIntegration(@Param("integration")Integer integration,@Param("uid")Integer uid,@Param("updateTime")String updateTime);
    @Insert("insert into sys_integration_log (uid,integration,type,addTime,quit_id) values (#{uid},#{integration},#{type},#{addTime},#{quit_id})")
    void addInLog(IntegrationModel model);
    @Insert("insert into sys_integration (uid,integration,updateTime) values (#{uid},#{integration},#{updateTime})")
    void addIn(IntegrationModel model);


    @Select("select * from sys_integration_log t where quit_id = #{quit_id} and type = #{type}")
    Integration queryByQuitId(@Param("quit_id") Integer quit_id,@Param("type") Integer type);

    @Select("<script>select count(0) from sys_integration_log t where uid = #{uid} </script>")
    Integer queryByIntLogCount(@Param("uid")Integer uid);

    @Select("<script>select i.*,v.tel,v.name from sys_integration i left join sys_vip v on v.id = i.uid " +
            "where integration BETWEEN #{integral_s} and #{integral_e} </script>")
    List<Integration> queryIntegration(@Param("integral_s")Integer integral_s,@Param("integral_e")Integer integral_e);
}
