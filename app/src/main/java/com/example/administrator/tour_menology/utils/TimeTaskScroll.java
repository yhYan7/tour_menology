package com.example.administrator.tour_menology.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.example.administrator.tour_menology.Adapter.Main_list2_adapter;
import com.example.administrator.tour_menology.Bean.main_list2_List;

import java.util.List;
import java.util.TimerTask;

public class TimeTaskScroll extends TimerTask {
	
	private ListView listView;
	
	public TimeTaskScroll(Context context, ListView listView, List<main_list2_List> list){
		this.listView = listView;
		listView.setAdapter(new Main_list2_adapter(context, list));
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
		listView.smoothScrollBy(1, 0);
		};

	};

	@Override
	public void run() {
		Message msg = handler.obtainMessage();
		handler.sendMessage(msg);

	}

}
