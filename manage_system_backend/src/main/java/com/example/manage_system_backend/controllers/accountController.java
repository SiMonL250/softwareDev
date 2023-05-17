package com.example.manage_system_backend.controllers;

import com.example.manage_system_backend.jsondata.JsonData;
import com.example.manage_system_backend.mapper.userMapper;
import com.example.manage_system_backend.models.user;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class accountController {
    @Autowired
    userMapper userMapper;
    user user;
    @GetMapping("/login")
    public JsonData login(@RequestParam("useraccount") String useraccount, @RequestParam("password") String password, @RequestParam("userprivilege") String userprivilege ){
        System.out.println("login:"+useraccount +"-" +password + '-'+ userprivilege);
        try{
            user = userMapper.getUser(useraccount);//useraccount 是key
            //validate password;
            if(user == null){
                return JsonData.buildFail("没有这个账号!");
            } else if (!password.equals(user.getPassword())) {
                return JsonData.buildFail("密码错误!");
            } else{
                if(user.getUserprivilege().equals(userprivilege)) {
                    return JsonData.buildSuccess(user);
                }else {
                    return JsonData.buildFail("角色错误!");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return JsonData.buildFail("数据库错误!");
    }

    @PostMapping("/register")
    public JsonData register(@RequestBody user user, HttpServletRequest request){
        String useraccount,password;
        useraccount = user.getUseraccount();
        password = user.getPassword();
        String userprivilege = user.getUserprivilege();
        System.out.println("register:"+useraccount +"-" +password +'-' + userprivilege);
        try{
            user = userMapper.getUser(useraccount);
            if(user == null){//先检查有没有注测过
                userMapper.postUser(useraccount,password,userprivilege);
                user user1 = userMapper.getUser(useraccount);
                return user1!=null?JsonData.buildSuccess("注册成功！")
                        :JsonData.buildFail("注册失败!");
                //再getUser一次
            }else{
                return JsonData.buildFail(" 账号已存在!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return JsonData.buildFail("数据库错误!");
    }

    @GetMapping("/getalluser")
    public JsonData getAllUser(){
        List<user> userList;
        try{
            userList = userMapper.getAlluser();
            if(userList ==null){
                return JsonData.buildFail("无数据");
            }else{

                return JsonData.buildSuccess(userList);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return JsonData.buildFail("数据库错误!");
    }

    @PostMapping("/changeaccount")
    JsonData changeAccount(@RequestBody user user){
        System.out.println("changeaccount-"+user.toString());
        try{
            userMapper.updateUser(user.getPassword(),user.getUserprivilege(),user.getUseraccount());//TODO 返回所有用户以更新表格？
            return JsonData.buildSuccess("成功!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonData.buildFail("数据库错误!");
    }

    @PostMapping("/deleteuser")
    JsonData deleteUser(@RequestParam("useraccount") String account){
        System.out.println("deleteaccount-"+account);
        try{
            userMapper.deleteAccount(account);
            return userMapper.getUser(account)==null?JsonData.buildSuccess("删除成功!")
                    :JsonData.buildFail("删除失败!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonData.buildFail("数据库错误!");
    }
}
