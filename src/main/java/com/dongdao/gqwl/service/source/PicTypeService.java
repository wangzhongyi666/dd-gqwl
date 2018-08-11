package com.dongdao.gqwl.service.source;

import com.dongdao.gqwl.bean.SysDept;
import com.dongdao.gqwl.mapper.source.DdPictypeMapper;
import com.dongdao.gqwl.model.SysDeptModel;
import com.dongdao.gqwl.model.source.DdPictype;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicTypeService<T> extends BaseService<T> {

    @Autowired
    private DdPictypeMapper<DdPictype> ddPictypeMapper;

    public DdPictypeMapper<DdPictype> getMapper() {
        return ddPictypeMapper;
    }

    public List<DdPictype> queryByList(DdPictype model){
        return ddPictypeMapper.queryByList(model);
    }
}
