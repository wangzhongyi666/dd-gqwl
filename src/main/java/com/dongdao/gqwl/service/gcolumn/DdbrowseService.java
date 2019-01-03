package com.dongdao.gqwl.service.gcolumn;

import com.dongdao.gqwl.mapper.website.DdbrowseMapper;
import com.dongdao.gqwl.model.routline.topic.DdZrecord;
import com.dongdao.gqwl.model.website.Ddbrowse;
import com.dongdao.gqwl.service.BaseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DdbrowseService<T> extends BaseService<T> {
    @Autowired
    private DdbrowseMapper<Ddbrowse> ddbrowseMapper;

    public DdbrowseMapper<Ddbrowse> getMapper() {
        return ddbrowseMapper;
    }

    public List<Map> queryByBrowse(Integer user_id){
        return getMapper().queryByBrowse(user_id);
    }

    public Map queryByBrowseInfo(Integer browse_id){
        return getMapper().queryByBrowseInfo(browse_id);
    }

    public List<Map> queryByCards(@Param("r_uid") Integer u_rid){
        return getMapper().queryByCards(u_rid);
    }

    public Map queryByCardsInfo(@Param("topid")Integer topid,@Param("cardid") Integer cardid){
        return getMapper().queryByCardsInfo(topid, cardid);
    }

    public void updateByCardsId(@Param("isdelete")Integer isdelete,@Param("cardid") Integer cardid){
        getMapper().updateByCardsId(1,cardid);
    }

    public Ddbrowse selectById(Ddbrowse record){
        return getMapper().selectById(record);
    }
}
