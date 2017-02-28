package com.example.administrator.tour_menology.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.tour_menology.R;
import com.example.administrator.tour_menology.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;

public class youjishezhi extends AppCompatActivity {
    private TextView toolbartext;
    private ImageButton toolbar, duihao;
    private ImageView img;
    private TextView title;
    private PreferenceUtils utils;//启用登录用户数据
    private String memberId;
    private Spinner mSpinner;

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO_ONE = 2;
    public static final int CROP_PHOTO_TWO = 3;
    public static final int SET_IMAGE_VIEW = 4;
    private Uri imageUri;
    private String mPath;
    private String mUrl;
    private String mPath1;
    private String mUrlid;
    private String mSpinner1;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youjishezhi);
        initview();
        //获取登录用户信息
        utils = new PreferenceUtils(youjishezhi.this);

        Map<String, Object> map = (Map<String, Object>) utils.getPreference("login");

        memberId = map.get("memberId").toString();
        initdata();
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                str = (String) mSpinner.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        initdate2();

    }

    private void initdate2() {
        duihao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url ="http://bobtrip.com/tripcal/api/travel/update.action";


                Map<String, String> params = new HashMap<>();
                params.put("travelId",mUrlid);
                params.put("title",title.getText().toString());
                if (str.toString().equals("公开"))
                    params.put("showStatus","0");
                else if (str.toString().equals("仅好友"))
                    params.put("showStatus","1");
                else if (str.toString().equals("仅自己"))
                    params.put("showStatus","2");

                if (mPath1!=null){
                    params.put("logo",mPath1);
                }

                OkHttpUtils.get().url(url).params(params).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                    Toast.makeText(youjishezhi.this,"更改成功",Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(youjishezhi.this,bianjiyj.class);
                        intent.putExtra("travelId",mUrlid);
                        startActivity(intent);
                        finish();

                    }
                });
            }
        });

    }

    private void initdata() {
        mUrlid = getIntent().getExtras().getString("travelId");
        String url = "http://bobtrip.com/tripcal/api/travel/detail.action?travelId=" + mUrlid + "&memberId=" + memberId;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("detail");
                    Glide.with(youjishezhi.this).load(jsonObject1.getString("logo")).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
                    title.setText(jsonObject1.getString("title"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void initview() {
        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("游记设置");
        toolbar = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        duihao = (ImageButton) findViewById(R.id.shezhi_duihao);
        img = (ImageView) findViewById(R.id.shezhi_img);
        title = (TextView) findViewById(R.id.shezhi_title);
        mSpinner = (Spinner) findViewById(R.id.shezhi_spinner);

        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.yinsi);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        //绑定 Adapter到控件
        mSpinner.setAdapter(_Adapter);


        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(youjishezhi.this);
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(youjishezhi.this);
                inputDialog.setTitle("请输入您要更改的标题").setView(editText);
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                title.setText(editText.getText().toString());
                            }
                        }).show();
            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
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
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MainActivity", "requestCode: " + requestCode);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO_ONE);
                }
                break;
            case CROP_PHOTO_ONE:
                //Log.d("MainActivity", "requestCode: " + resultCode);
                if (resultCode == RESULT_OK) {
                    try {
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
                    String imagePath = getPath(youjishezhi.this, data.getData());
                    Uri imageUri = Uri.fromFile(new File(imagePath));
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("crop", true);
                    intent.putExtra("scale", true);
                    intent.putExtra("return-data", false);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "output_image.jpg")));
                    Log.d("123", new File(Environment.getExternalStorageDirectory(), "output_image.jpg").getPath());
                    Log.d("123", new File(Environment.getExternalStorageDirectory(), "output_image.jpg").getName());
                    startActivityForResult(intent, SET_IMAGE_VIEW);
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
                        final Map<String, String> params = new HashMap<String, String>();
                        //params.put("picFile",new File(Environment.getExternalStorageDirectory(), "output_image.jpg"));
                        params.put("picFileFileName", new File(Environment.getExternalStorageDirectory(), "output_image.jpg").getName());
                        String url = "http://bobtrip.com/tripcal/api/upload/img.action";
                        OkHttpUtils.post().url(url).params(params).addFile("picFile", "output_image.jpg", new File(Environment.getExternalStorageDirectory(), "output_image.jpg"))
                                .build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    mPath1 = mPath;
                                    mPath1 = jsonObject.getString("path");
                                    mUrl = jsonObject.getString("url");

                                    Glide.with(youjishezhi.this).load(mUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);


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
     *
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
                String[] selectionArgs = new String[]{split[1]};
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
        String[] projection = {column};
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
