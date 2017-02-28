package com.example.administrator.tour_menology.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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

public class chakanxiangqing extends AppCompatActivity {
    private ImageButton toolback;
    private WebView mWebView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chakanxiangqing);
        toolback = (ImageButton) findViewById(R.id.toolbar_back);
        toolback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTextView = (TextView) findViewById(R.id.toolbar_title);
        mTextView.setText("活动详情");
        mWebView = (WebView) findViewById(R.id.main_huodong_webview);
        String url = getIntent().getExtras().getString("id");

        String URL = "http://bobtrip.com/tripcal/api/activity/details.action?activityId=" + url;


        OkHttpUtils.get().url(URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("detail");
                    String F_URL = jsonObject1.getString("actCont");
                    F_URL = F_URL.replaceAll("&amp;", "");
                    F_URL = F_URL.replaceAll("quot;", "\"");
                    F_URL = F_URL.replaceAll("lt;", "<");
                    F_URL = F_URL.replaceAll("gt;", ">");
                    mWebView.loadDataWithBaseURL(null, getNewContent(F_URL), "text/html", "UTF-8", null);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }

        Log.d("VACK", doc.toString());
        return doc.toString();
    }

}
