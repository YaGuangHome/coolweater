package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/***
 * demo
 * "basic":{
 *     "city":"苏州".
 *     "id":"CN101190401",
 *     "update":{
 *         "loc":"2016-08-08 21:48"
 *     }
 * }
 */
public class Basic {
    @SerializedName("city")
    public String cityName;//城市名字

    @SerializedName("id")
    public String weatherId;//天气Id

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;//当前更新时间
    }
}
