package com.example.nol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Egg extends AppCompatActivity {
    private ImageView imageView, correct;
    Button prevBtn, listBtn, hintBtn;
    int i=0;
    Drawable drawable; // 대리자를 선언합니다
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg);
        correct = (ImageView) findViewById(R.id.correct1);
        MySoundPlayer.initSounds(getApplicationContext());

        // 힌트 보기
        hintBtn = (Button) findViewById(R.id.eggHint);
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                makeDialog("계란을 자극시켜 보세요");
            }
        });

        imageView = (ImageView) findViewById(R.id.egg); // ID 설정을 합니다

            // 그림 터치시에, 이벤트를 발생하게 해주는 함수입니다
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    i++;

                    if (i==10) {
                        MySoundPlayer.play(MySoundPlayer.CRACK);
                        drawable = getResources().getDrawable(R.drawable.egg_cracked_1);
                        imageView.setImageDrawable(drawable); // 이미지를 적용합니다
                    }

                    else if(i==20) {
                        MySoundPlayer.play(MySoundPlayer.CRACK);
                        drawable = getResources().getDrawable(R.drawable.egg_cracked_2);
                        imageView.setImageDrawable(drawable); // 이미지를 적용합니다
                    }

                    else if(i==30) {
                        MySoundPlayer.play(MySoundPlayer.CRACK);
                        drawable = getResources().getDrawable(R.drawable.egg_cracked_3);
                        imageView.setImageDrawable(drawable); // 이미지를 적용합니다

                    }
                    else if(i==40) {
                        MySoundPlayer.play(MySoundPlayer.BROKEN);
                        MySoundPlayer.play(MySoundPlayer.CHICKEN);
                        drawable = getResources().getDrawable(R.drawable.chicken);
                        imageView.setImageDrawable(drawable); // 이미지를 적용합니다
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                MySoundPlayer.stop(MySoundPlayer.CHICKEN);
                                MySoundPlayer.play2(MySoundPlayer.CORRECT);
                                correct.setVisibility(View.VISIBLE); //딜레이 후 시작할 코드 작성
                            }
                        }, 2000);// 2초 정도 딜레이를 준 후 시작


                    }


                }
            });
        }

    public void makeDialog(String text){
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        Dialog dlg = new Dialog(this);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        TextView hintText = (TextView)dlg.findViewById(R.id.hintText);
        TextView hintOk = (TextView)dlg.findViewById(R.id.hintOk);

        hintText.setText(text);
        hintOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }

}