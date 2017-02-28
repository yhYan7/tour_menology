package com.example.administrator.tour_menology.date;

import android.util.Log;

import com.example.administrator.tour_menology.Bean.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小风 on 2016/8/24.
 * 解析工具类
 */
public class MyDownUtils {

    public static List<bean> getList(String json) throws JSONException {
        List<bean> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray listArray = jsonObject.getJSONArray("list");
        for (int i = 0; i < listArray.length(); i++) {
            JSONObject listObject = listArray.getJSONObject(i);
            String areaName = listObject.getString("areaName");
            String areaId = listObject.getString("areaId");
            list.add(new bean(areaName, areaId));
        }
        Log.d("tag", "size:" + list.size() + "");
        return list;
    }

    public static String parseJson(InputStream inputStream) throws IOException, JSONException {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        return (stringBuffer.toString());
    }
}
