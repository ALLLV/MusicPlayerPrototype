package com.example.musicapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<AudioModel> songsList;
    Context context;

    public MyAdapter(ArrayList<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_row_item, parent, false);
        return new MyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AudioModel songData = songsList.get(position);
        holder.songName.setText(songData.getTitle());
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView songName;
        TextView duration;
        ImageView artwork;

        public MyViewHolder(View itemView) {
            super(itemView);

            songName = itemView.findViewById(R.id.titleView);
            duration = itemView.findViewById(R.id.durationView);
            artwork = itemView.findViewById(R.id.artworkView);

        }
    }
}
