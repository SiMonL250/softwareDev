package com.example.manage_system_backend.mapper;

import com.example.manage_system_backend.models.StatusCount;
import com.example.manage_system_backend.models.MonthCount;
import com.example.manage_system_backend.models.equipmentInfo;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface equipmentMapper {
    @Select({"select etype,estatus ,count(estatus)from equipment group by estatus, etype;"})
    List<StatusCount> SelectCountByStatus();

    @Select({"select count(ekey) count,Month(addtime) month from equipment where addtime is not null group by MONTH(addtime)"})
    List<MonthCount> SelectCountByMonth();

    @Select({"select * from equipment"})
    List<equipmentInfo> getAllEquipment();
    @Select({"select * from equipment where ekey = #{ekey}"})
    equipmentInfo getEquipmentByEkey(@Param("ekey") String ekey);
    @Select({"select etype from equipment group by etype;"})
    List<String> SelectEtypes();

    @Insert({"insert into equipment value(#{key},#{name},#{type},#{status},#{user},#{date})"})
    void insertEquipment(@Param("key") String key,@Param("name") String name,@Param("type") String type,
                         @Param("status") String status,@Param("user") String user,@Param("date") Date date);

    @Update({"update equipment set ename=#{name},etype=#{type},estatus=#{status},useraccount=#{account},addtime=#{addtime} where ekey=#{key}"})
    void updateEquipment(@Param("name")String name,@Param("type") String type,@Param("status")String status,
                         @Param("account")String account,@Param("addtime") Date addtime,@Param("key")String key);

    @Delete({"delete from equipment where ekey = #{key}"})
    void deleteEquipment(@Param("key") String key);

}
