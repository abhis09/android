package com.example.new_meditation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_meditation.R;

public class Player extends AppCompatActivity {
    int file;
    int image;
    int imageBlur;
    MediaPlayer mediaPlayer;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        findViewById(R.id.ll_back).setOnClickListener(v -> {onBackPressed();});

        Intent intent = getIntent();
        this.file = intent.getIntExtra("musicFile", 0);
        this.image = intent.getIntExtra("musicImage", 0);
        this.imageBlur = intent.getIntExtra("musicImageBlur", 0);
        this.name = intent.getStringExtra("musicName");
        TextView txtView = (TextView) findViewById(R.id.itemTite);
        txtView.setText(this.name + "  Meditation");
        ImageView bg = (ImageView) findViewById(R.id.bg);
        bg.setImageResource(this.imageBlur);
        MediaPlayer create = MediaPlayer.create(this, this.file);
        this.mediaPlayer = create;
        create.start();
        this.mediaPlayer.setLooping(true);
        final ImageView imagePlayPause = (ImageView) findViewById(R.id.ic_play_pause);
        imagePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Player.this.mediaPlayer.isPlaying()) {
                        Player.this.mediaPlayer.pause();
                        imagePlayPause.setImageResource(R.drawable.play_player);
                    } else {
                        Player.this.mediaPlayer.start();
                        imagePlayPause.setImageResource(R.drawable.pause_player);
                        Player.this.mediaPlayer.setLooping(true);
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    public void backPress(View view) {
        this.mediaPlayer.stop();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.mediaPlayer.stop();
        finish();
    }
    }