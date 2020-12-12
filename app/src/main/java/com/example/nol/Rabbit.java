package com.example.nol;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class Rabbit extends AppCompatActivity implements View.OnTouchListener {
    ImageView rabbit, cookie, correct;
    Button prevBtn, listBtn, hintBtn;
    List<String> gameList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_rabbit);
        MySoundPlayer.initSounds(getApplicationContext());

        rabbit = (ImageView) findViewById(R.id.rabbit);
        cookie = (ImageView) findViewById(R.id.cookie);
        correct = (ImageView) findViewById(R.id.correct1);

        cookie.setOnTouchListener(this);

        // 이전 단계
        prevBtn = (Button) findViewById(R.id.rabbitPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                Intent intent = new Intent(getApplicationContext(), Enter.class);
                startActivity(intent);
                finish();
            }
        });

        // 게임 목록에 아이템 추가
        for(int i = 1; i <= 5; i++)
            gameList.add(i + "단계");

        // 게임 목록
        listBtn = (Button) findViewById(R.id.rabbitList);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                gameListDialog();
            }
        });

        // 힌트 보기
        hintBtn = (Button) findViewById(R.id.rabbitHint);
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);
                hintDialog("화면에는 토끼가 한 마리만 있는 게 아닙니다.");
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
            MySoundPlayer.play(MySoundPlayer.COOKIE);
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
                MySoundPlayer.stop(MySoundPlayer.COOKIE);
                correct.setVisibility(View.VISIBLE);
                MySoundPlayer.play(MySoundPlayer.CORRECT);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), Egg.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                },2000);
            }
            Log.d("viewTest", "x : " + event.getRawX() + " y : " + event.getRawY());
        }
        return true;
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

        GameListAdapter adapter = new GameListAdapter(this, R.layout.listitem_game);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
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
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public class GameListAdapter extends BaseAdapter {
        Context context;
        int layout;
        LayoutInflater inflater;

        public GameListAdapter(Context context, int layout){
            this.context = context;
            this.layout = layout;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return gameList.size();
        }

        @Override
        public Object getItem(int i) {
            return gameList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null)
                view = inflater.inflate(layout, null);

            TextView listStep = (TextView)view.findViewById(R.id.gameListStep);
            listStep.setText(gameList.get(i));
            return view;
        }
    }
}
