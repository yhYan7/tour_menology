package com.example.administrator.tour_menology.Main_Fragment;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Activity.jfshangcheng;
import com.example.administrator.tour_menology.Activity.myfriend;
import com.example.administrator.tour_menology.Activity.qiandaoa;
import com.example.administrator.tour_menology.Activity.renwushuoming;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.my_fragment_activity.Calendarhuodong;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.my_fragment_activity.haoyoudongtai;
import com.example.administrator.tour_menology.my_fragment_activity.myfans;
import com.example.administrator.tour_menology.my_fragment_activity.myfenxiang;
import com.example.administrator.tour_menology.my_fragment_activity.myguanzhu;
import com.example.administrator.tour_menology.my_fragment_activity.mypinglu;
import com.example.administrator.tour_menology.my_fragment_activity.myshequ;
import com.example.administrator.tour_menology.my_fragment_activity.myshoucang;
import com.example.administrator.tour_menology.my_fragment_activity.myyouji;
import com.example.administrator.tour_menology.my_fragment_activity.pcOne;
import com.example.administrator.tour_menology.my_fragment_activity.pcTwo;
import com.example.administrator.tour_menology.my_fragment_activity.personalActivity;
import com.example.administrator.tour_menology.my_fragment_activity.setupActivity;
import com.example.administrator.tour_menology.utils.HttpUtil;
import com.example.administrator.tour_menology.utils.MyConfig;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.example.administrator.tour_menology.utils.Util;
import com.example.administrator.tour_menology.widget.CircularImage;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


/**
 * A simple {@link Fragment} subclass.
 */
public class my_Fragment extends Fragment {

    private TextView toolbartext;
    private ImageButton toolbar_back;
    private ImageButton setup;
    private RelativeLayout search_allricheng,my_topc,jifen,renwu,shoucang,weiqian,haoyou,qiandao,shequ,huodong,fenxiang,pinglun,youji,my_haoyoudongtai;
    private Button daiban,yiwancheng,weiwancheng,jinxingzhong,wangqi;

    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private String httpPost;

    private TextView textView3,textView4,textAddr,textxintiao,textView6,textView8,textView9,dengji;
    private ImageView imageView;

    private JSONObject  jsonObject1;

    public my_Fragment() {
        // Required empty public constructor
    }
    private ImageView nan,nv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_my, container, false);

        final View view = inflater.inflate( R.layout.fragment_my, container, false);

        init(view);
        return view;
    }


    private void init(View view){

       // CircularImage imageView = (CircularImage) view.findViewById(R.id.imageView);
        //imageView.setImageResource(R.mipmap.face);

        toolbartext= (TextView) view.findViewById(R.id.toolbar_title);
        toolbartext.setText("我的");
        shoucang = (RelativeLayout) view.findViewById(R.id.my_rel1);
        shequ = (RelativeLayout) view.findViewById(R.id.my_rel2);
        huodong = (RelativeLayout) view.findViewById(R.id.my_rel3);
        imageView = (ImageView) view.findViewById(R.id.zhuye_img);
        textView3 = (TextView) view.findViewById(R.id.textView3);
//        textView4 = (TextView) view.findViewById(R.id.textView4);
        textAddr = (TextView) view.findViewById(R.id.zhuye_city);
        textxintiao = (TextView) view.findViewById(R.id.zhuye_xintiao);
        textView6 = (TextView) view.findViewById(R.id.zhuye_dingyue);
        textView8 = (TextView) view.findViewById(R.id.zhuye_guanzhuren);
        textView9 = (TextView) view.findViewById(R.id.zhuye_fensi);
        dengji = (TextView) view.findViewById(R.id.geren_dengji);




        qiandao = (RelativeLayout) view.findViewById(R.id.my_qiandao);
        weiqian = (RelativeLayout) view.findViewById(R.id.my_qiandao_wei);
        search_allricheng = (RelativeLayout)  view.findViewById(R.id.search_allricheng);
        search_allricheng.setOnClickListener(mListener);




        toolbar_back = (ImageButton) view.findViewById(R.id.toolbar_back);
        setup = (ImageButton) view.findViewById(R.id.btn_setup);

        fenxiang = (RelativeLayout) view.findViewById(R.id.my_fenxiang);
        pinglun = (RelativeLayout) view.findViewById(R.id.my_pinglun);
        youji  = (RelativeLayout) view.findViewById(R.id.my_youji);
        my_topc = (RelativeLayout) view.findViewById(R.id.my_topc);
        my_haoyoudongtai = (RelativeLayout) view.findViewById(R.id.my_haoyoudongtai);
        daiban = (Button) view.findViewById(R.id.btn_daiban);
        yiwancheng = (Button) view.findViewById(R.id.btn_yiwancheng);
        weiwancheng = (Button) view.findViewById(R.id.btn_weiwancheng);
        jinxingzhong = (Button) view.findViewById(R.id.btn_jinxingzhong);
        wangqi = (Button) view.findViewById(R.id.btn_wangqi);
        jifen = (RelativeLayout) view.findViewById(R.id.my_jifen);
        renwu = (RelativeLayout) view.findViewById(R.id.my_renwu);
        haoyou = (RelativeLayout) view.findViewById(R.id.my_friends);

        renwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),renwushuoming.class);
                startActivity(intent);
            }
        });
        nan = (ImageView) view.findViewById(R.id.zhuye_nan);
        nv = (ImageView) view.findViewById(R.id.zhuye_nv);

        my_haoyoudongtai.setOnClickListener(mListener);
        haoyou.setOnClickListener(mListener);
        pinglun.setOnClickListener(mListener);
        fenxiang.setOnClickListener(mListener);
        youji.setOnClickListener(mListener);
        shoucang.setOnClickListener(mListener);
        shequ.setOnClickListener(mListener);
        huodong.setOnClickListener(mListener);
        daiban.setOnClickListener(mListener);
        yiwancheng.setOnClickListener(mListener);
        weiwancheng.setOnClickListener(mListener);
        jinxingzhong.setOnClickListener(mListener);
        wangqi.setOnClickListener(mListener);
        my_topc.setOnClickListener(mListener);
        setup.setOnClickListener(mListener);
        jifen.setOnClickListener(mListener);







        weiqian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),qiandaoa.class);
                startActivity(intent);
            }
        });



        qiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),qiandaoa.class);
                startActivity(intent);
            }
        });




        //加载数据
        data();

      //调整头部布局
        DisplayMetrics dm2 = getResources().getDisplayMetrics();

        System.out.println("heigth2 : " + dm2.heightPixels);

        System.out.println("width2 : " + dm2.widthPixels);

        Log.d("hei", String.valueOf(dm2.heightPixels));


        String url ="http://bobtrip.com/tripcal/api/signin.action?memberId="+memberId;


        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =new JSONObject(response);

                    boolean qian =jsonObject.getBoolean("flag");
                    if (qian==true){
                        weiqian.setVisibility(View.GONE);
                        qiandao.setVisibility(View.VISIBLE);
                    }else {
                        qiandao .setVisibility(View.GONE);
                        weiqian.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });




    }

    public void data(){

        //获取登录用户信息
        utils = new PreferenceUtils(getActivity());
        Map<String, Object> map = (Map<String, Object>) utils
                .getPreference("login");
        if(map==null || map.size()<1) {
            Toast.makeText(getActivity(), "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(),
                    denglu.class);
            startActivity(intent);
        } else {
            memberId = map.get("memberId").toString();
            getData();
        }

    }

    public void getData(){
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("memberId", memberId);
                        Log.i("", "====memberId-->>" + memberId);
                        httpPost = HttpUtil.http(MyConfig.httpurl
                                + "/member/detail.action", params);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    handler.sendEmptyMessage(0x10);
                }
            }).start();
        }catch (Exception e){
            Toast.makeText(getContext(), "连接服务器失败,请检查您的网络并重试",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x10:
                    try {

                        JSONObject jsonObject = new JSONObject(httpPost);
                        if ("0".equals(jsonObject.getString("errcode"))) {
                            jsonObject1 =  (JSONObject) jsonObject.get("detail");
                            String nickName = jsonObject1.getString("nickName");
                            String sex = jsonObject1.getString("sex");
                            String areaname = jsonObject1.getString("areaname");
                            String creed = jsonObject1.getString("creed");
                            String countCollect = jsonObject1.getString("attentionCount");
                            String countFocus = jsonObject1.getString("countFocus");
                            String fansCount = jsonObject1.getString("fansCount");
                            String dengj = jsonObject1.getString("rank");
                            dengji.setText(dengj);

                            Glide.with(getContext()).load(jsonObject1.getString("headIcon")).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);


//                            new Thread() {
//                                @Override
//                                public void run() {
//
//                                    if (jsonObject1.has("headIcon")) {
//                                        Bitmap bitmap = null;
//                                        Log.i("", "====走没走看看3-->>");
//
//                                        try {
//                                            Log.i("", "====解析的图片-->>" + jsonObject1.getString("headIcon"));
//                                            String url = jsonObject1.getString("headIcon");
//                                            bitmap = Util.getbitmap(url);
//                                        } catch (JSONException e) {
//                                            Log.i("", "====图片解析错误-->>" + e);
//                                        }
//                                        Message msg = new Message();
//                                        msg.obj = bitmap;
//                                        msg.what = 1;
//                                        mHandler.sendMessage(msg);
//                                    }
//                                }
//                            }.start();

                            Log.i("", "====走没走看看5-->>");
                            textView3.setText(nickName);


                            if (sex.equals("男")){
                                nv.setVisibility(View.GONE);
                            }else {
                                nan.setVisibility(View.GONE);
                            }
                            textAddr.setText(areaname);
                            if("".equals(creed)){
                                textxintiao.setText("不一样的日历，不一般的旅行。");
                            }else {
                                textxintiao.setText(creed);
                            }
                            textView8.setText(countCollect);
                            textView6.setText(countFocus);
                            textView9.setText(fansCount);

                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 登录失败
                            Toast.makeText(getContext(),
                                    "获取信息失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
             if (msg.what == 1) {
                Bitmap bitmap = (Bitmap) msg.obj;
                 imageView.setImageBitmap(bitmap);
                 imageView.setVisibility(View.VISIBLE);
            }
        }
    };

    View.OnClickListener mListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_setup:
                    //设置
                    Intent intent = new Intent(getActivity(),
                            setupActivity.class);
                    startActivity(intent);
                    break;

                case R.id.my_topc:
                    //个人中心
                    Intent intent2 = new Intent(getActivity(),
                            personalActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.my_fenxiang:

                    //我的收藏
                    Intent intent8 = new Intent(getActivity(),
                            myshoucang.class);
                    intent8.putExtra("memberId",memberId);
                    startActivity(intent8);
                    break;

                case R.id.my_pinglun:
                    //我的评论
                    Intent intent12 = new Intent(getActivity(),
                            mypinglu.class);
                    intent12.putExtra("memberId",memberId);
                    startActivity(intent12);
                    break;
                case R.id.my_youji:
                    //我的游记
                    Intent intent13 = new Intent(getActivity(),
                            myyouji.class);
                    intent13.putExtra("memberId",memberId);
                    startActivity(intent13);
                    break;
                case R.id.my_rel1:
                    //社区
                    Intent intent9 = new Intent(getActivity(),
                            myshequ.class);
                    intent9.putExtra("memberId",memberId);
                    startActivity(intent9);
                    break;

                case R.id.my_rel2:
                    //我的关注
                   Intent intent11 = new Intent(getActivity(),
                            myguanzhu.class);
                    intent11.putExtra("memberId",memberId);
                    startActivity(intent11);
                    break;
                case R.id.my_rel3:
                    //粉丝
                   Intent intent10 = new Intent(getActivity(),
                            myfans.class);
                    intent10.putExtra("memberId",memberId);
                    startActivity(intent10);
                    break;
                case R.id.my_haoyoudongtai:
                    //好友动态
                    Intent intent14 = new Intent(getActivity(),haoyoudongtai.class);
                    intent14.putExtra("memberId", memberId);
                    startActivity(intent14);
                    break;

                case R.id.btn_daiban:
                    //待办按钮
                    Intent intent3 = new Intent(getActivity(),pcOne.class);
                    intent3.putExtra("type", "1");
                    intent3.putExtra("memberId", memberId);
                    startActivity(intent3);
                    break;
                case R.id.btn_yiwancheng:
                    //已完成
                    Intent intent4 = new Intent(getActivity(),pcOne.class);
                    intent4.putExtra("type", "2");
                    intent4.putExtra("memberId", memberId);
                    startActivity(intent4);
                    break;
                case R.id.btn_weiwancheng:
                    //未完成
                    Intent intent5 = new Intent(getActivity(),pcTwo.class);
                    intent5.putExtra("type","3");
                    intent5.putExtra("memberId", memberId);
                    startActivity(intent5);
                    break;
                case R.id.btn_jinxingzhong:
                    //进行中
                    Intent intent6 = new Intent(getActivity(),pcTwo.class);
                    intent6.putExtra("type","1");
                    intent6.putExtra("memberId", memberId);
                    startActivity(intent6);
                    break;
                case R.id.btn_wangqi:
                    //往期
                    Intent intent7 = new Intent(getActivity(),pcTwo.class);
                    intent7.putExtra("type","2");
                    intent7.putExtra("memberId", memberId);
                    startActivity(intent7);
                    break;

                case R.id.my_jifen:
                    //往期
                    Intent intent81 = new Intent(getActivity(),jfshangcheng.class);

                    startActivity(intent81);
                    break;
                case R.id.search_allricheng:
                    //查看全部日程
                    Intent intent15 = new Intent(getActivity(), Calendarhuodong.class);

                    startActivity(intent15);
                    break;
                case R.id.my_friends:
                    //好友
                    Intent intent16 = new Intent(getActivity(), myfriend.class);

                    startActivity(intent16);
                    break;
                default:
                    break;
            }

        }
        };

    /**
     * 根据图片的url路径获得Bitmap对象
     * @param url
     * @return
     */
    private Bitmap geturl(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }


}
