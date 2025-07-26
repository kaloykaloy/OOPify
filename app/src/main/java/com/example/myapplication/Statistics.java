package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Statistics extends Fragment {

    private RecyclerView rankingRecyclerView;
    private RankingAdapter rankingAdapter;
    private List<RankingItem> rankingList;

    public Statistics() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);


        rankingRecyclerView = rootView.findViewById(R.id.rankingRecyclerView);
        rankingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        rankingList = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        Cursor cursor = dbHelper.getRankingData();

        List<RankingItem> tempRankingList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
                int level = cursor.getInt(cursor.getColumnIndexOrThrow("lvl"));
                int points = cursor.getInt(cursor.getColumnIndexOrThrow("points"));
                String starString = cursor.getString(cursor.getColumnIndexOrThrow("star"));


                int progress = calculateProgress(starString, level);


                String accuracy = "N/A";
                if (level == 10) {
                    String[] starArray = starString.split(",");
                    if (Integer.parseInt(starArray[9]) > 0) {
                        accuracy = (points * 100) / 50000 + "%";
                    }
                }

                tempRankingList.add(new RankingItem(
                        name,
                        String.valueOf(points),
                        progress + "%",
                        accuracy,
                        points,
                        progress
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();

        Collections.sort(tempRankingList, new Comparator<RankingItem>() {
            @Override
            public int compare(RankingItem o1, RankingItem o2) {
                if (o1.getPointsValue() == o2.getPointsValue()) {
                    return Integer.compare(o2.getProgressValue(), o1.getProgressValue());
                }
                return Integer.compare(o2.getPointsValue(), o1.getPointsValue());
            }
        });

        int rank = 1;
        for (int i = 0; i < tempRankingList.size(); i++) {
            RankingItem currentItem = tempRankingList.get(i);

            if (i > 0) {
                RankingItem previousItem = tempRankingList.get(i - 1);
                if (previousItem.getPointsValue() == currentItem.getPointsValue() &&
                        previousItem.getProgressValue() == currentItem.getProgressValue()) {
                    currentItem.setRank(previousItem.getRank());
                } else {
                    currentItem.setRank(String.valueOf(rank));
                }
            } else {
                currentItem.setRank(String.valueOf(rank));
            }

            rankingList.add(currentItem);
            rank++;
        }

        rankingAdapter = new RankingAdapter(rankingList);
        rankingRecyclerView.setAdapter(rankingAdapter);

        return rootView;
    }


    private int calculateProgress(String starString, int level) {
        String[] starArray = starString.split(",");
        int completedLevels = 0;

        for (int i = 0; i < starArray.length; i++) {
            if (Integer.parseInt(starArray[i]) > 0) {
                completedLevels++;
            }
        }

        return (completedLevels * 100) / starArray.length;
    }

}
