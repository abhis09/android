package com.example.new_meditation.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.new_meditation.Database.DatabaseHandler;
import com.example.new_meditation.Model.Downloads;
import com.example.new_meditation.R;
import com.example.new_meditation.SongsManager;
import com.example.new_meditation.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@SuppressLint("WrongConstant")
public class AndroidBuildingMusicPlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {
    ArrayList<Downloads> arr;
    String bgimagename;
    private ImageView btnBack;
    private ImageView btnNext;
    
    public ImageView btnPlay;
    private ImageView btnPlaylist;
    private ImageView btnPrevious;
    ImageView bg_profile;
    
    public ImageView btnRepeat;
    
    public ImageView btnShuffle;
    private ImageView btnadd;
    
    public ImageView btnfav;
    
    public ImageView btnfavfull;
    
    public int currentSongIndex = 0;
    SQLiteDatabase db;
    DatabaseHandler dbh;
    Typeface face;
    
    public boolean isRepeat = false;
    
    public boolean isShuffle = false;
    
    public Handler mHandler = new Handler();
    
    public Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            try {
                long duration = (long) AndroidBuildingMusicPlayerActivity.this.mp.getDuration();
                long currentPosition = (long) AndroidBuildingMusicPlayerActivity.this.mp.getCurrentPosition();
                TextView access$1100 = AndroidBuildingMusicPlayerActivity.this.songTotalDurationLabel;
                access$1100.setText("" + AndroidBuildingMusicPlayerActivity.this.utils.milliSecondsToTimer(duration));
                TextView access$1200 = AndroidBuildingMusicPlayerActivity.this.songTime;
                access$1200.setText("" + AndroidBuildingMusicPlayerActivity.this.utils.milliSecondsToTimerText(duration));
                TextView access$1300 = AndroidBuildingMusicPlayerActivity.this.songCurrentDurationLabel;
                access$1300.setText("" + AndroidBuildingMusicPlayerActivity.this.utils.milliSecondsToTimer(currentPosition));
                AndroidBuildingMusicPlayerActivity.this.songProgressBar.setProgress(AndroidBuildingMusicPlayerActivity.this.utils.getProgressPercentage(currentPosition, duration));
                AndroidBuildingMusicPlayerActivity.this.mHandler.postDelayed(this, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    
    public MediaPlayer mp;
    private int seekBackwardTime = 5000;
    private int seekForwardTime = 5000;
    
    public TextView songCurrentDurationLabel;
    private SongsManager songManager;
    
    public SeekBar songProgressBar;
    
    public TextView songTime;
    
    public TextView songTitleLabel;
    
    public TextView songTotalDurationLabel;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
    String str;
    TextView txtType;
    
    public Utilities utils;

    
    public void BackScreen() {
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            requestWindowFeature(1);
            getWindow().setFlags(1024, 1024);
            setContentView((int) R.layout.player);
            this.face = Typeface.createFromAsset(getAssets(), "fonts/Pathway Gothic One Regular.ttf");
            this.btnPlay = (ImageView) findViewById(R.id.btnPlay);
            this.btnBack = (ImageView) findViewById(R.id.btnBack);
            this.btnNext = (ImageView) findViewById(R.id.btnNext);
            this.btnPrevious = (ImageView) findViewById(R.id.btnPrevious);
            this.btnPlaylist = (ImageView) findViewById(R.id.btnPlaylist);
            this.btnadd = (ImageView) findViewById(R.id.btnadd);
            this.btnfav = (ImageView) findViewById(R.id.btnfav);
            this.btnfavfull = (ImageView) findViewById(R.id.btnfavfull);
            this.bg_profile = (ImageView) findViewById(R.id.bg_profile);
            this.btnRepeat = (ImageView) findViewById(R.id.btnRepeat);
            this.btnShuffle = (ImageView) findViewById(R.id.btnShuffle);
            this.songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
            this.songTitleLabel = (TextView) findViewById(R.id.txtTitle);
            this.txtType = (TextView) findViewById(R.id.txtType);
            this.songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
            this.songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
            this.songTime = (TextView) findViewById(R.id.songTime);
            this.arr = new ArrayList<>();
            this.mp = new MediaPlayer();
            this.songManager = new SongsManager(this);
            this.utils = new Utilities();
            this.btnadd.setVisibility(8);
            this.btnPlaylist.setVisibility(0);
            this.songProgressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.txt_color), PorterDuff.Mode.MULTIPLY);
            this.songProgressBar.setOnSeekBarChangeListener(this);
            this.mp.setOnCompletionListener(this);
            this.dbh = new DatabaseHandler(this);
            this.songProgressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.txt_color), PorterDuff.Mode.MULTIPLY);
            this.db = this.dbh.getReadableDatabase();
            final String stringExtra = getIntent().getStringExtra("item_selected_key");
            this.arr = this.dbh.selectPlaylist(this, stringExtra);

            this.txtType.setText(stringExtra);


            this.btnfav.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        if (AndroidBuildingMusicPlayerActivity.this.dbh.updateRecordsname(AndroidBuildingMusicPlayerActivity.this.songTitleLabel.getText().toString(), 1) != 0) {
                            Toast.makeText(AndroidBuildingMusicPlayerActivity.this, "Marked as Favourite", 0).show();
                            AndroidBuildingMusicPlayerActivity.this.btnfav.setVisibility(8);
                            AndroidBuildingMusicPlayerActivity.this.btnfavfull.setVisibility(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("SHIVA", "onClick___favList__exception: "+e.getMessage());
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                        Log.e("SHIVA", "onClick___fav___throwable: "+throwable.getMessage());
                    }
                }
            });
            this.btnfavfull.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.e("SHIVA", "onClick: "+123);
                    try {
                        Log.e("SHIVA", "onClick: "+123 );
                        if (AndroidBuildingMusicPlayerActivity.this.dbh.updateRecordsname(AndroidBuildingMusicPlayerActivity.this.songTitleLabel.getText().toString(), 0) != 0) {
                            Toast.makeText(AndroidBuildingMusicPlayerActivity.this, "Removed from Favourite", 0).show();
                            AndroidBuildingMusicPlayerActivity.this.btnfav.setVisibility(0);
                            AndroidBuildingMusicPlayerActivity.this.btnfavfull.setVisibility(8);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                        Log.e("SHIVA", "onClick__liked: "+e.getMessage());
                    }
                }
            });
            if (this.arr.size() > 0) {
                playSong(0);
            }
            this.btnPlay.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        if (AndroidBuildingMusicPlayerActivity.this.mp.isPlaying()) {
                            if (AndroidBuildingMusicPlayerActivity.this.mp != null) {
                                AndroidBuildingMusicPlayerActivity.this.mp.pause();
                                AndroidBuildingMusicPlayerActivity.this.btnPlay.setImageResource(R.drawable.play_player);
                            }
                        } else if (AndroidBuildingMusicPlayerActivity.this.mp != null) {
                            AndroidBuildingMusicPlayerActivity.this.mp.start();
                            AndroidBuildingMusicPlayerActivity.this.btnPlay.setImageResource(R.drawable.pause_player);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            this.btnBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AndroidBuildingMusicPlayerActivity.this.onBackPressed();
                }
            });
            this.btnNext.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        boolean unused = AndroidBuildingMusicPlayerActivity.this.isRepeat = false;
                        AndroidBuildingMusicPlayerActivity.this.btnRepeat.setImageResource(R.drawable.ic_repeat);
                        if (AndroidBuildingMusicPlayerActivity.this.currentSongIndex < AndroidBuildingMusicPlayerActivity.this.arr.size() - 1) {
                            AndroidBuildingMusicPlayerActivity.this.playSong(AndroidBuildingMusicPlayerActivity.this.currentSongIndex + 1);
                            int unused2 = AndroidBuildingMusicPlayerActivity.this.currentSongIndex = AndroidBuildingMusicPlayerActivity.this.currentSongIndex + 1;
                            return;
                        }
                        AndroidBuildingMusicPlayerActivity.this.playSong(0);
                        int unused3 = AndroidBuildingMusicPlayerActivity.this.currentSongIndex = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
            this.btnPrevious.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        boolean unused = AndroidBuildingMusicPlayerActivity.this.isRepeat = false;
                        AndroidBuildingMusicPlayerActivity.this.btnRepeat.setImageResource(R.drawable.ic_repeat);
                        if (AndroidBuildingMusicPlayerActivity.this.currentSongIndex > 0) {
                            AndroidBuildingMusicPlayerActivity.this.playSong(AndroidBuildingMusicPlayerActivity.this.currentSongIndex - 1);
                            int unused2 = AndroidBuildingMusicPlayerActivity.this.currentSongIndex = AndroidBuildingMusicPlayerActivity.this.currentSongIndex - 1;
                            return;
                        }
                        AndroidBuildingMusicPlayerActivity.this.playSong(AndroidBuildingMusicPlayerActivity.this.arr.size() - 1);
                        int unused3 = AndroidBuildingMusicPlayerActivity.this.currentSongIndex = AndroidBuildingMusicPlayerActivity.this.arr.size() - 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
            this.btnRepeat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        if (AndroidBuildingMusicPlayerActivity.this.isRepeat) {
                            boolean unused = AndroidBuildingMusicPlayerActivity.this.isRepeat = false;
                            Toast.makeText(AndroidBuildingMusicPlayerActivity.this.getApplicationContext(), "Repeat is OFF", 0).show();
                            AndroidBuildingMusicPlayerActivity.this.btnRepeat.setImageResource(R.drawable.ic_repeat);
                            return;
                        }
                        boolean unused2 = AndroidBuildingMusicPlayerActivity.this.isRepeat = true;
                        Toast.makeText(AndroidBuildingMusicPlayerActivity.this.getApplicationContext(), "Repeat is ON", 0).show();
                        boolean unused3 = AndroidBuildingMusicPlayerActivity.this.isShuffle = false;
                        AndroidBuildingMusicPlayerActivity.this.btnRepeat.setImageResource(R.drawable.ic_repeat_blue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            this.btnShuffle.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        if (AndroidBuildingMusicPlayerActivity.this.isShuffle) {
                            boolean unused = AndroidBuildingMusicPlayerActivity.this.isShuffle = false;
                            Toast.makeText(AndroidBuildingMusicPlayerActivity.this.getApplicationContext(), "Shuffle is OFF", 0).show();
                            AndroidBuildingMusicPlayerActivity.this.btnShuffle.setImageResource(R.drawable.ic_shuffle);
                            return;
                        }
                        boolean unused2 = AndroidBuildingMusicPlayerActivity.this.isShuffle = true;
                        Toast.makeText(AndroidBuildingMusicPlayerActivity.this.getApplicationContext(), "Shuffle is ON", 0).show();
                        boolean unused3 = AndroidBuildingMusicPlayerActivity.this.isRepeat = false;
                        AndroidBuildingMusicPlayerActivity.this.btnShuffle.setImageResource(R.drawable.ic_shuffle_blue);
                        AndroidBuildingMusicPlayerActivity.this.btnRepeat.setImageResource(R.drawable.ic_repeat);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            this.btnPlaylist.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this.getApplicationContext(), PlayListActivity.class);
                        intent.putExtra("item_selected_key", stringExtra);
                        AndroidBuildingMusicPlayerActivity.this.startActivityForResult(intent, 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == 100) {
            this.currentSongIndex = intent.getExtras().getInt("songIndex");
//            String img  = intent.getStringExtra("imageKey");
            try {
                playSong(this.currentSongIndex);
//                Glide.with(this).load(img).centerCrop().into(bg_profile);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }


    public void playSong(int i) throws Throwable {
        try {
            Glide.with(this).load(arr.get(i).getImageurl()).centerCrop().into(bg_profile);
            if (this.arr.size() >= 0) {
                this.mp.reset();
                try {
                    this.mp.setDataSource(this.arr.get(i).getDname());
                    this.mp.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.mp.start();
                String sname = this.arr.get(i).getSname();
                this.songTitleLabel.setText(sname);
                if (this.dbh.getstatusSongname(sname) == 0) {
                    this.btnfav.setVisibility(0);
                    this.btnfavfull.setVisibility(8);
                } else {
                    this.btnfavfull.setVisibility(0);
                    this.btnfav.setVisibility(8);
                }
                this.btnPlay.setImageResource(R.drawable.pause_player);
                this.songProgressBar.setProgress(0);
                this.songProgressBar.setMax(100);
                updateProgressBar();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void updateProgressBar() {
        this.mHandler.postDelayed(this.mUpdateTimeTask, 100);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        this.mHandler.removeCallbacks(this.mUpdateTimeTask);
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        try {
            this.mHandler.removeCallbacks(this.mUpdateTimeTask);
            this.mp.seekTo(this.utils.progressToTimer(seekBar.getProgress(), this.mp.getDuration()));
            updateProgressBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        try {
            if (this.isRepeat) {
                playSong(this.currentSongIndex);
            } else if (this.isShuffle) {
                this.currentSongIndex = new Random().nextInt(((this.arr.size() - 1) - 0) + 1) + 0;
                playSong(this.currentSongIndex);
            } else if (this.currentSongIndex < this.arr.size() - 1) {
                playSong(this.currentSongIndex + 1);
                this.currentSongIndex++;
            } else {
                playSong(0);
                this.currentSongIndex = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    
    public void onPause() {
        super.onPause();
    }

    
    public void onResume() {
        super.onResume();
    }

    public void onBackPressed() {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setTitle("Meditation sleep music");
            dialog.setCancelable(false);
            TextView textView = (TextView) dialog.findViewById(R.id.alert_msg);
            TextView textView2 = (TextView) dialog.findViewById(R.id.alert_title);
            textView.setText("Are You Sure to Leave This Running Meditation Session?");
            textView2.setText("Exit Page");
            textView.setTypeface(this.face);
            textView2.setTypeface(this.face);
            ((Button) dialog.findViewById(R.id.customDialogOk)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                    if (AndroidBuildingMusicPlayerActivity.this.mHandler != null) {
                        AndroidBuildingMusicPlayerActivity.this.mHandler.removeCallbacks(AndroidBuildingMusicPlayerActivity.this.mUpdateTimeTask);
                        if (AndroidBuildingMusicPlayerActivity.this.mp != null) {
                            AndroidBuildingMusicPlayerActivity.this.mp.release();
                        }
                    }
                    AndroidBuildingMusicPlayerActivity.this.BackPressedAd();
                    AndroidBuildingMusicPlayerActivity.super.onBackPressed();
                }
            });
            ((Button) dialog.findViewById(R.id.customDialogCancel)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if (!isFinishing()) {
                dialog.show();
            }
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            this.mp.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void BackPressedAd() {
                BackScreen();
    }


}
