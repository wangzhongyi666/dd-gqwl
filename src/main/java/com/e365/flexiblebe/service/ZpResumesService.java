package com.e365.flexiblebe.service;

import com.e365.flexiblebe.bean.ZpResumes;
import com.e365.flexiblebe.mapper.ZpResumesMapper;
import com.e365.flexiblebe.model.ZpResumesModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZpResumesService<T> extends BaseService<T> {

    @Autowired
    private ZpResumesMapper<T> zpResumesMapper;

    public ZpResumesMapper<T> getMapper() {
        return zpResumesMapper;
    }

    public int addResumes(ZpResumesModel model){
        return getMapper().addResumes(model);
    }

    public int updateResumes(ZpResumesModel model){
        return getMapper().updateResumes(model);
    }

    public ZpResumes queryByMobile(@Param("mobile")String mobile){
        return getMapper().queryByMobile(mobile);
    }
}
