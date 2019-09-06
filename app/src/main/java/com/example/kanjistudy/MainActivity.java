package com.example.kanjistudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    AppCompatButton randomStudy, unknown, onlyMeaning, onlyPronc, phraseStudy, verbStudy, termination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomStudy = findViewById(R.id.randomStudy);
        unknown = findViewById(R.id.unknown);
        onlyMeaning = findViewById(R.id.onlyMeaning);
        onlyPronc = findViewById(R.id.onlyPronc);
        phraseStudy = findViewById(R.id.phraseStudy);
        verbStudy = findViewById(R.id.verbStudy);
        termination = findViewById(R.id.termination);

        randomStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RandomStudyActivity.class);
                startActivity(intent);
            }
        });
    }
}
