package com.example.nol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class A2_Egg extends AppCompatActivity {
    private ImageView imageView, correct;
    Button prevBtn, listBtn, hintBtn;
    int i = 0;
    int flag;
    Drawable drawable; // 대리자를 선언합니다
    List<String> gameList = new ArrayList<String>();
    Activity activity = A2_Egg.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_egg);
        correct = (ImageView) findViewById(R.id.eggCorrect);
        MySoundPlayer.initSounds(getApplicationContext());

        Intent intent = getIntent();
        flag = intent.getExtras().getInt("flag");

        // 타이머 시작
        TextView time = (TextView) findViewById(R.id.eggTimer);
        new Timer(this, time).startTimer();

        // 힌트 보기
        hintBtn = (Button) findViewById(R.id.eggHint);
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                new CustomDialog(A2_Egg.this).hintDialog("계란을 자극시켜보세요.");
            }
        });

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.eggPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), A1_Rabbit.class);
                intent.putExtra("flag", flag); // 이전 단계로 가도 현재 단계까지 깼음을 알 수 있음
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 게임 목록에 아이템 추가
        for (int i = 1; i <= 5; i++)
            gameList.add(i + "단계");

        // 게임 목록
        listBtn = (Button) findViewById(R.id.eggList);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                LayoutInflater inflater = getLayoutInflater();
                new CustomDialog(A2_Egg.this, activity, inflater, flag, 2).gameListDialog();
            }
        });


        imageView = (ImageView) findViewById(R.id.egg); // ID 설정을 합니다
        final Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE); // 진동

        // 그림 터치시에, 이벤트를 발생하게 해주는 함수입니다
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i++;
                vibrator.vibrate(50); // 계란 터치 시 0.05초 진동

                if (i == 10) {
                    MySoundPlayer.play(MySoundPlayer.CRACK);
                    drawable = getResources().getDrawable(R.drawable.egg_cracked_1);
                    imageView.setImageDrawable(drawable); // 이미지를 적용합니다
                } else if (i == 20) {
                    MySoundPlayer.play(MySoundPlayer.CRACK);
                    drawable = getResources().getDrawable(R.drawable.egg_cracked_2);
                    imageView.setImageDrawable(drawable); // 이미지를 적용합니다
                } else if (i == 30) {
                    MySoundPlayer.play(MySoundPlayer.CRACK);
                    drawable = getResources().getDrawable(R.drawable.egg_cracked_3);
                    imageView.setImageDrawable(drawable); // 이미지를 적용합니다

                } else if (i == 40) {
                    MySoundPlayer.play(MySoundPlayer.BROKEN);
                    MySoundPlayer.play(MySoundPlayer.CHICKEN);
                    drawable = getResources().getDrawable(R.drawable.chicken);
                    imageView.setImageDrawable(drawable); // 이미지를 적용합니다
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MySoundPlayer.stop(MySoundPlayer.CHICKEN);
                            MySoundPlayer.stop(MySoundPlayer.BROKEN);
                            MySoundPlayer.play(MySoundPlayer.CORRECT);
                            correct.setVisibility(View.VISIBLE); //딜레이 후 시작할 코드 작성
                        }
                    }, 2000);// 2초 정도 딜레이를 준 후 시작

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), A3_Birthday.class);
                            if (flag == 2) ++flag;
                            intent.putExtra("flag", flag);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }
                    }, 4000);
                }
            }
        });
    }
}