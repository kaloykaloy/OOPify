package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Introduction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Introduction extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView levelImageView;
    private Handler handler = new Handler();
    private int[] imageArray = {R.drawable.spinimage, R.drawable.mainrobot, R.drawable.loginbgimage, R.drawable.imagelevelintro};
    private int currentImageIndex = 0;



    TextView Points, loginPopup, signupPopup, signupFrag, loginFrag, fragmentBar;
    RelativeLayout relativeLayout;
    LinearLayout parentLayout, loginLayout, signupLayout;


    public Introduction() {
    }

    public static Introduction newInstance(String param1, String param2) {
        Introduction fragment = new Introduction();
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
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_introduction, container, false);
        parentLayout = view.findViewById(R.id.playNowButton);
        relativeLayout = view.findViewById(R.id.spinLayout);
        loginPopup = view.findViewById(R.id.loginPopup);
        signupPopup = view.findViewById(R.id.signupPopup);
        loginFrag = view.findViewById(R.id.loginFragment);
        signupFrag = view.findViewById(R.id.signupFragment);
        loginLayout = view.findViewById(R.id.loginFragmentLayout);
        signupLayout = view.findViewById(R.id.signupFragmentLayout);
        fragmentBar = view.findViewById(R.id.fragmentBar);
        Points = view.findViewById(R.id.totalPoints);

        SessionManager sessionManager = new SessionManager(getActivity());
        if (sessionManager.isLoggedIn()) {
            loginLayout.setVisibility(View.GONE);
            signupLayout.setVisibility(View.GONE);
        } else {
            loginLayout.setVisibility(View.VISIBLE);
            signupLayout.setVisibility(View.VISIBLE);
        }

        String tupId = sessionManager.getTupId();
        DatabaseHelper DB = new DatabaseHelper(getActivity());
        Cursor cursor = DB.getUserPointsAndRank(tupId);
        Cursor cursors = DB.getLevelSystemData(tupId);

        if (cursor != null && cursor.moveToFirst()) {
            String points = cursor.getString(cursor.getColumnIndexOrThrow("points"));
            Points.setText(points);
            cursor.close();
        }
        if (cursors != null && cursors.moveToFirst()) {
            String level = cursors.getString(cursors.getColumnIndexOrThrow("lvl"));
            String bar = cursors.getString(cursors.getColumnIndexOrThrow("bar"));

            int convertedLvl = Integer.parseInt(level);
            String[] barLevels = bar.split(",");

            switch (barLevels[convertedLvl - 1]){
                case "0":
                    fragmentBar.setBackgroundResource(0);
                    break;
                case "1":
                    fragmentBar.setBackgroundResource(R.drawable.bar1);
                    break;
                case "2":
                    fragmentBar.setBackgroundResource(R.drawable.bar2);
                    break;
                case "3":
                    fragmentBar.setBackgroundResource(R.drawable.bar3);
                    break;
                case "4":
                    fragmentBar.setBackgroundResource(R.drawable.bar4);
                    break;
                case "5":
                    fragmentBar.setBackgroundResource(R.drawable.bar5);
                    break;

            }

            cursors.close();
        }
        loginFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                stopBackgroundMusic();

            }
        });
        signupFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Signup.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                stopBackgroundMusic();

            }
        });

        loginPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                stopBackgroundMusic();
            }
        });
        signupPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Signup.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                stopBackgroundMusic();
            }
        });

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sessionManager.isLoggedIn()) {
                    Intent intent = new Intent(getActivity(), Maps.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
                    stopBackgroundMusic();
                } else {
                    LinearLayout attentionError = getActivity().findViewById(R.id.attentionError);
                    attentionError.setVisibility(View.VISIBLE);
                }
            }
        });


        levelImageView = view.findViewById(R.id.levelImageView);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sessionManager.isLoggedIn()) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new SpinWheel())
                            .commit();
                } else {
                    LinearLayout attentionError = getActivity().findViewById(R.id.attentionError);
                    attentionError.setVisibility(View.VISIBLE);
                }
            }
        });
        startImageSwitcher();

        return view;
    }

    private void startImageSwitcher() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                levelImageView.setImageResource(imageArray[currentImageIndex]);
                currentImageIndex++;


                if (currentImageIndex >= imageArray.length) {
                    currentImageIndex = 0;
                }


                handler.postDelayed(this, 2000);
            }
        };


        handler.post(runnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        handler.removeCallbacksAndMessages(null);
    }
    private void stopBackgroundMusic() {
        if (Mainmenu.mediaPlayer != null && Mainmenu.mediaPlayer.isPlaying()) {
            Mainmenu.mediaPlayer.stop();
            Mainmenu.mediaPlayer.release();
            Mainmenu.mediaPlayer = null;
        }
    }
}
