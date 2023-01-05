package com.example.new_meditation.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.new_meditation.R;
import com.example.new_meditation.activity.AndroidBuildingMusicPlayerActivity;
import com.example.new_meditation.activity.Player;

import java.util.List;

public class MediationMusicAdapter extends RecyclerView.Adapter<MediationMusicAdapter.AdapterViewHolder> {
    Activity activity;
    List<Integer> listImage;
    List<Integer> listMusic;
    List<String> listName;

    public MediationMusicAdapter(Activity activity, List<Integer> listMusic, List<String> listName, List<Integer> listImage) {
        this.activity = activity;
        this.listMusic = listMusic;
        this.listName = listName;
        this.listImage = listImage;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String name = this.listName.get(position);
        holder.textView.setText(name);
        holder.imageView.setImageResource(this.listImage.get(position).intValue());
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Adapter.MediationMusicAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(activity, Player.class);
                intent.putExtra("musicFile", listMusic.get(position));
                intent.putExtra("musicName", listName.get(position));
                intent.putExtra("musicImage", listImage.get(position));
                intent.putExtra("musicImageBlur", listImage.get(position));
//                intent.putExtra("item_selected_key",name);
//                Log.e("SHIVA", "onClick: "+name);
                MediationMusicAdapter.this.activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listMusic.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.itemImg);
            this.textView = (TextView) itemView.findViewById(R.id.itemTitle);
        }
    }
}
