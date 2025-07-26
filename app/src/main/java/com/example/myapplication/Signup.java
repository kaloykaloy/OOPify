package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import android.Manifest;

public class Signup extends AppCompatActivity {
    private EditText datePicker;
    private EditText passwordEditText, reEnterPasswordEditText;
    private boolean isPasswordVisible = false;
    private boolean isReEnterPasswordVisible = false;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE = 1;
    private ImageView profileImageView;
    private static final int PICK_IMAGE_REQUEST = 1;

    EditText tupID, lastName, firstName, middleName, suffix,
            password, passwordRe, email, birthdate, program, specialization;
    RadioGroup genderGroup;
    RadioButton selectedGender;
    TextView signupButton, loginButtonSign;
    Bitmap profileBitmap;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        hideSystemUI();

        datePicker = findViewById(R.id.datePicker);

        profileImageView = findViewById(R.id.profileImageView);
        loginButtonSign = findViewById(R.id.loginButtonSign);

        tupID = findViewById(R.id.tupID);
        lastName = findViewById(R.id.lastName);
        firstName = findViewById(R.id.firstName);
        middleName = findViewById(R.id.middleName);
        suffix = findViewById(R.id.suffix);
        password = findViewById(R.id.passwordEditText);
        passwordRe = findViewById(R.id.reEnterPasswordEditText);
        email = findViewById(R.id.email);
        birthdate = findViewById(R.id.datePicker);
        program = findViewById(R.id.program);
        specialization = findViewById(R.id.specialization);
        genderGroup = findViewById(R.id.radioGroup);
        profileImageView = findViewById(R.id.profileImageView);
        signupButton = findViewById(R.id.signupButton);

        databaseHelper = new DatabaseHelper(this);
        checkMediaPermissions();
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openImageChooser();
            }
        });

        loginButtonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);


            }
        });
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        passwordEditText = findViewById(R.id.passwordEditText);
        reEnterPasswordEditText = findViewById(R.id.reEnterPasswordEditText);

        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_END = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                        togglePasswordVisibility(passwordEditText, true);
                        return true;
                    }
                }
                return false;
            }
        });

        reEnterPasswordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_END = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (reEnterPasswordEditText.getRight() - reEnterPasswordEditText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                        togglePasswordVisibility(reEnterPasswordEditText, false);
                        return true;
                    }
                }
                return false;
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId = genderGroup.getCheckedRadioButtonId();

                String tupIdValue = tupID.getText().toString();
                String lastNameValue = lastName.getText().toString();
                String firstNameValue = firstName.getText().toString();
                String middleNameValue = middleName.getText().toString();
                String suffixNameValue = suffix.getText().toString();
                String passwordValue = password.getText().toString();
                int passwordLength = passwordValue.length();
                String passwordReValue = passwordRe.getText().toString();
                String emailValue = email.getText().toString();
                String birthdateValue = birthdate.getText().toString();
                String programValue = program.getText().toString();
                String specializationValue = specialization.getText().toString();

                String imagePath = null;
                if (profileBitmap != null) {
                    imagePath = saveImageToExternalStorage(profileBitmap);
                }

                if (profileBitmap == null || tupIdValue.isEmpty() || lastNameValue.isEmpty() || firstNameValue.isEmpty()
                        || passwordValue.isEmpty() || emailValue.isEmpty() || birthdateValue.isEmpty()
                        || programValue.isEmpty() || specializationValue.isEmpty()){
                    Toast.makeText(Signup.this, "Please complete all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
                    Toast.makeText(Signup.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedGenderId == -1) {
                    Toast.makeText(Signup.this, "Please select a gender.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordLength < 4){
                    Toast.makeText(Signup.this, "Password must contain at least 4 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                selectedGender = findViewById(selectedGenderId);
                String genderValue = selectedGender.getText().toString();
                if (!passwordValue.equals(passwordReValue)){
                    Toast.makeText(Signup.this, "Your password" + passwordValue + passwordReValue + "match!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (databaseHelper.isTupIdExists(tupIdValue)) {
                    Toast.makeText(Signup.this, "TUP ID already exists. Please choose a different one.", Toast.LENGTH_SHORT).show();
                } else {
                    SessionManager sessionManager = new SessionManager(Signup.this);
                    sessionManager.createLoginSession(tupIdValue);

                    databaseHelper.addUser(tupIdValue, lastNameValue, firstNameValue, middleNameValue, suffixNameValue, passwordValue, emailValue, birthdateValue, programValue, specializationValue, genderValue, imagePath);
                    Toast.makeText(Signup.this, "User Registered!", Toast.LENGTH_SHORT).show();
                    databaseHelper.addLevelSystemData(tupIdValue,"1","0,0,0,0,0,0,0,0,0,0","0","5,5,5,5,5,5,5,5,5,5");
                    Intent intent = new Intent(Signup.this, Mainmenu.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.enteranim, R.anim.exitanim);

                }



            }
        });

    }
    private void checkMediaPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                profileBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImageView.setImageBitmap(profileBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String saveImageToExternalStorage(Bitmap bitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/MyAppImages");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String fileName = "Profile_" + System.currentTimeMillis() + ".jpg";
        File file = new File(myDir, fileName);

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Media access permission is required to save profile images", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void togglePasswordVisibility(EditText editText, boolean isMainPassword) {
        Typeface customFont = ResourcesCompat.getFont(this, R.font.teko_light);

        if (isMainPassword) {
            if (isPasswordVisible) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.viewpassword, 0);
            } else {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hidepassword, 0);
            }
            isPasswordVisible = !isPasswordVisible;
        } else {
            if (isReEnterPasswordVisible) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.viewpassword, 0);
            } else {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hidepassword, 0);
            }
            isReEnterPasswordVisible = !isReEnterPasswordVisible;
        }

        if (customFont != null) {
            editText.setTypeface(customFont);
        }

        editText.setSelection(editText.getText().length());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Signup.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    datePicker.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
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

            Intent intent = new Intent(Signup.this, Mainmenu.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.enteranim, R.anim.exitanim);


        }
        return super.dispatchKeyEvent(event);
    }
}