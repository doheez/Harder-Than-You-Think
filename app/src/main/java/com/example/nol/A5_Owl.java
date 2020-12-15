package com.example.nol;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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

public class A5_Owl extends AppCompatActivity {
    int brightness;
    ImageView owlSleep, owlAwake, correct;
    Button prevBtn, listBtn, hintBtn;
    int flag;
    Activity activity = A5_Owl.this;
    Timer timer;
    public static MediaPlayer snoringSound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_owl);
        MySoundPlayer.initSounds(getApplicationContext());

        Intent intent = getIntent();
        flag = intent.getExtras().getInt("flag");

        // 타이머 시작
        TextView time = (TextView) findViewById(R.id.owlTimer);
        timer = new Timer(this,this, time, true);
        timer.startTimer();

        //코고는 소리
        snoringSound = MediaPlayer.create(this, R.raw.snoring_sound);
        snoringSound.setLooping(true);
        snoringSound.start();

        owlSleep = (ImageView) findViewById(R.id.owlSleep);
        owlAwake = (ImageView) findViewById(R.id.owlAwake);
        correct = (ImageView) findViewById(R.id.owlCorrect);

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.owlPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snoringSound.stop();
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

                Message msg2 = handlerToRiver.obtainMessage();
                handlerToRiver.sendMessage(msg2);

            } catch (Settings.SettingNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 부엉이 눈 뜸
    final Handler handlerOwl = new Handler() {
        public void handleMessage(Message msg){
            snoringSound.stop();
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

            ObjectAnimator anim1 = ObjectAnimator.ofFloat(correct, "rotation", -2f, 2f);
            anim1.setRepeatMode(ValueAnimator.REVERSE);
            anim1.setRepeatCount(9999);
            anim1.setDuration(300);
            anim1.start();
        }
    };

    // 화면 전환 효과 안 먹히는 현상 해결
    final Handler handlerToRiver = new Handler() {
        public void handleMessage(Message msg){
            Intent intent = new Intent(getApplicationContext(), AX_StageClear.class);
            intent.putExtra("text",
                    "밝기를 낮추면 어두워지죠\n" +
                            "똑똑하시군요!");
            if(flag == 5) ++flag;
            intent.putExtra("flag", flag);
            intent.putExtra("key", "river");
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        snoringSound.stop();
    }
}