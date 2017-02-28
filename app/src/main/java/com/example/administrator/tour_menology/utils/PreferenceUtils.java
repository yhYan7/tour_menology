package com.example.administrator.tour_menology.utils;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils {
	private Context context;

	public PreferenceUtils(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/**
	 * 
	 * @param fileName
	 * @param params
	 * @return
	 */
	public boolean savePreference(String fileName, Map<String, Object> params) {
		boolean flag = false;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value instanceof Integer) {
					Integer new_value = (Integer) value;
					editor.putInt(key, new_value);
				}
				if (value instanceof Boolean) {
					Boolean new_value = (Boolean) value;
					editor.putBoolean(key, new_value);
				}
				if (value instanceof Float) {
					Float new_value = (Float) value;
					editor.putFloat(key, new_value);
				}
				if (value instanceof Long) {
					Long new_value = (Long) value;
					editor.putLong(key, new_value);
				}
				if (value instanceof String) {
					String new_value = (String) value;
					editor.putString(key, new_value);
				}
			}
		}
		flag = editor.commit();
		return flag;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public Map<String, ?> getPreference(String fileName) {
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return preferences.getAll();
	}
}
