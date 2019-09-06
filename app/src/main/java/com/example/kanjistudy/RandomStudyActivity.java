package com.example.kanjistudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

public class RandomStudyActivity extends AppCompatActivity {
    private TextView baseView, meaningView, count;
    AppCompatButton knowKanji, unknownKanji, knowMeaning, knowPronunce;
    static HashSet<Integer> hashSet = new HashSet<>();
    static int hashCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_study);

        count = findViewById(R.id.count);
        baseView = findViewById(R.id.baseView);
        meaningView = findViewById(R.id.meaningView);
        knowKanji = findViewById(R.id.knowKanji);
        unknownKanji = findViewById(R.id.unknownKanji);
        knowMeaning = findViewById(R.id.knowMeaning);
        knowPronunce = findViewById(R.id.knowPronounce);

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            String kanjiValue = obj.getString("hanza");
            JSONArray kanjiArray = new JSONArray(kanjiValue);
            System.out.println("hashSet.size() : " +hashSet.size());
            System.out.println("hashCount : " + hashCount);
            while(hashSet.size() == hashCount) {
                int randomNumber = (int)(Math.random() * 200) + 1;
                System.out.println("randomNumber : " + randomNumber);
                JSONObject obj2 = kanjiArray.getJSONObject(randomNumber);
                String kanji = obj2.getString("kanji");
                String meaning = obj2.getString("meaning");
                baseView.setText(kanji);
                meaningView.setText(meaning);
                hashSet.add(randomNumber);
            }
            hashCount++;
            count.setText(hashCount+"/200");
            System.out.println("hashSet.size() : " +hashSet.size());
            System.out.println("hashCount : " + hashCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        knowKanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RandomStudyActivity.this, RandomStudyActivity.class));
            }
        });
    }


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
}
