package com.example.manage_system_backend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class MonthCount {
    private int month;
    private int count;

    public MonthCount(Long month, Integer count) {
        this.month = Integer.parseInt(month.toString());
        this.count = count;
    }
}
