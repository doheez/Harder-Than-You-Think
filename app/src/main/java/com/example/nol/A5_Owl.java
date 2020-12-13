package com.example.nol;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class A5_Owl extends AppCompatActivity {
    int brightness;
    ImageView owlSleep, owlAwake, correct;
    Button prevBtn, listBtn, hintBtn;
    int flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_owl);
        MySoundPlayer.initSounds(getApplicationContext());

        Intent intent = getIntent();
        flag = intent.getExtras().getInt("flag");

        // 타이머 시작
        timer();

        owlSleep = (ImageView) findViewById(R.id.owlSleep);
        owlAwake = (ImageView) findViewById(R.id.owlAwake);
        correct = (ImageView) findViewById(R.id.owlCorrect);

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.owlPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), A4_Pizza.class);
                intent.putExtra("flag", flag); // 이전 단계로 가도 현재 단계까지 깼음을 알 수 있음
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 게임 목록
        listBtn = (Button) findViewById(R.id.owlList);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                gameListDialog();
            }
        });

        // 힌트 보기
        hintBtn = (Button) findViewById(R.id.owlHint);
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                hintDialog("부엉이는 야행성입니다.");
            }
        });

        Thread thread = new Thread(new detectThread());
        thread.start();
    }

    // 화면 밝기 조절 감지
    class detectThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(3000);
                    brightness = Settings.System.getInt(getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS);
                    System.out.println(brightness);

                    if (brightness <= 30) {
                        owlSleep.setVisibility(View.INVISIBLE);
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                        break;
                    }
                }
            } catch (Settings.SettingNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 정답 맞춤
    final Handler handler = new Handler() {
        public void handleMessage(Message msg){
            correct.setVisibility(View.VISIBLE);
            MySoundPlayer.play(MySoundPlayer.CORRECT);
        }
    };

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
                if (position < adapter.flag) {
                    MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                    if (position == 0) { // 1단계
                        Intent intent = new Intent(getApplicationContext(), A1_Rabbit.class);
                        intent.putExtra("flag", flag);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    } else if (position == 1) { // 2단계
                        Intent intent = new Intent(getApplicationContext(), A2_Egg.class);
                        intent.putExtra("flag", flag);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    } else if (position == 2) {// 3단계
                        Intent intent = new Intent(getApplicationContext(), A3_Birthday.class);
                        intent.putExtra("flag", flag);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    } else if (position == 3) { // 4단계
                        Intent intent = new Intent(getApplicationContext(), A4_Pizza.class);
                        intent.putExtra("flag", flag);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                    else if(position == 4) { // 5단계
                        dialog.dismiss(); // 지금 화면이니까 그냥 다이얼로그 닫음
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
        TextView time = (TextView)findViewById(R.id.owlTimer);

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