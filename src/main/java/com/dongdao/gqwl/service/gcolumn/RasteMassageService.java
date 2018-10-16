package com.dongdao.gqwl.service.gcolumn;

import com.dongdao.gqwl.mapper.websit.RasteMassageMapper;
import com.dongdao.gqwl.mapper.websit.RasteUserMapper;
import com.dongdao.gqwl.model.websit.RasteMassage;
import com.dongdao.gqwl.model.websit.RasteUser;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RasteMassageService<T> extends BaseService<T> {
    @Autowired
    private RasteMassageMapper<RasteMassage> rasteMassageMapper;

    public RasteMassageMapper<RasteMassage> getMapper() {
        return rasteMassageMapper;
    }

}

