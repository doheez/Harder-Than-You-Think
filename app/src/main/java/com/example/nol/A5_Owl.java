package com.example.nol;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.nol.A0_Enter.mediaPlayer;

public class A5_Owl extends AppCompatActivity {
    int brightness;
    ImageView owlSleep, owlAwake, correct;
    Button prevBtn, listBtn, hintBtn;
    int flag;
    Activity activity = A5_Owl.this;
    Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_owl);
        MySoundPlayer.initSounds(getApplicationContext());

        Intent intent = getIntent();
        flag = intent.getExtras().getInt("flag");

        // 타이머 시작
        TextView time = (TextView) findViewById(R.id.owlTimer);
        timer = new Timer(this,this, time);
        timer.startTimer();

        owlSleep = (ImageView) findViewById(R.id.owlSleep);
        owlAwake = (ImageView) findViewById(R.id.owlAwake);
        correct = (ImageView) findViewById(R.id.owlCorrect);

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.owlPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), A4_Pizza.class);
                intent.putExtra("flag", flag); // 이전 단계로 가도 현재 단계까지 깼음을 알 수 있음
                startActivity(intent);
                timer.countDownTimer.cancel();
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 게임 목록
        listBtn = (Button) findViewById(R.id.owlList);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                LayoutInflater inflater = getLayoutInflater();
                new CustomDialog(A5_Owl.this, activity, inflater, flag, 5, timer).gameListDialog();
            }
        });

        // 힌트 보기
        hintBtn = (Button) findViewById(R.id.owlHint);
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                new CustomDialog(A5_Owl.this).hintDialog("부엉이는 야행성입니다.");
            }
        });

        Thread thread = new Thread(new detectThread());
        thread.start();
    }

    // 화면 밝기 조절 감지
    class detectThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(300);
                    brightness = Settings.System.getInt(getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS);
                    System.out.println(brightness);

                    if (brightness <= 30) {
                        Message msg = handlerOwl.obtainMessage();
                        handlerOwl.sendMessage(msg);
                        break;
                    }
                }
                Thread.sleep(1500);
                Message msg = handlerCorrect.obtainMessage();
                handlerCorrect.sendMessage(msg);
                Thread.sleep(2000);

                Intent intent = new Intent(getApplicationContext(), A6_River.class);
                if (flag == 5) ++flag;
                intent.putExtra("flag", flag);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            } catch (Settings.SettingNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 부엉이 눈 뜸
    final Handler handlerOwl = new Handler() {
        public void handleMessage(Message msg){
            MySoundPlayer.play(MySoundPlayer.OWL);
            owlSleep.setVisibility(View.INVISIBLE);
            timer.countDownTimer.cancel();
        }
    };

    // 정답 표시
    final Handler handlerCorrect = new Handler() {
        public void handleMessage(Message msg){
            correct.setVisibility(View.VISIBLE);
            MySoundPlayer.play(MySoundPlayer.CORRECT);

            ObjectAnimator anim1 = ObjectAnimator.ofFloat(correct, "rotation", 0f, 5f);
            anim1.setRepeatMode(ValueAnimator.REVERSE);
            anim1.setRepeatCount(5);
            anim1.setDuration(300);
            anim1.start();
        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}