package com.example.kanjistudy;

import android.app.Activity;
import android.widget.Toast;

public class onBackDoublePressed {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public onBackDoublePressed(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity,"もう一度押したら終了となります。", Toast.LENGTH_SHORT);
        toast.show();
    }
}



