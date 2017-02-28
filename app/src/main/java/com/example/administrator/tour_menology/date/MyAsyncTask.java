package com.example.administrator.tour_menology.date;

import android.os.AsyncTask;

import com.example.administrator.tour_menology.Bean.bean;

import org.json.JSONException;

import java.util.List;

/**
 * Created by 小风 on 2016/8/24.
 */
public class MyAsyncTask extends AsyncTask<String, Void, List<bean>> {
    private GetList mGetList;
    private int tag = -1;

    public interface GetList {
        void setList(List<bean> list, int tag);
    }

    public MyAsyncTask(GetList getList, int tag) {
        mGetList = getList;
        this.tag = tag;
    }

    @Override
    protected List<bean> doInBackground(String... params) {
        try {
            List<bean> list = MyDownUtils.getList(params[0]);
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<bean> list) {
        super.onPostExecute(list);
        mGetList.setList(list, tag);
    }
}
