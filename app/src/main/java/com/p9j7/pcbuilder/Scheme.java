package com.p9j7.pcbuilder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.material.button.MaterialButton;

import java.util.List;

@Entity
public class Scheme {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "price")
    private double price;
    //todo 怎么设计数据库和room
//    @ColumnInfo(name = "partList")

    @Ignore
    private List<Part> partList;
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
    public Scheme(String name, double price, List<Part> partList, String detail) {
        this.name = name;
        this.price = price;
        this.partList = partList;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Part> getPartList() {
        return partList;
    }

    public void setPartList(List<Part> partList) {
        this.partList = partList;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
