package com.example.administrator.tour_menology.date;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.tour_menology.R;


public class PositiveDialong extends AlertDialog
{
	
	public interface OnClickListener{
		public void onClick(View v);
	}
	
	private OnClickListener onclickListener;
	private CharSequence text;
	private int resid;

	public PositiveDialong(Context context, CharSequence text, OnClickListener onclickListener)
	{
		super(context);
		this.text = text;
		this.onclickListener = onclickListener;
	}
	
	public PositiveDialong(Context context, int resid, OnClickListener onclickListener)
	{
		super(context);
		this.resid = resid;
		this.onclickListener = onclickListener;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_layout);
		setCancelable(false);
		ImageButton button = (ImageButton) findViewById(R.id.dialog_btn);
		Button button2 = (Button) findViewById(R.id.dialog_btn_lq);
//		TextView message = (TextView) findViewById(R.id.message_tv);
//		TextView m = (TextView) findViewById(R.id.message_t2);
		// 粗体设置
//		TextPaint tp = button.getPaint();
//		tp.setFakeBoldText(true);
//		if (text != null)
//		{
//			message.setText(text);
//	//		m.setText(text);
//		}else if (resid != 0) {
//			message.setText(resid);
//	//		m.setText(resid);
//		}
		
		button.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				dismiss();
				onclickListener.onClick(v);
			}
		});
		button2.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				dismiss();
				onclickListener.onClick(v);
			}
		});
	}

}
