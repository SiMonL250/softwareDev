package com.example.manage_system_backend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class applyModel {
    private String ekey;
    private String ename;
    private String applicant;
    private String admin;
    private String applystatus;
    private Date applydate;
    private Date processdate;
    private String applykey;
    private Date datedue;
    private Date datestart;

}
