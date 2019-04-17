package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * demo
 * "daliy_forecast":[
 * {
 *  "date":"2018-01-02",
 *   "cond":{
 *       "txt_d":"阵雨"
 *   },
 *   "tmp":{
 *       "max":"34",
 *       "min":"27"
 *   }
 * },{
 *  "date":"2018-01-03",
 *   "cond":{
 *       "txt_d":"多云"
 *   },
 *   "tmp":{
 *       "max":"33",
 *       "min":"26"
 *   }
 * }
 * ]
 */

public class ForeCast {
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature{
        public String max;
        public String min;
    }
    public class More{
        @SerializedName("txt_d")
        public String info;
    }
}
