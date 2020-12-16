package com.example.nol;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.nol.A0_Enter.mediaPlayer;

public class AX_Timeover extends AppCompatActivity {
    TextView timeover, restart, exit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_timeover);
        MySoundPlayer.initSounds(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MySoundPlayer.play((MySoundPlayer.TIMEOVER));
            }
        }, 300);

        timeover = (TextView) findViewById(R.id.timeover);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(timeover, "rotation", -1.5f, 1.5f);
        anim1.setRepeatMode(ValueAnimator.REVERSE);
        anim1.setRepeatCount(9999);
        anim1.setDuration(100);
        anim1.start();

        // 처음으로
        restart = (TextView) findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), A0_Enter.class);
                intent.putExtra("flag", 0); // 게임 성공한 단계 초기화
                intent.putExtra("fromSplash", 0);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 끝내기
        exit = (TextView) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                mediaPlayer.stop();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}
