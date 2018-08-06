package com.e365.flexiblebe.service;

import com.e365.flexiblebe.bean.SysDept;
import com.e365.flexiblebe.bean.SysMenu;
import com.e365.flexiblebe.mapper.SysDeptMapper;
import com.e365.flexiblebe.mapper.SysMenuMapper;
import com.e365.flexiblebe.model.BaseModel;
import com.e365.flexiblebe.model.SysDeptModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysDeptService<T> extends BaseService<T> {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    public SysDeptMapper<T> getMapper() {
        return sysDeptMapper;
    }

    public List<SysDept> queryByList(SysDeptModel model){
        return sysDeptMapper.queryByList(model);
    }
    public List<SysDept> getDeptByParentId(@Param("parentId") Integer parentId){
        return sysDeptMapper.getDeptByParentId(parentId);
    }
    public void updateDeptByDeptId(SysDeptModel model){
        sysDeptMapper.updateDeptByDeptId(model);
    }
    public List<SysDept> queryByList1(SysDeptModel model){
        return sysDeptMapper.queryByList1(model);
    }

    public Integer queryByCount(SysDeptModel model){
        return sysDeptMapper.queryByCount(model);
    }

    public SysDept queryByDept(@Param("deptId")Integer deptId){
        return getMapper().queryByDept(deptId);
    }
    public List<SysDept> getDeptlist(SysDeptModel model){
        return sysDeptMapper.getDeptlist(model);
    }
    public List<SysDept> selectKTDept(SysDeptModel model){
        return sysDeptMapper.selectKTDept(model);
    }
    public List<Map<String,Object>> getktCity(){
        return sysDeptMapper.getktCity();
    }

    public void addDept(SysDeptModel model){
        getMapper().addDept(model);
    }

    public SysDept getDescDept(){
        return getMapper().getDescDept();
    }

    public void deleteDept(SysDeptModel model){
        getMapper().deleteDept(model);
    }
}
