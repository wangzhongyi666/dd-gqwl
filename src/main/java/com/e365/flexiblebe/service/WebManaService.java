package com.e365.flexiblebe.service;

import com.e365.flexiblebe.bean.Webinfo;
import com.e365.flexiblebe.mapper.WebManaMapper;
import com.e365.flexiblebe.model.WebTextModel;
import com.e365.flexiblebe.model.WebinfoModel;
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
