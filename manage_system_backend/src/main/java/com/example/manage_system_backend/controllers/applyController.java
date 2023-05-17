package com.example.manage_system_backend.controllers;


import com.example.manage_system_backend.jsondata.JsonData;
import com.example.manage_system_backend.mapper.applyMapper;
import com.example.manage_system_backend.models.applyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class applyController {
    @Autowired
    applyMapper applyMapper;

    @GetMapping("/getallapply")
    public JsonData getAllApply(){
        // 加一个申请过期验证；applystatus='待处理' and datedue<今天 就过期
        applyMapper.checkApplyStatus();
        List<applyModel> alldata;
        try{
            alldata = applyMapper.selectAllApplyRecord();
            if(alldata == null){
                return JsonData.buildFail("空数据!");
            }else{
                return JsonData.buildSuccess(alldata);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonData.buildFail("数据库错误!!");
    }

    @PostMapping("/apply")
    JsonData apply(@RequestBody applyModel apply){
        String applykey =apply.getApplykey();
        String ekey = apply.getEkey();
        String applicant = apply.getApplicant();
        Date datestart = apply.getDatestart();
        Date datedue = apply.getDatedue();
        System.out.println(apply.toString());
        try{
            applyModel a ;
            a = applyMapper.selectApplyRecordByapplicant(ekey,applicant);
            if(a !=null ){
                return JsonData.buildFail("设备已申请过！");
            }


            //正在使用的设备不能被其他申请 正在使用：在[datestart,datedue]之间并且applysattus 为‘已同意’；
            //select by ekey ,applystatus is '已同意'  ; 只要申请的datestart 和 datedue有一个在区间内就不能申请。
            applyModel tocheck =applyMapper.selectByEkeyAndAgreed(ekey);
            System.out.println("tocheck:"+tocheck);
            if(tocheck==null){
                applyMapper.insertApply(applykey,apply.getEkey(),apply.getEname(),
                        apply.getApplicant(),null,apply.getApplydate(),null,datedue,datestart);
            }else {
                Date tocheckDatestart = tocheck.getProcessdate();
                Date tocheckDatedue = tocheck.getDatedue();
                if(datedue.before(tocheckDatestart) || datestart.after(tocheckDatedue)){// 申请的datestart1 和 datedue1 都不在tocheck [datestart,datedue] 区间内 datedue1<datestart || datestart1>datedue
                    applyMapper.insertApply(applykey,apply.getEkey(),apply.getEname(),
                            apply.getApplicant(),null,apply.getApplydate(),null,datedue,datestart);
                }else{
                    return JsonData.buildFail("设备被占用！");
                }
            }

            //申请后检查是否申请成功；
            if(applyMapper.selectApplyRecordByKey(applykey) !=null){
                return JsonData.buildSuccess("申请成功！");
            }else {
                return JsonData.buildFail("申请失败！");
            }        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonData.buildFail("数据库错误!!");
    }

    @PostMapping("/processapply")
    JsonData processAppply(@RequestBody applyModel apply){
        String key = apply.getApplykey();
        System.out.println("processapply:"+apply.toString());
        try{
            applyModel a = applyMapper.selectApplyRecordByKey(key);
            if( a == null){
                return JsonData.buildFail("记录不存在？！");
            }else if(!a.getApplystatus().equals("待处理")){
                return JsonData.buildFail("已经处理过了！");
            }else{
                applyMapper.processApply(apply.getAdmin(),apply.getApplystatus(),apply.getApplydate(),apply.getApplykey());
                return JsonData.buildSuccess("修改成功！");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return JsonData.buildFail("数据库错误!!");
    }

    @PostMapping("/deleterecord")
    JsonData deleteRecord(@RequestParam("applykey")String applykey){
        System.out.println(applykey);
        try{
            applyModel a = applyMapper.selectApplyRecordByKey(applykey);
            if(a==null){
                return JsonData.buildFail("记录不存在！");
            }else {
                applyMapper.deleteRecord(applykey);
                return applyMapper.selectApplyRecordByKey(applykey)==null?JsonData.buildSuccess("删除成功！"):JsonData.buildFail("删除失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonData.buildFail("数据库错误!!");
    }
}
