package com.example.kanjistudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private static final String UNKNOWN_LIST = "unknown";
    ListView listView;
    private ArrayList<String> printList = new ArrayList<>();
    private ArrayList<Integer> arrayList;
    JSONObject obj;
    String kanjiValue;
    JSONArray kanjiArray;

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home: { // 뒤로가기 버튼
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = getIntent();
        int size;
        getJSON();
        //모르는 한자 리스트일 경우
        if (intent.getStringExtra("STATUS").equals(UNKNOWN_LIST)) {
            IntegerArrayPref iap = new IntegerArrayPref();
            arrayList = iap.getIntegerArrayPref(this, UNKNOWN_LIST);
            size = arrayList.size();

            for (int i = 0; i < size; i++)
                getInfo(kanjiArray, arrayList.get(i));
        }
        //랜덤학습에서 진행한 한자 리스트일 경우
        else {
            arrayList = intent.getIntegerArrayListExtra("arrayList");
            size = arrayList.size();

            for (int i = 0; i < size; i++)
                getInfo(kanjiArray, arrayList.get(i));
        }
        listView = findViewById(R.id.ListView);
        ListAdapter listAdapter = new ListAdapter(this, printList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, ShowInfoActivity.class);
                intent.putExtra("position", arrayList.get(position));
                startActivity(intent);
            }
        });
    }

    public void getJSON() {
        try {
            obj = new JSONObject(loadJSONFromAsset());
            kanjiValue = obj.getString("hanza");
            kanjiArray = new JSONArray(kanjiValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //json 파일로부터 index에 해당하는 위치의 데이터를 가져옴
    public void getInfo(JSONArray arr, int index) {
        try {
            JSONObject obj2 = arr.getJSONObject(index);
            printList.add(obj2.getString("kanji"));
        } catch (JSONException e) {
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

    class ListAdapter extends ArrayAdapter<String> {
        Context context;
        private ArrayList<String> list;

        ListAdapter(Context c, ArrayList<String> list) {
            super(c, R.layout.list_item, R.id.textKanji, list);
            this.context = c;
            this.list = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View ConvertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.list_item, parent, false);
            TextView textView1 = row.findViewById(R.id.textKanji);

            textView1.setText(list.get(position));

            return row;
        }

        public void clearView() {
            list.clear();
        }

    }

}
