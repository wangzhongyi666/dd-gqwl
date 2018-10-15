package com.dongdao.gqwl.service.website;

import com.dongdao.gqwl.mapper.website.DdPictureMapper;
import com.dongdao.gqwl.mapper.website.DdProfileMapper;
import com.dongdao.gqwl.model.website.DdPicture;
import com.dongdao.gqwl.model.website.DdProfile;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService<T> extends BaseService<T> {

    @Autowired
    private DdPictureMapper<DdPicture> ddAuditMapper;

    public DdPictureMapper<DdPicture> getMapper(){
        return ddAuditMapper;
    }
}
