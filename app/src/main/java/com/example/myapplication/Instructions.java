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

public class    Instructions extends AppCompatActivity {

    String receivedLevel = "";
    TextView pointsValue;
    ImageButton barStatusInstruction;
    TextView instructionValue;
    ImageButton proceedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        hideSystemUI();

        Intent intent = getIntent();
        instructionValue = findViewById(R.id.instructionValue);
        ImageButton proceedBack = findViewById(R.id.proceedBack);
        proceedButton = findViewById(R.id.proceedButton);
        pointsValue = findViewById(R.id.pointsValue);
        barStatusInstruction = findViewById(R.id.barStatusInstruction);
        receivedLevel = intent.getStringExtra("Level");
        String receivedInstruction = intent.getStringExtra("Instruction");

        instructionValue.setText(receivedInstruction);

        SessionManager sessionManager = new SessionManager(Instructions.this);
        String tupId = sessionManager.getTupId();
        DatabaseHelper DB = new DatabaseHelper(Instructions.this);
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
                        Intent intent = new Intent(Instructions.this, Maps.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                        break;
                    }
                    case "2": {
                        instructionValue.setText("Welcome to the world of OOPFIY ProGamer! Your mission is to save the once-glorious city of OOPify by mastering the art of C# programming. In this game, you will encounter various challenges that will test your understanding of object-oriented programming principles.");
                        receivedLevel = "1";
                        break;
                    }
                    case "3": {
                        instructionValue.setText("Each challenge presents you with incomplete code on the left side of the screen. Your task is to select the correct code snippet from the options on the right side to complete the program. Choose wisely, as each correct choice will help you rebuild and restore different parts of OOPify to its former brilliance.");
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
                        instructionValue.setText("Each challenge presents you with incomplete code on the left side of the screen. Your task is to select the correct code snippet from the options on the right side to complete the program. Choose wisely, as each correct choice will help you rebuild and restore different parts of OOPify to its former brilliance.");
                        receivedLevel = "2";
                        break;
                    }
                    case "2": {
                        instructionValue.setText("As you progress, you'll learn key concepts such as classes, inheritance, polymorphism, and more—all while helping the city of OOPify rise again. Get ready to dive in, test your skills, and become the hero OOPify desperately needs!");
                        proceedButton.setImageResource(R.drawable.proceed_button);
                        receivedLevel = "3";
                        break;
                    }
                    case "3": {
                        Intent intent = new Intent(Instructions.this, LevLes1.class);
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

                Intent intent = new Intent(Instructions.this, Maps.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);

        }
        return super.dispatchKeyEvent(event);
    }
}