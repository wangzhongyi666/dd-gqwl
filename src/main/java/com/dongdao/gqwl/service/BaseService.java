package com.dongdao.gqwl.service;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseService<T> {

    public abstract BaseMapper<T> getMapper();


    public void add(T t) {
        getMapper().add(t);
    }

    public void update(T t) {
        getMapper().update(t);
    }


    public void updateBySelective(T t){
        getMapper().updateBySelective(t);
    }

    public void delete(Object... ids) {
        if(ids == null || ids.length < 1){
            return;
        }
        for(Object id : ids ){
            getMapper().delete(id);
        }
    }

    public int queryByCount(BaseModel model) {
        return getMapper().queryByCount(model);
    }

//	public List<T> queryByListForAll(BaseModel model) throws Exception{
//		return getMapper().queryByList(model);
//	}

    public T queryById(Object id) {
        return getMapper().queryById(id);
    }

    public int deleteByPrimaryKey(Object id) {
        return getMapper().deleteByPrimaryKey(id);
    }
    public int insertSelective(T record) {
        return getMapper().insertSelective(record);
    }

    public int updateByPrimaryKeySelective(T record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    public List<T> queryType() {
        return getMapper().queryType();
    }
    public List<T> queryLabel() {
        return getMapper().queryLabel();
    }
    public  T selectByPrimaryKey(Object id){
        return getMapper().selectByPrimaryKey(id);
    }
    public List<T> queryByList(BaseModel model) {
        return getMapper().queryByList(model);
    }

    public List<Map> selectByType(Object type){
        return getMapper().selectByType(type);
    }

    public  List<HashMap<String,Object>> selectAll(){
        return getMapper().selectAll();
    }
   public List<HashMap<String,Object>> selectAll(Object model){
        return getMapper().selectAll(model);
    }

    public  int updateNums(T record){
        return getMapper().updateNums(record);
    }
}
