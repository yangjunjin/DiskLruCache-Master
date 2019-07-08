package com.cq.disklrucachedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cq.disklrucachedemo.disk.DiskLruCacheHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tv_result;
    private DiskLruCacheHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            helper = new DiskLruCacheHelper(MainActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        tv_result = findViewById(R.id.tv_result);
        imageView = findViewById(R.id.imageView);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_saveStr:
                helper.put("testString", "张鸿洋");
                break;
            case R.id.btn_getStr:
                setResult(helper.getAsString("testString"));
                break;

            case R.id.btn_saveJSONObject:
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject("{\"name\":\"zhy\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                helper.put("testJson", jsonObject);
                break;
            case R.id.btn_getJSONObject:
                setResult(helper.getAsJson("testJson").toString());
                break;

            case R.id.btn_saveObject:
                User u = new User();
                u.name = "张鸿洋";
                helper.put("testSerializable", u);
                break;
            case R.id.btn_getObject:
                User user = helper.getAsSerializable("testSerializable");
                setResult("name=" + user.name);
                break;
            case R.id.btn_saveBitmap:
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                helper.put("testBitmap", bm);
                break;
            case R.id.btn_getBitmap:
                Bitmap bm2 = helper.getAsBitmap("testBitmap");
                imageView.setImageBitmap(bm2);
                break;
            case R.id.btn_saveDrawable:
                Drawable d =  getResources().getDrawable(R.mipmap.ic_launcher);
                helper.put("testDrawable", d);
                break;
            case R.id.btn_getDrawable:
                Drawable d2 = helper.getAsDrawable("testDrawable");
                imageView.setImageDrawable(d2);
                break;

            case R.id.btn_close:
                try {
                    helper.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void setResult(String result) {
        tv_result.setText(result);
    }

    private static class User implements Serializable {
        private String name;
    }
}
