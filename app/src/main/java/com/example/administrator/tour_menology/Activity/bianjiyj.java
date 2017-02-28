package com.example.administrator.tour_menology.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.Adapter.youjixiangqing_adapter;
import com.example.administrator.tour_menology.Adapter.youjixiangqing_adapter2;
import com.example.administrator.tour_menology.Bean.youjixinagqing_list;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.date.PositiveDialong;
import com.example.administrator.tour_menology.my_fragment_activity.myyouji;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.helper.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class bianjiyj extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextView;
    private ImageButton mImageButton;
    private RelativeLayout write, yulan, tupian;
    private String mTravelId;
    private ListView mListView;
    private RequestQueue mRequestQueue;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private List<youjixinagqing_list> mList = new ArrayList<youjixinagqing_list>();
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO_ONE = 2;
    public static final int CROP_PHOTO_TWO = 3;
    public static final int SET_IMAGE_VIEW = 4;
    private Uri imageUri;
    private String mPath;
    private String mUrl;


    private PopupWindow mPopupWindow;
    private ImageView menu;
    private PositiveDialong dialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bianjiyj);
        utils = new PreferenceUtils(bianjiyj.this);
        initView();


        menu = (ImageView) findViewById(R.id.more);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });



        initdata();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String travelId =getIntent().getExtras().getString("travelId");

                Intent intent2 =new Intent(bianjiyj.this,youjiitem.class);

                Bundle bundle1 = new Bundle();

                bundle1.putString("travelContId",mList.get(position).getTravelContId());
                bundle1.putString("travelId",travelId);

                intent2.putExtras(bundle1);

                startActivity(intent2);
                finish();

            }
        });

    }

    private void showPopupWindow() {

        View contentView = LayoutInflater.from(bianjiyj.this).inflate(R.layout.bianjipopopwindow, null);
        mPopupWindow = new PopupWindow(contentView);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout p1 = (LinearLayout) contentView.findViewById(R.id.pw_shezhi);
        LinearLayout p2 = (LinearLayout) contentView.findViewById(R.id.pw_jieshu);
        LinearLayout p3 = (LinearLayout) contentView.findViewById(R.id.pw_shanchu);

        p1.setOnClickListener(this);
        p2.setOnClickListener(this);
        p3.setOnClickListener(this);

        mPopupWindow.showAsDropDown(menu);


    }

    private void initdata() {
        String travelId =getIntent().getExtras().getString("travelId");
        utils = new PreferenceUtils(bianjiyj.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");
        memberId = map.get("memberId").toString();

        String url = "http://bobtrip.com/tripcal/api/travel/detail.action?travelId=" + travelId + "&memberId=" + memberId;


        mRequestQueue = Volley.newRequestQueue(bianjiyj.this);


        JsonObjectRequest objectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("detail");


                    JSONArray jsonArray = jsonObject1.getJSONArray("travel_cont");
                    //  for (int i =1;i<jsonArray.length();i++){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                        String shoucangId = jsonObject2.getString("travelEnjoyId");
                        Log.d("ssss", String.valueOf(StringUtil.isBlank(shoucangId)));
                        Log.d("ssss", String.valueOf(shoucangId.equals("null")));
                        if (shoucangId.equals("null")) {
                            Log.d("ssss", "11");
                            shoucangId = "";
                        }
                        String id = jsonObject2.getString("travelContId");
                        if (id.equals("null")) {
                            Log.d("ssss", "11");
                            id = "";
                        }
                        String address = jsonObject2.getString("address");
                        if (address.equals("null")) {
                            address = "";
                        }
                        String shoucang = jsonObject2.getString("countEnjoy");
                        if (shoucang.equals("null")) {
                            Log.d("ssss", "11");
                            shoucang = "";
                        }
                        String cont = jsonObject2.getString("cont");

                        String beizhu = jsonObject2.getString("imgDesc");
                        if (beizhu.equals("null")) {
                            Log.d("ssss", "11");
                            beizhu = "";
                        }
                        String tape = jsonObject2.getString("travelType");
                        String travelId =jsonObject1.getString("travelId");
                        String date =jsonObject2.getString("day");
                        String date2 =jsonObject2.getString("travelDate");


                        mList.add(new youjixinagqing_list(shoucangId, id, address, shoucang, cont, beizhu, tape,travelId,date,date2));
                    }
                    youjixiangqing_adapter2 adapter = new youjixiangqing_adapter2(bianjiyj.this, mList);
//                    View headerView = View.inflate(youjixiangqing.this, R.layout.activity_youjixiangqing,null);
//                    mListView.addHeaderView(headerView);
                    mListView.setAdapter(adapter);




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mRequestQueue.add(objectRequest);



    }

    private void initView() {

        mTextView = (TextView) findViewById(R.id.toolbar_title);
        mTextView.setText("编辑游记");
        mImageButton = (ImageButton) findViewById(R.id.toolbar_back);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTravelId = getIntent().getExtras().getString("travelId");
        write = (RelativeLayout) findViewById(R.id.adyj_1);
        yulan = (RelativeLayout) findViewById(R.id.adyj_2);
        tupian = (RelativeLayout) findViewById(R.id.adyj_3);
        write.setOnClickListener(this);
        yulan.setOnClickListener(this);
        tupian.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.bianjiyj_list);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adyj_1:
                Intent intent1 =new Intent(bianjiyj.this,xiesuiji.class);
                    intent1.putExtra("travelId",mTravelId);
                startActivity(intent1);

                break;
            case R.id.adyj_2:
                Intent mIntent = new Intent(bianjiyj.this, youjixiangqing.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",getIntent().getExtras().getString("travelId"));
                mIntent.putExtras(bundle);
                startActivity(mIntent);

                break;
            case R.id.adyj_3:

                File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                try {
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //??????4.4?????????????????Uir??????????????
				/*imageUri = Uri.fromFile(outputImage);
				Intent intent = new Intent("android.intent.action.GET_CONTENT");
				intent.setType("image/*");//mime???? ????????????
				intent.putExtra("crop", true);//?????
				intent.putExtra("scale", true);//????????
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, CROP_PHOTO);*/
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                //intent.addCategory(Intent.CATEGORY_OPENABLE);//?????????? --??

                intent.setType("image/*");
                startActivityForResult(intent, CROP_PHOTO_TWO);
                break;
            case R.id.pw_shezhi:
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
                Intent intent2 =new Intent(bianjiyj.this,youjishezhi.class);
                intent2.putExtra("travelId",mTravelId);
                startActivity(intent2);
                break;
            case R.id.pw_jieshu:
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }

                String url ="http://bobtrip.com/tripcal/api/travel/finish.action";

                OkHttpUtils.post().url(url).addParams("travelId",mTravelId).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject =new JSONObject(response);


                            if (jsonObject.has("recordId")) {
                                final String recordId = jsonObject.getString("recordId");
                                dialog = new PositiveDialong(bianjiyj.this, "",
                                        new PositiveDialong.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                switch (v.getId()) {
                                                    case R.id.dialog_btn_lq:
                                                        String url = "http://bobtrip.com/tripcal/api/getRewords.action?recordId=" + recordId;
                                                        OkHttpUtils.get().url(url).build().execute(new StringCallback() {


                                                                       @Override
                                                                                                       public void onError(Call call, Exception e) {

                                                                                                       }

                                                                                                       @Override
                                                                                                       public void onResponse(String response) {
                                                                                                           try {
                                                                                                               JSONObject jsonObject1 = new JSONObject(response);
                                                                                                               String fen = jsonObject1.getString("point");
                                                                                                               Toast.makeText(bianjiyj.this, "成功领取" + fen + "分",
                                                                                                                       Toast.LENGTH_SHORT).show();
                                                                                                               Intent intent3 =new Intent(bianjiyj.this,myyouji.class);
                                                                                                               startActivity(intent3);
                                                                                                               finish();
                                                                                                           } catch (JSONException e) {
                                                                                                               e.printStackTrace();
                                                                                                           }

                                                                                                       }
                                                                                                   }
                                                        );


                                                        break;
                                                    case R.id.dialog_btn:
                                                        break;
                                                }

                                            }
                                        });
                                dialog.show();

                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });




                break;
            case R.id.pw_shanchu:
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }


                String url1 ="http://bobtrip.com/tripcal/api/travel/del.action";

                OkHttpUtils.post().url(url1).addParams("travelId",mTravelId).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(bianjiyj.this,"删除失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(bianjiyj.this,"删除成功",Toast.LENGTH_SHORT).show();



                    }
                });



                Intent intent4 =new Intent(bianjiyj.this,myyouji.class);
                startActivity(intent4);
                finish();

                break;



        }
    }



    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        Log.d("MainActivity", "requestCode: " + requestCode);
		/* stratActivityForResult??????????????????????????????onActivityResult????????*/
		/* ???????????????onActivityResult()?????????*/
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {//?????????
                    Intent intent = new Intent("com.android.camera.action.CROP");//??????????Intent?????????????????????????
                    intent.setDataAndType(imageUri, "image/*");//??????????????????MIME???? ??setDataAndType
                    intent.putExtra ("scale", true);
                    intent.putExtra (MediaStore.EXTRA_OUTPUT, imageUri);//???????????????????????????????????????????
                    startActivityForResult (intent, CROP_PHOTO_ONE);//?????????????????startActivityForResult?????????
                }
                break;
            case CROP_PHOTO_ONE:
                //Log.d("MainActivity", "requestCode: " + resultCode);
                if (resultCode == RESULT_OK) {//???? ?????????????????????????????????ImageView???????
                    try {
					 /*?????????????????????????????????????????????????????????????????????????????????????????????4.4??????????BitmapFactory????decodeStream()??????????????????????Bitmap????*/
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//					Log.d("123", new File(Environment.getExternalStorageDirectory(), "output_image.jpg").getPath());
                        Log.d("123", new File(Environment.getExternalStorageDirectory(), "output_image.jpg").getName());

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case CROP_PHOTO_TWO:
                Log.d("MainActivity", "???????: " + resultCode);
                if (resultCode == RESULT_OK) {
                    String imagePath = getPath(bianjiyj.this, data.getData()); //4.4?????????????????????????????????
                    Uri imageUri = Uri.fromFile(new File(imagePath));
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra ("crop" , true);
                    intent.putExtra ("scale", true);
                    intent.putExtra("return-data", false);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "output_image.jpg")));
                    Log.d("123", new File(Environment.getExternalStorageDirectory(), "output_image.jpg").getPath());
                    Log.d("123", new File(Environment.getExternalStorageDirectory(), "output_image.jpg").getName());
                    startActivityForResult (intent, SET_IMAGE_VIEW);
                }
                break;
            case SET_IMAGE_VIEW:
                Log.d("MainActivity", "1?????????: " + resultCode);
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "output_image.jpg"))));
                        //				Log.d("123", new File(Environment.getExternalStorageDirectory(), "output_image.jpg"));
                        Log.d("123", new File(Environment.getExternalStorageDirectory(), "output_image.jpg").getName());


                        //okhttp?????
                        final Map<String,String>params =new HashMap<String, String>();
                        //params.put("picFile",new File(Environment.getExternalStorageDirectory(), "output_image.jpg"));
                        params.put("picFileFileName",new File(Environment.getExternalStorageDirectory(), "output_image.jpg").getName());
                        String url ="http://bobtrip.com/tripcal/api/upload/img.action";
                        OkHttpUtils.post().url(url).params(params).addFile("picFile","output_image.jpg",new File(Environment.getExternalStorageDirectory(), "output_image.jpg"))
                                .build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject =new JSONObject(response);

                                    mPath = jsonObject.getString("path");
                                    mUrl = jsonObject.getString("url");


                                    Intent intent2 =new Intent(bianjiyj.this,jiatupian.class);

                                    Bundle bundle1 = new Bundle();

                                    bundle1.putString("path",mPath);
                                    bundle1.putString("url",mUrl);
                                    bundle1.putString("travelId",mTravelId);

                                    intent2.putExtras(bundle1);

                                    startActivity(intent2);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });




                        //		picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
    /**
     * ????Uri??????????????????Android4.4??????Uri???
     * @param
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @TargetApi(19)
    public static String getPath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}
