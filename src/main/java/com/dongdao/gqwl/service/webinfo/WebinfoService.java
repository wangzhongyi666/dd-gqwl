package com.dongdao.gqwl.service.webinfo;

import com.dongdao.gqwl.mapper.source.DdLabelMapper;
import com.dongdao.gqwl.mapper.webinfo.DdWebinfoMapper;
import com.dongdao.gqwl.model.source.DdLabel;
import com.dongdao.gqwl.model.source.DdStype;
import com.dongdao.gqwl.model.webinfo.DdWebinfo;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebinfoService<T> extends BaseService<T> {

    @Autowired
    private DdWebinfoMapper<DdWebinfo> ddStypeMapper;

    public DdWebinfoMapper<DdWebinfo> getMapper() {
        return ddStypeMapper;
    }


}
