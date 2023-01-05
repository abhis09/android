package com.example.new_meditation.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import com.example.new_meditation.MainActivity;
import com.example.new_meditation.R;

import java.util.ArrayList;

public class PreMain extends AppCompatActivity {
    ImageView menu, start_meditation, playlist;
    DrawerLayout mdrawerlayout;
    RelativeLayout share, rate, privacy,rl_relax_music,rl_meditation;
    ArrayList<String> navigation_items;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash2);
        menu = findViewById(R.id.menu);
        share = findViewById(R.id.rl2);
        rate = findViewById(R.id.rl3);
        privacy = findViewById(R.id.rl4);
        rl_relax_music = findViewById(R.id.rl_relax_music);
        rl_meditation = findViewById(R.id.rl_meditation);

//        start_meditation = findViewById(R.id.start_meditation);
        playlist = findViewById(R.id.playlist);
        mdrawerlayout = findViewById(R.id.mdrawerlayout);
        menu.setOnClickListener(v -> {
            if (!mdrawerlayout.isDrawerOpen(Gravity.START))
                mdrawerlayout.openDrawer(Gravity.START);
            else {
                mdrawerlayout.closeDrawer(Gravity.END);
            }
        });
        share.setOnClickListener(v -> {
            share();
        });
        rate.setOnClickListener(v -> {
            rating();
        });
        privacy.setOnClickListener(v -> {

        });
        rl_relax_music.setOnClickListener(v -> {
            Intent intent = new Intent(PreMain.this, RelaxMusicActivity.class);
            startActivity(intent);
        });
        rl_meditation.setOnClickListener(v -> {
            Intent intent = new Intent(PreMain.this, MeditationMusicActivity.class);
            startActivity(intent);
        });
        playlist.setOnClickListener(v -> {
            Intent intent = new Intent(PreMain.this, PlayListActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint("WrongConstant")
    private void rating() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(this, "You don't have Google Play installed", 1).show();
        }
    }

    private void share() {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT",
                    "Sleep Music - Get instant sleep after " + "listening Sleep Music Meditation " +
                            "& Relax Melodies\n★ High quality soothing " + "sounds\n★ Smart, " +
                            "simple and beautiful design\n★ Smart timer and app turns " + "off " +
                            "automatically\n★ Better uninterrupted your deep sleep and " + "relax" +
                            "\n\nhttps://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            Log.d("", e.toString());
        }
    }
}