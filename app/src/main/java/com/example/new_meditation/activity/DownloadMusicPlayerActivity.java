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
import android.util.Log;
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
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.daimajia.numberprogressbar.NumberProgressBar;
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
public class DownloadMusicPlayerActivity extends AppCompatActivity {
    boolean adShow = false;
    AudioManager audioManager;
    ImageView bgImage;
    String bgimagename;
    ImageView btnBack;
    ImageView btnadd;
    ImageView btnfav;
    ImageView btnfavfull;
    ImageView profile_image;
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
    LinearLayout lnbtn;
    Toolbar mToolbar;
    MediaPlayer mediaPlayer;
    TextView seekArcProgressTextView;
    CircularSeekBar seekBar;
    SeekBar speakerSeekbar;
    boolean stopSeekbar = false;
    Integer str;
    String time;
    Runnable moveSeekBarThread = new Runnable() {
        public void run() {
            try {
                if (DownloadMusicPlayerActivity.this.mediaPlayer != null) {
                    try {
                        int currentPosition =
                                DownloadMusicPlayerActivity.this.mediaPlayer.getCurrentPosition();
                        DownloadMusicPlayerActivity.this.seekBar.setMax(DownloadMusicPlayerActivity.this.mediaPlayer.getDuration());
                        DownloadMusicPlayerActivity.this.seekBar.setProgress(DownloadMusicPlayerActivity.this.mediaPlayer.getCurrentPosition());
                        DownloadMusicPlayerActivity.this.seekBar.getProgress();
                        long j = (long) currentPosition;
                        DownloadMusicPlayerActivity.this.time = String.format("%02d:%02d",
                                new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j)),
                                        Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)))});
                        DownloadMusicPlayerActivity.this.seekArcProgressTextView.setText(DownloadMusicPlayerActivity.this.time);
                        if (!DownloadMusicPlayerActivity.this.stopSeekbar) {
                            DownloadMusicPlayerActivity.this.handler.postDelayed(this, 100);
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
    String title;
    TextView txtComplete;
    TextView txtDuration;
    TextView txtTitle;
    TextView txtType;
    private NumberProgressBar bnp;
    private ImageView btnPlay;
    private Button cancle;
    private TextView header;
    private boolean isRepeat = false;
    private TextView progrss;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<>();

    @SuppressLint("ResourceType")
    @RequiresApi(api = 16)
    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            requestWindowFeature(1);
            getWindow().setFlags(1024, 1024);
            setContentView((int) R.layout.view_mediaplayer_fragment);
            this.mToolbar = findViewById(R.id.my_toolbar);
            this.mToolbar.setBackgroundColor(getResources().getColor(17170445));
            this.btnadd = (ImageView) findViewById(R.id.btnadd);
            this.btnfav = (ImageView) findViewById(R.id.btnfav);
            this.btnfavfull = (ImageView) findViewById(R.id.btnfavfull);
            this.profile_image = (ImageView) findViewById(R.id.profile_image);
            this.btnBack = (ImageView) findViewById(R.id.btnBack);
            this.face = Typeface.createFromAsset(getAssets(), "fonts/Pathway Gothic One Regular.ttf");
            File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
            File file = new File(externalFilesDir.getAbsolutePath() + "/sleep_music");
            file.mkdirs();
            file.listFiles();
            Intent intent = getIntent();
            this.fileName = intent.getStringExtra(DatabaseHandler.KEY_OLD_SONG);
            this.str = Integer.valueOf(intent.getIntExtra("item_selected_key", 0));
            this.title = intent.getStringExtra(DatabaseHandler.KEY_NEW_SONG);
            this.dbh = new DatabaseHandler(getApplicationContext());
            this.db = this.dbh.getReadableDatabase();
            this.btnBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    DownloadMusicPlayerActivity.this.onBackPressed();
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
                    DownloadMusicPlayerActivity.this.audioManager.setStreamVolume(3, i, 0);
                }
            });
            try {
                this.handler = new Handler();
                this.txtTitle.setText(this.title);
                this.txtType.setText("Downloads");
                this.txtTitle.setTypeface(this.face);
                this.txtDuration.setTypeface(this.face);
                int i = this.dbh.getstatus(this.fileName);
                if (i == 0) {
                    Log.i("hfvhhsdhfv", "onCreate_status2: " + i);
                    this.btnfav.setVisibility(0);
                    this.btnfavfull.setVisibility(8);
                } else {
                    this.btnfavfull.setVisibility(0);
                    this.btnfav.setVisibility(8);
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
                            if (DownloadMusicPlayerActivity.this.mediaPlayer != null) {
                                DownloadMusicPlayerActivity.this.mediaPlayer.pause();
                                if (DownloadMusicPlayerActivity.this.isPlaying) {
                                    DownloadMusicPlayerActivity.this.pause();
                                } else {
                                    DownloadMusicPlayerActivity.this.play();
                                }
                                DownloadMusicPlayerActivity.this.isPlaying =
                                        !DownloadMusicPlayerActivity.this.isPlaying;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                try {
                    RequestManager with = Glide.with((FragmentActivity) this);
                    with.load(bgimagename).fitCenter().centerCrop().into(profile_image);
                    //                    this.bgImage.setBackground(getResources().getDrawable(R
                    //                    .drawable.playlist_background));
                    this.fileExists = new File(file, this.fileName);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    playMusic();
                    long duration = (long) this.mediaPlayer.getDuration();
                    this.txtDuration.setText(String.format("%02d min, %02d sec",
                            new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(duration)),
                                    Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)))}));
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
                        if (!DownloadMusicPlayerActivity.this.mediaPlayer.isPlaying()) {
                            if (DownloadMusicPlayerActivity.this.mediaPlayer == null) {
                                if (DownloadMusicPlayerActivity.this.mediaPlayer == null) {
                                    try {
                                        Toast.makeText(DownloadMusicPlayerActivity.this.getApplicationContext(), "Media is not running", 0).show();
                                        DownloadMusicPlayerActivity.this.seekBar.setProgress(0);
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
                                DownloadMusicPlayerActivity.this.mediaPlayer.seekTo(i);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    } catch (Exception unused) {
                        DownloadMusicPlayerActivity.this.seekBar.setEnabled(false);
                    }
                }
            });
            this.btnfav.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    try {
                        int updateRecord =
                                DownloadMusicPlayerActivity.this.dbh.updateRecord(DownloadMusicPlayerActivity.this.fileName, 1);
                        Log.i("hfvhhsdhfv", "onCreate_status1: " + updateRecord);
                        if (updateRecord != 0) {
                            DownloadMusicPlayerActivity.this.btnfav.setVisibility(8);
                            DownloadMusicPlayerActivity.this.btnfavfull.setVisibility(0);
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
                        int updateRecord =
                                DownloadMusicPlayerActivity.this.dbh.updateRecord(DownloadMusicPlayerActivity.this.fileName, 0);
                        Log.i("hfvhhsdhfv", "onCreate_status: " + updateRecord);
                        if (updateRecord != 0) {
                            DownloadMusicPlayerActivity.this.btnfav.setVisibility(0);
                            DownloadMusicPlayerActivity.this.btnfavfull.setVisibility(8);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            });
            this.btnadd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    new ArrayList();
                    ArrayList<Downloads> loadplaylist = null;
                    try {
                        loadplaylist = DownloadMusicPlayerActivity.this.dbh.loadplaylist();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                    loadplaylist.add(loadplaylist.size(), new Downloads("Create Playlist", "", ""
                            , 0, 0, 0));
                    if (loadplaylist.size() > 0) {
                        try {
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(DownloadMusicPlayerActivity.this);
                            LayoutInflater layoutInflater =
                                    (LayoutInflater) DownloadMusicPlayerActivity.this.getSystemService("layout_inflater");
                            View inflate = layoutInflater.inflate(R.layout.listview,
                                    (ViewGroup) null);
                            if (inflate == null) {
                                inflate = layoutInflater.inflate(R.layout.listview,
                                        (ViewGroup) null);
                            }
                            builder.setView(inflate);
                            builder.setNegativeButton((CharSequence) "Cancel",
                                    (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                            ListView listView = (ListView) inflate.findViewById(R.id.mylistview);
                            final AlertDialog create = builder.create();
                            listView.setAdapter(new RecyclerViewAdapter.MyAdapter(DownloadMusicPlayerActivity.this, R.layout.listview_item, loadplaylist));
                            ArrayList<Downloads> finalLoadplaylist = loadplaylist;
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> adapterView, View view,
                                                        int i, long j) {
                                    try {
                                        if (((Downloads) finalLoadplaylist.get(i)).getDname().equals("Create Playlist")) {
                                            DownloadMusicPlayerActivity.this.showChangeLangDialog(DownloadMusicPlayerActivity.this.str.intValue());
                                        } else {
                                            try {
                                                ((Downloads) finalLoadplaylist.get(i)).getDname();
                                                DownloadMusicPlayerActivity.this.dbh.getplaylistid();
                                                DownloadMusicPlayerActivity.this.dbh.savePlaylist(((Downloads) finalLoadplaylist.get(i)).getPlaylistid(), DownloadMusicPlayerActivity.this.dbh.getdname(DownloadMusicPlayerActivity.this.fileName));
                                                Toast.makeText(DownloadMusicPlayerActivity.this,
                                                        "Added to Playlist", 0).show();
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
                        Toast.makeText(DownloadMusicPlayerActivity.this, "Create Playlist first",
                                0).show();
                    }
                }
            });
        } catch (Exception e5) {
            e5.printStackTrace();
        }
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
                        if (!DownloadMusicPlayerActivity.this.stopSeekbar) {
                            int i = 0;
                            while (i < DownloadMusicPlayerActivity.this.lnbtn.getChildCount()) {
                                try {
                                    DownloadMusicPlayerActivity.this.lnbtn.getChildAt(i).setEnabled(false);
                                    i++;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            DownloadMusicPlayerActivity.this.txtComplete.setVisibility(0);
                            DownloadMusicPlayerActivity.this.adShow = true;
                        }
                        DownloadMusicPlayerActivity.this.stopSeekbar = true;
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
            ((TextView) dialog.findViewById(R.id.txttitle)).setText("Please input Playlist " +
                    "name\t\t\t\t\t\t\t");
            ((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
                /* JADX WARNING: Removed duplicated region for block: B:19:0x0068 A[Catch:{
                Exception -> 0x006e }] */
                /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
                public void onClick(View view) {
                    try {
                        String obj = editText.getText().toString();
                        if (!obj.isEmpty() && obj.trim().length() != 0 && !obj.equals("")) {
                            if (obj != null) {
                                DownloadMusicPlayerActivity.this.dbh.addplaylist(obj);
                                int i = DownloadMusicPlayerActivity.this.dbh.getplaylistid();
                                if (i != 0) {
                                    if (i != 0) {
                                        DownloadMusicPlayerActivity.this.dbh.savePlaylist(i, i);
                                        Toast.makeText(DownloadMusicPlayerActivity.this, "Added " +
                                                "to Playlist", 0).show();
                                        if (dialog != null) {
                                            dialog.dismiss();
                                            return;
                                        }
                                        return;
                                    }
                                }
                                DownloadMusicPlayerActivity.this.dbh.delplaylist(i);
                                if (dialog != null) {
                                }
                            }
                        }
                        Toast.makeText(DownloadMusicPlayerActivity.this, "Enter playlist name",
                                0).show();
                        if (dialog != null) {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
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
                    if (DownloadMusicPlayerActivity.this.handler != null) {
                        try {
                            DownloadMusicPlayerActivity.this.handler.removeCallbacks(DownloadMusicPlayerActivity.this.moveSeekBarThread);
                            try {
                                if (DownloadMusicPlayerActivity.this.mediaPlayer != null) {
                                    DownloadMusicPlayerActivity.this.mediaPlayer.release();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            DownloadMusicPlayerActivity.this.seekArcProgressTextView.setText("00" +
                                    ":00");
//                            if (MainActivity.adCount % 3 != 0) {
//                                if (!DownloadMusicPlayerActivity.this.adShow) {
//                                    DownloadMusicPlayerActivity.this.finish();
//                                    return;
//                                }
//                            }
                            DownloadMusicPlayerActivity.this.BackPressedAd();
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
                        DownloadMusicPlayerActivity.this.pause();
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
