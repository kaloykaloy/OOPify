package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;import android.os.Handler;

public class Level7 extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper DB;
    ImageButton barStatusLevel7;
    TextView codeLevel7;
    TextView choice1Level7, choice2Level7, choice3Level7, choice4Level7, choice5Level7, pointsValue;
    private List<String> clickOrder = new ArrayList<>();
    private int currentReplacementIndex = 0;
    private final String[] replacements = {"_________", "_________", "_________", "_________", "_________"};
    private PopupWindowHelper popupWindowHelper;
    ImageButton runLevel7;

    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level7);
        hideSystemUI();
        DB = new DatabaseHelper(this);
        SessionManager sessionManager = new SessionManager(this);
        String tupId = sessionManager.getTupId();
        popupWindowHelper = new PopupWindowHelper(this, "Level7", tupId, DB);

        choice1Level7 = findViewById(R.id.choice1Level7);
        choice2Level7 = findViewById(R.id.choice2Level7);
        choice3Level7 = findViewById(R.id.choice3Level7);
        choice4Level7 = findViewById(R.id.choice4Level7);
        choice5Level7 = findViewById(R.id.choice5Level7);

        choice1Level7.setOnClickListener(this);
        choice2Level7.setOnClickListener(this);
        choice3Level7.setOnClickListener(this);
        choice4Level7.setOnClickListener(this);
        choice5Level7.setOnClickListener(this);

        codeLevel7 = findViewById(R.id.codeLevel7);

        barStatusLevel7 = findViewById(R.id.barStatusLevel7);
        pointsValue = findViewById(R.id.pointsValueLevel7);

        currentBar();

        ImageButton backLevel7 = findViewById(R.id.backLevel7);
        runLevel7 = findViewById(R.id.runLevel7);

        runLevel7.setEnabled(false);
        runLevel7.setAlpha(0.3f);

        backLevel7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level7.this, Maps.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);

            }
        });
        runLevel7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] LevelAnswer = {"using System;", "public void Deposit(decimal amount)", "else", "if (amount > 0 && amount <= _balance)", "public static void Main()"};
                String clickOrderString = "Click Order: " + clickOrder.toString();

                List<String> levelAnswerList = Arrays.asList(LevelAnswer);

                if (levelAnswerList.equals(clickOrder)) {
                    popupWindowHelper.showPopupWindow(v, R.layout.popup_congrats);
                    updateLevelData();
                    mediaPlayer = MediaPlayer.create(Level7.this, R.raw.congrats);
                    mediaPlayer.start();
                } else {
                    runLevel7.setEnabled(false);
                    runLevel7.setAlpha(0.3f);
                    popupWindowHelper.showPopupWindow(v, R.layout.popup_error);
                    updateLevelBar();
                    choice1Level7.setEnabled(true);
                    choice1Level7.setAlpha(1.0f);
                    choice2Level7.setEnabled(true);
                    choice2Level7.setAlpha(1.0f);
                    choice3Level7.setEnabled(true);
                    choice3Level7.setAlpha(1.0f);
                    choice4Level7.setEnabled(true);
                    choice4Level7.setAlpha(1.0f);
                    choice5Level7.setEnabled(true);
                    choice5Level7.setAlpha(1.0f);
                    clickOrder.clear();
                    currentBar();
                }
            }
        });

    }
    @Override
    public void onClick(View view) {
        String clickedLabel = "";

        if (view.getId() == R.id.choice1Level7) {
            clickedLabel = choice1Level7.getText().toString();
            choice1Level7.setEnabled(false);
            choice1Level7.setAlpha(0.3f);
        } else if (view.getId() == R.id.choice2Level7) {
            clickedLabel = choice2Level7.getText().toString();
            choice2Level7.setEnabled(false);
            choice2Level7.setAlpha(0.3f);
        } else if (view.getId() == R.id.choice3Level7) {
            clickedLabel = choice3Level7.getText().toString();
            choice3Level7.setEnabled(false);
            choice3Level7.setAlpha(0.3f);
        } else if (view.getId() == R.id.choice4Level7) {
            clickedLabel = choice4Level7.getText().toString();
            choice4Level7.setEnabled(false);
            choice4Level7.setAlpha(0.3f);
        } else if (view.getId() == R.id.choice5Level7) {
            clickedLabel = choice5Level7.getText().toString();
            choice5Level7.setEnabled(false);
            choice5Level7.setAlpha(0.3f);
        }

        addToClickOrder(clickedLabel);
        replaceNextBlank(clickedLabel);
        if (clickOrder.size() == 5) {
            runLevel7.setEnabled(true);
            runLevel7.setAlpha(1.0f);
        }
    }

    private void replaceNextBlank(String label) {
        if (currentReplacementIndex >= replacements.length) {
            return;
        }

        String currentText = codeLevel7.getText().toString();
        String newAnswer = label;

        if (newAnswer != null) {
            String modifiedText = currentText.replaceFirst(replacements[currentReplacementIndex], newAnswer);
            codeLevel7.setText(modifiedText);
            currentReplacementIndex++;
        }
    }


    private void addToClickOrder(String label) {
        if (!clickOrder.contains(label)) {
            clickOrder.add(label);
        }
    }
    private void currentBar(){
        SessionManager sessionManager = new SessionManager(this);
        String tupId = sessionManager.getTupId();

        Cursor cursor = DB.getLevelSystemData(tupId);

        if (cursor != null && cursor.moveToFirst()) {
            String bar = cursor.getString(cursor.getColumnIndexOrThrow("bar"));

            String getBar = bar;
            String[] barArray = getBar.split(",");
            switch (barArray[6]){
                case "5":
                    barStatusLevel7.setImageResource(R.drawable.bar5);
                    pointsValue.setText("5000");
                    break;
                case "4":
                    barStatusLevel7.setImageResource(R.drawable.bar4);
                    pointsValue.setText("4000");
                    break;
                case "3":
                    barStatusLevel7.setImageResource(R.drawable.bar3);
                    pointsValue.setText("3000");
                    break;
                case "2":
                    barStatusLevel7.setImageResource(R.drawable.bar2);
                    pointsValue.setText("2000");
                    break;
                case "1":
                    barStatusLevel7.setImageResource(R.drawable.bar1);
                    pointsValue.setText("1000");
                    break;
                default:
                    View rootView = findViewById(android.R.id.content);
                    popupWindowHelper.dismissPopupWindow();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            popupWindowHelper.showPopupWindow(rootView, R.layout.popup_nobar);
                        }
                    }, 100);
                    barStatusLevel7.setImageResource(R.drawable.bar5);
                    pointsValue.setText("5000");
                    break;
            }

            cursor.close();
        } else {
            Toast.makeText(this, "Level system data not found for this user!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLevelData() {
        SessionManager sessionManager = new SessionManager(this);
        String tupId = sessionManager.getTupId();

        Cursor cursor = DB.getLevelSystemData(tupId);

        if (cursor != null && cursor.moveToFirst()) {
            String level = cursor.getString(cursor.getColumnIndexOrThrow("lvl"));
            String star = cursor.getString(cursor.getColumnIndexOrThrow("star"));
            String points = cursor.getString(cursor.getColumnIndexOrThrow("points"));
            String bar = cursor.getString(cursor.getColumnIndexOrThrow("bar"));

            int resultLevel = Integer.parseInt(level) + Integer.parseInt("1");
            String resultStringLevel = Integer.toString(resultLevel);

            String newStar = star;
            String[] starArray = newStar.split(",");

            switch (pointsValue.getText().toString()){
                case "5000":
                case "4000":
                    starArray[6] = "3";
                    newStar = String.join(",", starArray);
                    break;
                case "3000":
                    starArray[6] = "2";
                    newStar = String.join(",", starArray);
                    break;
                case "2000":
                case "1000":
                    starArray[6] = "1";
                    newStar = String.join(",", starArray);
                    break;
                default:
                    break;
            }

            int resultPoints = Integer.parseInt(points) + Integer.parseInt(pointsValue.getText().toString());
            String resultStringPoints = Integer.toString(resultPoints);

            DB.updateLevelSystemData(tupId, resultStringLevel, newStar, resultStringPoints, bar);

            cursor.close();
        } else {
            Toast.makeText(this, "Level system data not found for this user!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLevelBar() {
        SessionManager sessionManager = new SessionManager(this);
        String tupId = sessionManager.getTupId();

        Cursor cursor = DB.getLevelSystemData(tupId);

        if (cursor != null && cursor.moveToFirst()) {
            String level = cursor.getString(cursor.getColumnIndexOrThrow("lvl"));
            String star = cursor.getString(cursor.getColumnIndexOrThrow("star"));
            String points = cursor.getString(cursor.getColumnIndexOrThrow("points"));
            String bar = cursor.getString(cursor.getColumnIndexOrThrow("bar"));

            String newBar = bar;
            String[] starArray = newBar.split(",");
            int resultBar = Integer.parseInt(starArray[6]) - Integer.parseInt("1");
            starArray[6] = Integer.toString(resultBar);
            newBar = String.join(",", starArray);

            DB.updateLevelSystemData(tupId, level, star, points, newBar);

            cursor.close();
        } else {
            Toast.makeText(this, "Level system data not found for this user!", Toast.LENGTH_SHORT).show();
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
            } else {
                Intent intent = new Intent(Level7.this, Maps.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
            }
        }
        return super.dispatchKeyEvent(event);
    }
}