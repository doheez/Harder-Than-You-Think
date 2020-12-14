package com.example.nol;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class A0_Enter extends AppCompatActivity {
    private Button start, explain, info;
    MediaPlayer mediaPlayer;
    int flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0_enter);
        MySoundPlayer.initSounds(getApplicationContext());

        Intent intent = getIntent();
        flag = intent.getExtras().getInt("flag");

        // 최초 실행 시에만 배경음악 생성함
        // 게임화면 -> 이전단계 -> Enter화면으로 되돌아왔을 때는 음악 생성 X
        if (flag == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.backgroundmusic);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        start = (Button)findViewById(R.id.enterStart);
        explain = (Button)findViewById(R.id.enterExplain);
        info = (Button)findViewById(R.id.enterInfo);

       // MySoundPlayer.play(MySoundPlayer.BACKGROUNDMUSIC);

        // 게임 시작 클릭
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), A1_Rabbit.class);
                if(flag == 0) ++flag;
                intent.putExtra("flag", flag);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 게임 설명 클릭
        explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                makeDialog("게임 설명",
                        "정말이에요. 생각보다 어려울 걸요?\n" +
                                "각 단계마다 제시된 게임을 해결해 보세요!\n\n" +
                                "블라블라... 말 생각해보기");
            }
        });

        // 개발자 소개 클릭
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                makeDialog("개발자 소개",
                        "< 경북대학교 IT대학 컴퓨터학부 >\n\n" +
                                "2018111947 김도희\n" +
                                "2018110931 오주영\n" +
                                "2018110861 조인후");
            }
        });
    }

    // 힌트 다이얼로그
    public void makeDialog (String title, String text) {
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        Dialog dialog = new Dialog(this);

        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.custom_dialog_hint);

        // 커스텀 다이얼로그를 노출한다.
        dialog.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        TextView hintTitle = (TextView) dialog.findViewById(R.id.hintTitle);
        TextView hintText = (TextView) dialog.findViewById(R.id.hintText);
        TextView hintOk = (TextView) dialog.findViewById(R.id.hintOk);

        hintTitle.setText(title);
        hintText.setText(text);
        hintOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
