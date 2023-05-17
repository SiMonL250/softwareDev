package com.example.manage_system_backend.mapper;

import com.example.manage_system_backend.models.applyModel;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface applyMapper {
    @Select({"select * from applyrecord"})
    List<applyModel> selectAllApplyRecord();

    @Insert({"insert into applyrecord values(#{ekey},#{name},#{applicant},#{admin},'待处理',#{date},#{processdate},#{applykey},#{datedue},#{datestart})"})
    void insertApply(@Param("applykey")String applykey,@Param("ekey") String ekey,@Param("name") String ename,@Param("applicant") String applicant,@Param("admin")String admin,
                     @Param("date") Date applydate, @Param("processdate") Date processdate,@Param("datedue")Date datedue,@Param("datestart")Date datestart);
    @Select({"select * from applyrecord where applykey=#{key}"})
    applyModel selectApplyRecordByKey(@Param("key")String key);

    @Select({"select * from applyrecord where ekey = #{ekey}"})
    List<applyModel> selectByEkey(@Param("ekey") String ekey);
    @Select({"select * from applyrecord where ekey=#{ekey} and applicant=#{applicant} and applystatus=#{status}"})
    applyModel selectApplyRecordByapplicant(@Param("ekey")String ekey,@Param("applicant")String applicant,@Param("status")String status);
    @Update({"update applyrecord set admin=#{admin},applystatus=#{status},processdate=#{date} where applykey=#{applykey}"})
    void processApply(@Param("admin")String admin,@Param("status")String status,@Param("date") Date date,
                      @Param("applykey")String applykey);

    @Delete({"delete from applyrecord where applykey = #{applykey}"})
    void deleteRecord(@Param("applykey")String applykey);
}
