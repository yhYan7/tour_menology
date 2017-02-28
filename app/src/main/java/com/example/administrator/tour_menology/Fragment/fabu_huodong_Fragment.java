package com.example.administrator.tour_menology.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.tour_menology.Activity.ain_list_xiangqing;
import com.example.administrator.tour_menology.Adapter.fabu_huodong_adapter;
import com.example.administrator.tour_menology.Bean.fabu_huodong_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.xiaosu.pulllayout.PullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class fabu_huodong_Fragment extends Fragment implements BasePullLayout.OnPullCallBackListener {

    private static final String ID = "id";

    private RelativeLayout r1,r2,r3,r4;
    private ListView mListView;
    private fabu_huodong_adapter mAdapter;
    private PullLayout mPullLayout;
    private List<fabu_huodong_list.list> mListDataTemp =new ArrayList<>();
    private List<fabu_huodong_list.list> mListData =new ArrayList<>();
    private String id ;
    private fabu_huodong_list mbean;
    private int ca =1;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;

    public static fabu_huodong_Fragment newInstance(String id){
        fabu_huodong_Fragment fragmentone=new fabu_huodong_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        fragmentone.setArguments(bundle);
        return fragmentone;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_fabu_huodong, container, false);
        //接受activity传过来的bundle

        Bundle bundle = getArguments();
        id = bundle.getString(ID);

        initView(view);
        initdata(1,true);
        initclick();
        return view;
    }

    private void initclick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getContext(), ain_list_xiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mListData.get(position).getActivityId());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });
    }

    private void initdata(int pager, final boolean isRefresh) {
        String URL= "http://bobtrip.com/tripcal/api/activity/unitlist.action?/memberId="+
                memberId+"&unitId="+id +"&currentPage="+pager;

        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPullLayout.finishPull();
                Toast.makeText(getContext(), "暂无数据", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(String response) {
                mbean = new Gson().fromJson(response,fabu_huodong_list.class);
                mListDataTemp =mbean.getList();
                int length =mListDataTemp.size();

                if (length >0){
                    if (isRefresh){
                        mListData.clear();
                        Iterator<fabu_huodong_list.list> iter = mListDataTemp.iterator();
                        while (iter.hasNext())
                            mListData.add(iter.next());
                        mAdapter.notifyDataSetChanged();
                        mListView.setSelection(0);
                    }
                    else {
                     //   mListData.addAll(mListDataTemp);
                   //     mAdapter.notifyDataSetChanged();

                        int size = mListView.getFirstVisiblePosition();
                        View view = mListView.getChildAt(0);
                        int top = view == null ? 0 : view.getTop();
                        mListData.addAll(mListDataTemp);
                        mAdapter.notifyDataSetChanged();
                        mListView.setSelectionFromTop(size, top - 1);
                    }
                    mPullLayout.finishPull();
                }
            }
        });
    }

    private void initView(View view) {
        mPullLayout = (PullLayout) view.findViewById(R.id.huodong_pulllayout);
        mPullLayout.setOnPullListener(this);
        mListView = (ListView) view.findViewById(R.id.huodong_list);
        mAdapter =new fabu_huodong_adapter(mListData,getContext());
        mListView.setAdapter(mAdapter);

        //获取登录用户信息
        utils = new PreferenceUtils(getContext());

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(getContext(), "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(),
                    denglu.class);
            startActivity(intent);

        }else
            memberId = map.get("memberId").toString();
    }


    @Override
    public void onRefresh() {
        initdata(1,false);
    }

    @Override
    public void onLoad() {
        initdata(ca++,true);
    }



}
