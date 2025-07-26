package com.example.myapplication;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper DB;
    DatabaseHelper databaseHelper;
    public static MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DatabaseHelper(this);

        hideSystemUI();

        SessionManager sessionManager = new SessionManager(this);

        View mainLayout = findViewById(R.id.main_layout);
        TextView clickToBegin = findViewById(R.id.click_to_begin);
        Animation fadeEffect = AnimationUtils.loadAnimation(this, R.anim.breathing_effect);
        clickToBegin.startAnimation(fadeEffect);

        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    if (sessionManager.isLoggedIn()) {
                        String tupId = sessionManager.getTupId();

                        Intent intent = new Intent(MainActivity.this, Mainmenu.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                        mediaPlayer.release();
                    } else {
                        Intent intent = new Intent(MainActivity.this, Welcome.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                    }
                }
                return true;
            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.welcome);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }
}