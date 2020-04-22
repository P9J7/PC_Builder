package com.p9j7.pcbuilder;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Part {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String imgPath;
    private String title;
    private double price;
    private double score;
    private String detail;

    public Part(int id, String imgPath, String title, double price, double score, String detail) {
        this.id = id;
        this.imgPath = imgPath;
        this.title = title;
        this.price = price;
        this.score = score;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
