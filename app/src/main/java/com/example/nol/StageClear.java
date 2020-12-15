package com.example.nol;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.nol.A0_Enter.mediaPlayer;

public class StageClear extends AppCompatActivity {
    Button next, donate;
    TextView box;
    String text, key;
    Intent getintent, outintent;
    int flag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stageclear);
        getintent = getIntent();

        donate = (Button) findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                makeDialog("개발자에게 후원하기", "마음만 받을게요❤");}
        });

        box = (TextView) findViewById(R.id.box);
        text = getintent.getStringExtra("text");
        box.setText(text);

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                key = getintent.getStringExtra("key");
                flag = getintent.getIntExtra("flag", 0);

                if(key.equals("egg"))
                    outintent = new Intent(getApplicationContext(), A2_Egg.class);
                else if(key.equals("birthday"))
                    outintent = new Intent(getApplicationContext(), A3_Birthday.class);
                else if(key.equals("pizza"))
                    outintent = new Intent(getApplicationContext(), A4_Pizza.class);
                else if(key.equals("owl"))
                    outintent = new Intent(getApplicationContext(), A5_Owl.class);
                else if(key.equals("river"))
                    outintent = new Intent(getApplicationContext(), A6_River.class);
                else if(key.equals("drink"))
                    outintent = new Intent(getApplicationContext(), A7_Drink.class);

                outintent.putExtra("flag", flag);
                startActivity(outintent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}
