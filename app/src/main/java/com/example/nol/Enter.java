package com.example.nol;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Enter extends AppCompatActivity {
    private Button start, explain, info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        start = (Button)findViewById(R.id.enterStart);
        explain = (Button)findViewById(R.id.enterExplain);
        info = (Button)findViewById(R.id.enterInfo);

        // 게임 시작 클릭
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 게임 설명 클릭
        explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 개발자 소개 클릭
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
