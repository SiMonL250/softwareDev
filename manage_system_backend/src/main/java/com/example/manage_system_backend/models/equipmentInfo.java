package com.example.manage_system_backend.models;

import java.sql.Date;

/*equipment object  //
    {
        "ename":"",
        "ekey":"",
        "etype":"",
        "estatus":"",//正常、损坏、
        "addtime":date,
    }
*/
public class equipmentInfo {
    private String ename;
    private String ekey;
    private String etype;
    private String estatus;
    private String useraccount;
    private Date addtime;

    public equipmentInfo(String ename, String ekey, String etype, String estatus, String useraccount,Date addtime) {
        this.ename = ename;
        this.ekey = ekey;
        this.etype = etype;
        this.estatus = estatus;
        this.useraccount = useraccount;
        this.addtime = addtime;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEkey() {
        return ekey;
    }

    public void setEkey(String ekey) {
        this.ekey = ekey;
    }

    public String getEtype() {
        return etype;
    }

    public void setEtype(String etype) {
        this.etype = etype;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    @Override
    public String toString() {
        return "equipmentInfo{" +
                "ename='" + ename + '\'' +
                ", ekey='" + ekey + '\'' +
                ", etype='" + etype + '\'' +
                ", estatus='" + estatus + '\'' +
                ", useraccount='" + useraccount + '\'' +
                ", addtime=" + addtime +
                '}';
    }
}
