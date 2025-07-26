package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class PopupWindowHelper {

    private Context context;
    Boolean nextCongrats = false;
    Boolean StoryCon = false;
    private static final long ONE_HOUR_IN_MILLIS = 3600000;
    private static final long SPIN_WHEEL_COUNTDOWN = 10000;
    private static final String PREFS_NAME = "CountdownPrefs";
    private String uniqueKey;
    private String userId;
    private DatabaseHelper DB;
    private PopupWindow popupWindow;
    private boolean isPopupVisible = false;
    private CountDownTimer countDownTimer;

    public PopupWindowHelper(Context context, String uniqueKey, String userId, DatabaseHelper DB) {
        this.context = context;
        this.uniqueKey = uniqueKey;
        this.userId = userId;
        this.DB = DB;
    }

    public void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public void showSpinWheelPopupWindow(View anchorView, int layoutResId, long remainingTimeMillis) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(layoutResId, null);

        popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true);

        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        isPopupVisible = true;

        popupWindow.setOnDismissListener(() -> {
            isPopupVisible = false;
        });

        TextView countDownText = popupView.findViewById(R.id.countDownWheel);

        if (countDownText != null) {

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            startSpinWheelCountdown(countDownText, remainingTimeMillis);
        }

        LinearLayout proceedWheel = popupView.findViewById(R.id.proceedWheel);
        if (proceedWheel != null) {
            proceedWheel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Mainmenu.class);
                    context.startActivity(intent);
                    dismissPopupWindow();
                    if (context instanceof Activity) {
                        ((Activity) context).overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                    }
                }
            });
        }
    }

    private void startSpinWheelCountdown(final TextView countDownText, long remainingTimeMillis) {
        countDownTimer = new CountDownTimer(remainingTimeMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                        TimeUnit.HOURS.toMinutes(hours);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                String timeLeft = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                countDownText.setText(timeLeft);
            }

            public void onFinish() {
                countDownText.setText("00:00:00");
                dismissPopupAndUpdateDatabase();
            }
        }.start();
    }



    public void showPopupWindow(View anchorView, int layoutResId) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(layoutResId, null);

        popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true);

        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        isPopupVisible = true;

        popupWindow.setOnDismissListener(() -> {
            isPopupVisible = false;
        });



        LinearLayout retry = popupView.findViewById(R.id.retryLevel);
        if (retry != null) {
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }

        LinearLayout quit = popupView.findViewById(R.id.quitLevel);
        if (quit != null) {
            quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Maps.class);
                    context.startActivity(intent);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (context instanceof Activity) {
                                ((Activity) context).finish();
                            }
                        }
                    }, 500);
                    if (context instanceof Activity) {
                        ((Activity) context).overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                    }
                }
            });
        }

        LinearLayout proceed = popupView.findViewById(R.id.proceedLevel);
        if (proceed != null) {
            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Maps.class);
                    context.startActivity(intent);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (context instanceof Activity) {
                                ((Activity) context).finish();
                            }
                        }
                    }, 500);
                    if (context instanceof Activity) {
                        ((Activity) context).overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                    }
                }
            });
        }

        LinearLayout proceedNoBar = popupView.findViewById(R.id.proceedLevelNoBar);
        if (proceedNoBar != null) {
            proceedNoBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Maps.class);
                    context.startActivity(intent);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (context instanceof Activity) {
                                ((Activity) context).finish();
                            }
                        }
                    }, 500);
                    if (context instanceof Activity) {
                        ((Activity) context).overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                    }
                }
            });
        }

        LinearLayout proceedCongrats = popupView.findViewById(R.id.proceedCongrats);
        if (proceedCongrats != null) {
            proceedCongrats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView congratsText = popupView.findViewById(R.id.congratsText);
                    if (nextCongrats){
                        Intent intent = new Intent(context, Maps.class);
                        context.startActivity(intent);
                        nextCongrats = false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (context instanceof Activity) {
                                    ((Activity) context).finish();
                                }
                            }
                        }, 500);
                        if (context instanceof Activity) {
                            ((Activity) context).overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                        }
                    } else {
                        congratsText.setText("The city now stands stronger than ever, thanks to your expertise. But remember, the journey doesn’t end here—new challenges and opportunities await. Keep honing your skills, and continue to build, innovate, and create.\n" +
                                "\n" +
                                "OOPify is grateful, and its future is bright because of you!");
                        proceedCongrats.setBackgroundResource(R.drawable.proceedbuttoncongrats);
                        nextCongrats = true;
                    }

                }
            });
        }

        LinearLayout proceedStory = popupView.findViewById(R.id.proceedStory);
        if (proceedStory != null) {
            proceedStory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView storyText = popupView.findViewById(R.id.storyText);
                    proceedStory.setVisibility(View.GONE);

                    if (!StoryCon) {
                        CharSequence secondStoryText = "But there's more to this adventure—your mission is to help revive the once-thriving metropolis of OOPify. By mastering C# and OOP, you'll gain the skills needed to rebuild this fallen city, restoring its former glory through well-designed, robust code. Get ready to dive in and become the hero OOPify needs!";

                        TypingEffect secondTypingEffect = new TypingEffect(storyText, secondStoryText);

                        secondTypingEffect.setTypingSpeed(1);

                        secondTypingEffect.setOnTypingCompleteListener(new Maps.OnTypingCompleteListener() {
                            @Override
                            public void onTypingComplete() {
                                proceedStory.setVisibility(View.VISIBLE);
                                proceedStory.setBackgroundResource(R.drawable.proceed_button);
                            }
                        });

                        secondTypingEffect.startTypingEffect();

                        popupView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                storyText.setText(secondStoryText);
                                secondTypingEffect.stopTypingEffect();
                                proceedStory.setVisibility(View.VISIBLE);
                                proceedStory.setBackgroundResource(R.drawable.proceed_button);
                                popupView.setOnTouchListener(null);
                                return true;

                            }
                        });


                        StoryCon = true;
                    } else {

                        popupWindow.dismiss();
                    }
                }
            });
        }


        TextView countDownText = popupView.findViewById(R.id.countDown);

        if (countDownText != null) {

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }


            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            long endTime = prefs.getLong(userId + "_" + uniqueKey + "_END_TIME", 0);


            if (endTime == 0 || endTime < System.currentTimeMillis()) {
                long currentTimeMillis = System.currentTimeMillis();
                endTime = currentTimeMillis + ONE_HOUR_IN_MILLIS;


                prefs.edit().putLong(userId + "_" + uniqueKey + "_END_TIME", endTime).apply();
            }

            long remainingTimeMillis = endTime - System.currentTimeMillis();


            if (remainingTimeMillis > 0) {
                startCountdown(countDownText, remainingTimeMillis);
            } else {
                countDownText.setText("00:00");

                prefs.edit().putLong(userId + "_" + uniqueKey + "_END_TIME", 0).apply();
            }
        }
    }

    private void startCountdown(final TextView countDownText, long remainingTimeMillis) {
        countDownTimer = new CountDownTimer(remainingTimeMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                String timeLeft = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                );
                countDownText.setText(timeLeft);
            }

            public void onFinish() {
                countDownText.setText("00:00");
                dismissPopupAndUpdateDatabase();
            }
        }.start();
    }

    private void dismissPopupAndUpdateDatabase() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        updateDatabase();
    }

    private void updateDatabase() {
        Cursor cursor = DB.getLevelSystemData(userId);

        if (cursor != null && cursor.moveToFirst()) {
            String level = cursor.getString(cursor.getColumnIndexOrThrow("lvl"));
            String star = cursor.getString(cursor.getColumnIndexOrThrow("star"));
            String points = cursor.getString(cursor.getColumnIndexOrThrow("points"));
            String bar = cursor.getString(cursor.getColumnIndexOrThrow("bar"));

            String[] barArray = bar.split(",");

            switch (uniqueKey) {
                case "Level1":
                    barArray[0] = "5";
                    break;
                case "Level2":
                    barArray[1] = "5";
                    break;
                case "Level3":
                    barArray[2] = "5";
                    break;
                case "Level4":
                    barArray[3] = "5";
                    break;
                case "Level5":
                    barArray[4] = "5";
                    break;
                case "Level6":
                    barArray[5] = "5";
                    break;
                case "Level7":
                    barArray[6] = "5";
                    break;
                case "Level8":
                    barArray[7] = "5";
                    break;
                case "Level9":
                    barArray[8] = "5";
                    break;
                case "Level10":
                    barArray[9] = "5";
                    break;
                default:
                    break;
            }

            String newBar = String.join(",", barArray);
            DB.updateLevelSystemData(userId, level, star, points, newBar);

            cursor.close();
        } else {
            Toast.makeText(context, "Error retrieving data from the database", Toast.LENGTH_SHORT).show();
        }
    }
    public View getPopupView() {
        return popupWindow.getContentView();
    }

    public class TypingEffect {

        private int index = 0;
        private long delay = 50;
        private CharSequence text;
        private TextView textView;
        private Handler handler = new Handler();
        private Maps.OnTypingCompleteListener listener;


        public TypingEffect(TextView textView, CharSequence text) {
            this.textView = textView;
            this.text = text;
        }


        private Runnable characterAdder = new Runnable() {
            @Override
            public void run() {
                textView.setText(text.subSequence(0, index++));

                if (index <= text.length()) {
                    handler.postDelayed(characterAdder, delay);
                } else {

                    if (listener != null) {
                        listener.onTypingComplete();
                    }
                }
            }
        };


        public void startTypingEffect() {
            index = 0;
            handler.removeCallbacks(characterAdder);
            handler.postDelayed(characterAdder, delay);
        }


        public void setTypingSpeed(long delayInMillis) {
            this.delay = delayInMillis;
        }


        public void setOnTypingCompleteListener(Maps.OnTypingCompleteListener listener) {
            this.listener = listener;
        }
        public void stopTypingEffect() {
            handler.removeCallbacks(characterAdder);
        }
    }
    public boolean isPopupShowing() {
        return isPopupVisible;
    }

    public boolean isPopupVisible() {
        return isPopupVisible;
    }


}
