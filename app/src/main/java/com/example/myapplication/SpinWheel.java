package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class SpinWheel extends Fragment {

    private ImageView wheelImageView;
    private TextView resultTextView;
    private Random random = new Random();
    private View rootView;
    private PopupWindowHelper popupHelper;
    private static final String PREFS_NAME = "CountdownPrefs";
    private static final long SPIN_WHEEL_COUNTDOWN = 86400000;
    private String userId;
    private DatabaseHelper DB;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    View popupView;
    TextView resultWheel;
    private ObjectAnimator infiniteSpinAnimator;

    public SpinWheel() {
    }

    public static SpinWheel newInstance(String param1, String param2) {
        SpinWheel fragment = new SpinWheel();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_spin_wheel, container, false);

        wheelImageView = rootView.findViewById(R.id.wheelImageView);
        resultTextView = rootView.findViewById(R.id.resultTextView);

        SessionManager sessionManager = new SessionManager(getActivity());
        userId = sessionManager.getTupId();
        DB = new DatabaseHelper(getActivity());
        popupHelper = new PopupWindowHelper(getActivity(), "SpinWheelKey", userId, DB);

        Animation fadeEffect = AnimationUtils.loadAnimation(getActivity(), R.anim.breathing_effect);
        resultTextView.startAnimation(fadeEffect);

        checkRemainingTimeAndShowPopup();
        startInfiniteSpin();

        wheelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopInfiniteSpin();
                spinTheWheel();
                wheelImageView.setEnabled(false);
            }
        });

        return rootView;
    }
    private void startInfiniteSpin() {
        infiniteSpinAnimator = ObjectAnimator.ofFloat(wheelImageView, "rotation", 0f, 360f);
        infiniteSpinAnimator.setDuration(15000);
        infiniteSpinAnimator.setInterpolator(new LinearInterpolator());
        infiniteSpinAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        infiniteSpinAnimator.setRepeatMode(ObjectAnimator.RESTART);
        infiniteSpinAnimator.start();
    }

    private void stopInfiniteSpin() {
        if (infiniteSpinAnimator != null && infiniteSpinAnimator.isRunning()) {
            infiniteSpinAnimator.cancel();
        }
    }


    private void checkRemainingTimeAndShowPopup() {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        long endTime = prefs.getLong(userId + "_SpinWheelKey_END_TIME", 0);

        long currentTimeMillis = System.currentTimeMillis();
        long remainingTimeMillis = endTime - currentTimeMillis;

        if (remainingTimeMillis > 0) {
            popupHelper.showSpinWheelPopupWindow(rootView, R.layout.popup_spinwheel, remainingTimeMillis);
        } else {
            wheelImageView.setEnabled(true);
        }
    }

    private void spinTheWheel() {
        int endAngle = random.nextInt(3600) + 360;

        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(wheelImageView, "rotation", 0f, endAngle);
        rotateAnimator.setDuration(5000);
        rotateAnimator.setInterpolator(new DecelerateInterpolator());

        rotateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int finalAngle = (int) (wheelImageView.getRotation() % 360);
                int result = calculateResult(finalAngle);
                displayResult(result);

                setCountdownEndTime();
            }
        });

        rotateAnimator.start();
    }

    private int calculateResult(int finalAngle) {
        if (finalAngle >= 0 && finalAngle < 90) {
            return 1;
        } else if (finalAngle >= 90 && finalAngle < 180) {
            return 2;
        } else if (finalAngle >= 180 && finalAngle < 270) {
            return 3;
        } else {
            return 4;
        }
    }


    private void displayResult(int result) {
        switch (result) {
            case 1:
                popupHelper.showSpinWheelPopupWindow(rootView, R.layout.popup_spinwheel, SPIN_WHEEL_COUNTDOWN);
                popupView = popupHelper.getPopupView();
                resultWheel = popupView.findViewById(R.id.resultWheel);
                resultWheel.setText("Better luck next time! \n\n Please come back again later!");
                resultTextView.setText("");
                break;
            case 2:
                popupHelper.showSpinWheelPopupWindow(rootView, R.layout.popup_spinwheel, SPIN_WHEEL_COUNTDOWN);
                popupView = popupHelper.getPopupView();
                resultWheel = popupView.findViewById(R.id.resultWheel);
                resultWheel.setText("You won 500 Points! \n\n Please come back again later!");
                DB.addPoints(userId, 500);
                resultTextView.setText("");
                break;
            case 3:
                resultTextView.setText("Spin again!");
                break;
            case 4:
                popupHelper.showSpinWheelPopupWindow(rootView, R.layout.popup_spinwheel, SPIN_WHEEL_COUNTDOWN);
                popupView = popupHelper.getPopupView();
                resultWheel = popupView.findViewById(R.id.resultWheel);
                resultWheel.setText("You won 100 Points! \n\n Please come back again later!");
                DB.addPoints(userId, 100);
                resultTextView.setText("");
                break;
        }


        wheelImageView.setEnabled(true);
    }


    private void setCountdownEndTime() {
        long endTime = System.currentTimeMillis() + SPIN_WHEEL_COUNTDOWN;
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putLong(userId + "_SpinWheelKey_END_TIME", endTime).apply();
    }



    public boolean isPopupVisible() {
        return popupHelper != null && popupHelper.isPopupVisible();
    }

}
