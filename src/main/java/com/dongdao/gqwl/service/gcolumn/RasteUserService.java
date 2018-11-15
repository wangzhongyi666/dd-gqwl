package com.dongdao.gqwl.service.gcolumn;

import com.dongdao.gqwl.mapper.website.RasteUserMapper;
import com.dongdao.gqwl.model.website.RasteUser;
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

    public RasteUser queryByToLogin(RasteUser user){
        return getMapper().queryByToLogin(user);
    }

    public Integer updateByWxIdent(RasteUser user){
        return getMapper().updateByWxIdent(user);
    }
}
