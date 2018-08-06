package com.e365.flexiblebe.service;

import com.e365.flexiblebe.bean.Goods;
import com.e365.flexiblebe.bean.Message;
import com.e365.flexiblebe.mapper.GoodsMapper;
import com.e365.flexiblebe.mapper.MessageMapper;
import com.e365.flexiblebe.model.GoodsModel;
import com.e365.flexiblebe.model.MessageModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService<T> extends BaseService<T> {

    @Autowired
    private MessageMapper<T> messageMapper;

    public MessageMapper<T> getMapper() {
        return messageMapper;
    }

    public List<Message> queryByList(MessageModel model){
        return getMapper().queryByList(model);
    }

    public Integer queryByCount(MessageModel model){
        return getMapper().queryByCount(model);
    }

    /**
     * 查看消息详情
     * @param id
     * @return
     */
    public Message queryById(@Param("id")Integer id){
        return getMapper().queryById(id);
    }

    /**
     * 修改为已读
     * @param id
     * @return
     */
    public Integer updateUnread(@Param("id")Integer id,@Param("user_id")Integer user_id){
        return getMapper().updateUnread(id,user_id);
    }
    public void sendMsg(MessageModel model){
        getMapper().sendMsg(model);
    }
}
