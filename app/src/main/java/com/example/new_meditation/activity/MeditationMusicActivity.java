package com.example.new_meditation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.new_meditation.Adapter.MediationMusicAdapter;
import com.example.new_meditation.R;

import java.util.ArrayList;
import java.util.List;

public class MeditationMusicActivity extends AppCompatActivity {
    List<Integer> listImage;
    List<Integer> listMusic;
    ArrayList<String> listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_music);
        findViewById(R.id.ll_back).setOnClickListener(v -> {onBackPressed();});

        RecyclerView rvSongList = (RecyclerView) findViewById(R.id.recyclerView);
        rvSongList.setLayoutManager(new GridLayoutManager(this, 2));
        this.listMusic = new ArrayList();
        this.listName = new ArrayList<>();
        this.listImage = new ArrayList();
        this.listMusic.add(Integer.valueOf((int) R.raw.autumn_sky_meditation));
        this.listImage.add(Integer.valueOf((int) R.drawable.ic_sky));
        this.listName.add("Sky");
        this.listMusic.add(Integer.valueOf((int) R.raw.bird_relaxing_mediation));
        this.listImage.add(Integer.valueOf((int) R.drawable.ic_bird));
        this.listName.add("Bird");
        this.listMusic.add(Integer.valueOf((int) R.raw.classic_meditation));
        this.listImage.add(Integer.valueOf((int) R.drawable.ic_classic));
        this.listName.add("Classic");
        this.listMusic.add(Integer.valueOf((int) R.raw.cloud_meditation));
        this.listImage.add(Integer.valueOf((int) R.drawable.ic_cloud));
        this.listName.add("Cloud");
        this.listMusic.add(Integer.valueOf((int) R.raw.water_mediation));
        this.listImage.add(Integer.valueOf((int) R.drawable.ic_water));
        this.listName.add("Water");
        this.listMusic.add(Integer.valueOf((int) R.raw.yoga_relaxing_mediation));
        this.listImage.add(Integer.valueOf((int) R.drawable.ic_yoga));
        this.listName.add("Yoga");
        this.listMusic.add(Integer.valueOf((int) R.raw.rain_solace_yoga));
        this.listImage.add(Integer.valueOf((int) R.drawable.ic_rain));
        this.listName.add("Rain");
        this.listMusic.add(Integer.valueOf((int) R.raw.classic_bell_music));
        this.listImage.add(Integer.valueOf((int) R.drawable.ic_bell));
        this.listName.add("Bell");
        MediationMusicAdapter mediationMusicAdapter = new MediationMusicAdapter(this, this.listMusic, this.listName, this.listImage);
        rvSongList.setAdapter(mediationMusicAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}