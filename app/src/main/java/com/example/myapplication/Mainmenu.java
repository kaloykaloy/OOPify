package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Mainmenu extends AppCompatActivity implements View.OnClickListener{

    TextView logoutButton;
    LinearLayout introButton, profileButton, mapButton, statistics, logoutContainer, lesson;
    SessionManager sessionManager;
    DatabaseHelper DB;
    private PopupWindowHelper popupHelper;
    String userId;
    private PopupWindowHelper popupWindowHelper;
    int backPress = 0;
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        hideSystemUI();

        sessionManager = new SessionManager(this);
        userId = sessionManager.getTupId();
        DB = new DatabaseHelper(this);
        popupWindowHelper = new PopupWindowHelper(this, "wheelTime", userId, DB);


        findViewById(R.id.introductionButton).setOnClickListener(this);
        findViewById(R.id.profileButton).setOnClickListener(this);
        findViewById(R.id.mapButton).setOnClickListener(this);
        findViewById(R.id.statisticsButton).setOnClickListener(this);
        findViewById(R.id.lessonButton).setOnClickListener(this);

        introButton = findViewById(R.id.introductionBg);
        profileButton = findViewById(R.id.profileBg);
        mapButton = findViewById(R.id.mapBg);
        statistics = findViewById(R.id.statisticsBg);
        lesson = findViewById(R.id.lessonBg);
        logoutButton = findViewById(R.id.logoutButton);
        logoutContainer = findViewById(R.id.logoutContainer);
        if (sessionManager.isLoggedIn()) {
            logoutContainer.setVisibility(View.VISIBLE);
        } else {
            logoutContainer.setVisibility(View.GONE);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(Mainmenu.this);
                sessionManager.logoutUser();

                Intent intent = new Intent(Mainmenu.this, Login.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                mediaPlayer.release();
            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.main);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        loadIntroductionFragment();

    }

    private void loadIntroductionFragment() {
        Fragment fragment = new Introduction();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

        introButton.setBackgroundResource(R.drawable.rounded_buttonclicked);
        profileButton.setBackgroundResource(R.drawable.rounded_button);
        mapButton.setBackgroundResource(R.drawable.rounded_button);
        statistics.setBackgroundResource(R.drawable.rounded_button);
        lesson.setBackgroundResource(R.drawable.rounded_button);


    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        if (v.getId() == R.id.introductionButton) {
            if (sessionManager.isLoggedIn()) {
                fragment = new Introduction();
            } else {
                LinearLayout attentionError = findViewById(R.id.attentionError);
                attentionError.setVisibility(View.VISIBLE);
            }
            introButton.setBackgroundResource(R.drawable.rounded_buttonclicked);
            profileButton.setBackgroundResource(R.drawable.rounded_button);
            mapButton.setBackgroundResource(R.drawable.rounded_button);
            statistics.setBackgroundResource(R.drawable.rounded_button);
            lesson.setBackgroundResource(R.drawable.rounded_button);

        } else if (v.getId() == R.id.profileButton) {

            if (sessionManager.isLoggedIn()) {
                fragment = new Profile();
            } else {
                LinearLayout attentionError = findViewById(R.id.attentionError);
                attentionError.setVisibility(View.VISIBLE);
            }
            introButton.setBackgroundResource(R.drawable.rounded_button);
            profileButton.setBackgroundResource(R.drawable.rounded_buttonclicked);
            mapButton.setBackgroundResource(R.drawable.rounded_button);
            statistics.setBackgroundResource(R.drawable.rounded_button);
            lesson.setBackgroundResource(R.drawable.rounded_button);

        } else if (v.getId() == R.id.mapButton) {

            if (sessionManager.isLoggedIn()) {
                Intent intent = new Intent(Mainmenu.this, Maps.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                mediaPlayer.release();
                return;
            } else {
                LinearLayout attentionError = findViewById(R.id.attentionError);
                attentionError.setVisibility(View.VISIBLE);
            }
            introButton.setBackgroundResource(R.drawable.rounded_button);
            profileButton.setBackgroundResource(R.drawable.rounded_button);
            mapButton.setBackgroundResource(R.drawable.rounded_buttonclicked);
            statistics.setBackgroundResource(R.drawable.rounded_button);
            lesson.setBackgroundResource(R.drawable.rounded_button);

        } else if (v.getId() == R.id.statisticsButton){

            if (sessionManager.isLoggedIn()) {
                fragment = new Statistics();
            } else {
                LinearLayout attentionError = findViewById(R.id.attentionError);
                attentionError.setVisibility(View.VISIBLE);
            }
            introButton.setBackgroundResource(R.drawable.rounded_button);
            profileButton.setBackgroundResource(R.drawable.rounded_button);
            mapButton.setBackgroundResource(R.drawable.rounded_button);
            statistics.setBackgroundResource(R.drawable.rounded_buttonclicked);
            lesson.setBackgroundResource(R.drawable.rounded_button);
        } else if (v.getId() == R.id.lessonButton){

            if (sessionManager.isLoggedIn()) {
                fragment = new Lesson();
            } else {
                LinearLayout attentionError = findViewById(R.id.attentionError);
                attentionError.setVisibility(View.VISIBLE);
            }
            introButton.setBackgroundResource(R.drawable.rounded_button);
            profileButton.setBackgroundResource(R.drawable.rounded_button);
            mapButton.setBackgroundResource(R.drawable.rounded_button);
            statistics.setBackgroundResource(R.drawable.rounded_button);
            lesson.setBackgroundResource(R.drawable.rounded_buttonclicked);
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
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
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (popupWindowHelper.isPopupVisible()) {

                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof SpinWheel) {
            SpinWheel spinWheelFragment = (SpinWheel) currentFragment;
            if (spinWheelFragment.isPopupVisible()) {
                return;
            } else {
                backPress = backPress + 1;
                if(backPress>1){
                    finishAffinity();
                    System.exit(0);
                    mediaPlayer.release();
                    return;
                }
                Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();
            }
        } else {
            backPress = backPress + 1;
            if(backPress>1){
                finishAffinity();
                System.exit(0);
                mediaPlayer.release();
                return;

            }
            Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();
        }
    }
}