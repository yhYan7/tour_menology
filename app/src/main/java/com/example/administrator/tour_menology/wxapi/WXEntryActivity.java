package com.example.administrator.tour_menology.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


import com.example.administrator.tour_menology.Bean.WeiXinLoginGetTokenBean;
import com.example.administrator.tour_menology.Bean.WeiXinLoginGetUserinfoBean;
import com.example.administrator.tour_menology.MainActivity;
import com.example.administrator.tour_menology.my_fragment_activity.denglu;


import com.example.administrator.tour_menology.utils.AllApk;
import com.example.administrator.tour_menology.utils.HttpUtil;
import com.example.administrator.tour_menology.utils.JsonUtil;
import com.example.administrator.tour_menology.utils.MyConfig;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * create by fml
 * time:2015-10-28
 * */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private Context mContext;
    private String openid,touxiang,nickname,memberId;
    private String httpPostwx;

    private PreferenceUtils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        utils = new PreferenceUtils(WXEntryActivity.this);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        denglu.api.handleIntent(getIntent(), this);
        Log.e("!!!!!!!!!ceshi走不走开始", "123222221");
    }  
    @Override
    public void onReq(BaseReq req) {
    	
    }
    /** 微信登录第一步：获取code */
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp)resp).code;
      //          BaseActivity.log("获取code成功："+code);
                Log.e("!!!!!!!!!ceshi走不走", "123222221");
                if(code != null){
                    new AsynctaskToken().execute("https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=" + AllApk.WEIXIN_APP_ID + "&secret=" + AllApk.WEIXIN_APP_SECRET + "&grant_type=authorization_code" + "&code=" + code);
                }
                break;
            default:
                break;
        }

    }

    /** 微信登录第二步：获取token */
    class AsynctaskToken extends AsyncTask<Object , Object , Object> {
        @Override
        protected Object doInBackground(Object... params) {
            HttpGet httpRequest = new HttpGet(params[0].toString());
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpRequest);
                if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                  //  BaseActivity.log("请求个人信息成功");
                    String strResult = EntityUtils.toString(httpResponse.getEntity());
                    Log.e("!!!!!!!!!ceshi走不走2", "123222221");
                    return strResult;
                }
                else{
                  //  BaseActivity.log("请求个人信息失败");
                    return "请求出错";
                }
            }
            catch(ClientProtocolException e){
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Object obj = null;
            try {
                obj = JsonUtil.toObjectByJson(o.toString(), WeiXinLoginGetTokenBean.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            WeiXinLoginGetTokenBean bean = (WeiXinLoginGetTokenBean)obj;
         //   BaseActivity.log("获取token成功：\n" + "token:"+bean.getAccess_token()+"\nopenid"+bean.getOpenid());
            String url = "https://api.weixin.qq.com/sns/userinfo?"+"access_token="+bean.getAccess_token()+"&openid="+bean.getOpenid();
            Log.e("!!!!!!!!!openid=", bean.getOpenid());
        //    openid = bean.getOpenid();
            Log.e("!!!!!!!!!Unionid", bean.getUnionid());
            openid = bean.getUnionid();
            new AsynctaskInfo().execute(url);
        }
        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }
    }
    /** 微信登录第三步：获取用户信息 */
    class AsynctaskInfo extends AsyncTask<Object , Object , Object> {
        @Override
        protected Object doInBackground(Object... params) {
            HttpGet httpRequest = new HttpGet(params[0].toString());
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpRequest);
                if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
             //       BaseActivity.log("请求个人信息成功");
                    String strResult = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
                    return strResult;
                }
                else{
              //      BaseActivity.log("请求个人信息失败");
                    return "请求出错";
                }
            }
            catch(ClientProtocolException e){
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Object obj = null;
            try {
                obj = JsonUtil.toObjectByJson(o.toString(), WeiXinLoginGetUserinfoBean.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            WeiXinLoginGetUserinfoBean bean = (WeiXinLoginGetUserinfoBean)obj;
        //    BaseActivity.log("获取用户信息成功：\n" + "昵称:"+bean.getNickname()+"\n头像路径"+bean.getHeadimgurl());
            Log.e("头像wei", bean.getNickname());
            touxiang = bean.getHeadimgurl();
            nickname = bean.getNickname();
         /*   Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]" ,
                    Pattern . UNICODE_CASE | Pattern. CASE_INSENSITIVE );
            Matcher emojiMatcher = emoji.matcher(nickname);
            if (emojiMatcher.find()) {
                nickname = emojiMatcher.replaceAll("*");
            }*/

            //   Toast.makeText(mContext,"获取用户信息成功：\n"+"昵称："+bean.getNickname() + "\n头像路径："+bean.getHeadimgurl(), Toast.LENGTH_LONG).show();

            //登录旅行日历方法
            login();

            finish();
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }
    }

    public void login(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("type", "2");
                    params.put("openId", openid);
                    params.put("nickName", nickname);
                    params.put("headIcon", touxiang);
                    Log.i("", "====openId-->>" + openid);
                    Log.i("", "====nickName--->>" + nickname);
                    httpPostwx = HttpUtil.http(MyConfig.httpurl
                            + "/member/thirdlogin.action", params);
                } catch (Exception e) {
                    Toast.makeText(WXEntryActivity.this, "连接服务器失败,请检查您的网络并重试",
                            Toast.LENGTH_SHORT).show();
                }
                handlerwx.sendEmptyMessage(0x10);
            }
        }).start();
    }
    private Handler handlerwx = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x10:
                    System.out.println("######测试微信登录专用#######");
                    try {
                        JSONObject jsonObject = new JSONObject(httpPostwx);
                        if ("0".equals(jsonObject.getString("errcode"))) {
                            // 注册成功
                            Log.i("", "====返回字段用户id-->>" + jsonObject.getString("memberId"));
                            Toast.makeText(WXEntryActivity.this, "恭喜，登录成功！",
                                    Toast.LENGTH_SHORT).show();
                            memberId = jsonObject.getString("memberId");

                            //将登陆数据保存起来 以作后用
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("memberId", memberId);
                            utils.savePreference("login", map);

                            Intent intent = new Intent(WXEntryActivity.this,
                                    MainActivity.class);
                            intent.putExtra("memberId",memberId);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("", "====失败原因-->>" + jsonObject.getString("errmesg"));
                            // 登录失败
                            Toast.makeText(WXEntryActivity.this,
                                    "登录失败，"+jsonObject.getString("errmesg"), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(WXEntryActivity.this, "连接服务器失败,请检查您的网络并重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };



}
