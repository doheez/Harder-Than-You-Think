package com.example.nol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

        // 타이머 시작
        TextView time = (TextView) findViewById(R.id.riverTimer);
        new Timer(this, this, time).startTimer();

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.riverPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), A5_Owl.class);
                intent.putExtra("flag", flag); // 이전 단계로 가도 현재 단계까지 깼음을 알 수 있음
                startActivity(intent);
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
                new CustomDialog(A6_River.this, activity, inflater, flag, 6).gameListDialog();
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
        int parentHeight = ((ViewGroup) v.getParent()).getHeight();    // 부모 View 의 Height

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 뷰 누름
            oldXvalue = event.getX();
            oldYvalue = event.getY();
            Log.d("viewTest", "oldXvalue : " + oldXvalue + " oldYvalue : " + oldYvalue);    // View 내부에서 터치한 지점의 상대 좌표값.
            Log.d("viewTest", "v.getX() : " + v.getX());    // View 의 좌측 상단이 되는 지점의 절대 좌표값.
            Log.d("viewTest", "RawX : " + event.getRawX() + " RawY : " + event.getRawY());    // View 를 터치한 지점의 절대 좌표값.
            Log.d("viewTest", "v.getHeight : " + v.getHeight() + " v.getWidth : " + v.getWidth());    // View 의 Width, Height

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

            Log.d("viewTest", "x : " + event.getRawX() + " y : " + event.getRawY());
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}
