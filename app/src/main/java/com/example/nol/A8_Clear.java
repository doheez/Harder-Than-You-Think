package com.example.nol;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.nol.A0_Enter.mediaPlayer;

public class A8_Clear extends AppCompatActivity {
    TextView restart, exit;
    ImageView board;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8_clear);
        MySoundPlayer.initSounds(getApplicationContext());

        // 처음으로
        restart = (TextView) findViewById(R.id.clearRestart);
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
        exit = (TextView) findViewById(R.id.clearExit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                mediaPlayer.stop();
                finish();
            }
        });

        // 보드 움직임
        board = (ImageView) findViewById(R.id.board);

        ObjectAnimator animX = ObjectAnimator.ofFloat(board, "translationX", -500);
        animX.setRepeatMode(ValueAnimator.REVERSE);
        animX.setRepeatCount(9999);
        ObjectAnimator animY = ObjectAnimator.ofFloat(board, "translationY", -130);
        animY.setRepeatMode(ValueAnimator.REVERSE);
        animY.setRepeatCount(9999);

        AnimatorSet animator = new AnimatorSet();
        animator.playTogether(animX, animY);
        animator.setDuration(1000);
        animator.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}
