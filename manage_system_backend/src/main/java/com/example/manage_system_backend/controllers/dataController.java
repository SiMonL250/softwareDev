package com.example.manage_system_backend.controllers;

import com.example.manage_system_backend.jsondata.JsonData;
import com.example.manage_system_backend.mapper.equipmentMapper;
import com.example.manage_system_backend.models.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
public class dataController {
    @Autowired
    equipmentMapper equipmentMapper;

    @GetMapping("/getallequipment")
    public JsonData getAllaEquipment(){
        try{
            List<equipmentInfo> equipmentInfoList = equipmentMapper.getAllEquipment();
            if(equipmentInfoList!=null){
                return JsonData.buildSuccess(equipmentInfoList);
            }else{
                return JsonData.buildFail("空数据!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       return JsonData.buildFail("数据库错误!");
    }

    @GetMapping("/bardata")
    public JsonData getBarData(){//主页柱形图
        List<StatusCount> statusCountsList;
        try{
            statusCountsList =equipmentMapper.SelectCountByStatus();
            if(statusCountsList!=null){
                List<String> etypes = equipmentMapper.SelectEtypes();//all types
                int length = etypes.size();
                System.out.println(length);
                List<Integer> normals = new ArrayList<>();
                List<Integer> brokes = new ArrayList<>();
                int sum=0;
                for(int i=0; i<length; i++){//initialize
                    normals.add(i,0);
                    brokes.add(i,0);
                }

                for(StatusCount i: statusCountsList){
                    int index = etypes.indexOf(i.getEtype());
                    String temp = i.getCount().toString();
                    if(i.getEstatus().equals("正常")){
                        normals.set(index,Integer.parseInt(temp));
                    }else {
                        brokes.set(index,Integer.parseInt(temp));
                    }
                    sum+=i.getCount();
                }

                barreturn barreturn = new barreturn(etypes, normals, brokes);
                barreturn.setSum(sum);
                System.out.println(barreturn.toString());
                return JsonData.buildSuccess(barreturn);
            }else{
                return JsonData.buildFail("空数据!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonData.buildFail("数据库错误!");
    }

    @GetMapping("/lineincrease")
    JsonData getLineData(){
        List<MonthCount> monthCountList;

        try{
            monthCountList = equipmentMapper.SelectCountByMonth();
            System.out.println(monthCountList);
            return monthCountList.size()==0?JsonData.buildFail("空数据！"):JsonData.buildSuccess(monthCountList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonData.buildFail("数据库错误!");
    }
    @PostMapping("/addequipment")
    public JsonData addEquipment(@RequestBody equipmentInfo equipment){
        System.out.print("post equipment");
        System.out.println(equipment.toString());
        String  key=equipment.getEkey();

        if(equipmentMapper.getEquipmentByEkey(key) == null){//TODO 返回所有设备以更新表格？
            equipmentMapper.insertEquipment(key,equipment.getEname(),equipment.getEtype(),
                    equipment.getEstatus(),equipment.getUseraccount(),equipment.getAddtime());
            return JsonData.buildSuccess("成功!!");
        }else{
            return JsonData.buildFail("键值重复!!");
        }

    }

    @PostMapping("/delete")
    public JsonData deleteEquipment(@Param("ekey")String ekey ){
        System.out.println("delete-"+ ekey);
        try{
            if(equipmentMapper.getEquipmentByEkey(ekey)!=null){
                equipmentMapper.deleteEquipment(ekey);
            }else {
                return JsonData.buildFail("不存在这个设备？");
            }

            if(equipmentMapper.getEquipmentByEkey(ekey) != null){
                return JsonData.buildFail("删除失败,不知道为什么");
            }else{
                return JsonData.buildSuccess("删除成功");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return JsonData.buildFail("数据库错误!");
    }

    @PostMapping("/editequipment")
    public JsonData editEquipment(@RequestBody equipmentInfo equipment){
        String key = equipment.getEkey();
        System.out.println("editequipment:"+equipment.toString());
        try{
            if(equipmentMapper.getEquipmentByEkey(key) != null){
                equipmentMapper.updateEquipment(equipment.getEname(),equipment.getEtype(),equipment.getEstatus(),
                        equipment.getUseraccount(),equipment.getAddtime(),key);
                return JsonData.buildSuccess("成功!!");
            }else {
                return JsonData.buildFail("不存在这个设备？");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonData.buildFail("数据库错误!");
    }
}
