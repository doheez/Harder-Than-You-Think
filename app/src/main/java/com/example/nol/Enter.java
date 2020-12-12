package com.example.nol;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Enter extends AppCompatActivity {
    private Button start, explain, info;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        MySoundPlayer.initSounds(getApplicationContext());

        mediaPlayer = MediaPlayer.create(this,R.raw.backgroundmusic);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        start = (Button)findViewById(R.id.enterStart);
        explain = (Button)findViewById(R.id.enterExplain);
        info = (Button)findViewById(R.id.enterInfo);

       // MySoundPlayer.play(MySoundPlayer.BACKGROUNDMUSIC);

        // 게임 시작 클릭
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), Rabbit.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 게임 설명 클릭
        explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);

            }
        });

        // 개발자 소개 클릭
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);

            }
        });
    }
}
