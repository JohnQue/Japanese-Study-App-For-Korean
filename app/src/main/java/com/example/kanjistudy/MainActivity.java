package com.example.kanjistudy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private TextView baseView, meaningView;
    final static int MY_PERMISSIONS_REQUEST_READ_FILE = 9876;
    final static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TestLog/kanji.txt";
    final static String jsonFilePath = "/storage/emulated/0/TestLog/kanji.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseView = findViewById(R.id.baseView);
        meaningView = findViewById(R.id.meaningView);
        fileRead();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("kanji.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public void fileRead() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //보여줘야 할 경우에만 보여줌
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_FILE);
            }
        } else {
            JSONObject obj = null;
            try {
                obj = new JSONObject(loadJSONFromAsset());
                String kanjiValue = obj.getString("hanza");
                JSONArray kanjiArray = new JSONArray(kanjiValue);
                JSONObject obj2 = kanjiArray.getJSONObject(0);
                String kanji = obj2.getString("kanji");
                String meaning = obj2.getString("meaning");
                baseView.setText(kanji);
                meaningView.setText(meaning);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_FILE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("Success", "You've got Permissions");
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(loadJSONFromAsset());
                        String kanjiValue = obj.getString("hanza");
                        JSONObject kanjiObject = new JSONObject(kanjiValue);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("Denied", "Permission still denied");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
