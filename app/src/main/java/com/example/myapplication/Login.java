package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    private boolean isPasswordVisible = false;

    EditText idNoLogin, passwordLogin;
    TextView loginButton, signupButton;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hideSystemUI();

        idNoLogin = findViewById(R.id.idNoLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButtonLog);


        databaseHelper = new DatabaseHelper(this);

        EditText passwordEditText = findViewById(R.id.passwordLogin);

        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    if (passwordEditText.getCompoundDrawables()[2] != null) {
                        int drawableStart = passwordEditText.getWidth()
                                - passwordEditText.getPaddingEnd()
                                - passwordEditText.getCompoundDrawables()[2].getBounds().width();

                        if (event.getX() >= drawableStart) {
                            togglePasswordVisibility(passwordEditText, true);
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idNo = idNoLogin.getText().toString();
                String password = passwordLogin.getText().toString();

                if (idNo.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isValidUser = databaseHelper.checkUser(idNo, password);
                    if (isValidUser) {

                        SessionManager sessionManager = new SessionManager(Login.this);
                        sessionManager.createLoginSession(idNo);

                        Intent intent = new Intent(Login.this, Mainmenu.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                    } else {
                        Toast.makeText(Login.this, "Invalid ID or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
            }
        });


    }
    private void togglePasswordVisibility(EditText editText, boolean isMainPassword) {
        if (isMainPassword) {
            if (isPasswordVisible) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.viewpassword, 0);
            } else {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hidepassword, 0);
            }
            isPasswordVisible = !isPasswordVisible;
        }
        editText.setSelection(editText.getText().length());
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

            Intent intent = new Intent(Login.this, Mainmenu.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.enteranim, R.anim.exitanim);

        }
        return super.dispatchKeyEvent(event);
    }

}