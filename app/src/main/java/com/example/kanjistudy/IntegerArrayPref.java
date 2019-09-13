package com.example.kanjistudy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class IntegerArrayPref {

    //리스트에 저장할 때 사용할 SharedPreference
    public void setIntegerArrayPref(Context context, String key, ArrayList<Integer> values){
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

    //SharedPreference에 저장된 Integer형 ArrayList 가져오기
    public ArrayList<Integer> getIntegerArrayPref(Context context, String key){
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

    //SharedPreference에 저장된 Int 숫자 중 해당 인덱스 삭제
    public ArrayList<Integer> getRemovedIntegerArrayPref(Context context, String key, int index){
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
        urls.remove(index);
        return urls;
    }
}
