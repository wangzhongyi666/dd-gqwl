package com.dongdao.gqwl.service;

import com.dongdao.gqwl.bean.Webinfo;
import com.dongdao.gqwl.mapper.WebManaMapper;
import com.dongdao.gqwl.model.WebTextModel;
import com.dongdao.gqwl.model.WebinfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WebManaService {
    @Autowired
    private WebManaMapper webManaMapper;

    public Integer countWebInfo(WebinfoModel model){
        return webManaMapper.countWebInfo(model);
    }

    public List<Webinfo> getwebinfolist(WebinfoModel model){
        return webManaMapper.getwebinfolist(model);
    }
    public void deletinfo(WebinfoModel model){
        webManaMapper.deletinfo(model);
    }
    public void addInfo(WebinfoModel model){
        webManaMapper.addInfo(model);
    }
    public void updateInfo(WebinfoModel model){
        webManaMapper.updateInfo(model);
    }
    public void addText(WebTextModel model){
        webManaMapper.addText(model);
    }
    public List<Map<String,Object>> getOffice(){
        return webManaMapper.getOffice();
    }
}
