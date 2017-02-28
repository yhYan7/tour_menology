package com.example.administrator.tour_menology.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.tour_menology.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.Call;

public class main_list2_xiangqing extends AppCompatActivity implements View.OnClickListener {
    private ImageButton toolbar;
    private WebView mWebView;
    private TextView title, address, date, name;
    private String URL = "http://bobtrip.com/tripcal/api/news/detail.action?newsId=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list2_xiangqing);
        initview();
        initDate();
    }

    private void initDate() {
        String url = getIntent().getExtras().getString("id");
        String okurl = URL + url;
        OkHttpUtils.get().url(okurl).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject detail = jsonObject.getJSONObject("detail");
                    title.setText(detail.getString("title"));
                    address.setText(detail.getString("source"));
                    date.setText(detail.getString("pubTime"));

                    String F_URL =detail.getString("cont");
                    Log.d("url",detail.getString("cont"));
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
    private void initview() {
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(this);
        name = (TextView) findViewById(R.id.toolbar_title);
        name.setText("行业资讯");
        title = (TextView) findViewById(R.id.main_List2_title);
        address = (TextView) findViewById(R.id.main_list2_address);
        date = (TextView) findViewById(R.id.main_List2_date);
        mWebView = (WebView) findViewById(R.id.main_list2_webview);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
