package com.example.new_meditation.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.new_meditation.R;


public class NatureFragment extends Fragment {
    Integer flagBird;
    Integer flagCreek;
    Integer flagFire;
    Integer flagForest;
    Integer flagFrogs;
    Integer flagGrasshopper;
    Integer flagLeaves;
    Integer flagWaterfall;
    Integer flagWind;
    ImageView imgBird;
    ImageView imgCreek;
    ImageView imgFire;
    ImageView imgForest;
    ImageView imgFrogs;
    ImageView imgGrasshopper;
    ImageView imgLeaves;
    ImageView imgWaterfall;
    ImageView imgWind;
    RelativeLayout layoutBird;
    RelativeLayout layoutCreek;
    RelativeLayout layoutFire;
    RelativeLayout layoutForest;
    RelativeLayout layoutFrogs;
    RelativeLayout layoutGrasshopper;
    RelativeLayout layoutLeaves;
    RelativeLayout layoutWaterfall;
    RelativeLayout layoutWind;
    MediaPlayer mpBird;
    MediaPlayer mpCreek;
    MediaPlayer mpFire;
    MediaPlayer mpForest;
    MediaPlayer mpFrogs;
    MediaPlayer mpGrasshopper;
    MediaPlayer mpLeaves;
    MediaPlayer mpWaterfall;
    MediaPlayer mpWind;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nature, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.layoutForest = (RelativeLayout) view.findViewById(R.id.layout_forest);
        this.layoutCreek = (RelativeLayout) view.findViewById(R.id.layout_creek);
        this.layoutBird = (RelativeLayout) view.findViewById(R.id.layout_bird);
        this.layoutWaterfall = (RelativeLayout) view.findViewById(R.id.layout_waterfall);
        this.layoutFire = (RelativeLayout) view.findViewById(R.id.layout_fire);
        this.layoutLeaves = (RelativeLayout) view.findViewById(R.id.layout_leaves);
        this.layoutGrasshopper = (RelativeLayout) view.findViewById(R.id.layout_grasshopper);
        this.layoutWind = (RelativeLayout) view.findViewById(R.id.layout_wind);
        this.layoutFrogs = (RelativeLayout) view.findViewById(R.id.layout_frogs);
        this.imgForest = (ImageView) view.findViewById(R.id.img_forest);
        this.imgCreek = (ImageView) view.findViewById(R.id.img_creek);
        this.imgLeaves = (ImageView) view.findViewById(R.id.img_leaves);
        this.imgBird = (ImageView) view.findViewById(R.id.img_bird);
        this.imgWaterfall = (ImageView) view.findViewById(R.id.img_waterfall);
        this.imgWind = (ImageView) view.findViewById(R.id.img_wind);
        this.imgFire = (ImageView) view.findViewById(R.id.img_fire);
        this.imgGrasshopper = (ImageView) view.findViewById(R.id.ic_grasshopper);
        this.imgFrogs = (ImageView) view.findViewById(R.id.img_frogs);
        this.flagForest = 0;
        this.flagCreek = 0;
        this.flagLeaves = 0;
        this.flagBird = 0;
        this.flagWaterfall = 0;
        this.flagWind = 0;
        this.flagFire = 0;
        this.flagGrasshopper = 0;
        this.flagFrogs = 0;
        this.mpForest = MediaPlayer.create(view.getContext(), (int) R.raw.nature_forest);
        this.mpCreek = MediaPlayer.create(view.getContext(), (int) R.raw.nature_creek);
        this.mpLeaves = MediaPlayer.create(view.getContext(), (int) R.raw.nature_leaves);
        this.mpBird = MediaPlayer.create(view.getContext(), (int) R.raw.nature_birds);
        this.mpWaterfall = MediaPlayer.create(view.getContext(), (int) R.raw.nature_waterfall);
        this.mpWind = MediaPlayer.create(view.getContext(), (int) R.raw.nature_wind);
        this.mpFire = MediaPlayer.create(view.getContext(), (int) R.raw.nature_fire);
        this.mpGrasshopper = MediaPlayer.create(view.getContext(), (int) R.raw.nature_grasshopper);
        this.mpFrogs = MediaPlayer.create(view.getContext(), (int) R.raw.nature_frogs);
        this.imgForest.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.NatureFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (NatureFragment.this.flagForest.intValue() == 0) {
                    NatureFragment.this.flagForest = 1;
                    NatureFragment.this.imgForest.setImageResource(R.drawable.ic_forest_1);
                    NatureFragment.this.layoutForest.setBackgroundResource(R.drawable.bg_select);
                    NatureFragment.this.mpForest.start();
                    NatureFragment.this.mpForest.setLooping(true);
                } else if (NatureFragment.this.flagForest.intValue() == 1) {
                    NatureFragment.this.flagForest = 0;
                    NatureFragment.this.imgForest.setImageResource(R.drawable.ic_forest_2);
                    NatureFragment.this.layoutForest.setBackgroundResource(R.drawable.bg_unselect);
                    NatureFragment.this.mpForest.pause();
                }
            }
        });
        this.imgCreek.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.NatureFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (NatureFragment.this.flagCreek.intValue() == 0) {
                    NatureFragment.this.flagCreek = 1;
                    NatureFragment.this.imgCreek.setImageResource(R.drawable.ic_creek_1);
                    NatureFragment.this.layoutCreek.setBackgroundResource(R.drawable.bg_select);
                    NatureFragment.this.mpCreek.start();
                    NatureFragment.this.mpCreek.setLooping(true);
                } else if (NatureFragment.this.flagCreek.intValue() == 1) {
                    NatureFragment.this.flagCreek = 0;
                    NatureFragment.this.imgCreek.setImageResource(R.drawable.ic_creek_2);
                    NatureFragment.this.layoutCreek.setBackgroundResource(R.drawable.bg_unselect);
                    NatureFragment.this.mpCreek.pause();
                }
            }
        });
        this.imgBird.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.NatureFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (NatureFragment.this.flagBird.intValue() == 0) {
                    NatureFragment.this.flagBird = 1;
                    NatureFragment.this.imgBird.setImageResource(R.drawable.ic_bird_1);
                    NatureFragment.this.layoutBird.setBackgroundResource(R.drawable.bg_select);
                    NatureFragment.this.mpBird.start();
                    NatureFragment.this.mpBird.setLooping(true);
                } else if (NatureFragment.this.flagBird.intValue() == 1) {
                    NatureFragment.this.flagBird = 0;
                    NatureFragment.this.imgBird.setImageResource(R.drawable.ic_bird_2);
                    NatureFragment.this.layoutBird.setBackgroundResource(R.drawable.bg_unselect);
                    NatureFragment.this.mpBird.pause();
                }
            }
        });
        this.imgWaterfall.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.NatureFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (NatureFragment.this.flagWaterfall.intValue() == 0) {
                    NatureFragment.this.flagWaterfall = 1;
                    NatureFragment.this.imgWaterfall.setImageResource(R.drawable.ic_waterfall_1);
                    NatureFragment.this.layoutWaterfall.setBackgroundResource(R.drawable.bg_select);
                    NatureFragment.this.mpWaterfall.start();
                    NatureFragment.this.mpWaterfall.setLooping(true);
                } else if (NatureFragment.this.flagWaterfall.intValue() == 1) {
                    NatureFragment.this.flagWaterfall = 0;
                    NatureFragment.this.imgWaterfall.setImageResource(R.drawable.ic_waterfall_2);
                    NatureFragment.this.layoutWaterfall.setBackgroundResource(R.drawable.bg_unselect);
                    NatureFragment.this.mpWaterfall.pause();
                }
            }
        });
        this.imgFire.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.NatureFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (NatureFragment.this.flagFire.intValue() == 0) {
                    NatureFragment.this.flagFire = 1;
                    NatureFragment.this.imgFire.setImageResource(R.drawable.ic_fire_1);
                    NatureFragment.this.layoutFire.setBackgroundResource(R.drawable.bg_select);
                    NatureFragment.this.mpFire.start();
                    NatureFragment.this.mpFire.setLooping(true);
                } else if (NatureFragment.this.flagFire.intValue() == 1) {
                    NatureFragment.this.flagFire = 0;
                    NatureFragment.this.imgFire.setImageResource(R.drawable.ic_fire_2);
                    NatureFragment.this.layoutFire.setBackgroundResource(R.drawable.bg_unselect);
                    NatureFragment.this.mpFire.pause();
                }
            }
        });
        this.imgLeaves.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.NatureFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (NatureFragment.this.flagLeaves.intValue() == 0) {
                    NatureFragment.this.flagLeaves = 1;
                    NatureFragment.this.imgLeaves.setImageResource(R.drawable.ic_leaves_1);
                    NatureFragment.this.layoutLeaves.setBackgroundResource(R.drawable.bg_select);
                    NatureFragment.this.mpLeaves.start();
                    NatureFragment.this.mpLeaves.setLooping(true);
                } else if (NatureFragment.this.flagLeaves.intValue() == 1) {
                    NatureFragment.this.flagLeaves = 0;
                    NatureFragment.this.imgLeaves.setImageResource(R.drawable.ic_leaves_2);
                    NatureFragment.this.layoutLeaves.setBackgroundResource(R.drawable.bg_unselect);
                    NatureFragment.this.mpLeaves.pause();
                }
            }
        });
        this.imgGrasshopper.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.NatureFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (NatureFragment.this.flagGrasshopper.intValue() == 0) {
                    NatureFragment.this.flagGrasshopper = 1;
                    NatureFragment.this.imgGrasshopper.setImageResource(R.drawable.ic_grasshopper_1);
                    NatureFragment.this.layoutGrasshopper.setBackgroundResource(R.drawable.bg_select);
                    NatureFragment.this.mpGrasshopper.start();
                    NatureFragment.this.mpGrasshopper.setLooping(true);
                } else if (NatureFragment.this.flagGrasshopper.intValue() == 1) {
                    NatureFragment.this.flagGrasshopper = 0;
                    NatureFragment.this.imgGrasshopper.setImageResource(R.drawable.ic_grasshopper_2);
                    NatureFragment.this.layoutGrasshopper.setBackgroundResource(R.drawable.bg_unselect);
                    NatureFragment.this.mpGrasshopper.pause();
                }
            }
        });
        this.imgWind.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.NatureFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (NatureFragment.this.flagWind.intValue() == 0) {
                    NatureFragment.this.flagWind = 1;
                    NatureFragment.this.imgWind.setImageResource(R.drawable.ic_wind_1);
                    NatureFragment.this.layoutWind.setBackgroundResource(R.drawable.bg_select);
                    NatureFragment.this.mpWind.start();
                    NatureFragment.this.mpWind.setLooping(true);
                } else if (NatureFragment.this.flagWind.intValue() == 1) {
                    NatureFragment.this.flagWind = 0;
                    NatureFragment.this.imgWind.setImageResource(R.drawable.ic_wind_2);
                    NatureFragment.this.layoutWind.setBackgroundResource(R.drawable.bg_unselect);
                    NatureFragment.this.mpWind.pause();
                }
            }
        });
        this.imgFrogs.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.NatureFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (NatureFragment.this.flagFrogs.intValue() == 0) {
                    NatureFragment.this.flagFrogs = 1;
                    NatureFragment.this.imgFrogs.setImageResource(R.drawable.ic_frogs_1);
                    NatureFragment.this.layoutFrogs.setBackgroundResource(R.drawable.bg_select);
                    NatureFragment.this.mpFrogs.start();
                    NatureFragment.this.mpFrogs.setLooping(true);
                } else if (NatureFragment.this.flagFrogs.intValue() == 1) {
                    NatureFragment.this.flagFrogs = 0;
                    NatureFragment.this.imgFrogs.setImageResource(R.drawable.ic_frogs_2);
                    NatureFragment.this.layoutFrogs.setBackgroundResource(R.drawable.bg_unselect);
                    NatureFragment.this.mpFrogs.pause();
                }
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        MediaPlayer mediaPlayer = this.mpForest;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        MediaPlayer mediaPlayer2 = this.mpCreek;
        if (mediaPlayer2 != null) {
            mediaPlayer2.stop();
        }
        MediaPlayer mediaPlayer3 = this.mpLeaves;
        if (mediaPlayer3 != null) {
            mediaPlayer3.stop();
        }
        MediaPlayer mediaPlayer4 = this.mpBird;
        if (mediaPlayer4 != null) {
            mediaPlayer4.stop();
        }
        MediaPlayer mediaPlayer5 = this.mpWaterfall;
        if (mediaPlayer5 != null) {
            mediaPlayer5.stop();
        }
        MediaPlayer mediaPlayer6 = this.mpWind;
        if (mediaPlayer6 != null) {
            mediaPlayer6.stop();
        }
        MediaPlayer mediaPlayer7 = this.mpFire;
        if (mediaPlayer7 != null) {
            mediaPlayer7.stop();
        }
        MediaPlayer mediaPlayer8 = this.mpGrasshopper;
        if (mediaPlayer8 != null) {
            mediaPlayer8.stop();
        }
        MediaPlayer mediaPlayer9 = this.mpFrogs;
        if (mediaPlayer9 != null) {
            mediaPlayer9.stop();
        }
    }
}
