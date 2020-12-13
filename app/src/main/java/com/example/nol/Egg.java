package com.example.nol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.List;

public class Egg extends AppCompatActivity {
    private ImageView imageView, correct;
    Button prevBtn, listBtn, hintBtn;
    int i=0;
    int flag;
    Drawable drawable; // 대리자를 선언합니다
    List<String> gameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_egg);
        correct = (ImageView) findViewById(R.id.eggCorrect);
        MySoundPlayer.initSounds(getApplicationContext());

        Intent intent = getIntent();
        flag = intent.getExtras().getInt("flag");

        // 타이머 시작
        timer();

        // 힌트 보기
        hintBtn = (Button) findViewById(R.id.eggHint);
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                hintDialog("계란을 자극시켜 보세요.");
            }
        });

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.eggPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), Rabbit.class);
                intent.putExtra("flag", flag); // 이전 단계로 가도 현재 단계까지 깼음을 알 수 있음
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 게임 목록에 아이템 추가
        for(int i = 1; i <= 5; i++)
            gameList.add(i + "단계");

        // 게임 목록
        listBtn = (Button) findViewById(R.id.eggList);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                gameListDialog();
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
                                MySoundPlayer.stop(MySoundPlayer.BROKEN);
                                MySoundPlayer.play(MySoundPlayer.CORRECT);
                                correct.setVisibility(View.VISIBLE); //딜레이 후 시작할 코드 작성
                            }
                        }, 2000);// 2초 정도 딜레이를 준 후 시작

                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Intent intent = new Intent(getApplicationContext(), Birthday.class);
                                if(flag == 2) ++flag;
                                intent.putExtra("flag", flag);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            }
                        }, 4000);
                    }
                }
            });
        }

    // 힌트 다이얼로그
    public void hintDialog(String text) {
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        Dialog dialog = new Dialog(this);

        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.custom_dialog_hint);

        // 커스텀 다이얼로그를 노출한다.
        dialog.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        TextView hintText = (TextView) dialog.findViewById(R.id.hintText);
        TextView hintOk = (TextView) dialog.findViewById(R.id.hintOk);

        hintText.setText(text);
        hintOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    // 게임 목록 다이얼로그
    public void gameListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_list, null);
        builder.setView(view);

        ListView listview = (ListView)view.findViewById(R.id.gameList);
        AlertDialog dialog = builder.create();

        GameListAdapter adapter = new GameListAdapter(this, R.layout.listitem_game, flag);

        // 게임 목록에 아이템 추가
        for(int i = 1; i <= 5; i++)
            adapter.gameList.add(i + "단계");

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 갈색이면 클릭 가능
                if (position < adapter.flag){
                    MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                    if(position == 0) { // 1단계
                        Intent intent = new Intent(getApplicationContext(), Rabbit.class);
                        intent.putExtra("flag", flag);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                    else if(position == 1) { // 2단계
                        dialog.dismiss(); // 지금 화면이니까 그냥 다이얼로그 닫음
                    }
                    else if(position == 2) {// 3단계
                        Intent intent = new Intent(getApplicationContext(), Birthday.class);
                        intent.putExtra("flag", flag);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                }
                // 회색이면 클릭 불가능
            }
        });

        // 닫기 버튼
        TextView listClose = (TextView)view.findViewById(R.id.gameListClose);
        listClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    // 타이머
    public void timer(){
        final int MILLISINFUTURE = 60 * 1000; // 총 시간 (60초)
        final int COUNT_DOWN_INTERVAL = 1000; // onTick 메소드를 호출할 간격 (1초)
        TextView time = (TextView)findViewById(R.id.eggTimer);

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