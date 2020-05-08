package com.p9j7.pcbuilder.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Scheme {
    @PrimaryKey(autoGenerate = true)
    private int schemeId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "price")
    private double price;
    @ColumnInfo(name = "detail")
    private String detail;

    public Scheme() {
    }

    @Ignore
    public Scheme(String name, double price, String detail) {
        this.name = name;
        this.price = price;
        this.detail = detail;
    }

    @Ignore
    public Scheme(int schemeId, String name, double price, String detail) {
        this.schemeId = schemeId;
        this.name = name;
        this.price = price;
        this.detail = detail;
    }


    public int getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(int schemeId) {
        this.schemeId = schemeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
