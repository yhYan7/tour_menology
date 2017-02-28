package com.example.administrator.tour_menology.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.Call;

public class gonglue_xiangqing extends AppCompatActivity {

    private TextView address,title,date,date2,toolbar_text;
    private ImageView img;
    private WebView mWebView;
    private ImageButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonglue_xiangqing);


        address = (TextView) findViewById(R.id.gonglue_address);
        title = (TextView) findViewById(R.id.gonglue_title);
        date = (TextView) findViewById(R.id.gonglue_date);
        date2 = (TextView) findViewById(R.id.gonglue_date2);
        img = (ImageView) findViewById(R.id.gonglue_img);
        mWebView = (WebView) findViewById(R.id.gonglue_web);
        toolbar_text= (TextView) findViewById(R.id.toolbar_title);
        toolbar_text.setText("攻略详情");
        mButton = (ImageButton) findViewById(R.id.toolbar_back);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String url ="http://bobtrip.com/tripcal/api/strategy/detail.action?strategyId=";
        String url2 = getIntent().getExtras().getString("id");

        String okurl =url+url2;

        OkHttpUtils.get().url(okurl).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {


            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 =jsonObject.getJSONObject("detail");
                    title.setText(jsonObject1.getString("title"));
                    address.setText(jsonObject1.getString("areaName"));
                    date.setText(jsonObject1.getString("publishDate"));
                    date2.setText(jsonObject1.getString("days"));
                    Glide.with(gonglue_xiangqing.this).load(jsonObject1.getString("logo")).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);


                    String F_URL =jsonObject1.getString("cont");
                    Log.d("url",jsonObject1.getString("cont"));
                    F_URL = F_URL.replaceAll("&amp;", "");
                    F_URL = F_URL.replaceAll("quot;", "\"");
                    F_URL = F_URL.replaceAll("lt;", "<");
                    F_URL = F_URL.replaceAll("gt;", ">");
//                    mWebView.loadDataWithBaseURL(null,F_URL,"text/html", "UTF-8", null);
                    mWebView.loadDataWithBaseURL(null, getNewContent(F_URL), "text/html", "UTF-8", null);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private String getNewContent(String htmltext){

        Document doc= Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
        }

        Log.d("VACK", doc.toString());
        return doc.toString();
    }
}
