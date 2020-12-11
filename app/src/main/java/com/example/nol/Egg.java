package com.example.nol;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class Egg extends AppCompatActivity {
    private ImageView imageView, correct; // 이미지 버퍼 메모리를 설정합니다, 4개
    int i=0;
    Drawable drawable; // 대리자를 선언합니다
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg);
        correct = (ImageView) findViewById(R.id.correct1);

        imageView = (ImageView) findViewById(R.id.egg); // ID 설정을 합니다

            // 그림 터치시에, 이벤트를 발생하게 해주는 함수입니다
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    i++;

                    if (i==10) {
                        drawable = getResources().getDrawable(R.drawable.egg_cracked_1);
                        imageView.setImageDrawable(drawable); // 이미지를 적용합니다
                    }

                    else if(i==20) {
                        drawable = getResources().getDrawable(R.drawable.egg_cracked_2);
                        imageView.setImageDrawable(drawable); // 이미지를 적용합니다
                    }

                    else if(i==30) {
                        drawable = getResources().getDrawable(R.drawable.egg_cracked_3);
                        imageView.setImageDrawable(drawable); // 이미지를 적용합니다

                    }
                    else if(i==40) {
                        drawable = getResources().getDrawable(R.drawable.chicken);
                        imageView.setImageDrawable(drawable); // 이미지를 적용합니다
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                correct.setVisibility(View.VISIBLE); //딜레이 후 시작할 코드 작성
                            }
                        }, 600);// 0.6초 정도 딜레이를 준 후 시작


                    }


                }
            });
        }

}