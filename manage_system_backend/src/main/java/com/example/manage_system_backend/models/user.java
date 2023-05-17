package com.example.manage_system_backend.models;

public class user {
    private String useraccount;
    private String password;
    private String userprivilege;

    public user(String useraccount, String password, String userprivilege) {
        this.useraccount = useraccount;
        this.password = password;
        this.userprivilege = userprivilege;
    }

    public user() {
    }

    public String getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public user(String account, String password) {
        this.useraccount = account;
        this.password = password;
    }


    public String getUserprivilege() {
        return userprivilege;
    }

    public void setUserprivilege(String userprivillege) {
        this.userprivilege = userprivillege;
    }

    @Override
    public String toString() {
        return "user{" +
                "useraccount='" + useraccount + '\'' +
                ", password='" + password + '\'' +
                ", userprivilege='" + userprivilege + '\'' +
                '}';
    }
}
