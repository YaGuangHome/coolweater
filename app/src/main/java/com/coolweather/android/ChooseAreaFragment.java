package com.coolweather.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.android.db.City;
import com.coolweather.android.db.Country;
import com.coolweather.android.db.Province;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTRY = 2;
    private static final String provinceType = "province";
    private static final String cityType = "city";
    private static final String countryType = "country";
    private ArrayAdapter<String> adapter;//View组件适配器
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private List<String> dataList = new ArrayList<String>();

    private List<Province> provinceList = new ArrayList<Province>();//省份列表
    private List<City> cityList = new ArrayList<City>();//城市列表
    private List<Country> countryList = new ArrayList<Country>();//乡镇列表
    private int currentLevel ;//当前选中的级别
    private Province selectProvince;//当前选中的省份
    private City selectCity;//当前选中的城市
    private Country selectCountry;//当前选中的乡镇

    private ProgressDialog progressDialog;//进度条对话框



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area,container,false);
        titleText = view.findViewById(R.id.title_text);
        backButton = view.findViewById(R.id.back_button);
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,dataList);//产生适配器
        listView.setAdapter(adapter);//为ListView组件添加适配器
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel == LEVEL_PROVINCE){
                    selectProvince = provinceList.get(position);
                    queryCitys();
                }else if(currentLevel == LEVEL_CITY){
                    selectCity = cityList.get(position);
                    queryCountrys();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel == LEVEL_COUNTRY){
                    queryCitys();
                }else if(currentLevel == LEVEL_CITY){
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询所有的省份信息，先从数据库查询，如果数据库没有，再从服务器查询
     */
    private void queryProvinces(){
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = LitePal.findAll(Province.class);
        if(provinceList.size()>0){
            dataList.clear();
            for(Province province : provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }else{
            String adress = "http://guolin.tech/api/china";
            queryFromServer(adress,provinceType);
        }
    }

    /**
     * 查询所有的省份信息，先从数据库查询，如果数据库没有，再从服务器查询
     */
    private void queryCitys(){
        titleText.setText(selectProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = LitePal.where("provinceId=?",String.valueOf(selectProvince.getId())).find(City.class);
        if(cityList.size()>0){
            dataList.clear();
            for(City city:cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else{
            int provinceCode = selectProvince.getProvinceCode();
            queryFromServer("http://guolin.tech/api/china/"+provinceCode,cityType);
        }
    }

    /**
     * 查询所有的乡镇信息，先从数据库查询，如果数据库没有，再从服务器查询
     */
    private void queryCountrys(){
        titleText.setText(selectCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countryList = LitePal.where("cityId=?",String.valueOf(selectCity.getId())).find(Country.class);
        if(countryList.size()>0){
            dataList.clear();
            for(Country country : countryList){
                dataList.add(country.getCountryName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTRY;
        }else{
            int provinceCode = selectProvince.getProvinceCode();
            int cityCode = selectCity.getCityCode();
            String address = "http://guolin.tech/api/china/"+provinceCode+"/"+cityCode;
            queryFromServer(address,countryType);
        }
    }
    /**
     * 根据传入的地址和类型，查询数据省市县 数据
     * @param address
     * @param type province city country
     */
    private void queryFromServer(String address,final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //通过OnUiThread方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败！",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Log.e("jiazai",responseText);
                boolean result = false;
                if(type.equals(provinceType)){
                    result = Utility.handleProvinceResponse(responseText);
                }else if(type.equals(cityType)){
                    result = Utility.handleCityResponse(responseText
                    ,selectProvince.getId());
                }else if(type.equals(countryType)){
                    result = Utility.handleCountryResponse(responseText,selectCity.getId());
                }
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if(type.equals(provinceType)){
                                queryProvinces();
                            }else if(type.equals(cityType)){
                                queryCitys();
                            }else if(type.equals(countryType)){
                                queryCountrys();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 显示进度条
     */
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭对话框
     */
    private void closeProgressDialog(){
        if(progressDialog!=null)
            progressDialog.dismiss();
    }
}
