package com.example.new_meditation.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.new_meditation.Adapter.RecyclerViewAdapter;
import com.example.new_meditation.CircularSeekBar;
import com.example.new_meditation.Database.DatabaseHandler;
import com.example.new_meditation.Model.Downloads;
import com.example.new_meditation.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@SuppressLint("WrongConstant")
public class FavouriteMusicPlayerActivity extends AppCompatActivity {
    boolean adShow = false;
    AudioManager audioManager;
    ImageView bgImage, profile_image;
    String bgimagename;
    ImageView btnBack;
    private ImageView btnPlay;
    ImageView btnadd;
    ImageView btnfav;
    ImageView btnfavfull;
    SQLiteDatabase db;
    SQLiteDatabase dbb;
    DatabaseHandler dbh;
    Typeface face;
    File fileExists;
    String fileName;
    Uri filePath;
    Handler handler;
    String id;
    ImageView imgBtnPlay;
    boolean isPlaying = false;
    private boolean isRepeat = false;
    LinearLayout lnbtn;
    Toolbar mToolbar;
    MediaPlayer mediaPlayer;
    Runnable moveSeekBarThread = new Runnable() {
        public void run() {
            try {
                if (FavouriteMusicPlayerActivity.this.mediaPlayer != null) {
                    try {
                        int currentPosition = FavouriteMusicPlayerActivity.this.mediaPlayer.getCurrentPosition();
                        FavouriteMusicPlayerActivity.this.seekBar.setMax(FavouriteMusicPlayerActivity.this.mediaPlayer.getDuration());
                        FavouriteMusicPlayerActivity.this.seekBar.setProgress(FavouriteMusicPlayerActivity.this.mediaPlayer.getCurrentPosition());
                        FavouriteMusicPlayerActivity.this.seekBar.getProgress();
                        long j = (long) currentPosition;
                        FavouriteMusicPlayerActivity.this.time = String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)))});
                        FavouriteMusicPlayerActivity.this.seekArcProgressTextView.setText(FavouriteMusicPlayerActivity.this.time);
                        if (!FavouriteMusicPlayerActivity.this.stopSeekbar) {
                            FavouriteMusicPlayerActivity.this.handler.postDelayed(this, 100);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    };
    TextView seekArcProgressTextView;
    CircularSeekBar seekBar;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
    SeekBar speakerSeekbar;
    boolean stopSeekbar = false;
    Integer str;
    String time;
    String title;
    TextView txtComplete;
    TextView txtDuration;
    TextView txtTitle;
    TextView txtType;

    @SuppressLint({"ResourceType"})
    @RequiresApi(api = 16)
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.view_mediaplayer_fragment);
        this.mToolbar = findViewById(R.id.my_toolbar);
        this.btnadd = findViewById(R.id.btnadd);
        this.btnfav = findViewById(R.id.btnfav);
        this.btnfavfull = findViewById(R.id.btnfavfull);
        this.profile_image = findViewById(R.id.profile_image);
        this.btnBack = findViewById(R.id.btnBack);
        this.face = Typeface.createFromAsset(getAssets(), "fonts/Pathway Gothic One Regular.ttf");
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(externalFilesDir.getAbsolutePath() + "/sleep_music");
        file.mkdirs();
        file.listFiles();
        Intent intent = getIntent();
        this.fileName = intent.getStringExtra(DatabaseHandler.KEY_OLD_SONG);
        this.str = Integer.valueOf(intent.getIntExtra("item_selected_key", 0));
        String str_img = intent.getStringExtra("image_key");
        Glide.with(this).load(str_img).into(profile_image);
        this.title = intent.getStringExtra(DatabaseHandler.KEY_NEW_SONG);
        this.dbh = new DatabaseHandler(getApplicationContext());
        this.db = this.dbh.getReadableDatabase();
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FavouriteMusicPlayerActivity.this.onBackPressed();
            }
        });
        this.imgBtnPlay = (ImageView) findViewById(R.id.btn_play);
        this.bgImage = (ImageView) findViewById(R.id.bgImage);
        this.seekBar = (CircularSeekBar) findViewById(R.id.picker);
        this.txtTitle = (TextView) findViewById(R.id.txtTitle);
        this.txtType = (TextView) findViewById(R.id.txtType);
        this.txtComplete = (TextView) findViewById(R.id.txtComplete);
        this.txtDuration = (TextView) findViewById(R.id.txtDuration);
        this.seekArcProgressTextView = (TextView) findViewById(R.id.seekArcProgressTextView);
        this.speakerSeekbar = (SeekBar) findViewById(R.id.materialSeekBar);
        this.lnbtn = (LinearLayout) findViewById(R.id.lnbtn);
        this.audioManager = (AudioManager) getSystemService("audio");
        this.speakerSeekbar.setMax(this.audioManager.getStreamMaxVolume(3));
        this.speakerSeekbar.setProgress(this.audioManager.getStreamVolume(3));
        this.speakerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                FavouriteMusicPlayerActivity.this.audioManager.setStreamVolume(3, i, 0);
            }
        });
        try {
            this.handler = new Handler();
            this.txtTitle.setText(this.title);
//            this.txtType.setText("Favourites");
            this.txtTitle.setTypeface(this.face);
            this.txtDuration.setTypeface(this.face);
            if (this.dbh.getstatus(this.fileName) == 0) {
                this.btnfav.setVisibility(0);
                this.btnfavfull.setVisibility(8);
            } else {
                this.btnfav.setVisibility(8);
                this.btnfavfull.setVisibility(0);
            }
            this.seekArcProgressTextView.setTypeface(this.face);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        try {
            this.filePath = Uri.parse(this.fileName);
            this.imgBtnPlay.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        if (FavouriteMusicPlayerActivity.this.mediaPlayer != null) {
                            FavouriteMusicPlayerActivity.this.mediaPlayer.pause();
                            if (FavouriteMusicPlayerActivity.this.isPlaying) {
                                FavouriteMusicPlayerActivity.this.pause();
                            } else {
                                FavouriteMusicPlayerActivity.this.play();
                            }
                            FavouriteMusicPlayerActivity.this.isPlaying = !FavouriteMusicPlayerActivity.this.isPlaying;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
//                this.bgImage.setBackground(getResources().getDrawable(R.drawable.playlist_background));
                this.fileExists = new File(file, this.fileName);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                playMusic();
                long duration = (long) this.mediaPlayer.getDuration();
                this.txtDuration.setText(String.format("%02d min, %02d sec", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(duration)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)))}));
                this.txtDuration.setTypeface(this.face);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.seekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            public void onStartTrackingTouch(CircularSeekBar circularSeekBar) {
            }

            public void onStopTrackingTouch(CircularSeekBar circularSeekBar) {
            }

            public void onProgressChanged(CircularSeekBar circularSeekBar, int i, boolean z) {
                try {
                    if (!FavouriteMusicPlayerActivity.this.mediaPlayer.isPlaying()) {
                        if (FavouriteMusicPlayerActivity.this.mediaPlayer == null) {
                            if (FavouriteMusicPlayerActivity.this.mediaPlayer == null) {
                                try {
                                    Toast.makeText(FavouriteMusicPlayerActivity.this.getApplicationContext(), "Media is not running", 0).show();
                                    FavouriteMusicPlayerActivity.this.seekBar.setProgress(0);
                                    return;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return;
                                }
                            } else {
                                return;
                            }
                        }
                    }
                    if (z) {
                        try {
                            FavouriteMusicPlayerActivity.this.mediaPlayer.seekTo(i);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (Exception unused) {
                    FavouriteMusicPlayerActivity.this.seekBar.setEnabled(false);
                }
            }
        });
        this.btnfav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    if (FavouriteMusicPlayerActivity.this.dbh.updateRecord(FavouriteMusicPlayerActivity.this.fileName, 1) != 0) {
                        FavouriteMusicPlayerActivity.this.btnfav.setVisibility(8);
                        FavouriteMusicPlayerActivity.this.btnfavfull.setVisibility(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        this.btnfavfull.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    if (FavouriteMusicPlayerActivity.this.dbh.updateRecord(FavouriteMusicPlayerActivity.this.fileName, 0) != 0) {
                        FavouriteMusicPlayerActivity.this.btnfav.setVisibility(0);
                        FavouriteMusicPlayerActivity.this.btnfavfull.setVisibility(8);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        this.btnadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new ArrayList();
                ArrayList<Downloads> loadplaylist = null;
                try {
                    loadplaylist = FavouriteMusicPlayerActivity.this.dbh.loadplaylist();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                loadplaylist.add(loadplaylist.size(), new Downloads("Create Playlist", "", "", 0, 0, 0));
                if (loadplaylist.size() > 0) {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FavouriteMusicPlayerActivity.this);
                        LayoutInflater layoutInflater = (LayoutInflater) FavouriteMusicPlayerActivity.this.getSystemService("layout_inflater");
                        View inflate = layoutInflater.inflate(R.layout.listview, (ViewGroup) null);
                        if (inflate == null) {
                            inflate = layoutInflater.inflate(R.layout.listview, (ViewGroup) null);
                        }
                        builder.setView(inflate);
                        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        ListView listView = (ListView) inflate.findViewById(R.id.mylistview);
                        final AlertDialog create = builder.create();
                        listView.setAdapter(new RecyclerViewAdapter.MyAdapter(FavouriteMusicPlayerActivity.this, R.layout.listview_item, loadplaylist));
                        ArrayList<Downloads> finalLoadplaylist = loadplaylist;
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                                try {
                                    if (((Downloads) finalLoadplaylist.get(i)).getDname().equals("Create Playlist")) {
                                        FavouriteMusicPlayerActivity.this.showChangeLangDialog(FavouriteMusicPlayerActivity.this.str.intValue());
                                    } else {
                                        try {
                                            String str = bgimagename;
                                            ((Downloads) finalLoadplaylist.get(i)).getDname();
                                            FavouriteMusicPlayerActivity.this.dbh.getplaylistid();
                                            FavouriteMusicPlayerActivity.this.dbh.savePlaylist(((Downloads) finalLoadplaylist.get(i)).getPlaylistid(), FavouriteMusicPlayerActivity.this.dbh.getdname(FavouriteMusicPlayerActivity.this.fileName));
                                            Toast.makeText(FavouriteMusicPlayerActivity.this, "Added to Playlist", 0).show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } catch (Throwable throwable) {
                                            throwable.printStackTrace();
                                        }
                                    }
                                    create.cancel();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        });
                        create.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(FavouriteMusicPlayerActivity.this, "Create Playlist first", 0).show();
                }
            }
        });
    }

    public void pause() {
        try {
            if (this.mediaPlayer != null) {
                try {
                    this.mediaPlayer.seekTo(this.mediaPlayer.getCurrentPosition());
                    this.handler.removeCallbacks(this.moveSeekBarThread);
                    this.handler.postDelayed(this.moveSeekBarThread, 100);
                    this.mediaPlayer.start();
                    this.imgBtnPlay.setImageResource(R.drawable.pause);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void play() {
        try {
            if (this.mediaPlayer != null) {
                try {
                    this.mediaPlayer.pause();
                    this.imgBtnPlay.setImageResource(R.drawable.play_player);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void playMusic() {
        try {
            this.mediaPlayer = new MediaPlayer();
            this.mediaPlayer.setDataSource(String.valueOf(this.fileExists));
            this.mediaPlayer.setAudioStreamType(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.mediaPlayer.prepare();
            this.mediaPlayer.start();
            this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer mediaPlayer) {
                    try {
                        mediaPlayer.stop();
                        if (!FavouriteMusicPlayerActivity.this.stopSeekbar) {
                            int i = 0;
                            while (i < FavouriteMusicPlayerActivity.this.lnbtn.getChildCount()) {
                                try {
                                    FavouriteMusicPlayerActivity.this.lnbtn.getChildAt(i).setEnabled(false);
                                    i++;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            FavouriteMusicPlayerActivity.this.txtComplete.setVisibility(0);
                            FavouriteMusicPlayerActivity.this.adShow = true;
                        }
                        FavouriteMusicPlayerActivity.this.stopSeekbar = true;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.handler.removeCallbacks(this.moveSeekBarThread);
            this.handler.postDelayed(this.moveSeekBarThread, 100);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void showChangeLangDialog(final int i) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_playlist);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            final EditText editText = (EditText) dialog.findViewById(R.id.edit1);
            ((TextView) dialog.findViewById(R.id.txttitle)).setText("Please input Playlist name\t\t\t\t\t\t\t");
            ((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String obj = editText.getText().toString();
                    if (obj.isEmpty() || obj.trim().length() == 0 || obj.equals("") || obj == null) {
                        Toast.makeText(FavouriteMusicPlayerActivity.this, "Enter playlist name", 0).show();
                    } else {
                        FavouriteMusicPlayerActivity.this.dbh.addplaylist(obj);
                        int i = 0;
                        try {
                            i = FavouriteMusicPlayerActivity.this.dbh.getplaylistid();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                        if (i == 0 || i == 0) {
                            FavouriteMusicPlayerActivity.this.dbh.delplaylist(i);
                        } else {
                            try {
                                FavouriteMusicPlayerActivity.this.dbh.savePlaylist(i, i);
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                            Toast.makeText(FavouriteMusicPlayerActivity.this, "Added to Playlist", 0).show();
                        }
                    }
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });
            ((Button) dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    if (FavouriteMusicPlayerActivity.this.handler != null) {
                        try {
                            FavouriteMusicPlayerActivity.this.handler.removeCallbacks(FavouriteMusicPlayerActivity.this.moveSeekBarThread);
                            try {
                                if (FavouriteMusicPlayerActivity.this.mediaPlayer != null) {
                                    FavouriteMusicPlayerActivity.this.mediaPlayer.release();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            FavouriteMusicPlayerActivity.this.seekArcProgressTextView.setText("00:00");
//                            if (MainActivity.adCount % 3 != 0) {
//                                if (!FavouriteMusicPlayerActivity.this.adShow) {
//                                    FavouriteMusicPlayerActivity.this.finish();
//                                    return;
//                                }
//                            }
                            FavouriteMusicPlayerActivity.this.BackPressedAd();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
            ((Button) dialog.findViewById(R.id.customDialogCancel)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        dialog.dismiss();
                        FavouriteMusicPlayerActivity.this.pause();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if (!isFinishing()) {
                dialog.show();
            }
        } catch (Exception e) {
            try {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    public void BackScreen() {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 24) {
            try {
                this.speakerSeekbar.setProgress(this.speakerSeekbar.getProgress() + 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else if (i != 25) {
            return super.onKeyDown(i, keyEvent);
        } else {
            try {
                this.speakerSeekbar.setProgress(this.speakerSeekbar.getProgress() - 1);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return true;
        }
    }

    public void onDestroy() {
        try {
            if (this.mediaPlayer != null) {
                try {
                    this.mediaPlayer.release();
                    this.handler.removeCallbacks(this.moveSeekBarThread);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        super.onDestroy();
    }


    public void BackPressedAd() {
        BackScreen();
    }


}
