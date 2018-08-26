package com.dongdao.gqwl.mapper.source;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.source.DdAudit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DdAuditMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long s_audit_id);

    int insert(DdAudit record);

    int insertSelective(DdAudit record);

    DdAudit selectByPrimaryKey(Long s_audit_id);

    int updateByPrimaryKeySelective(DdAudit record);

    int updateByPrimaryKey(DdAudit record);
}