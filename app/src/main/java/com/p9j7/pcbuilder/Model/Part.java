package com.p9j7.pcbuilder.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Part {
    @PrimaryKey(autoGenerate = true)
    private int partId;
    private int schemeForeId;
    //当做商品id使用
    private String imgPath;
    private String title;
    private double price;
    @ColumnInfo(defaultValue = "0")
    private double score;
    private String detail;
    private String category;

    public Part(int partId, int schemeForeId, String title, double price) {
        this.partId = partId;
        this.schemeForeId = schemeForeId;
        this.title = title;
        this.price = price;
    }

    @Ignore
    public Part(int partId, String imgPath, String title, double price) {
        this.partId = partId;
        this.imgPath = imgPath;
        this.title = title;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public int getSchemeForeId() {
        return schemeForeId;
    }

    public void setSchemeForeId(int schemeForeId) {
        this.schemeForeId = schemeForeId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @NonNull
    @Override
    public String toString() {
        return title.toString();
    }
}
