package com.coolweather.android;

import android.os.SystemClock;
import android.util.Log;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void test(){
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url("http://guolin.tech/api/china").build();
//        client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                String response_str = response.body().string();
//                String tag = "JUnit";
//                System.out.println();
//            }
//        });
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(string);
////            JsonArray jsonArray = new JsonArray();
//
//            JSONArray jsonArray = new JSONArray(string);
//            for(int i = 0;i<jsonArray.length();i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                System.out.println(jsonObject.get("Name"));
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void demo(){

    }
}