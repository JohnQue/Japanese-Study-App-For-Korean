package com.example.kanjistudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    AppCompatButton randomStudy, unknown, phraseStudy, verbStudy, termination;
    private onBackDoublePressed dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomStudy = findViewById(R.id.randomStudy);
        unknown = findViewById(R.id.unknown);
        phraseStudy = findViewById(R.id.phraseStudy);
        verbStudy = findViewById(R.id.verbStudy);
        termination = findViewById(R.id.termination);
        dp = new onBackDoublePressed(this);

        randomStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RandomStudyActivity.class);
                startActivity(intent);
            }
        });
        unknown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UnknownListActivity.class));
            }
        });
        phraseStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
        termination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        dp.onBackPressed();
    }
}
