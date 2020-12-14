package com.example.nol;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class Timer {
    private TextView time;
    private Context context;

    // 생성자
    public Timer(Context context, TextView time){
        this.context = context;
        this.time = time;
    }

    // 타이머
    public void startTimer(){
        final int MILLISINFUTURE = 15 * 1000; // 총 시간 (60초)
        final int COUNT_DOWN_INTERVAL = 1000; // onTick 메소드를 호출할 간격 (1초)

        new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL){

            @Override
            public void onTick(long l) {
                long seconds = l/1000; // 남은 시간

                if(seconds >= 10) // 초가 두 자리 수이면 바로 출력
                    time.setText("0:" + seconds);
                else{ // 초가 한 자리 수이면 0 붙여서 출력
                    time.setText("0:0" + seconds);

                    if(seconds == 9) time.setBackground(ContextCompat.getDrawable(context, R.drawable.timer_red1));
                    else if(seconds == 8) time.setBackground(ContextCompat.getDrawable(context, R.drawable.timer_red2));
                    else if(seconds == 7) time.setBackground(ContextCompat.getDrawable(context, R.drawable.timer_red3));
                    else if(seconds == 6) time.setBackground(ContextCompat.getDrawable(context, R.drawable.timer_red4));
                    else if(seconds == 5) time.setBackground(ContextCompat.getDrawable(context, R.drawable.timer_red5));
                    else if(seconds == 4) time.setBackground(ContextCompat.getDrawable(context, R.drawable.timer_red6));
                    else if(seconds == 3) time.setBackground(ContextCompat.getDrawable(context, R.drawable.timer_red7));
                    else if(seconds == 2) time.setBackground(ContextCompat.getDrawable(context, R.drawable.timer_red8));
                    else if(seconds == 1) time.setBackground(ContextCompat.getDrawable(context, R.drawable.timer_red9));
                    else if(seconds == 0) time.setBackground(ContextCompat.getDrawable(context, R.drawable.timer_red10));
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}
