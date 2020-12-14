package com.example.nol;

import android.os.CountDownTimer;
import android.widget.TextView;

public class Timer {
    private TextView time;

    // 생성자
    public Timer(TextView time){
        this.time = time;
    }

    // 타이머
    public void startTimer(){
        final int MILLISINFUTURE = 60 * 1000; // 총 시간 (60초)
        final int COUNT_DOWN_INTERVAL = 1000; // onTick 메소드를 호출할 간격 (1초)

        new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL){

            @Override
            public void onTick(long l) {
                long seconds = l/1000; // 남은 시간

                if(seconds >= 10) // 초가 두 자리 수이면 바로 출력
                    time.setText("0:" + seconds);
                else // 초가 한 자리 수이면 0 붙여서 출력
                    time.setText("0:0" + seconds);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}
