package com.example.administrator.tour_menology.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {
	public static String http(String url, Map<String, String> params) {
		URL u = null;
		HttpURLConnection con = null;
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			// 构建请求参数
			StringBuffer sb = new StringBuffer();
			if (params != null) {
				for (Entry<String, String> e : params.entrySet()) {
					sb.append(e.getKey());
					sb.append("=");
					sb.append(e.getValue());
					sb.append("&");
				}
				sb.substring(0, sb.length() - 1);
			}
			System.out.println("send_url:" + url);
			System.out.println("send_data:" + sb.toString());

			// 发送请求
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setConnectTimeout(30000);
			con.setReadTimeout(60000);
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();// 关闭socket
			}
		}
		return buffer.toString();
	}

}
