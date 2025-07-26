package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {

    private List<RankingItem> rankingList;

    public RankingAdapter(List<RankingItem> rankingList) {
        this.rankingList = rankingList;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        RankingItem rankingItem = rankingList.get(position);
        holder.rankTextView.setText(rankingItem.getRank());
        holder.nameTextView.setText(rankingItem.getName());
        holder.pointsTextView.setText(rankingItem.getPoints());
        holder.progressTextView.setText(rankingItem.getProgress());
        holder.accuracyTextView.setText(rankingItem.getAccuracy());
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    public static class RankingViewHolder extends RecyclerView.ViewHolder {
        public TextView rankTextView, nameTextView, pointsTextView, progressTextView, accuracyTextView;

        public RankingViewHolder(View itemView) {
            super(itemView);
            rankTextView = itemView.findViewById(R.id.rankTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            pointsTextView = itemView.findViewById(R.id.pointsTextView);
            progressTextView = itemView.findViewById(R.id.progressTextView);
            accuracyTextView = itemView.findViewById(R.id.accuracyTextView);
        }
    }
}

