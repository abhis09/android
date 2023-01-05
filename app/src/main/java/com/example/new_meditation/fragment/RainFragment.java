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


public class RainFragment extends Fragment {
    Integer flagDrops;
    Integer flagRainLeaves;
    Integer flagRainLight;
    Integer flagRainMedium;
    Integer flagRainOcean;
    Integer flagRainRoof;
    Integer flagRainThunder;
    Integer flagRainUmbrella;
    Integer flagRainWindow;
    ImageView imgDrops;
    ImageView imgRainLeaves;
    ImageView imgRainLight;
    ImageView imgRainMedium;
    ImageView imgRainOcean;
    ImageView imgRainRoof;
    ImageView imgRainThunder;
    ImageView imgRainUmbrella;
    ImageView imgRainWindow;
    RelativeLayout layoutRainLeaves;
    RelativeLayout layoutRainLight;
    RelativeLayout layoutRainMedium;
    RelativeLayout layoutRainRoof;
    RelativeLayout layoutRainThunder;
    RelativeLayout layoutRainUmbrella;
    RelativeLayout layoutRainWindow;
    MediaPlayer mpRainLeaves;
    MediaPlayer mpRainLight;
    MediaPlayer mpRainNormal;
    MediaPlayer mpRainOcean;
    MediaPlayer mpRainRoof;
    MediaPlayer mpRainThunder;
    MediaPlayer mpRainUmbrella;
    MediaPlayer mpRainWater;
    MediaPlayer mpRainWindow;
    RelativeLayout relativeLayout7;
    RelativeLayout relativeLayout8;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rain, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.layoutRainLight = (RelativeLayout) view.findViewById(R.id.layout_rain_light);
        this.layoutRainMedium = (RelativeLayout) view.findViewById(R.id.layout_rain_medium);
        this.layoutRainThunder = (RelativeLayout) view.findViewById(R.id.layout_rain_thunder);
        this.layoutRainUmbrella = (RelativeLayout) view.findViewById(R.id.layout_rain_umbrella);
        this.layoutRainRoof = (RelativeLayout) view.findViewById(R.id.layout_rain_roof);
        this.layoutRainWindow = (RelativeLayout) view.findViewById(R.id.layout_rain_window);
        this.layoutRainLeaves = (RelativeLayout) view.findViewById(R.id.layout_rain_leaves);
        this.relativeLayout7 = (RelativeLayout) view.findViewById(R.id.layout_drops);
        this.relativeLayout8 = (RelativeLayout) view.findViewById(R.id.layout_rain_ocean);
        this.imgRainThunder = (ImageView) view.findViewById(R.id.img_rain_thunder);
        this.imgRainLight = (ImageView) view.findViewById(R.id.img_rain_light);
        this.imgRainWindow = (ImageView) view.findViewById(R.id.img_rain_window);
        this.imgRainMedium = (ImageView) view.findViewById(R.id.img_rain_medium);
        this.imgRainUmbrella = (ImageView) view.findViewById(R.id.img_rain_umbrella);
        this.imgRainRoof = (ImageView) view.findViewById(R.id.img_rain_roof);
        this.imgRainLeaves = (ImageView) view.findViewById(R.id.img_rain_leaves);
        this.imgDrops = (ImageView) view.findViewById(R.id.img_drops);
        this.imgRainOcean = (ImageView) view.findViewById(R.id.img_rain_ocean);
        this.flagRainThunder = 0;
        this.flagRainLight = 0;
        this.flagRainWindow = 0;
        this.flagRainMedium = 0;
        this.flagRainUmbrella = 0;
        this.flagRainRoof = 0;
        this.flagRainLeaves = 0;
        this.flagDrops = 0;
        this.flagRainOcean = 0;
        this.mpRainThunder = MediaPlayer.create(view.getContext(), (int) R.raw.rain_thunder);
        this.mpRainLight = MediaPlayer.create(view.getContext(), (int) R.raw.rain_light);
        this.mpRainWindow = MediaPlayer.create(view.getContext(), (int) R.raw.rain_window);
        this.mpRainNormal = MediaPlayer.create(view.getContext(), (int) R.raw.rain_normal);
        this.mpRainUmbrella = MediaPlayer.create(view.getContext(), (int) R.raw.rain_umbrella);
        this.mpRainRoof = MediaPlayer.create(view.getContext(), (int) R.raw.rain_roof);
        this.mpRainLeaves = MediaPlayer.create(view.getContext(), (int) R.raw.rain_leaves);
        this.mpRainWater = MediaPlayer.create(view.getContext(), (int) R.raw.rain_water);
        this.mpRainOcean = MediaPlayer.create(view.getContext(), (int) R.raw.rain_ocean);
        this.imgRainLight.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.RainFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (RainFragment.this.flagRainLight.intValue() == 0) {
                    RainFragment.this.flagRainLight = 1;
                    RainFragment.this.imgRainLight.setImageResource(R.drawable.ic_rain_light_1);
                    RainFragment.this.layoutRainLight.setBackgroundResource(R.drawable.bg_select);
                    RainFragment.this.mpRainLight.start();
                    RainFragment.this.mpRainLight.setLooping(true);
                } else if (RainFragment.this.flagRainLight.intValue() == 1) {
                    RainFragment.this.flagRainLight = 0;
                    RainFragment.this.imgRainLight.setImageResource(R.drawable.ic_rain_light_2);
                    RainFragment.this.layoutRainLight.setBackgroundResource(R.drawable.bg_unselect);
                    RainFragment.this.mpRainLight.pause();
                }
            }
        });
        this.imgRainMedium.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.RainFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (RainFragment.this.flagRainMedium.intValue() == 0) {
                    RainFragment.this.flagRainMedium = 1;
                    RainFragment.this.imgRainMedium.setImageResource(R.drawable.ic_rain_medium_1);
                    RainFragment.this.layoutRainMedium.setBackgroundResource(R.drawable.bg_select);
                    RainFragment.this.mpRainNormal.start();
                    RainFragment.this.mpRainNormal.setLooping(true);
                } else if (RainFragment.this.flagRainMedium.intValue() == 1) {
                    RainFragment.this.flagRainMedium = 0;
                    RainFragment.this.imgRainMedium.setImageResource(R.drawable.ic_rain_medium_2);
                    RainFragment.this.layoutRainMedium.setBackgroundResource(R.drawable.bg_unselect);
                    RainFragment.this.mpRainNormal.pause();
                }
            }
        });
        this.imgRainThunder.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.RainFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (RainFragment.this.flagRainThunder.intValue() == 0) {
                    RainFragment.this.flagRainThunder = 1;
                    RainFragment.this.imgRainThunder.setImageResource(R.drawable.ic_rain_thunder_1);
                    RainFragment.this.layoutRainThunder.setBackgroundResource(R.drawable.bg_select);
                    RainFragment.this.mpRainThunder.start();
                    RainFragment.this.mpRainThunder.setLooping(true);
                } else if (RainFragment.this.flagRainThunder.intValue() == 1) {
                    RainFragment.this.flagRainThunder = 0;
                    RainFragment.this.imgRainThunder.setImageResource(R.drawable.ic_rain_thunder_2);
                    RainFragment.this.layoutRainThunder.setBackgroundResource(R.drawable.bg_unselect);
                    RainFragment.this.mpRainThunder.pause();
                }
            }
        });
        this.imgRainUmbrella.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.RainFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (RainFragment.this.flagRainUmbrella.intValue() == 0) {
                    RainFragment.this.flagRainUmbrella = 1;
                    RainFragment.this.imgRainUmbrella.setImageResource(R.drawable.ic_rain_umbrella_1);
                    RainFragment.this.layoutRainUmbrella.setBackgroundResource(R.drawable.bg_select);
                    RainFragment.this.mpRainUmbrella.start();
                    RainFragment.this.mpRainUmbrella.setLooping(true);
                } else if (RainFragment.this.flagRainUmbrella.intValue() == 1) {
                    RainFragment.this.flagRainUmbrella = 0;
                    RainFragment.this.imgRainUmbrella.setImageResource(R.drawable.ic_rain_umbrella_2);
                    RainFragment.this.layoutRainUmbrella.setBackgroundResource(R.drawable.bg_unselect);
                    RainFragment.this.mpRainUmbrella.pause();
                }
            }
        });
        this.imgRainRoof.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.RainFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (RainFragment.this.flagRainRoof.intValue() == 0) {
                    RainFragment.this.flagRainRoof = 1;
                    RainFragment.this.imgRainRoof.setImageResource(R.drawable.ic_rain_roof_1);
                    RainFragment.this.layoutRainRoof.setBackgroundResource(R.drawable.bg_select);
                    RainFragment.this.mpRainRoof.start();
                    RainFragment.this.mpRainRoof.setLooping(true);
                } else if (RainFragment.this.flagRainRoof.intValue() == 1) {
                    RainFragment.this.flagRainRoof = 0;
                    RainFragment.this.imgRainRoof.setImageResource(R.drawable.ic_rain_roof_2);
                    RainFragment.this.layoutRainRoof.setBackgroundResource(R.drawable.bg_unselect);
                    RainFragment.this.mpRainRoof.pause();
                }
            }
        });
        this.imgRainWindow.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.RainFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (RainFragment.this.flagRainWindow.intValue() == 0) {
                    RainFragment.this.flagRainWindow = 1;
                    RainFragment.this.imgRainWindow.setImageResource(R.drawable.ic_rain_window_1);
                    RainFragment.this.layoutRainWindow.setBackgroundResource(R.drawable.bg_select);
                    RainFragment.this.mpRainWindow.start();
                    RainFragment.this.mpRainWindow.setLooping(true);
                } else if (RainFragment.this.flagRainWindow.intValue() == 1) {
                    RainFragment.this.flagRainWindow = 0;
                    RainFragment.this.imgRainWindow.setImageResource(R.drawable.ic_rain_window_2);
                    RainFragment.this.layoutRainWindow.setBackgroundResource(R.drawable.bg_unselect);
                    RainFragment.this.mpRainWindow.pause();
                }
            }
        });
        this.imgRainLeaves.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.RainFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (RainFragment.this.flagRainLeaves.intValue() == 0) {
                    RainFragment.this.flagRainLeaves = 1;
                    RainFragment.this.imgRainLeaves.setImageResource(R.drawable.ic_rain_leaf_1);
                    RainFragment.this.layoutRainLeaves.setBackgroundResource(R.drawable.bg_select);
                    RainFragment.this.mpRainLeaves.start();
                    RainFragment.this.mpRainLeaves.setLooping(true);
                } else if (RainFragment.this.flagRainLeaves.intValue() == 1) {
                    RainFragment.this.flagRainLeaves = 0;
                    RainFragment.this.imgRainLeaves.setImageResource(R.drawable.ic_rain_leaf_2);
                    RainFragment.this.layoutRainLeaves.setBackgroundResource(R.drawable.bg_unselect);
                    RainFragment.this.mpRainLeaves.pause();
                }
            }
        });
        this.imgDrops.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.RainFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (RainFragment.this.flagDrops.intValue() == 0) {
                    RainFragment.this.flagDrops = 1;
                    RainFragment.this.imgDrops.setImageResource(R.drawable.ic_drop_1);
                    RainFragment.this.relativeLayout7.setBackgroundResource(R.drawable.bg_select);
                    RainFragment.this.mpRainWater.start();
                    RainFragment.this.mpRainWater.setLooping(true);
                } else if (RainFragment.this.flagDrops.intValue() == 1) {
                    RainFragment.this.flagDrops = 0;
                    RainFragment.this.imgDrops.setImageResource(R.drawable.ic_drop_2);
                    RainFragment.this.relativeLayout7.setBackgroundResource(R.drawable.bg_unselect);
                    RainFragment.this.mpRainWater.pause();
                }
            }
        });
        this.imgRainOcean.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.RainFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (RainFragment.this.flagRainOcean.intValue() == 0) {
                    RainFragment.this.flagRainOcean = 1;
                    RainFragment.this.imgRainOcean.setImageResource(R.drawable.ic_rain_ocean_1);
                    RainFragment.this.relativeLayout8.setBackgroundResource(R.drawable.bg_select);
                    RainFragment.this.mpRainOcean.start();
                    RainFragment.this.mpRainOcean.setLooping(true);
                } else if (RainFragment.this.flagRainOcean.intValue() == 1) {
                    RainFragment.this.flagRainOcean = 0;
                    RainFragment.this.imgRainOcean.setImageResource(R.drawable.ic_rain_ocean_2);
                    RainFragment.this.relativeLayout8.setBackgroundResource(R.drawable.bg_unselect);
                    RainFragment.this.mpRainOcean.pause();
                }
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        MediaPlayer mediaPlayer = this.mpRainThunder;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        MediaPlayer mediaPlayer2 = this.mpRainLight;
        if (mediaPlayer2 != null) {
            mediaPlayer2.stop();
        }
        MediaPlayer mediaPlayer3 = this.mpRainWindow;
        if (mediaPlayer3 != null) {
            mediaPlayer3.stop();
        }
        MediaPlayer mediaPlayer4 = this.mpRainNormal;
        if (mediaPlayer4 != null) {
            mediaPlayer4.stop();
        }
        MediaPlayer mediaPlayer5 = this.mpRainUmbrella;
        if (mediaPlayer5 != null) {
            mediaPlayer5.stop();
        }
        MediaPlayer mediaPlayer6 = this.mpRainRoof;
        if (mediaPlayer6 != null) {
            mediaPlayer6.stop();
        }
        MediaPlayer mediaPlayer7 = this.mpRainLeaves;
        if (mediaPlayer7 != null) {
            mediaPlayer7.stop();
        }
        MediaPlayer mediaPlayer8 = this.mpRainWater;
        if (mediaPlayer8 != null) {
            mediaPlayer8.stop();
        }
        MediaPlayer mediaPlayer9 = this.mpRainOcean;
        if (mediaPlayer9 != null) {
            mediaPlayer9.stop();
        }
    }
}
