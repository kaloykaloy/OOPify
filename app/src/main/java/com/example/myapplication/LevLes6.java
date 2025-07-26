package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LevLes6 extends AppCompatActivity {


    String receivedLevel = "";
    TextView pointsValue;
    ImageButton barStatusInstruction;
    TextView instructionValue;
    ImageButton proceedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lev_les6);
        hideSystemUI();

        Intent intent = getIntent();
        instructionValue = findViewById(R.id.instructionValue);
        ImageButton proceedBack = findViewById(R.id.proceedBack);
        proceedButton = findViewById(R.id.proceedButton);
        pointsValue = findViewById(R.id.pointsValue);
        barStatusInstruction = findViewById(R.id.barStatusInstruction);
        receivedLevel = intent.getStringExtra("Level");
        String receivedInstruction = intent.getStringExtra("tip 1");

        instructionValue.setText(receivedInstruction);

        SessionManager sessionManager = new SessionManager(LevLes6.this);
        String tupId = sessionManager.getTupId();
        DatabaseHelper DB = new DatabaseHelper(LevLes6.this);
        Cursor cursors = DB.getLevelSystemData(tupId);

        if (cursors != null && cursors.moveToFirst()) {
            String bar = cursors.getString(cursors.getColumnIndexOrThrow("bar"));

            String[] barLevels = bar.split(",");

            switch (barLevels[0]){
                case "0":
                    barStatusInstruction.setImageResource(0);
                    pointsValue.setText("5000");
                    break;
                case "1":
                    barStatusInstruction.setImageResource(R.drawable.bar1);
                    pointsValue.setText("1000");
                    break;
                case "2":
                    barStatusInstruction.setImageResource(R.drawable.bar2);
                    pointsValue.setText("2000");
                    break;
                case "3":
                    barStatusInstruction.setImageResource(R.drawable.bar3);
                    pointsValue.setText("3000");
                    break;
                case "4":
                    barStatusInstruction.setImageResource(R.drawable.bar4);
                    pointsValue.setText("4000");
                    break;
                case "5":
                    barStatusInstruction.setImageResource(R.drawable.bar5);
                    pointsValue.setText("5000");
                    break;

            }

            cursors.close();
        }


        proceedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (receivedLevel) {
                    case "1": {
                        Intent intent = new Intent(LevLes6.this, Maps.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                        break;
                    }
                    case "2": {
                        instructionValue.setText("Inheritance in Object-Oriented Programming (OOP) in C# is a mechanism that allows one class to inherit properties and methods from another class. It establishes a parent-child relationship between classes, where the base class (or parent class) provides common functionality, and the derived class (or child class) extends or modifies this functionality.");
                        receivedLevel = "1";
                        break;
                    }
                    case "3": {
                        instructionValue.setText("This is the class whose members (fields, properties, methods) are inherited by another class. It provides common functionality that can be reused by derived classes. In C#, a class can only inherit from one base class (single inheritance).");
                        receivedLevel = "2";
                        proceedButton.setImageResource(R.drawable.nextinstruction);
                        break;
                    }
                }

            }
        });

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (receivedLevel) {
                    case "1": {
                        instructionValue.setText("This is the class whose members (fields, properties, methods) are inherited by another class. It provides common functionality that can be reused by derived classes. In C#, a class can only inherit from one base class (single inheritance).");
                        receivedLevel = "2";
                        break;
                    }
                    case "2": {
                        instructionValue.setText("Members marked as public or protected in the base class can be accessed by the derived class. Members marked as private in the base class are not accessible directly in the derived class. The protected modifier allows the base class to share its members with derived classes while keeping them hidden from other parts of the code.");
                        proceedButton.setImageResource(R.drawable.proceed_button);
                        receivedLevel = "3";
                        break;
                    }
                    case "3": {
                        Intent intent = new Intent(LevLes6.this, LevProb6.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                        break;
                    }
                }

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
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

            Intent intent = new Intent(LevLes6.this, Maps.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.enteranim, R.anim.exitanim);

        }
        return super.dispatchKeyEvent(event);
    }
}