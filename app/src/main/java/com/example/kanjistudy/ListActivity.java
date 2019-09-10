package com.example.kanjistudy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> mTitle = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mTitle.add("facebook");
        mTitle.add("whatsapp");
        mTitle.add("twitter");
        mTitle.add("instagram");
        mTitle.add("youtube");
        listView = findViewById(R.id.listView);

        //create adapter
        MyAdapter adapter = new MyAdapter(this, mTitle);
        listView.setAdapter(adapter);

        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Toast.makeText(ListActivity.this, "Facebook Description", Toast.LENGTH_SHORT).show();
                }
                if(position == 1){
                    Toast.makeText(ListActivity.this, "Whatsapp Description", Toast.LENGTH_SHORT).show();
                }
                if(position == 2){
                    Toast.makeText(ListActivity.this, "Twitter Description", Toast.LENGTH_SHORT).show();
                }
                if(position == 3){
                    Toast.makeText(ListActivity.this, "Instagram Description", Toast.LENGTH_SHORT).show();
                }
                if(position == 4){
                    Toast.makeText(ListActivity.this, "Youtube Description", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> rTitle;

        MyAdapter (Context c, ArrayList<String> title){
            super(c, R.layout.list_item, R.id.textKanji, title);
            this.context = c;
            this.rTitle = title;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View ConvertView, @NonNull ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.list_item, parent, false);
            TextView textView1 = row.findViewById(R.id.textKanji);

            textView1.setText(rTitle.get(position));

            return row;
        }

    }
}
