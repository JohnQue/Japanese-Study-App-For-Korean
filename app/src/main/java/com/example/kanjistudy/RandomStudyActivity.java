package com.example.kanjistudy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class RandomStudyActivity extends AppCompatActivity {
    private TextView baseView, meaningView, count;
    private static final String UNKNOWN_LIST = "unknown";
    private static final String ONLY_KNOW_MEANING = "knowMeaning";
    private static final String ONLY_KNOW_PRONUNCE = "knowPronunce";
    private int randomNumber;
    AppCompatButton knowKanji, unknownKanji, knowMeaning, knowPronunce;

    //중복으로 랜덤 한자를 추출하지 않기 위해 만든 변수들
    static HashSet<Integer> hashSet = new HashSet<>();
    static int hashCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_study);

        Toolbar toolbar = findViewById(R.id.toolBar);
        count = findViewById(R.id.count);
        baseView = findViewById(R.id.baseView);
        meaningView = findViewById(R.id.meaningView);
        knowKanji = findViewById(R.id.knowKanji);
        unknownKanji = findViewById(R.id.unknownKanji);
        knowMeaning = findViewById(R.id.knowMeaning);
        knowPronunce = findViewById(R.id.knowPronounce);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getRandomKanji();

        knowKanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomKanji();
            }
        });

        unknownKanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> before = getStringArrayPref(RandomStudyActivity.this, UNKNOWN_LIST);
                if(!before.contains(randomNumber)){
                    before.add(randomNumber);
                    setStringArrayPref(RandomStudyActivity.this, UNKNOWN_LIST, before);
                    ArrayList<Integer> after = getStringArrayPref(RandomStudyActivity.this, UNKNOWN_LIST);
                    System.out.println(after);
                }else{
                    Toast.makeText(RandomStudyActivity.this, "もうリストに追加しています！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        knowMeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> before = getStringArrayPref(RandomStudyActivity.this, ONLY_KNOW_MEANING);
                if(!before.contains(randomNumber)){
                    before.add(randomNumber);
                    setStringArrayPref(RandomStudyActivity.this, UNKNOWN_LIST, before);
                    ArrayList<Integer> after = getStringArrayPref(RandomStudyActivity.this, ONLY_KNOW_MEANING);
                    System.out.println(after);
                }else{
                    Toast.makeText(RandomStudyActivity.this, "もうリストに追加しています！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        knowPronunce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> before = getStringArrayPref(RandomStudyActivity.this, ONLY_KNOW_PRONUNCE);
                if(!before.contains(randomNumber)){
                    before.add(randomNumber);
                    setStringArrayPref(RandomStudyActivity.this, UNKNOWN_LIST, before);
                    ArrayList<Integer> after = getStringArrayPref(RandomStudyActivity.this, ONLY_KNOW_PRONUNCE);
                    System.out.println(after);
                }else{
                    Toast.makeText(RandomStudyActivity.this, "もうリストに追加しています！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //json 파일로부터 Random하게 숫자를 통해, 한자 출력
    public void getRandomKanji() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            String kanjiValue = obj.getString("hanza");
            JSONArray kanjiArray = new JSONArray(kanjiValue);
            System.out.println("hashSet.size() : " + hashSet.size());
            System.out.println("hashCount : " + hashCount);
            while (hashSet.size() == hashCount) {
                randomNumber = (int) (Math.random() * 300) + 1;
                System.out.println("randomNumber : " + randomNumber);
                JSONObject obj2 = kanjiArray.getJSONObject(randomNumber);
                String kanji = obj2.getString("kanji");
                String meaning = obj2.getString("meaning");
                baseView.setText(kanji);
                meaningView.setText(meaning);
                hashSet.add(randomNumber);
            }
            hashCount++;
            count.setText(hashCount + "/300");
            System.out.println("hashSet.size() : " + hashSet.size());
            System.out.println("hashCount : " + hashCount);
        } catch (
                JSONException e) {
            e.printStackTrace();
        }

    }

    //JSON 파일로부터 데이터 추출
    public String loadJSONFromAsset() {
        String json;
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

    //학습종료? or Not?
    public void alert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("学習終了");
        alertDialogBuilder
                .setMessage("学習を終了しますか？")
                .setCancelable(false)
                .setPositiveButton("終了",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                hashCount = 0;
                                hashSet.clear();
                                finish();
                            }
                        })
                .setNegativeButton("キャンセル",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //뒤로가기 버튼 클릭시 alert() 메소드 실행
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 뒤로가기 버튼
                alert();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setStringArrayPref(Context context, String key, ArrayList<Integer> values){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        int size = values.size();
        JSONArray a = new JSONArray();
        for (int i=0; i<size; i++){
            a.put(values.get(i));
        }
        if(!values.isEmpty()) editor.putString(key, a.toString());
        else editor.putString(key, null);
        editor.apply();
    }

    private ArrayList<Integer> getStringArrayPref(Context context, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<Integer> urls = new ArrayList<>();
        if(json != null){
            try{
                JSONArray a = new JSONArray(json);
                int size = a.length();
                for(int i=0; i<size; i++){
                    int url = a.optInt(i);
                    urls.add(url);
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return urls;
    }
}
