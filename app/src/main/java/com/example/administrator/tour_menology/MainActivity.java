package com.example.administrator.tour_menology;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.tour_menology.Adapter.MainAdapter;
import com.example.administrator.tour_menology.Main_Fragment.CalendarActivity;
import com.example.administrator.tour_menology.Main_Fragment.Main_Fragment;
import com.example.administrator.tour_menology.Main_Fragment.find_Fragment;
import com.example.administrator.tour_menology.Main_Fragment.my_Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> mList;
    private ViewPager mPager;
    private MainAdapter mAdapter;
    private RadioGroup mRadioGroup;
    private RadioButton tab1, tab2, tab3, tab4;

    private Main_Fragment mMain_fragment;
    private CalendarActivity mTour_fragment;
    private find_Fragment mFind_fragment;
    private my_Fragment mMy_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();


    }

    private void initView() {
        mPager = (ViewPager) findViewById(R.id.main_viewpager);

        mPager.setOffscreenPageLimit(2);
        mList = getData();
        mAdapter = new MainAdapter(getSupportFragmentManager(), mList);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new TabOnPageChangeListener());

        mRadioGroup = (RadioGroup) findViewById(R.id.radio);
        tab1 = (RadioButton) findViewById(R.id.tab1);
        tab2 = (RadioButton) findViewById(R.id.tab2);
        tab3 = (RadioButton) findViewById(R.id.tab3);
        tab4 = (RadioButton) findViewById(R.id.tab4);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tab1:
                        mPager.setCurrentItem(0);
                        break;
                    case R.id.tab2:
                        mPager.setCurrentItem(1);
                        break;
                    case R.id.tab3:
                        mPager.setCurrentItem(2);
                        break;
                    case R.id.tab4:
                        mPager.setCurrentItem(3);
                        break;

                }
            }
        });

    }


    public List<Fragment> getData() {
        mList =new ArrayList<Fragment>();
        mMain_fragment = new Main_Fragment();
        mList.add(mMain_fragment);
        mTour_fragment = new CalendarActivity();
        mList.add(mTour_fragment);
        mFind_fragment = new find_Fragment();
        mList.add(mFind_fragment);
        mMy_fragment = new my_Fragment();
        mList.add(mMy_fragment);
        return mList;
    }


    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    tab1.setChecked(true);

                    break;
                case 1:
                    tab2.setChecked(true);

                    break;
                case 2:
                    tab3.setChecked(true);
                    break;
                case 3:
                    tab4.setChecked(true);
                    break;
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}
