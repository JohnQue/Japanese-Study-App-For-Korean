package com.example.kanjistudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class ShowInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        TextView kanji, meaning, countText;
        kanji = findViewById(R.id.kanji);
        meaning = findViewById(R.id.meaning);
        countText = findViewById(R.id.countText);

        Intent intent = getIntent();
        int getNum = intent.getIntExtra("position", -1);
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            String kanjiValue = obj.getString("hanza");
            JSONArray kanjiArray = new JSONArray(kanjiValue);

            Data data = getInfo(kanjiArray, getNum);
            kanji.setText(data.getKanji());
            meaning.setText(data.getMeaning());
            String snum = "2136中 " +data.getIndex()+ "番目";
            countText.setText(snum);
        }catch(JSONException e){
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

    //json 파일로부터 index에 해당하는 위치의 데이터를 가져옴
    public Data getInfo(JSONArray arr, int index) {
        Data data;
        try {
            JSONObject obj2 = arr.getJSONObject(index);
            data = new Data(obj2.getInt("index"), obj2.getString("kanji"), obj2.getString("meaning"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return data;
    }
}
