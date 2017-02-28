package com.example.administrator.tour_menology.my_fragment_activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.administrator.tour_menology.MainActivity;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.qihoo.appstore.common.updatesdk.lib.UpdateHelper;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class setupActivity extends AppCompatActivity{

    private ImageButton toolbar_back;
    private Button tuichu;
    private PreferenceUtils utils;//启用登录用户数据
    private TextView toolbar_title , set_t2;
    private RelativeLayout sethuodong,fankui,gengxin;
    private ToggleButton mTogBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        init();
    }

    public void init(){

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("设置");
        set_t2 = (TextView) findViewById(R.id.set_t2);
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        tuichu = (Button) findViewById(R.id.set_tuichu);
        sethuodong = (RelativeLayout) findViewById(R.id.set_huodong);
        fankui = (RelativeLayout) findViewById(R.id.set_fankui);
        mTogBtn = (ToggleButton) findViewById(R.id.mTogBtn) ;
        gengxin = (RelativeLayout) findViewById(R.id.gengxin);
        //添加事件
        mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //选中
                }else{
                    //未选中
                }
            }
        });// 添加监听事件

        mTogBtn.setOnClickListener(mListener);
        fankui.setOnClickListener(mListener);
        sethuodong.setOnClickListener(mListener);
        toolbar_back.setOnClickListener(mListener);
        tuichu.setOnClickListener(mListener);
        gengxin.setOnClickListener(mListener);

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;

               /* case R.id.mTogBtn:
                    //开关按钮

                    break;*/
                case R.id.set_huodong:
                    //活动设定
                    showSexChooseDialog();
                    break;
                case R.id.gengxin:
                    //更新
                    String packageName = setupActivity.this.getPackageName();
                    UpdateHelper.getInstance().init(setupActivity.this, Color.parseColor("#0A93DB"));
                    UpdateHelper.getInstance().setDebugMode(true);
                    UpdateHelper.getInstance().manualUpdate(packageName);
                    break;

                case R.id.set_fankui:
                    //疑问与反馈
                    Intent intent1 = new Intent(setupActivity.this,
                            fankui.class);
            //        intent.putExtra("memberId", memberId);
                    //intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent1);
                    break;

                //退出登录
                case R.id.set_tuichu:

                    //获取登录用户信息
                    utils = new PreferenceUtils(setupActivity.this);
                    Map<String, Object> map =  (Map<String, Object>) utils
                            .getPreference("login");
                    map.clear();
                    Toast.makeText(setupActivity.this, "退出成功！",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(setupActivity.this,
                            denglu.class);
                    startActivity(intent);
                    finish();
                    break;

                default:
                    break;

            }
        }

    };

    private String[] setArry = new String[] { "当天提醒", "一天前提醒" };// 性别选择
    private String set;

    /* 性别选择框 */
    private void showSexChooseDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        builder.setSingleChoiceItems(setArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                set_t2.setText(setArry[which]);

                //将修改信息发送至后台
             //   sendSet();
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示
    }




}
