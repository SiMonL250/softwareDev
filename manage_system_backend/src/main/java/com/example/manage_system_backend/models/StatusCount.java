package com.example.manage_system_backend.models;

public class StatusCount {
    private String etype;
    private String estatus;
    private Long count;

    public StatusCount(String etype, String estatus, Long count) {
        this.etype = etype;
        this.estatus = estatus;
        this.count = count;
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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
