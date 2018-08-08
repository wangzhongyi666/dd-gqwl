package com.dongdao.gqwl.service;

import com.dongdao.gqwl.bean.Vip;
import com.dongdao.gqwl.mapper.VipMapper;
import com.dongdao.gqwl.model.VipModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VipService {
    @Autowired
    private VipMapper vipMapper;
    public Integer countAuditlist(VipModel model){
        return vipMapper.countAuditlist(model);
    }
    public List<Vip> getAuditlist(VipModel model){
        return vipMapper.getAuditlist(model);
    }
    public void updateById(VipModel model){
        vipMapper.updateById(model);
    }
    public Integer countViplist(VipModel model){
        return vipMapper.countViplist(model);
    }
    public List<Vip> getViplist(VipModel model){
        return vipMapper.getViplist(model);
    }
    public Integer countorderinfo(Integer uid,String inseuranceCycle,Integer insuranceType){
        return vipMapper.countorderinfo(uid,inseuranceCycle,insuranceType);
    }
    public void updateVip(VipModel model){
        vipMapper.updateVip(model);
    }
    public List<Vip> getVipLogin(@Param("tel")String tel, @Param("pwd")String pwd){
        return vipMapper.getVipLogin(tel,pwd);
    }
    public void updateLoginCount(@Param("id")Integer id,@Param("loginCount")Integer loginCount){
        vipMapper.updateLoginCount(id,loginCount);
    }
    public void vipregister(VipModel model){
        vipMapper.vipregister(model);
    }
    public void updatePwd(VipModel model){
        vipMapper.updatePwd(model);
    }
    public void updatePayPwd(VipModel model){
        vipMapper.updatePayPwd(model);
    }
    public Integer countVipBytel(VipModel model){
        return  vipMapper.countVipBytel(model);
    }
    public void updateBytel(VipModel model){
        vipMapper.updateBytel(model);
    }
    public List<Vip> getVipByTel(VipModel model){
        return vipMapper.getVipByTel(model);
    }
    public Vip queryVipById(@Param("id")Integer id){
        return vipMapper.queryVipById(id);
    }
    public List<Map> getIdent(@Param("tel")String tel){
        return vipMapper.getIdent(tel);
    }
    public void updateIdentBytel(@Param("tel")String tel,@Param("ident")String ident){
        vipMapper.updateIdentBytel(tel,ident);
    }
    public void addIdentBytel(@Param("tel")String tel,@Param("ident")String ident){
        vipMapper.addIdentBytel(tel,ident);
    }
    public void deletIdentByTel(@Param("tel")String tel){
        vipMapper.deletIdentByTel(tel);
    }

    public String getTelById(@Param("id")Integer id){
        return vipMapper.getTelById(id);
    }
}
