package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


public class Maps extends AppCompatActivity {
    DatabaseHelper DB;

    ImageButton level1Image, level2Image, level3Image, level4Image, level5Image,
            level6Image, level7Image, level8Image, level9Image, level10Image;

    LinearLayout level1Layout, level2Layout, level3Layout, level4Layout, level5Layout,
            level6Layout, level7Layout, level8Layout, level9Layout, level10Layout, map;

    private PopupWindowHelper popupWindowHelper;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        DB = new DatabaseHelper(this);

        hideSystemUI();

        mediaPlayer = MediaPlayer.create(this, R.raw.bgmusicmap);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        SessionManager sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            String tupId = sessionManager.getTupId();

            popupWindowHelper = new PopupWindowHelper(this, "Maps", tupId, DB);


        } else {
            Intent intent = new Intent(Maps.this, Login.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
        }


        ImageButton backButton = findViewById(R.id.backButton);

        level1Layout = findViewById(R.id.level1);
        level1Image = findViewById(R.id.level1Image);
        level2Layout = findViewById(R.id.level2);
        level2Image = findViewById(R.id.level2Image);
        level3Layout = findViewById(R.id.level3);
        level3Image = findViewById(R.id.level3Image);
        level4Layout = findViewById(R.id.level4);
        level4Image = findViewById(R.id.level4Image);
        level5Layout = findViewById(R.id.level5);
        level5Image = findViewById(R.id.level5Image);
        level6Layout = findViewById(R.id.level6);
        level6Image = findViewById(R.id.level6Image);
        level7Layout = findViewById(R.id.level7);
        level7Image = findViewById(R.id.level7Image);
        level8Layout = findViewById(R.id.level8);
        level8Image = findViewById(R.id.level8Image);
        level9Layout = findViewById(R.id.level9);
        level9Image = findViewById(R.id.level9Image);
        level10Layout = findViewById(R.id.level10);
        level10Image = findViewById(R.id.level10Image);

        map = findViewById(R.id.map);


        loadLevelSystemData();
        checkStory();

        CustomVideoView backgroundVideo = findViewById(R.id.backgroundVideo);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mapbgvideo);
        backgroundVideo.setVideoURI(videoUri);

        backgroundVideo.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            backgroundVideo.start();
        });



        View.OnClickListener changeLevel1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maps.this,Instructions.class);
                intent.putExtra("Level", "1");
                intent.putExtra("Instruction", "Welcome to the world of OOPFIY ProGamer! Your mission is to save the once-glorious city of OOPify by mastering the art of C# programming. In this game, you will encounter various challenges that will test your understanding of object-oriented programming principles.");
                startActivity(intent);
                overridePendingTransition(R.anim.enterlevel, R.anim.exitlevel);
                mediaPlayer.release();
            }
        };
        View.OnClickListener changeLevel2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maps.this, LevLes2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enterlevel, R.anim.exitlevel);
                mediaPlayer.release();
            }
        };
        View.OnClickListener changeLevel3 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maps.this, LevLes3.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enterlevel, R.anim.exitlevel);
                mediaPlayer.release();
            }
        };
        View.OnClickListener changeLevel4 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maps.this, LevLes4.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enterlevel, R.anim.exitlevel);
                mediaPlayer.release();
            }
        };
        View.OnClickListener changeLevel5 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maps.this, LevLes5.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enterlevel, R.anim.exitlevel);
                mediaPlayer.release();
            }
        };
        View.OnClickListener changeLevel6 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maps.this, LevLes6.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enterlevel, R.anim.exitlevel);
                mediaPlayer.release();
            }
        };
        View.OnClickListener changeLevel7 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maps.this, LevLes7.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enterlevel, R.anim.exitlevel);
                mediaPlayer.release();
            }
        };
        View.OnClickListener changeLevel8 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maps.this, LevLes8.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enterlevel, R.anim.exitlevel);
                mediaPlayer.release();
            }
        };
        View.OnClickListener changeLevel9 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maps.this, LevLes9.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enterlevel, R.anim.exitlevel);
                mediaPlayer.release();
            }
        };
        View.OnClickListener changeLevel10 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maps.this, LevLes10.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enterlevel, R.anim.exitlevel);
                mediaPlayer.release();
            }
        };


        level1Layout.setOnClickListener(changeLevel1);
        level1Image.setOnClickListener(changeLevel1);

        level2Layout.setOnClickListener(changeLevel2);
        level2Image.setOnClickListener(changeLevel2);

        level3Layout.setOnClickListener(changeLevel3);
        level3Image.setOnClickListener(changeLevel3);

        level4Layout.setOnClickListener(changeLevel4);
        level4Image.setOnClickListener(changeLevel4);

        level5Layout.setOnClickListener(changeLevel5);
        level5Image.setOnClickListener(changeLevel5);

        level6Layout.setOnClickListener(changeLevel6);
        level6Image.setOnClickListener(changeLevel6);

        level7Layout.setOnClickListener(changeLevel7);
        level7Image.setOnClickListener(changeLevel7);

        level8Layout.setOnClickListener(changeLevel8);
        level8Image.setOnClickListener(changeLevel8);

        level9Layout.setOnClickListener(changeLevel9);
        level9Image.setOnClickListener(changeLevel9);

        level10Layout.setOnClickListener(changeLevel10);
        level10Image.setOnClickListener(changeLevel10);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Maps.this, Mainmenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                mediaPlayer.release();

            }
        });

    }

    private void checkStory() {
        SessionManager sessionManager = new SessionManager(this);
        String tupId = sessionManager.getTupId();

        Cursor cursor = DB.getLevelSystemData(tupId);

        if (cursor != null && cursor.moveToFirst()) {
            String level = cursor.getString(cursor.getColumnIndexOrThrow("lvl"));
            String star = cursor.getString(cursor.getColumnIndexOrThrow("star"));

            String[] getStarIndex = star.split(",");

            if (level.equals("1") && getStarIndex[0].equals("0")){
                View rootView = findViewById(android.R.id.content);
                popupWindowHelper.dismissPopupWindow();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popupWindowHelper.showPopupWindow(rootView, R.layout.popup_story);
                        View popupView = popupWindowHelper.getPopupView();
                        TextView storyText = popupView.findViewById(R.id.storyText);
                        LinearLayout proceedStoryButton = popupView.findViewById(R.id.proceedStory);
                        proceedStoryButton.setVisibility(View.GONE);
                        CharSequence fullText = storyText.getText();
                        TypingEffect typingEffect = new TypingEffect(storyText, fullText);
                        typingEffect.setTypingSpeed(1);
                        typingEffect.setOnTypingCompleteListener(new OnTypingCompleteListener() {
                            @Override
                            public void onTypingComplete() {
                                proceedStoryButton.setVisibility(View.VISIBLE);
                            }
                        });
                        typingEffect.startTypingEffect();
                        popupView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                storyText.setText(fullText);
                                typingEffect.stopTypingEffect();
                                proceedStoryButton.setVisibility(View.VISIBLE);
                                popupView.setOnTouchListener(null);
                                return true;

                            }
                        });



                    }
                }, 100);

            }

            cursor.close();
        } else {
            Toast.makeText(this, "Level system data not found for this user!", Toast.LENGTH_SHORT).show();
        }

    }
    private void loadLevelSystemData() {
        SessionManager sessionManager = new SessionManager(this);
        String tupId = sessionManager.getTupId();

//        String newLevel = "10";
//        String newStar = "1,2,3,1,2,3,1,2,3,0";
//        String newPoints = "35000";
//        String newBar = "5,5,5,5,5,5,5,5,5,5";
//        DB.updateLevelSystemData(tupId, newLevel, newStar, newPoints, newBar);

        Cursor cursor = DB.getLevelSystemData(tupId);

        if (cursor != null && cursor.moveToFirst()) {
            String level = cursor.getString(cursor.getColumnIndexOrThrow("lvl"));
            String star = cursor.getString(cursor.getColumnIndexOrThrow("star"));
            String points = cursor.getString(cursor.getColumnIndexOrThrow("points"));
            String bar = cursor.getString(cursor.getColumnIndexOrThrow("bar"));

            String[] starLevels = star.split(",");



            if(level.equalsIgnoreCase("0")){
                level1Image.setImageResource(R.drawable.lockedcolored);
            }

            for (int i = 1; i <= 10; i++) {
                ImageButton levelImage = getLevelImageButton(i);
                LinearLayout levelLayout = getLevelLayout(i);


                if (i <= Integer.parseInt(level)) {

                    int starsForLevel = Integer.parseInt(starLevels[i - 1]);

                    switch (starsForLevel) {
                        case 0:
                            levelImage.setImageResource(R.drawable.lockedcolored);
                            map.setBackgroundResource(getMapLayout(Integer.parseInt(level)));
                            break;
                        case 1:
                            levelImage.setImageResource(R.drawable.star1);
                            map.setBackgroundResource(getMapLayout(Integer.parseInt(level) + 1));
                            levelImage.setEnabled(false);
                            levelLayout.setEnabled(false);
                            break;
                        case 2:
                            levelImage.setImageResource(R.drawable.star2);
                            map.setBackgroundResource(getMapLayout(Integer.parseInt(level) + 1));
                            levelImage.setEnabled(false);
                            levelLayout.setEnabled(false);
                            break;
                        case 3:
                            levelImage.setImageResource(R.drawable.star3);
                            map.setBackgroundResource(getMapLayout(Integer.parseInt(level) + 1));
                            levelImage.setEnabled(false);
                            levelLayout.setEnabled(false);
                            break;
                    }
                    if (i < 10) {
                        ImageButton nextLevelImage = getLevelImageButton(i + 1);
                        LinearLayout nextLevelLayout = getLevelLayout(i + 1);

                        if (starsForLevel > 0) {
                            nextLevelImage.setImageResource(R.drawable.lockedcolored);
                            nextLevelImage.setEnabled(true);
                            nextLevelLayout.setEnabled(true);
                        } else {
                            nextLevelImage.setImageResource(R.drawable.locked);
                            nextLevelImage.setEnabled(false);
                            nextLevelLayout.setEnabled(false);
                        }
                    }

                } else {
                    if (i != Integer.parseInt(level) + 1) {
                        levelImage.setImageResource(R.drawable.locked);
                        levelLayout.setEnabled(false);
                        levelImage.setEnabled(false);
                    }
                }
            }
            map.setBackgroundResource(getMapLayout(Integer.parseInt(level)));
            cursor.close();
        } else {
            Toast.makeText(this, "Level system data not found for this user!", Toast.LENGTH_SHORT).show();
        }
    }


    private ImageButton getLevelImageButton(int level) {
        switch (level) {
            case 1:
                return findViewById(R.id.level1Image);
            case 2:
                return findViewById(R.id.level2Image);
            case 3:
                return findViewById(R.id.level3Image);
            case 4:
                return findViewById(R.id.level4Image);
            case 5:
                return findViewById(R.id.level5Image);
            case 6:
                return findViewById(R.id.level6Image);
            case 7:
                return findViewById(R.id.level7Image);
            case 8:
                return findViewById(R.id.level8Image);
            case 9:
                return findViewById(R.id.level9Image);
            case 10:
                return findViewById(R.id.level10Image);
            default:
                return null;
        }
    }

    private LinearLayout getLevelLayout(int level) {
        switch (level) {
            case 1:
                return findViewById(R.id.level1);
            case 2:
                return findViewById(R.id.level2);
            case 3:
                return findViewById(R.id.level3);
            case 4:
                return findViewById(R.id.level4);
            case 5:
                return findViewById(R.id.level5);
            case 6:
                return findViewById(R.id.level6);
            case 7:
                return findViewById(R.id.level7);
            case 8:
                return findViewById(R.id.level8);
            case 9:
                return findViewById(R.id.level9);
            case 10:
                return findViewById(R.id.level10);
            default:
                return null;
        }
    }
    private int getMapLayout(int level) {
        switch (level) {
            case 1:
                return R.drawable.level1map;
            case 2:
                return R.drawable.level2map;
            case 3:
                return R.drawable.level3map;
            case 4:
                return R.drawable.level4map;
            case 5:
                return R.drawable.level5map;
            case 6:
                return R.drawable.level6map;
            case 7:
                return R.drawable.level7map;
            case 8:
                return R.drawable.level8map;
            case 9:
                return R.drawable.level9map;
            case 10:
                return R.drawable.map;
            default:
                return 0;
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


    public interface OnTypingCompleteListener {
        void onTypingComplete();
    }

    public class TypingEffect {

        private int index = 0;
        private long delay = 50;
        private CharSequence text;
        private TextView textView;
        private Handler handler = new Handler();
        private OnTypingCompleteListener listener;

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

        public void setOnTypingCompleteListener(OnTypingCompleteListener listener) {
            this.listener = listener;
        }
        public void stopTypingEffect() {
            handler.removeCallbacks(characterAdder);
        }
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (popupWindowHelper.isPopupVisible()) {

                return true;
            } else {
                Intent intent = new Intent(Maps.this, Mainmenu.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                mediaPlayer.release();

            }
        }
        return super.dispatchKeyEvent(event);
    }


}