package com.example.myapplication;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.content.Intent;
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
import java.util.List;

public class Level6 extends AppCompatActivity implements View.OnClickListener{
    DatabaseHelper DB;
    ImageButton barStatusLevel6;

    TextView codeLevel6;
    TextView choice1Level6, choice2Level6, choice3Level6, choice4Level6, choice5Level6, pointsValue;
    private List<String> clickOrder = new ArrayList<>();
    private int currentReplacementIndex = 0;
    private final String[] replacements = {"_________", "_________", "_________", "_________", "_________"};
    private PopupWindowHelper popupWindowHelper;
    ImageButton runLevel6;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level6);
        hideSystemUI();
        DB = new DatabaseHelper(this);

        SessionManager sessionManager = new SessionManager(this);
        String tupId = sessionManager.getTupId();
        popupWindowHelper = new PopupWindowHelper(this, "Level6", tupId, DB);

        choice1Level6 = findViewById(R.id.choice1Level6);
        choice2Level6 = findViewById(R.id.choice2Level6);
        choice3Level6 = findViewById(R.id.choice3Level6);
        choice4Level6 = findViewById(R.id.choice4Level6);
        choice5Level6 = findViewById(R.id.choice5Level6);

        choice1Level6.setOnClickListener(this);
        choice2Level6.setOnClickListener(this);
        choice3Level6.setOnClickListener(this);
        choice4Level6.setOnClickListener(this);
        choice5Level6.setOnClickListener(this);

        codeLevel6 = findViewById(R.id.codeLevel6);

        barStatusLevel6 = findViewById(R.id.barStatusLevel6);
        pointsValue = findViewById(R.id.pointsValueLevel6);

        currentBar();

        ImageButton backLevel6 = findViewById(R.id.backLevel6);
        runLevel6 = findViewById(R.id.runLevel6);

        runLevel6.setEnabled(false);
        runLevel6.setAlpha(0.3f);

        backLevel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level6.this, Maps.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);

            }
        });
        runLevel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] LevelAnswer = {"public void DisplayDetails()", "Console.WriteLine", "public void ShowSalary()", "fullTimeEmployee", "partTimeEmployee.DisplayDetails();"};
                String clickOrderString = "Click Order: " + clickOrder.toString();

                List<String> levelAnswerList = Arrays.asList(LevelAnswer);

                if (levelAnswerList.equals(clickOrder)) {
                    popupWindowHelper.showPopupWindow(v, R.layout.popup_congrats);
                    updateLevelData();
                    mediaPlayer = MediaPlayer.create(Level6.this, R.raw.congrats);
                    mediaPlayer.start();
                } else {
                    runLevel6.setEnabled(false);
                    runLevel6.setAlpha(0.3f);
                    popupWindowHelper.showPopupWindow(v, R.layout.popup_error);
                    updateLevelBar();
                    choice1Level6.setEnabled(true);
                    choice1Level6.setAlpha(1.0f);
                    choice2Level6.setEnabled(true);
                    choice2Level6.setAlpha(1.0f);
                    choice3Level6.setEnabled(true);
                    choice3Level6.setAlpha(1.0f);
                    choice4Level6.setEnabled(true);
                    choice4Level6.setAlpha(1.0f);
                    choice5Level6.setEnabled(true);
                    choice5Level6.setAlpha(1.0f);
                    clickOrder.clear();
                    currentBar();
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        String clickedLabel = "";

        if (view.getId() == R.id.choice1Level6) {
            clickedLabel = choice1Level6.getText().toString();
            choice1Level6.setEnabled(false);
            choice1Level6.setAlpha(0.3f);
        } else if (view.getId() == R.id.choice2Level6) {
            clickedLabel = choice2Level6.getText().toString();
            choice2Level6.setEnabled(false);
            choice2Level6.setAlpha(0.3f);
        } else if (view.getId() == R.id.choice3Level6) {
            clickedLabel = choice3Level6.getText().toString();
            choice3Level6.setEnabled(false);
            choice3Level6.setAlpha(0.3f);
        } else if (view.getId() == R.id.choice4Level6) {
            clickedLabel = choice4Level6.getText().toString();
            choice4Level6.setEnabled(false);
            choice4Level6.setAlpha(0.3f);
        } else if (view.getId() == R.id.choice5Level6) {
            clickedLabel = choice5Level6.getText().toString();
            choice5Level6.setEnabled(false);
            choice5Level6.setAlpha(0.3f);
        }

        addToClickOrder(clickedLabel);
        replaceNextBlank(clickedLabel);
        if (clickOrder.size() == 5) {
            runLevel6.setEnabled(true);
            runLevel6.setAlpha(1.0f);
        }
    }

    private void replaceNextBlank(String label) {
        if (currentReplacementIndex >= replacements.length) {
            return;
        }

        String currentText = codeLevel6.getText().toString();
        String newAnswer = label;

        if (newAnswer != null) {
            String modifiedText = currentText.replaceFirst(replacements[currentReplacementIndex], newAnswer);
            codeLevel6.setText(modifiedText);
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
            switch (barArray[5]){
                case "5":
                    barStatusLevel6.setImageResource(R.drawable.bar5);
                    pointsValue.setText("5000");
                    break;
                case "4":
                    barStatusLevel6.setImageResource(R.drawable.bar4);
                    pointsValue.setText("4000");
                    break;
                case "3":
                    barStatusLevel6.setImageResource(R.drawable.bar3);
                    pointsValue.setText("3000");
                    break;
                case "2":
                    barStatusLevel6.setImageResource(R.drawable.bar2);
                    pointsValue.setText("2000");
                    break;
                case "1":
                    barStatusLevel6.setImageResource(R.drawable.bar1);
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
                    barStatusLevel6.setImageResource(R.drawable.bar5);
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
                    starArray[5] = "3";
                    newStar = String.join(",", starArray);
                    break;
                case "3000":
                    starArray[5] = "2";
                    newStar = String.join(",", starArray);
                    break;
                case "2000":
                case "1000":
                    starArray[5] = "1";
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
            int resultBar = Integer.parseInt(starArray[5]) - Integer.parseInt("1");
            starArray[5] = Integer.toString(resultBar);
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
                Intent intent = new Intent(Level6.this, Maps.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
            }
        }
        return super.dispatchKeyEvent(event);
    }
}