package com.example.nol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

public class AX_Splash extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_x_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), A0_Enter.class);
                intent.putExtra("flag", 0);
                startActivity(intent);
                finish();
            }
        },1300);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}