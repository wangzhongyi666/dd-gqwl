package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.bean.Webinfo;
import com.dongdao.gqwl.model.WebTextModel;
import com.dongdao.gqwl.model.WebinfoModel;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface WebManaMapper {
    @Select("<script> select count(1) from sys_information t left join sys_dept d on t.deptId=d.deptId where 1=1 " +
            "<if test=\"title != null and title != ''\"> and t.title = #{title}</if>" +
            "<if test=\"id != null and id != ''\"> and t.id = #{id} </if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.deptId = #{deptId}</if></script>")
    Integer countWebInfo(WebinfoModel model);

    @Select("<script> select t.* from sys_information t left join sys_dept d on t.deptId=d.deptId where 1=1" +
            "<if test=\"title != null and title != ''\"> and t.title = #{title} </if>" +
            "<if test=\"id != null and id != ''\"> and t.id = #{id} </if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.deptId = #{deptId}</if> " +
            " order by t.releTime desc " +
            " <if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2} </if> </script>")
    List<Webinfo> getwebinfolist(WebinfoModel model);

    @Delete("delete from sys_information where id=#{id}")
    void deletinfo(WebinfoModel model);
    @Insert("insert into sys_information (title,cont,picurl1,picurl2,picurl3,releTime,releid,deptId) value(" +
            " #{title},#{cont},#{picurl1},#{picurl2},#{picurl3},#{releTime},#{releid},#{deptId})")
    void addInfo(WebinfoModel model);
    @Insert("update sys_office set picture1=#{picture1},url1=#{url1},picture2=#{picture2},url2=#{url2},picture3=#{picture3},url3=#{url3} where id=1")
    void addText(WebTextModel model);

    @Update("update sys_information set title=#{title},cont=#{cont},picurl1=#{picurl1},picurl2=#{picurl2},picurl3=#{picurl3} where id=#{id}")
    void updateInfo(WebinfoModel model);
    @Select("<script>select * from sys_office</script>")
    List<Map<String,Object>> getOffice();
}
