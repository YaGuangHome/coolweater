package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

/*
* 城市表*/
public class City extends LitePalSupport {
    private int id;//城市id
    private String cityName;//城市名字
    private String cityCode;//城市代码
    private int provinceId;//城市所在的省份ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
