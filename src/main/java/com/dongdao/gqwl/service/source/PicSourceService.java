package com.dongdao.gqwl.service.source;

import com.dongdao.gqwl.mapper.source.DdPicsourceMapper;
import com.dongdao.gqwl.model.source.DdPicsource;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicSourceService<T> extends BaseService<T> {

    @Autowired
    private DdPicsourceMapper<DdPicsource> ddPicMapper;

    public DdPicsourceMapper<DdPicsource> getMapper() {
        return ddPicMapper;
    }

    public List<DdPicsource> queryByList(DdPicsource model){
        return ddPicMapper.queryByList(model);
    }
}
