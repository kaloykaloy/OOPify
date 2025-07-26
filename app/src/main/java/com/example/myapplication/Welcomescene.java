package com.example.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
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

public class Welcomescene extends AppCompatActivity {

    TextView sceneText;
    LinearLayout sceneImage;
    ImageButton button;

    Boolean Scene2 = false;
    Boolean Scene3 = false;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescene);
        hideSystemUI();

        sceneText = findViewById(R.id.sceneText);
        sceneImage = findViewById(R.id.sceneImage);
        button = findViewById(R.id.nextButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Scene2) {
                    sceneText.setText("But when a malicious virus called The Void struck, the city fell into chaos. Systems crashed, buildings crumbled, and the once-thriving metropolis became a shadow of its former self.");
                    sceneImage.setBackgroundResource(R.drawable.scenetwoimage);
                    Scene2 = true;
                } else if (Scene2 && !Scene3) {
                    sceneText.setText("As you coded, the city started to heal. Each successful function restored a piece of what had been lost. When you finally complete levels and collect those points you need, I knew this was the turning point. With one final line of code, you can save the city and come back to its life, stronger than before. \n" +
                            "\n" +
                            "So good luck on your journey, hero.");
                    sceneImage.setBackgroundResource(R.drawable.scenethreeimage);
                    button.setImageResource(R.drawable.okwelcome);
                    Scene3 = true;
                } else {
                    Intent intent = new Intent(Welcomescene.this, Mainmenu.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                    stopBackgroundMusic();

                }

            }
        });




    }


    private void stopBackgroundMusic() {
        if (MainActivity.mediaPlayer != null && MainActivity.mediaPlayer.isPlaying()) {
            MainActivity.mediaPlayer.stop();
            MainActivity.mediaPlayer.release();
            MainActivity.mediaPlayer = null;
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
}