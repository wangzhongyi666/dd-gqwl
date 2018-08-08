package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.bean.SmsTemplate;
import com.dongdao.gqwl.model.SmsTemplateModel;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface SmsTemplateMapper {

    @Select("<script> select * from sms_template t  where 1=1 " +
            "<if test=\"deptId != null\"> and t.deptId = #{deptId}</if>" +
            "<if test=\"state != null and state == 1\"> and t.send_time != null and t.id = 8</if>"+
            "</script>")
    List<SmsTemplate> queryByList(SmsTemplateModel model);

    @Update("<script>update sms_template set status=#{status} where id = #{id} </script>")
    Integer updateEnable(@Param("id")Integer id,@Param("status")Integer status);

    @Update("<script>update sms_template set sendTime=#{sendTime} where id = #{id} </script>")
    Integer updateSendTime(@Param("id")Integer id,@Param("sendTime")Date sendTime);
}
