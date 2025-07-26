package com.example.myapplication;
import android.Manifest;
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import android.graphics.drawable.BitmapDrawable;
public class UpdateAccount extends AppCompatActivity {

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
    TextView updateButton, cancelButtonSign;
    Bitmap profileBitmap;
    DatabaseHelper databaseHelper;
    String tupId;
    String profileImageData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        hideSystemUI();
        SessionManager sessionManager = new SessionManager(this);
        tupId = sessionManager.getTupId();
        datePicker = findViewById(R.id.datePickerUpdate);

        profileImageView = findViewById(R.id.profileImageViewUpdate);
        cancelButtonSign = findViewById(R.id.cancelButtonUpdate);


        lastName = findViewById(R.id.lastNameUpdate);
        firstName = findViewById(R.id.firstNameUpdate);
        middleName = findViewById(R.id.middleNameUpdate);
        suffix = findViewById(R.id.suffixUpdate);
        password = findViewById(R.id.passwordEditTextUpdate);
        passwordRe = findViewById(R.id.reEnterPasswordEditTextUpdate);
        email = findViewById(R.id.emailUpdate);
        birthdate = findViewById(R.id.datePickerUpdate);
        program = findViewById(R.id.programUpdate);
        specialization = findViewById(R.id.specializationUpdate);
        genderGroup = findViewById(R.id.radioGroupUpdate);

        RadioGroup radioGroupUpdate = findViewById(R.id.radioGroupUpdate);
        RadioButton radioMaleUpdate = findViewById(R.id.radioMaleUpdate);
        RadioButton radioFemaleUpdate = findViewById(R.id.radioFemaleUpdate);

        updateButton = findViewById(R.id.updateButton);

        databaseHelper = new DatabaseHelper(this);
        checkMediaPermissions();

        Cursor cursor = databaseHelper.getUserData(tupId);

        if (cursor != null && cursor.moveToFirst()) {
            String tupId = cursor.getString(cursor.getColumnIndexOrThrow("tup_id"));
            String lastNameData = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
            String firstNameData = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
            String middleNameData = cursor.getString(cursor.getColumnIndexOrThrow("middle_name"));
            String suffixNameData = cursor.getString(cursor.getColumnIndexOrThrow("suffix_name"));
            String passwordData = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String emailData = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String birthdateData = cursor.getString(cursor.getColumnIndexOrThrow("birthdate"));
            String programData = cursor.getString(cursor.getColumnIndexOrThrow("program"));
            String specializationData = cursor.getString(cursor.getColumnIndexOrThrow("specialization"));
            String genderData = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
            profileImageData = cursor.getString(cursor.getColumnIndexOrThrow("profile_image"));

            if (profileImageData != null) {

                Bitmap bitmap = BitmapFactory.decodeFile(profileImageData);

                profileImageView.setImageBitmap(bitmap);
            }

            lastName.setText(lastNameData);
            firstName.setText(firstNameData);
            middleName.setText(middleNameData);
            suffix.setText(suffixNameData);
            password.setText(passwordData);
            passwordRe.setText(passwordData);
            email.setText(emailData);
            birthdate.setText(birthdateData);
            program.setText(programData);
            specialization.setText(specializationData);

            if (genderData.equals("Male")) {
                radioMaleUpdate.setChecked(true);
            } else if (genderData.equals("Female")) {
                radioFemaleUpdate.setChecked(true);
            }


        }

        if (cursor != null) {
            cursor.close();
        }

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openImageChooser();
            }
        });

        cancelButtonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateAccount.this, Mainmenu.class);
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

        passwordEditText = findViewById(R.id.passwordEditTextUpdate);
        reEnterPasswordEditText = findViewById(R.id.reEnterPasswordEditTextUpdate);

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

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGenderId = genderGroup.getCheckedRadioButtonId();

                String lastNameValue = lastName.getText().toString();
                String firstNameValue = firstName.getText().toString();
                String middleNameValue = middleName.getText().toString();
                String suffixNameValue = suffix.getText().toString();
                String passwordValue = password.getText().toString();
                String passwordReValue = passwordRe.getText().toString();
                int passwordLength = passwordValue.length();
                String emailValue = email.getText().toString();
                String birthdateValue = birthdate.getText().toString();
                String programValue = program.getText().toString();
                String specializationValue = specialization.getText().toString();

                String imagePath = null;
                if (profileBitmap != null) {
                    imagePath = saveImageToExternalStorage(profileBitmap);
                } else {
                    imagePath = profileImageData;
                }

                if (lastNameValue.isEmpty() || firstNameValue.isEmpty()
                        || passwordValue.isEmpty() || emailValue.isEmpty() || birthdateValue.isEmpty()
                        || programValue.isEmpty() || specializationValue.isEmpty()){
                    Toast.makeText(UpdateAccount.this, "Please complete all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
                    Toast.makeText(UpdateAccount.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedGenderId == -1) {
                    Toast.makeText(UpdateAccount.this, "Please select a gender.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordLength < 4){
                    Toast.makeText(UpdateAccount.this, "Password must contain at least 4 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                selectedGender = findViewById(selectedGenderId);
                String genderValue = selectedGender.getText().toString();
                if (!passwordValue.equals(passwordReValue)){
                    return;
                }

                    databaseHelper.updateUser(tupId, lastNameValue, firstNameValue, middleNameValue, suffixNameValue, passwordValue, emailValue, birthdateValue, programValue, specializationValue, genderValue, imagePath);

                    Toast.makeText(UpdateAccount.this, "Account Successfully Updated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateAccount.this, Mainmenu.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.enteranim, R.anim.exitanim);

            }
        });

    }
    private void checkMediaPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_MEDIA_IMAGES, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
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
                UpdateAccount.this,
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
}