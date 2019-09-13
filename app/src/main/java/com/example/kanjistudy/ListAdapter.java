//package com.example.kanjistudy;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class ListAdapter extends ArrayAdapter<String> {
//    private ArrayList<String> list = null;
//    private int cnt = 0;
//
//    public ListAdapter(Context c, ArrayList<String> list)
//    {
//        this.list = list;
//        cnt = list.size();
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View ConvertView, @NonNull ViewGroup parent){
//        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View row = layoutInflater.inflate(R.layout.list_item, parent, false);
//        TextView textView1 = row.findViewById(R.id.textKanji);
//
//        textView1.setText(list.get(position));
//
//        return row;
//    }
//
//    public void clearView(){
//        list.clear();
//    }
//
//
//
//}
