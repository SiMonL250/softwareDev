package com.example.manage_system_backend.mapper;

import com.example.manage_system_backend.models.user;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface userMapper {
    @Select({"select * from user where user.useraccount = #{useraccount}"})
    user getUser(@Param("useraccount") String useraccount);

    @Insert({"insert into user value( #{useraccount},#{password}, #{userprivilege} )"})
    void postUser(@Param("useraccount")String useraccount, @Param("password")String password, @Param("userprivilege")String userprivilege);

    @Select({"select * from user"})
    List<user> getAlluser();

    @Update({"update user set password=#{password},userprivilege=#{userprivilege} where useraccount = #{useraccount}"})
    void updateUser(@Param("password") String password,@Param("userprivilege")String userprivilege, @Param("useraccount")String useraccount);

    @Delete({"delete from user where useraccount=#{account}"})
    void deleteAccount(@Param("account")String account);
}
