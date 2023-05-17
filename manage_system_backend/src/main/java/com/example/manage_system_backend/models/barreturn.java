package com.example.manage_system_backend.models;


import java.util.List;

public class barreturn{
    private List<String> etype;
    private List<Integer> normal;
    private List<Integer> broke;

    private int sum;
    public barreturn(List<String> etype, List<Integer> normal, List<Integer> broke) {
        this.etype = etype;
        this.normal = normal;
        this.broke = broke;
    }

    @Override
    public String toString() {
        return "barreturn{" +
                "etype=" + etype +
                ", normal=" + normal +
                ", broke=" + broke +
                '}';
    }

    public List<String> getEtype() {
        return etype;
    }

    public void setEtype(List<String> etype) {
        this.etype = etype;
    }

    public List<Integer> getNormal() {
        return normal;
    }

    public void setNormal(List<Integer> normal) {
        this.normal = normal;
    }

    public List<Integer> getBroke() {
        return broke;
    }

    public void setBroke(List<Integer> broke) {
        this.broke = broke;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}