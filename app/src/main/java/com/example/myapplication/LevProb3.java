package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LevProb3 extends AppCompatActivity {


    TextView welcomeText, welcomeLongText;
    LinearLayout skipNextLayout;
    ImageButton skip, next, ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lev_prob3);
        hideSystemUI();

        welcomeText = findViewById(R.id.welcomeText);
        welcomeLongText = findViewById(R.id.welcomeLongText);
        skipNextLayout = findViewById(R.id.skipNextButton);
        skip = findViewById(R.id.skipButton);
        next = findViewById(R.id.nextButton);
        ok = findViewById(R.id.okButton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomeText.setVisibility(View.GONE);
                skipNextLayout.setVisibility(View.GONE);
                welcomeLongText.setVisibility(View.VISIBLE);
                ok.setVisibility(View.VISIBLE);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevProb3.this, Maps.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevProb3.this, Level3.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);

            }
        });

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