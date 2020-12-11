package com.example.nol;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Rabbit extends AppCompatActivity implements View.OnTouchListener {
    ImageView rabbit, cookie, correct;
    Button prevBtn, listBtn, hintBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_rabbit);

        rabbit = (ImageView) findViewById(R.id.rabbit);
        cookie = (ImageView) findViewById(R.id.cookie);
        correct = (ImageView) findViewById(R.id.correct1);

        cookie.setOnTouchListener(this);

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.rabbitPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Enter.class);
                startActivity(intent);
                finish();
            }
        });

        // 게임 목록
        listBtn = (Button) findViewById(R.id.rabbitList);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 힌트 보기
        hintBtn = (Button) findViewById(R.id.rabbitHint);
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDialog("화면에는 토끼가 한 마리만 있는 게 아닙니다.");
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

            // 토끼 글자 위에서 손을 뗌
            if (event.getRawX() > 300 && event.getRawX() < 500 && event.getRawY() > 160 && event.getRawY() < 290) {
                correct.setVisibility(View.VISIBLE);
            }
            Log.d("viewTest", "x : " + event.getRawX() + " y : " + event.getRawY());
        }
        return true;
    }

    public void makeDialog(String text) {
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        Dialog dlg = new Dialog(this);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        TextView hintText = (TextView) dlg.findViewById(R.id.hintText);
        TextView hintOk = (TextView) dlg.findViewById(R.id.hintOk);

        hintText.setText(text);
        hintOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }
}
