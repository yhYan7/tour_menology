package com.example.administrator.tour_menology.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.tour_menology.Activity.main_huodong_activity;
import com.example.administrator.tour_menology.Adapter.pinglunAdapter;
import com.example.administrator.tour_menology.Bean.pinglun_list;
import com.example.administrator.tour_menology.R;

import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class fabu_pinglun_Fragment extends Fragment {

    private EditText mText;
    private Button fabiao;
    private static final String ID = "id";


    private ListView mListView;
    private List<pinglun_list> mList;
    private RequestQueue mRequestQueue;
    private TextView mTextView;
    private String id =null;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;

    public static fabu_pinglun_Fragment newInstance(String id) {
        fabu_pinglun_Fragment fragment = new fabu_pinglun_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_fabu_pinglun, container, false);

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

        mText = (EditText) view.findViewById(R.id.pinglun_edit);
        fabiao = (Button) view.findViewById(R.id.pinglun_fab);
        Bundle bundle = getArguments();
        id = bundle.getString(ID);



        mListView = (ListView) view.findViewById(R.id.pinglun_list);
        mTextView = (TextView) view.findViewById(R.id.pinglun_text);

        fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://bobtrip.com/tripcal/api/discuss/create.action";

                Map<String, String> params = new HashMap<>();
                params.put("unitId", id);
                Log.d("ping", "memberId" + memberId);
                params.put("currentId", memberId);
                Log.d("ping", id);
                params.put("content", mText.getText().toString());
                Log.d("ping", "content" + mText.getText().toString());
                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(getContext(), "评论失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


        initVolley();
        return view;
    }

    private void initVolley() {
        mRequestQueue = Volley.newRequestQueue(getContext());
        String Url = "http://bobtrip.com/tripcal/api/discuss/list.action?unitId="+id+"&currentPage=1";

        JsonObjectRequest jsonObject = new JsonObjectRequest(Url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mList=new ArrayList<>();
                try {
                    JSONArray data =jsonObject.getJSONArray("list");
                    for (int i=0;i<data.length();i++){
                        JSONObject jsonObject1=data.getJSONObject(i);
                        String content=jsonObject1.getString("content");
                        String img =jsonObject1.getString("headIcon");
                        String title =jsonObject1.getString("nickName");
                        String date =jsonObject1.getString("time");
                        mList.add(new pinglun_list(content,date,img,title));
                    }

                    pinglunAdapter adapter = new pinglunAdapter(getContext(),mList);
                    mListView.setAdapter(adapter);

                    setListViewHeightBasedOnChildren(mListView);

                    mListView.setEmptyView(mTextView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mRequestQueue.add(jsonObject);
    }


    /**
     * @param listView
     */
    private void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
