package com.example.nol;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.nol.A0_Enter.mediaPlayer;

public class A6_River extends AppCompatActivity implements View.OnTouchListener{
    ImageView river, board, correct;
    Button prevBtn, listBtn, hintBtn;
    int flag;
    Activity activity = A6_River.this;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    Timer timer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6_river);
        MySoundPlayer.initSounds(getApplicationContext());

        Intent intent = getIntent();
        flag = intent.getExtras().getInt("flag");

        river = (ImageView) findViewById(R.id.river);
        board = (ImageView) findViewById(R.id.board);
        correct = (ImageView) findViewById(R.id.riverCorrect);

        board.setOnTouchListener(this);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        // 타이머 시작
        TextView time = (TextView) findViewById(R.id.riverTimer);
        timer = new Timer(this,this, time);
        timer.startTimer();

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.riverPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), A5_Owl.class);
                intent.putExtra("flag", flag); // 이전 단계로 가도 현재 단계까지 깼음을 알 수 있음
                startActivity(intent);
                timer.countDownTimer.cancel();
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 게임 목록
        listBtn = (Button) findViewById(R.id.riverList);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                LayoutInflater inflater = getLayoutInflater();
                new CustomDialog(A6_River.this, activity, inflater, flag, 6, timer).gameListDialog();
            }
        });

        // 힌트 보기
        hintBtn = (Button) findViewById(R.id.riverHint);
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                new CustomDialog(A6_River.this).hintDialog("판자 크기가 너무 작아요!");
            }
        });
    }

    float oldXvalue;
    float oldYvalue;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int parentWidth = ((ViewGroup) v.getParent()).getWidth();    // 부모 View 의 Width
        int parentHeight = ((ViewGroup) v.getParent()).getHeight();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 뷰 누름
            oldXvalue = event.getX();
            oldYvalue = event.getY();

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // 뷰 이동 중
            v.setX(v.getX() + (event.getX()) - (v.getWidth() / 2));
            v.setY(v.getY() + (event.getY()) - (v.getHeight() / 2));

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // 뷰에서 손을 뗌

            if (v.getX() < 0) {
                v.setX(0);
            } else if ((v.getX() + v.getWidth()) > parentWidth) {
                v.setX(parentWidth - v.getWidth());
            }

            if (v.getY() < 0) {
                v.setY(0);
            } else if ((v.getY() + v.getHeight()) > parentHeight) {
                v.setY(parentHeight - v.getHeight());
            }

            if(event.getRawY() > 1100 && event.getRawY() < 1200)
                if(board.getScaleX() > 2.0 && board.getScaleY() > 2.0) {
                    correct.setVisibility(View.VISIBLE);

                    ObjectAnimator anim1 = ObjectAnimator.ofFloat(correct, "rotation", 0f, 5f);
                    anim1.setRepeatMode(ValueAnimator.REVERSE);
                    anim1.setRepeatCount(5);
                    anim1.setDuration(300);
                    anim1.start();

                    timer.countDownTimer.cancel();
                    MySoundPlayer.play(MySoundPlayer.CORRECT);
                }// 부모 View 의 Height

            Log.d("viewTest", "x : " + event.getRawX() + " y : " + event.getRawY());
            Log.d("viewTest", "height : " + board.getScaleX() + " width : " + board.getScaleY());
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        //변수로 선언해 놓은 ScaleGestureDetector
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            // ScaleGestureDetector에서 factor를 받아 변수로 선언한 factor에 넣고
            mScaleFactor *= scaleGestureDetector.getScaleFactor();

            // 최대 10배, 최소 10배 줌 한계 설정
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));

            // 이미지뷰 스케일에 적용
            board.setScaleX(mScaleFactor);
            board.setScaleY(mScaleFactor);

            return true;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();

    }
}
