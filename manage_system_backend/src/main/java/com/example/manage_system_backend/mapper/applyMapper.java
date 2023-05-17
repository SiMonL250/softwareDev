package com.example.manage_system_backend.mapper;

import com.example.manage_system_backend.models.applyModel;
import com.example.manage_system_backend.models.myApplysCount;
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

    @Select({"select * from applyrecord where ekey = #{ekey} and applystatus='已同意'"})
    applyModel selectByEkeyAndAgreed(@Param("ekey") String ekey);
    @Select({"select * from applyrecord where ekey=#{ekey} and applicant=#{applicant} and applystatus='待处理'"})
    applyModel selectApplyRecordByapplicant(@Param("ekey")String ekey,@Param("applicant")String applicant);
    @Update({"update applyrecord set admin=#{admin},applystatus=#{status},processdate=#{date} where applykey=#{applykey}"})
    void processApply(@Param("admin")String admin,@Param("status")String status,@Param("date") Date date,
                      @Param("applykey")String applykey);

    @Delete({"delete from applyrecord where applykey = #{applykey}"})
    void deleteRecord(@Param("applykey")String applykey);

    @Select({"select applystatus name,count(applystatus) value from applyrecord where applicant=#{student} group by applystatus;"})
    List<myApplysCount> selectmyApplystatusCount(@Param("student") String student);

    @Select({"""
            select etype name,count(applyrecord.ekey) value
            from applyrecord,equipment
            where equipment.ekey=applyrecord.ekey and applicant=#{student}
            group by etype;"""})
    List<myApplysCount> selectmyApplyEtypeCount(@Param("student") String student);

    @Update({"""
            update applyrecord
            set applystatus = '已过期'
            where datedue<CURDATE()  and applystatus='待处理'"""})
    void checkApplyStatus();
}
