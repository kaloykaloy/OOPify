package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseHelper databaseHelper;
    private TextView birthdayData, programData, majorData,
            fullNameData, userTupIDData, firstNameData;
    private ImageView profileImageView;

    private LinearLayout userGenderData;
    private LinearLayout editProfileButton;
    TextView userPoints, rankTextView;

    public Profile() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        birthdayData = view.findViewById(R.id.birthdayData);
        programData = view.findViewById(R.id.programData);
        majorData = view.findViewById(R.id.majorData);
        profileImageView = view.findViewById(R.id.profile_image);
        fullNameData = view.findViewById(R.id.userFullName);
        userTupIDData = view.findViewById(R.id.usertupID);
        firstNameData = view.findViewById(R.id.userFirstName);
        userGenderData = view.findViewById(R.id.userGender);
        editProfileButton = view.findViewById(R.id.editProfileButton);
        userPoints = view.findViewById(R.id.userPoints);
        rankTextView = view.findViewById(R.id.rankTextView);

                loadUserProfileData();

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateAccount.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enteranim, R.anim.exitanim);

            }
        });

        return view;
    }
    @SuppressLint("SetTextI18n")
    private void loadUserProfileData() {
        SessionManager sessionManager = new SessionManager(getContext());
        String tupId = sessionManager.getTupId();

        Cursor cursorRP = databaseHelper.getUserPointsAndRank(tupId);

        if (cursorRP != null && cursorRP.moveToFirst()) {
            String points = cursorRP.getString(cursorRP.getColumnIndexOrThrow("points"));
            int rank = cursorRP.getInt(cursorRP.getColumnIndexOrThrow("rank"));
            setRankWithSuperscript(rank);
            userPoints.setText(points);
            cursorRP.close();
        }

        Cursor cursor = databaseHelper.getUserData(tupId);

        if (cursor != null && cursor.moveToFirst()) {
            String tupIDdb = cursor.getString(cursor.getColumnIndexOrThrow("tup_id"));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
            String middleName = cursor.getString(cursor.getColumnIndexOrThrow("middle_name"));
            String suffixName = cursor.getString(cursor.getColumnIndexOrThrow("suffix_name"));
            String birthdate = cursor.getString(cursor.getColumnIndexOrThrow("birthdate"));
            String program = cursor.getString(cursor.getColumnIndexOrThrow("program"));
            String major = cursor.getString(cursor.getColumnIndexOrThrow("specialization"));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
            String profileImage = cursor.getString(cursor.getColumnIndexOrThrow("profile_image"));

            if (profileImage != null) {

                Bitmap bitmap = BitmapFactory.decodeFile(profileImage);

                // Set the bitmap to the ImageView
                profileImageView.setImageBitmap(bitmap);
            } else {
                profileImageView.setImageResource(R.drawable.cidimage);
            }
            if (gender.equalsIgnoreCase("male")) {
                userGenderData.setBackgroundResource(R.drawable.maleicon);
            } else if (gender.equalsIgnoreCase("female")) {
                userGenderData.setBackgroundResource(R.drawable.femaleicon);
            } else {
                userGenderData.setBackgroundResource(R.drawable.maleicon);
            }

            if (middleName.isEmpty() && suffixName.isEmpty()){
                fullNameData.setText(firstName + " " + lastName);
            } else if (middleName.isEmpty()){
                fullNameData.setText(firstName + " " + lastName + " " + suffixName);
            } else if (suffixName.isEmpty()){
                fullNameData.setText(firstName + " " + middleName + " " + lastName);
            } else {
                fullNameData.setText(firstName + " " + middleName + " " + lastName + " " + suffixName);
            }

            firstNameData.setText(firstName);
            userTupIDData.setText(tupIDdb);
            birthdayData.setText(birthdate);
            programData.setText(program);
            majorData.setText(major);

            cursor.close();
        } else {
            Toast.makeText(getContext(), "User data not found!", Toast.LENGTH_SHORT).show();
        }
    }

    public void setRankWithSuperscript(int rank) {
        String rankSuffix = getRankSuffix(rank);
        String rankString = rank + rankSuffix;
        SpannableString spannableString = new SpannableString(rankString);
        int suffixStartIndex = rankString.length() - rankSuffix.length();
        int suffixEndIndex = rankString.length();
        spannableString.setSpan(new SuperscriptSpan(), suffixStartIndex, suffixEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(0.5f), suffixStartIndex, suffixEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        rankTextView.setText(spannableString);
    }

    private String getRankSuffix(int rank) {
        if (rank >= 11 && rank <= 13) {
            return "th";
        }
        switch (rank % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
}