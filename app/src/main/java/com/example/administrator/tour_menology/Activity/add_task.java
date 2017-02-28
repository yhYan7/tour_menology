package com.example.administrator.tour_menology.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.DateTimePickDialogUtil;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class add_task extends AppCompatActivity {

    private EditText startDateTime;
    private EditText endDateTime;
    private TextView toolbartext, add_title, add_address, add_beizhu;
    private ImageButton toolbar;
    private Button mButton;
    private Spinner mSpinner;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
    private String initStartDateTime = formatter.format(curDate);
    private String initEndDateTime = formatter.format(curDate);
//    date.setText(str);
////
//    private String initStartDateTime = "2016年8月6日 14:44"; // 初始化开始时间
//
//
//
//    private String initEndDateTime = "2016年8月6日 17:44"; // 初始化结束时间



    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;

    String str =null;
    //联网请求的工具
    private HttpUtils mHttpUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        initHttpUtils();
        initView();
    }

    private void initHttpUtils() {


        mHttpUtils = new HttpUtils(5000);//5000-->联网超时时间-->可以无参

        //设置缓存大小
        int size = (int) (Runtime.getRuntime().totalMemory() / 8);
        mHttpUtils.configHttpCacheSize(size);

        //设置编码格式
        mHttpUtils.configResponseTextCharset("UTF-8");

        //设置重连次数,如果链接服务失败,再次尝试
        mHttpUtils.configRequestRetryCount(3);

        //设置多线程迸发(线程池)
        mHttpUtils.configRequestThreadPoolSize(4);

        //设置缓存数据失效的时间(设置30分钟后缓存数据失效)
        mHttpUtils.configDefaultHttpCacheExpiry(1000 * 60 * 30);

    }

    private void initView() {

        //获取登录用户信息
        utils = new PreferenceUtils(add_task.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        if (map == null || map.size() < 1) {
            Toast.makeText(add_task.this, "用户尚未登录",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(add_task.this,
                    denglu.class);
            startActivity(intent);

        }else
            memberId = map.get("memberId").toString();

        // 两个输入框        startDateTime = (EditText) findViewById(R.id.inputDate);

        startDateTime = (EditText) findViewById(R.id.inputDate);
        endDateTime = (EditText) findViewById(R.id.inputDate2);

        startDateTime.setText(initStartDateTime);
        endDateTime.setText(initEndDateTime);
        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("添加任务");

        mButton = (Button) findViewById(R.id.add_button);
        add_title = (TextView) findViewById(R.id.add_title);
        add_address = (TextView) findViewById(R.id.add_address);
        add_beizhu = (TextView) findViewById(R.id.add_beizhu);

        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSpinner = (Spinner) findViewById(R.id.add_spinner);



        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.add_spinner);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        //绑定 Adapter到控件
        mSpinner.setAdapter(_Adapter);


//        //点击添加按钮,触发添加事件
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "http://bobtrip.com/tripcal/api/task/add.action";
///**
// * memberId	是	会员
// title	是	任务标题
// address	是	地址
// priority	是	优先级，如：高,中,低;
// remindTime	是	提醒时间
// endTime	是	结束时间
// remark	是	备注
// */
//                RequestParams params =new RequestParams();
//
//                params.addBodyParameter("memberId",memberId);
//                params.addBodyParameter("title",add_title.toString());
//                params.addBodyParameter("address",add_address.toString());
//
//                params.addBodyParameter("priority",str);
//
//
//
//            }
//        });


        //获取spinner 选中的值
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              str = (String) mSpinner.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//  事件选择器
        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        add_task.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

        endDateTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        add_task.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(endDateTime);
            }
        });



        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://bobtrip.com/tripcal/api/task/add.action";
/**
 * memberId	是	会员
 title	是	任务标题
 address	是	地址
 priority	是	优先级，如：高,中,低;
 remindTime	是	提醒时间
 endTime	是	结束时间
 remark	是	备注
 */
                RequestParams params =new RequestParams();

                params.addBodyParameter("memberId",memberId);
                params.addBodyParameter("title",add_title.getText().toString());
                params.addBodyParameter("address",add_address.getText().toString());
                params.addBodyParameter("priority",str);
                params.addBodyParameter("remindTime",startDateTime.getText().toString());
                params.addBodyParameter("endTime",endDateTime.getText().toString());
                params.addBodyParameter("remark",add_beizhu.getText().toString());


//                Log.d("loog","memberId"+id.getValue());
//                Log.d("loog","title"+add_title.getText().toString());
//                Log.d("loog","address"+add_address.getText().toString());
//                Log.d("loog","remindTime"+startDateTime.getText().toString());
//                Log.d("loog","endTime"+endDateTime.getText().toString());
//                Log.d("loog","remark"+add_beizhu.getText().toString());


                mHttpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Toast.makeText(add_task.this, "添加成功", Toast.LENGTH_SHORT).show();
                        if (responseInfo != null) {
                            String result = responseInfo.result;
                            Log.d("flag", "--->POST:" + result);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(add_task.this, "添加失败了亲,请一定要写好日期啊", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

    }


}
