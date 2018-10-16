package com.dongdao.gqwl.service.gcolumn;

import com.dongdao.gqwl.mapper.websit.GSeoMapper;
import com.dongdao.gqwl.mapper.websit.RasteUserMapper;
import com.dongdao.gqwl.model.websit.GSeo;
import com.dongdao.gqwl.model.websit.RasteUser;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RasteUserService<T> extends BaseService<T> {
    @Autowired
    private RasteUserMapper<RasteUser> rasteUserMapper;

    public RasteUserMapper<RasteUser> getMapper() {
        return rasteUserMapper;
    }

}