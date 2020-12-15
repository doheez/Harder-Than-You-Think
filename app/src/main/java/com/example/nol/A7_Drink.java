package com.example.nol;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class A7_Drink extends AppCompatActivity implements SensorEventListener {

    Drawable drawable;
    private ImageView imageView, correct, drawable2;
    Timer timer;

    //Using the Accelometer & Gyroscoper
    //센서 매니저 얻기
    private SensorManager mSensorManager;
    //자이로스코프 센서(회전)
    private Sensor mGyroscope;

    Button prevBtn, listBtn, hintBtn;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_drink);
        Activity activity = A7_Drink.this;

        MySoundPlayer.initSounds(getApplicationContext());
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // 타이머 시작
        TextView time = (TextView) findViewById(R.id.drinkTimer);
        timer = new Timer(this,this, time);
        timer.startTimer();

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.drinkPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), A6_River.class);
                intent.putExtra("flag", flag); // 이전 단계로 가도 현재 단계까지 깼음을 알 수 있음
                startActivity(intent);
                timer.countDownTimer.cancel();
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 게임 목록
        listBtn = (Button) findViewById(R.id.drinkList);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                LayoutInflater inflater = getLayoutInflater();
                new CustomDialog(A7_Drink.this, activity, inflater, flag, 6, timer).gameListDialog();
            }
        });



        // 힌트 보기
        hintBtn = (Button) findViewById(R.id.drinkHint);
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                new CustomDialog(A7_Drink.this).hintDialog("음료를 부으려면 어떻게 해야할까요?");            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGyroscope != null)
            mSensorManager.registerListener(this, mGyroscope, mSensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mSensorManager != null)
            mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private double RAD2DGR = 180 / Math.PI;
    private static final float NS2S = 1.0f / 1000000000.0f;
    private double dt;
    private double lastX;
    private double lastY;
    private double lastZ;
    private double currentTime;
    private double x, y, z;
    int count = 0;

    // 센서 변화 감지
    @Override
    public void onSensorChanged(SensorEvent event) {
        correct = (ImageView) findViewById(R.id.drinkCorrect);
        imageView = (ImageView) findViewById(R.id.empty_glass); // ID 설정을 합니다
        drawable2 = (ImageView) findViewById(R.id.juice);

        Sensor sensor = event.sensor;
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            //long currentTime = System.currentTimeMillis();
            // long gabOfTime = (currentTime - lastTime);
            //if (gabOfTime > 100) {
            dt = (event.timestamp - currentTime) * NS2S;
            currentTime = event.timestamp;
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            /* 각속도 성분을 적분 -> 회전각(pitch, roll)으로 변환.
             * 여기까지의 pitch, roll의 단위는 '라디안'이다.
             * SO 아래 로그 출력부분에서 멤버변수 'RAD2DGR'를 곱해주어 degree로 변환해줌.  */
            if (dt - currentTime * NS2S != 0) {
                lastY = (lastY + y * dt) * RAD2DGR;
                lastX = (lastX + x * dt) * RAD2DGR;
                lastZ = (lastZ + z * dt) * RAD2DGR;

                if (y < -1.3 && count == 0) {
                    MySoundPlayer.play(MySoundPlayer.POUR_SOUND);
                    drawable = getResources().getDrawable(R.drawable.pour_juice);
                    imageView.setImageDrawable(drawable); // 이미지를 적용합니다
                    count++;

                    ObjectAnimator animRotation = ObjectAnimator.ofFloat(drawable2, "rotation", 0f, -30f);
                    animRotation.setDuration(500);
                    animRotation.start();

                    Log.d("센서 감지: ", "x "+x);
                    Log.d("센서 감지: ", "y "+y);
                    Log.d("센서 감지: ", "z "+z);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            drawable = getResources().getDrawable(R.drawable.complete_juice);
                            imageView.setImageDrawable(drawable); // 이미지를 적용합니다
                            drawable2.setVisibility(View.GONE);
                            //success
                            MySoundPlayer.play(MySoundPlayer.CORRECT);
                            correct.setVisibility(View.VISIBLE); //딜레이 후 시작할 코드 작성\
                            ObjectAnimator anim1 = ObjectAnimator.ofFloat(correct, "rotation", 0f, 5f);
                            anim1.setRepeatMode(ValueAnimator.REVERSE);
                            anim1.setRepeatCount(5);
                            anim1.setDuration(300);
                            anim1.start();

                            timer.countDownTimer.cancel();
                            MySoundPlayer.play(MySoundPlayer.CORRECT);
                            //딜레이 후 시작할 코드 작성
                        }
                    }, 3000);
                }
            }
        }
    }


}