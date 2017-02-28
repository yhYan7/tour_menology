package com.example.administrator.tour_menology.date;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yanyh on 2016/8/18.
 */
public class HeiViewPager extends ViewPager
{

    private int mDownX;
    private int mDownY;

    public HeiViewPager(Context context)
    {
        super(context);
    }

    public HeiViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = (int) ev.getX() - mDownX;
                int dy = (int) ev.getY() - mDownY;

                if (Math.abs(dx) > 50)
                    return true;
                else if (Math.abs(dy) > 0)
                    return false;

                break;
        }
        return false;
    }
}
