package com.example.administrator.tour_menology.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.administrator.tour_menology.Adapter.pinglunAdapter;
import com.example.administrator.tour_menology.Adapter.zan_adapter;
import com.example.administrator.tour_menology.Bean.pinglun_list;
import com.example.administrator.tour_menology.Bean.zan_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.HorizontalListView;
import com.example.administrator.tour_menology.date.henglistview;
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

public class youji_pinglun extends AppCompatActivity {
    private TextView toolbart,xihuan,jianjie,date,address,tiaomu;
    private ImageButton toolbar;
    private henglistview mListView_h;
    private ImageView imgtitle;
    private List<zan_list>mList;
    private RequestQueue mRequestQueue;
    private ListView mListView;
    private List<pinglun_list> mList2;
    private EditText mEditText;
    private Button mButton;

    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youji_pinglun);

        toolbart = (TextView) findViewById(R.id.toolbar_title);
        toolbart.setText("评论");
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //获取登录用户信息
        utils = new PreferenceUtils(youji_pinglun.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");
            memberId = map.get("memberId").toString();

        initview();

        initdata();

        initdate2();

        initpinlun();

    }

    private void initpinlun() {
        String travelContId =getIntent().getExtras().getString("travelContId");

        String url ="http://bobtrip.com/tripcal/api/travel/reviewlist.action?travelContId="+travelContId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback(){

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                mList2 =new ArrayList<pinglun_list>();
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("list");
                    tiaomu.setText(jsonObject.getString("count"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String content = jsonObject1.getString("content");
                        String img = jsonObject1.getString("headIcon");
                        String title = jsonObject1.getString("nickName");
                        String date = jsonObject1.getString("reviewDate");
                        mList2.add(new pinglun_list(content, date, img, title));
                    }
                    pinglunAdapter adapter = new pinglunAdapter(youji_pinglun.this, mList2);
                    mListView.setAdapter(adapter);
              //      setListViewHeightBasedOnChildren(mListView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private void initdate2() {
        mRequestQueue = Volley.newRequestQueue(youji_pinglun.this);
        String travelContId =getIntent().getExtras().getString("travelContId");
        String travelId =getIntent().getExtras().getString("travelId");

        String url ="http://bobtrip.com/tripcal/api/travel/enjoycont.action?travelId="+travelId+"&travelContId="+travelContId;
        JsonObjectRequest jsonObject = new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mList =new ArrayList<>();
                try {
                    xihuan.setText(jsonObject.getString("totalCount"));
                    JSONArray jsonArray =jsonObject.getJSONArray("list");
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 =jsonArray.getJSONObject(i);
                        String menberId =jsonObject1.getString("memberId");
                        JSONObject jsonObject2=jsonObject1.getJSONObject("member");
                        String imgid =jsonObject2.getString("headIcon");
                        mList.add(new zan_list(imgid,menberId));
                    }
                    zan_adapter adapter =new zan_adapter(youji_pinglun.this,mList);
                    mListView_h.setAdapter(adapter);


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

    private void initdata() {
        String travelContId = getIntent().getExtras().getString("travelContId");
        String travelId = getIntent().getExtras().getString("travelId");

        String url = "http://bobtrip.com/tripcal/api/travel/detailCont.action?travelId=" + travelId + "&travelContId=" + travelContId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {


            @Override
            public void onError(Call call, Exception e) {


            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONObject jsonObject1 =jsonObject.getJSONObject("detail");
                    if (jsonObject1.getString("travelType").equals("随记"))
                        jianjie.setText(jsonObject1.getString("content"));
                   else  if(jsonObject1.getString("travelType").equals("图片")){
                        String url =jsonObject1.getString("content");
                        Glide.with(youji_pinglun.this).load("http://bobtrip.com/tripcal"+url).into(imgtitle);
                        jianjie.setText(jsonObject1.getString("imgDesc"));
                    }
                    date.setText(jsonObject1.getString("travelDate"));
                    address.setText(jsonObject1.getString("address"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initview() {
        xihuan = (TextView) findViewById(R.id.yjxq_xihuan);
        jianjie = (TextView) findViewById(R.id.yjxq_text);
        date = (TextView) findViewById(R.id.yjxq_date);
        address = (TextView) findViewById(R.id.yjxq_address);
        imgtitle = (ImageView) findViewById(R.id.yjxq_img);
        mListView_h = (henglistview) findViewById(R.id.yjpl_listview_h);
        mListView = (ListView) findViewById(R.id.yjpl_list);
        tiaomu = (TextView) findViewById(R.id.yjxq_pltiaomu);

        mEditText = (EditText) findViewById(R.id.yjpl_edt);
        mButton = (Button) findViewById(R.id.yjpl_btn);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mEditText.getText().toString().equals("")){
                    Toast.makeText(youji_pinglun.this,"请输入评论内容",Toast.LENGTH_SHORT).show();
                }else {

                String url ="http://bobtrip.com/tripcal/api/travel/reviewCreate.action";
                Map<String, String> params = new HashMap<>();
                /**
                 travelId	是	游记Id
                 travelContId	是	游记随记id
                 memberId	是	评论人id
                 content	是	评论内容
                 */
                String travelId =getIntent().getExtras().getString("travelId");
                String travelContId =getIntent().getExtras().getString("travelContId");
                params.put("travelId",travelId);
                params.put("travelContId",travelContId);
                params.put("memberId",memberId);
                params.put("content",mEditText.getText().toString());

                OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(youji_pinglun.this,"评论失败",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(youji_pinglun.this,"评论成功",Toast.LENGTH_SHORT).show();
                        mEditText.getText().clear();
                        initpinlun();
                    }
                });

            }

            }
        });


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
