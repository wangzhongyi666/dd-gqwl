package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.bean.SysDept;
import com.dongdao.gqwl.model.SysDeptModel;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysDeptMapper<T> extends BaseMapper {

//    @Select("<script>SELECT *,(SELECT GROUP_CONCAT(c.`name`)  " +
//            "FROM sys_dept c where c.deleted = 0 and m.deptId = c.parentId and c.dre_type=1 GROUP BY m.parentId ) AS subDeptNames," +
//            "(SELECT COUNT(*) FROM sys_dept c WHERE c.parentId = m.deptId and c.dre_type=1) AS subCount "+
//            "FROM sys_dept m  WHERE m.deleted = 0 " +
//            "<if test=\"parentId == null\"> and m.parentId = 0 </if>"+
//            "<if test=\"name != null and name != ''\"> and m.name=#{name} </if>"+
//            "<if test=\"deptId != null and deptId != ''\"> AND m.deptId = #{deptId} </if>"+
//            "<if test=\"id != null and id != ''\"> AND m.id = #{id} </if>"+
//            "<if test=\"deleted != null and deleted != ''\"> AND m.deleted = #{deleted} </if>"+
//            "<if test=\"parentId != null \"> AND m.parentId = #{parentId} </if>"+
//            "<if test=\"dre_type != null \"> AND m.dre_type = #{dre_type} </if>"+
//            "order by m.deptId " +
//            "<if test=\"num1 != null and num2 !=null \"> limit #{num1},#{num2}</if>"+
//            "</script>")
    @Select("<script>SELECT *,(SELECT GROUP_CONCAT(c.`name`)  " +
            "FROM sys_dept c where m.deptId = c.parentId and c.dre_type=1 GROUP BY m.parentId ) AS subDeptNames,a.subCount " +
            "FROM sys_dept m left join (select parentId,count(0) as subCount from sys_dept where dre_type=1 group by parentId ) a on m.deptId=a.parentId WHERE 1=1 " +
            "<if test=\"parentId == null or parentId == 0\"> and m.parentId = 0 and a.subCount>0</if>"+
            "<if test=\"name != null and name != ''\"> and m.name=#{name} </if>"+
            "<if test=\"parentId != null and parentId != 0 \"> AND m.parentId = #{parentId}</if>"+
            "<if test=\"dre_type != null \"> AND m.dre_type = #{dre_type} </if>"+
            "<if test=\"num1 != null and num2 !=null \"> limit #{num1},#{num2}</if>"+
            "</script>")
    public List<SysDept> queryByList(SysDeptModel model);
    @Select("<script>SELECT *,(SELECT GROUP_CONCAT(c.`name`)  " +
            "FROM sys_dept c where c.deleted = 0 and m.deptId = c.parentId GROUP BY m.parentId ) AS subDeptNames," +
            "(SELECT COUNT(*) FROM sys_dept c WHERE c.parentId = m.id) AS subCount "+
            "FROM sys_dept m WHERE m.deleted = 0  and (m.tank =3 or m.parentId is NULL)"+
            "order by m.deptId</script>")
    public List<SysDept> queryByList1(SysDeptModel model);

    @Select("<script>SELECT * from sys_dept where parentId=#{parentId}</script>")
    List<SysDept> getDeptByParentId(@Param("parentId") Integer parentId);

    @Update("<script>update sys_dept set id=id " +
            "<if test=\"dre_type != null and dre_type != 0\"> ,dre_type=#{dre_type} </if>"+
            "<if test=\"bank != null and bank != ''\"> ,bank=#{bank} </if>"+
            "<if test=\"rank != null and rank != ''\"> ,rank=#{rank} </if>"+
            "where deptId=#{deptId}</script>")
    void updateDeptByDeptId(SysDeptModel model);

    @Select("<script>SELECT count(0) " +
            "FROM sys_dept m left join (select parentId,count(0) as decoun from sys_dept where dre_type=1 group by parentId ) a on m.deptId=a.parentId WHERE 1=1 " +
            "<if test=\"parentId == null or parentId == 0\"> and m.parentId = 0 and a.decoun>0</if>"+
            "<if test=\"name != null and name != ''\"> and m.name=#{name} </if>"+
            "<if test=\"parentId != null and parentId != 0 \"> AND m.parentId = #{parentId}</if>"+
            "<if test=\"dre_type != null \"> AND m.dre_type = #{dre_type} </if>"+
            "<if test=\"num1 != null and num2 !=null \"> limit #{num1},#{num2}</if>"+
            "</script>")
    public Integer queryByCount(SysDeptModel model);

    @Select("select * from sys_dept where deptId = #{deptId}")
    public SysDept queryByDept(@Param("deptId")Integer deptId);

    @Select("<script>SELECT * FROM sys_dept  WHERE  deleted = 0 " +
            "<if test=\"parentId == null\"> and parentId IS NULL </if>"+
            "<if test=\"parentId != null\"> AND parentId = #{parentId} </if>"+
            "order by id</script>")
    List<SysDept> getDeptlist(SysDeptModel model);
    @Select("<script>SELECT * FROM sys_dept  WHERE  dre_type = 1 " +
            "order by id</script>")
    List<SysDept> selectKTDept(SysDeptModel model);
    @Select("<script>SELECT id,parentId,deptId,name FROM sys_dept  WHERE  deleted = 0 and dre_type=1 " +
            "order by id</script>")
    List<Map<String,Object>> getktCity();

    @Insert("insert into sys_dept (deptId,name,deleted,createTime,updateTime,rank,is_date,base,yilbase,dre_type,tank,parentId)" +
            "values(#{deptId},#{name},#{deleted},#{createTime},#{updateTime},#{rank},#{is_date},#{base},#{yilbase},#{dre_type},#{tank},#{parentId})")
    void addDept(SysDeptModel model);


    @Select("<script>SELECT * FROM sys_dept order by deptId desc LIMIT 1</script>")
    SysDept getDescDept();

    @Select("<script>update sys_dept set dre_type = 2,bank=null where deptId = #{deptId} </script>")
    void deleteDept(SysDeptModel model);
}
