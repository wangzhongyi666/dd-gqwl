package com.e365.flexiblebe.mapper;

import com.e365.flexiblebe.bean.Goods;
import com.e365.flexiblebe.bean.Message;
import com.e365.flexiblebe.model.GoodsModel;
import com.e365.flexiblebe.model.MessageModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper<T> extends BaseMapper {

    @Select("<script>SELECT *,DATE_FORMAT(t.create_time,'%Y-%m-%d %H:%i:%s') as createTime FROM msg_message t WHERE 1=1 "+
            "<if test=\"user_id != null\"> AND t.user_id = #{user_id} </if>"+
            "<if test=\"startDate != null and startDate != ''\"> AND #{startDate} >= t.create_time </if> "+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.create_time </if> "+
            "<if test=\"flag != null \">AND t.flag = #{flag} </if>"+
            "<if test=\"unread != null \">AND t.unread = #{unread} </if>"+
            "<if test=\"type != null\">AND t.type = #{type}</if>" +
            " order by unread asc,t.create_time desc  "+
            "<if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2} </if>"+
            "</script>")
    List<Message> queryByList(MessageModel model);

    @Select("<script>SELECT count(0) FROM msg_message t WHERE 1=1 "+
            "<if test=\"user_id != null\"> AND t.user_id = #{user_id} </if>"+
            "<if test=\"startDate != null and startDate != ''\"> AND #{startDate} >= t.create_time </if> "+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.create_time </if> "+
            "<if test=\"flag != null \">AND t.flag = #{flag} </if>"+
            "<if test=\"unread != null \">AND t.unread = #{unread} </if>"+
            "<if test=\"type != null\">AND t.type = #{type}</if></script>")
    Integer queryByCount(MessageModel model);

    @Select("select *,DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') as createTime from msg_message where id = #{id}")
    Message queryById(@Param("id")Integer id);

    @Update("<script>update msg_message set unread=2 where 1=1 " +
            "<if test=\"id != null\"> and id = #{id} </if>"+
            "<if test=\"user_id != null\"> and user_id = #{user_id} </if></script>")
    Integer updateUnread(@Param("id")Integer id,@Param("user_id")Integer user_id);

    @Insert("<script>insert into  msg_message (user_id,title,content,create_time,flag,unread,type)" +
            " values(#{user_id},#{title},#{content},#{create_time},#{flag},#{unread},#{type})"+
            "</script>")
    void sendMsg(MessageModel model);
}
