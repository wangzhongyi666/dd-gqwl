package com.dongdao.gqwl.service;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.BaseModel;

public abstract class BaseService<T> {

    public abstract BaseMapper<T> getMapper();


    public void add(T t)  throws Exception{
        getMapper().add(t);
    }

    public void update(T t)  throws Exception{
        getMapper().update(t);
    }


    public void updateBySelective(T t){
        getMapper().updateBySelective(t);
    }

    public void delete(Object... ids) throws Exception{
        if(ids == null || ids.length < 1){
            return;
        }
        for(Object id : ids ){
            getMapper().delete(id);
        }
    }

    public int queryByCount(BaseModel model)throws Exception{
        return getMapper().queryByCount(model);
    }

//	public List<T> queryByListForAll(BaseModel model) throws Exception{
//		return getMapper().queryByList(model);
//	}

    public T queryById(Object id) throws Exception{
        return getMapper().queryById(id);
    }

    public int deleteByPrimaryKey(Object id) throws Exception{
        return getMapper().deleteByPrimaryKey(id);
    }
    public int insertSelective(T record) throws Exception{
        return getMapper().insertSelective(record);
    }

    public int updateByPrimaryKeySelective(T record) throws Exception{
        return getMapper().updateByPrimaryKeySelective(record);
    }
}
