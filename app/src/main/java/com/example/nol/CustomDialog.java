package com.example.nol;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.nol.A5_Owl.snoringSound;

public class CustomDialog {
    Context context;
    Activity activity;
    LayoutInflater inflater;
    int flag; // 게임 목록에서 몇 단계까지 터치할 수 있는가
    int whatAmI; // 현재 액티비티는 몇 단계인가
    Timer timer;

    public CustomDialog(Context context){
        this.context = context;
    }

    public CustomDialog(Context context, Activity activity, LayoutInflater inflater, int flag, int whatAmI, Timer timer){
        this.context = context;
        this.activity = activity;
        this.inflater = inflater;
        this.flag = flag;
        this.whatAmI = whatAmI;
        this.timer = timer;
    }

    // 힌트 다이얼로그
    public void hintDialog(String text) {
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        Dialog dialog = new Dialog(context);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.custom_dialog_list, null);
        builder.setView(view);

        ListView listview = (ListView)view.findViewById(R.id.gameList);
        AlertDialog dialog = builder.create();

        GameListAdapter adapter = new GameListAdapter(context, R.layout.listitem_game, flag);

        // 게임 목록에 아이템 추가
        for(int i = 1; i <= 7; i++)
            adapter.gameList.add(i + "단계");

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 갈색이면 클릭 가능
                if (position < adapter.flag){
                    MySoundPlayer.play(MySoundPlayer.BUTTON_SOUND);

                    // 여기서 초기화 안 해주면 공통 부분 실행 안 됨
                    Intent intent = new Intent(context, A0_Enter.class);

                    if(position == 0){ // 1단계
                        if(whatAmI == 1){
                            dialog.dismiss(); // 지금 화면이니까 그냥 다이얼로그 닫음
                            return;
                        }
                        else intent = new Intent(context, A1_Rabbit.class);
                    }
                    else if(position == 1){ // 2단계
                        if(whatAmI == 2){
                            dialog.dismiss();
                            return;
                        }
                        else intent = new Intent(context, A2_Egg.class);
                    }
                    else if(position == 2){ // 3단계
                        if(whatAmI == 3){
                            dialog.dismiss();
                            return;
                        }
                        else intent = new Intent(context, A3_Birthday.class);
                    }
                    else if(position == 3){ // 4단계
                        if(whatAmI == 4){
                            dialog.dismiss();
                            return;
                        }
                        else intent = new Intent(context, A4_Pizza.class);
                    }
                    else if(position == 4){ // 5단계
                        if(whatAmI == 5){
                            dialog.dismiss();
                            return;
                        }
                        else intent = new Intent(context, A5_Owl.class);
                    }
                    else if(position == 5) { // 6단계
                        if (whatAmI == 6) {
                            dialog.dismiss();
                            return;
                        } else intent = new Intent(context, A6_River.class);
                    }
                    else if(position == 6) { // 7단계
                        if (whatAmI == 7) {
                            dialog.dismiss();
                            return;
                        } else intent = new Intent(context, A7_Drink.class);
                    }

                    // 부엉이면 코 고는 소리 멈추고
                    if(whatAmI == 5) snoringSound.stop();
                    
                    // 인텐트 생성 후 공통으로 실행하는 부분
                    intent.putExtra("flag", flag);
                    context.startActivity(intent);
                    timer.countDownTimer.cancel();
                    activity.finish();
                    activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
}
