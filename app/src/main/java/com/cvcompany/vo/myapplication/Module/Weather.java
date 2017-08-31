package com.cvcompany.vo.myapplication.Module;

/**
 * Created by vinh on 8/30/2017.
 */

public class Weather {

    private String date;
    private String description;
    private String tempTo;
    private String tempFrom;
    private String img;

    public Weather(String date, String description, String tempTo, String tempFrom, String img) {
        this.date = date;
        this.description = description;
        this.tempTo = tempTo;
        this.tempFrom = tempFrom;
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTempTo() {
        return tempTo;
    }

    public void setTempTo(String tempTo) {
        this.tempTo = tempTo;
    }

    public String getTempFrom() {
        return tempFrom;
    }

    public void setTempFrom(String tempFrom) {
        this.tempFrom = tempFrom;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
