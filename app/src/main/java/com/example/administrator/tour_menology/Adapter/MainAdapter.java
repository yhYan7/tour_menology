package com.example.administrator.tour_menology.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/7/15.
 */
public class MainAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;

    public MainAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
       this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
