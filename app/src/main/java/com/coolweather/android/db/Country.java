package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

public class Country extends LitePalSupport {
    private int id;//农村ID
    private String countryName;//农村名字
    private int countryCode;//农村代码
    private int cityId;//农村对应的城市ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
