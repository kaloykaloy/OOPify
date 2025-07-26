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

public class LevLes5 extends AppCompatActivity {


    String receivedLevel = "";
    TextView pointsValue;
    ImageButton barStatusInstruction;
    TextView instructionValue;
    ImageButton proceedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lev_les5);
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

        SessionManager sessionManager = new SessionManager(LevLes5.this);
        String tupId = sessionManager.getTupId();
        DatabaseHelper DB = new DatabaseHelper(LevLes5.this);
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
                        Intent intent = new Intent(LevLes5.this, Instructions.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                        break;
                    }
                    case "2": {
                        instructionValue.setText("Abstraction in Object-Oriented Programming (OOP) in C# is the concept of hiding the complex implementation details of an object and exposing only the essential features or functionalities to the user. This simplifies the interaction with objects by providing a clear and simplified interface, allowing users to work with the object without needing to understand the underlying complexities.");
                        receivedLevel = "1";
                        break;
                    }
                    case "3": {
                        instructionValue.setText("An abstract class is a class that cannot be instantiated on its own and is intended to be a base class for other classes. It can contain abstract methods (methods without implementation) and non-abstract methods (methods with implementation).");
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
                        instructionValue.setText("An abstract class is a class that cannot be instantiated on its own and is intended to be a base class for other classes. It can contain abstract methods (methods without implementation) and non-abstract methods (methods with implementation).");
                        receivedLevel = "2";
                        break;
                    }
                    case "2": {
                        instructionValue.setText("An abstract method is declared without an implementation in an abstract class. Subclasses that derive from this abstract class must provide their own implementation of these abstract methods.");
                        proceedButton.setImageResource(R.drawable.proceed_button);
                        receivedLevel = "3";
                        break;
                    }
                    case "3": {
                        Intent intent = new Intent(LevLes5.this, LevProb5.class);
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

            Intent intent = new Intent(LevLes5.this, Instructions.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.enteranim, R.anim.exitanim);

        }
        return super.dispatchKeyEvent(event);
    }
}