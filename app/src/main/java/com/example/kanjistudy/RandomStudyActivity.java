package com.example.kanjistudy;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
    private TextView baseView, count;
    private static final String UNKNOWN_LIST = "unknown";
    private static final String ONLY_KNOW_MEANING = "knowMeaning";
    private static final String ONLY_KNOW_PRONUNCE = "knowPronunce";
    private IntegerArrayPref iap = new IntegerArrayPref();
    private ArrayList<Integer> arrayList = new ArrayList<>();
    private int randomNumber;
    AppCompatButton knowKanji, unknownKanji, knowMeaning, knowPronunce;

    //중복으로 랜덤 한자를 추출하지 않기 위해 만든 변수들
    static HashSet<Integer> hashSet = new HashSet<>();
    static int hashCount = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.appbar_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home: { // 뒤로가기 버튼
                alert();
                return true;
            }
            case R.id.action_list: {
                Intent intent = new Intent(RandomStudyActivity.this, ListActivity.class);
                intent.putExtra("STATUS", "already");
                intent.putIntegerArrayListExtra("arrayList", arrayList);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_study);

        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(Color.WHITE);
        count = findViewById(R.id.count);
        baseView = findViewById(R.id.baseView);
        knowKanji = findViewById(R.id.knowKanji);
        unknownKanji = findViewById(R.id.unknownKanji);
        knowMeaning = findViewById(R.id.knowMeaning);
        knowPronunce = findViewById(R.id.knowPronounce);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
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
                ArrayList<Integer> before = iap.getIntegerArrayPref(RandomStudyActivity.this, UNKNOWN_LIST);
                if(!before.contains(randomNumber)){
                    before.add(randomNumber);
                    iap.setIntegerArrayPref(RandomStudyActivity.this, UNKNOWN_LIST, before);
                    Toast.makeText(RandomStudyActivity.this, "リストに追加しました！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RandomStudyActivity.this, "もうリストに追加しています！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        knowMeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> before = iap.getIntegerArrayPref(RandomStudyActivity.this, ONLY_KNOW_MEANING);
                if(!before.contains(randomNumber)){
                    before.add(randomNumber);
                    iap.setIntegerArrayPref(RandomStudyActivity.this, ONLY_KNOW_MEANING, before);
                    Toast.makeText(RandomStudyActivity.this, "リストに追加しました！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RandomStudyActivity.this, "もうリストに追加しています！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        knowPronunce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> before = iap.getIntegerArrayPref(RandomStudyActivity.this, ONLY_KNOW_PRONUNCE);
                if(!before.contains(randomNumber)){
                    before.add(randomNumber);
                    iap.setIntegerArrayPref(RandomStudyActivity.this, ONLY_KNOW_PRONUNCE, before);
                    Toast.makeText(RandomStudyActivity.this, "リストに追加しました！", Toast.LENGTH_SHORT).show();
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
                randomNumber = (int) (Math.random() * 500) + 1;
                System.out.println("randomNumber : " + randomNumber);
                JSONObject obj2 = kanjiArray.getJSONObject(randomNumber);
                String kanji = obj2.getString("kanji");
                baseView.setText(kanji);
                hashSet.add(randomNumber);
            }
            arrayList.add(randomNumber);
            hashCount++;
            count.setText(hashCount + "/500");
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
}
