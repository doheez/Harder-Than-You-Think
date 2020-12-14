package com.example.nol;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.nol.A0_Enter.mediaPlayer;

public class A4_Pizza extends AppCompatActivity implements View.OnTouchListener {
    ImageView pizza1, pizza2, pizza2v2, pizza2v3, pizza3, pizza4, pizza5, pizza5v2, pizza6, pizza7, pizza8, pizza8v2;
    ImageView correct;
    ImageView add, delete;
    TextView cnt, submit;
    Button prevBtn, listBtn, hintBtn;
    int flag;
    Activity activity = A4_Pizza.this;
    Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_pizza);
        MySoundPlayer.initSounds(getApplicationContext());

        Intent intent = getIntent();
        flag = intent.getExtras().getInt("flag");

        // 타이머 시작
        TextView time = (TextView) findViewById(R.id.pizzaTimer);
        timer = new Timer(this,this, time);
        timer.startTimer();

        pizza1 = (ImageView) findViewById(R.id.pizza1);
        pizza1.setDrawingCacheEnabled(true);
        pizza1.setOnTouchListener(this);
        pizza2 = (ImageView) findViewById(R.id.pizza2);
        pizza2.setDrawingCacheEnabled(true);
        pizza2.setOnTouchListener(this);
        pizza2v2 = (ImageView) findViewById(R.id.pizza2v2);
        pizza2v2.setDrawingCacheEnabled(true);
        pizza2v2.setOnTouchListener(this);
        pizza2v3 = (ImageView) findViewById(R.id.pizza2v3);
        pizza2v3.setDrawingCacheEnabled(true);
        pizza2v3.setOnTouchListener(this);
        pizza3 = (ImageView) findViewById(R.id.pizza3);
        pizza3.setDrawingCacheEnabled(true);
        pizza3.setOnTouchListener(this);
        pizza4 = (ImageView) findViewById(R.id.pizza4);
        pizza4.setDrawingCacheEnabled(true);
        pizza4.setOnTouchListener(this);
        pizza5 = (ImageView) findViewById(R.id.pizza5);
        pizza5.setDrawingCacheEnabled(true);
        pizza5.setOnTouchListener(this);
        pizza5v2 = (ImageView) findViewById(R.id.pizza5v2);
        pizza5v2.setDrawingCacheEnabled(true);
        pizza5v2.setOnTouchListener(this);
        pizza6 = (ImageView) findViewById(R.id.pizza6);
        pizza6.setDrawingCacheEnabled(true);
        pizza6.setOnTouchListener(this);
        pizza7 = (ImageView) findViewById(R.id.pizza7);
        pizza7.setDrawingCacheEnabled(true);
        pizza7.setOnTouchListener(this);
        pizza8 = (ImageView) findViewById(R.id.pizza8);
        pizza8.setDrawingCacheEnabled(true);
        pizza8.setOnTouchListener(this);
        pizza8v2 = (ImageView) findViewById(R.id.pizza8v2);
        pizza8v2.setDrawingCacheEnabled(true);
        pizza8v2.setOnTouchListener(this);
        correct = (ImageView) findViewById(R.id.pizzaCorrect);
        cnt = (TextView) findViewById(R.id.pizzaCnt);

        // 플러스 버튼
        add = (ImageView) findViewById(R.id.pizzaAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                cnt.setText("" + (Integer.parseInt(cnt.getText().toString()) + 1));
            }
        });

        // 마이너스 버튼
        delete = (ImageView) findViewById(R.id.pizzaDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                if (Integer.parseInt(cnt.getText().toString()) != 0)
                    cnt.setText("" + (Integer.parseInt(cnt.getText().toString()) - 1));
            }
        });

        // 제출 버튼
        submit = (TextView) findViewById(R.id.pizzaSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                if (Integer.parseInt(cnt.getText().toString()) == 12) { // 정답이면
                    correct.setVisibility(View.VISIBLE);
                    MySoundPlayer.play(MySoundPlayer.CORRECT);
                    timer.countDownTimer.cancel();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), A5_Owl.class);
                            if (flag == 4) ++flag;
                            intent.putExtra("flag", flag);
                            startActivity(intent);

                            finish();
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }
                    }, 2000);
                }
            }
        });

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.pizzaPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), A3_Birthday.class);
                intent.putExtra("flag", flag); // 이전 단계로 가도 현재 단계까지 깼음을 알 수 있음
                startActivity(intent);
                timer.countDownTimer.cancel();
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 게임 목록
        listBtn = (Button) findViewById(R.id.pizzaList);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                LayoutInflater inflater = getLayoutInflater();
                new CustomDialog(A4_Pizza.this, activity, inflater, flag, 4, timer).gameListDialog();
            }
        });

        // 힌트 보기
        hintBtn = (Button) findViewById(R.id.pizzaHint);
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                new CustomDialog(A4_Pizza.this).hintDialog("피자가 어디엔가 더 숨어있어요.");
            }
        });
    }

    float oldXvalue;
    float oldYvalue;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int parentWidth = ((ViewGroup) v.getParent()).getWidth();    // 부모 View 의 Width
        int parentHeight = ((ViewGroup) v.getParent()).getHeight();    // 부모 View 의 Height

        // 투명 영역 터치 안 되게
        //Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
        //int color = bmp.getPixel((int) event.getX(), (int) event.getY());
        //if (color == Color.TRANSPARENT)
        //    return false;

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
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}
