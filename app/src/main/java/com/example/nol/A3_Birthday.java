package com.example.nol;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.nol.A0_Enter.mediaPlayer;

public class A3_Birthday extends AppCompatActivity implements SensorEventListener {

    Activity activity = A3_Birthday.this;

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 1000;
    private static final int DATA_X = 0;
    private static final int DATA_Y = 1;
    private static final int DATA_Z = 2;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;

    ImageView man, cake, correct;
    Button prevBtn, listBtn, hintBtn;
    int flag;
    int shakingCnt = 0;
    Timer timer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_birthday);
        MySoundPlayer.initSounds(getApplicationContext());

        Intent intent = getIntent();
        flag = intent.getExtras().getInt("flag");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        man = (ImageView)findViewById(R.id.man);
        cake = (ImageView)findViewById(R.id.cake);
        correct = (ImageView)findViewById(R.id.birthdayCorrect);

        // 타이머 시작
        TextView time = (TextView) findViewById(R.id.birthdayTimer);
        timer = new Timer(this,this, time);
        timer.startTimer();

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.birthdayPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), A2_Egg.class);
                intent.putExtra("flag", flag); // 이전 단계로 가도 현재 단계까지 깼음을 알 수 있음
                startActivity(intent);
                timer.countDownTimer.cancel();
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 게임 목록
        listBtn = (Button) findViewById(R.id.birthdayList);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                LayoutInflater inflater = getLayoutInflater();
                new CustomDialog(A3_Birthday.this, activity, inflater, flag, 3, timer).gameListDialog();
            }
        });

        // 힌트 보기
        hintBtn = (Button) findViewById(R.id.birthdayHint);
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                new CustomDialog(A3_Birthday.this).hintDialog("화면 터치로는 아무 일도 일어나지 않아요!");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // 가속도 센서 변화 감지
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) {
                lastTime = currentTime;
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                // 쉐이킹 감지
                if (shakingCnt == 0 && speed > SHAKE_THRESHOLD) {
                    shakingCnt++; // 딱 한 번만 감지하도록 함. 여러 번 감지하면 효과음도 여러 번 나고 다음 액티비티가 여러 개 뜸

                    ObjectAnimator animX = ObjectAnimator.ofFloat(cake, "translationX", 50);
                    ObjectAnimator animY = ObjectAnimator.ofFloat(cake, "translationY", -1150);
                    ObjectAnimator animRotation = ObjectAnimator.ofFloat(cake, "rotation", 0f, 380f);

                    AnimatorSet animator = new AnimatorSet();
                    animator.playTogether(animX, animY, animRotation);
                    animator.setDuration(300);
                    animator.start();

                    MySoundPlayer.play(MySoundPlayer.CAKE);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            man.setImageResource(R.drawable.birthday2);
                            correct.setVisibility(View.VISIBLE);
                            MySoundPlayer.play(MySoundPlayer.CORRECT);

                            ObjectAnimator anim1 = ObjectAnimator.ofFloat(correct, "rotation", 0f, 5f);
                            anim1.setRepeatMode(ValueAnimator.REVERSE);
                            anim1.setRepeatCount(5);
                            anim1.setDuration(300);
                            anim1.start();

                            timer.countDownTimer.cancel();
                        }
                    },300);

                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), A4_Pizza.class);
                            if(flag == 3) ++flag;
                            intent.putExtra("flag", flag);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }
                    },2300);
                }

                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}