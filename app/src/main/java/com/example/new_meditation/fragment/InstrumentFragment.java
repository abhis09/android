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


public class InstrumentFragment extends Fragment {
    Integer flagBell;
    Integer flagBowl;
    Integer flagBrownNoise;
    Integer flagChimes;
    Integer flagFlute;
    Integer flagPiano;
    Integer flagPinkNoise;
    Integer flagStone;
    Integer flagWhiteNoise;
    ImageView imgBell;
    ImageView imgBowl;
    ImageView imgBrownNoise;
    ImageView imgChimes;
    ImageView imgFlute;
    ImageView imgPiano;
    ImageView imgPinkNoise;
    ImageView imgStone;
    ImageView imgWhiteNoise;
    RelativeLayout layoutBell;
    RelativeLayout layoutBowl;
    RelativeLayout layoutBrownNoise;
    RelativeLayout layoutChimes;
    RelativeLayout layoutFlute;
    RelativeLayout layoutPiano;
    RelativeLayout layoutPinkNoise;
    RelativeLayout layoutStone;
    RelativeLayout layoutWhiteNoise;
    MediaPlayer mpBell;
    MediaPlayer mpBowl;
    MediaPlayer mpBrownNoise;
    MediaPlayer mpChimes;
    MediaPlayer mpFlute;
    MediaPlayer mpPiano;
    MediaPlayer mpPinkNoise;
    MediaPlayer mpStone;
    MediaPlayer mpWhiteNoise;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_instrument, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.layoutPiano = (RelativeLayout) view.findViewById(R.id.layout_piano);
        this.layoutFlute = (RelativeLayout) view.findViewById(R.id.layout_flute);
        this.layoutStone = (RelativeLayout) view.findViewById(R.id.layout_stone);
        this.layoutBowl = (RelativeLayout) view.findViewById(R.id.layout_bowl);
        this.layoutBell = (RelativeLayout) view.findViewById(R.id.layout_bell);
        this.layoutChimes = (RelativeLayout) view.findViewById(R.id.layout_chimes);
        this.layoutWhiteNoise = (RelativeLayout) view.findViewById(R.id.layout_white_noise);
        this.layoutPinkNoise = (RelativeLayout) view.findViewById(R.id.layout_pink_noise);
        this.layoutBrownNoise = (RelativeLayout) view.findViewById(R.id.layout_brown_noise);
        this.imgPiano = (ImageView) view.findViewById(R.id.img_piano);
        this.imgFlute = (ImageView) view.findViewById(R.id.img_flute);
        this.imgStone = (ImageView) view.findViewById(R.id.img_stone);
        this.imgBowl = (ImageView) view.findViewById(R.id.img_bowl);
        this.imgBell = (ImageView) view.findViewById(R.id.img_bell);
        this.imgChimes = (ImageView) view.findViewById(R.id.img_chimes);
        this.imgWhiteNoise = (ImageView) view.findViewById(R.id.img_white_noise);
        this.imgPinkNoise = (ImageView) view.findViewById(R.id.img_pink_noise);
        this.imgBrownNoise = (ImageView) view.findViewById(R.id.img_brown_noise);
        this.flagPiano = 0;
        this.flagFlute = 0;
        this.flagStone = 0;
        this.flagBowl = 0;
        this.flagBell = 0;
        this.flagChimes = 0;
        this.flagWhiteNoise = 0;
        this.flagPinkNoise = 0;
        this.flagBrownNoise = 0;
        this.mpPiano = MediaPlayer.create(view.getContext(), (int) R.raw.instrument_piano);
        this.mpFlute = MediaPlayer.create(view.getContext(), (int) R.raw.instrument_flute);
        this.mpStone = MediaPlayer.create(view.getContext(), (int) R.raw.instrument_stone);
        this.mpBowl = MediaPlayer.create(view.getContext(), (int) R.raw.instrument_bowl);
        this.mpBell = MediaPlayer.create(view.getContext(), (int) R.raw.instrument_bells);
        this.mpChimes = MediaPlayer.create(view.getContext(), (int) R.raw.instruement_chimes);
        this.mpWhiteNoise = MediaPlayer.create(view.getContext(), (int) R.raw.instrument_white_noise);
        this.mpPinkNoise = MediaPlayer.create(view.getContext(), (int) R.raw.instrument_pink_noise);
        this.mpBrownNoise = MediaPlayer.create(view.getContext(), (int) R.raw.instrument_brown_noise);
        this.imgPiano.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.InstrumentFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (InstrumentFragment.this.flagPiano.intValue() == 0) {
                    InstrumentFragment.this.flagPiano = 1;
                    InstrumentFragment.this.imgPiano.setImageResource(R.drawable.ic_piano_1);
                    InstrumentFragment.this.layoutPiano.setBackgroundResource(R.drawable.bg_select);
                    InstrumentFragment.this.mpPiano.start();
                    InstrumentFragment.this.mpPiano.setLooping(true);
                } else if (InstrumentFragment.this.flagPiano.intValue() == 1) {
                    InstrumentFragment.this.flagPiano = 0;
                    InstrumentFragment.this.imgPiano.setImageResource(R.drawable.ic_piano_2);
                    InstrumentFragment.this.layoutPiano.setBackgroundResource(R.drawable.bg_unselect);
                    InstrumentFragment.this.mpPiano.pause();
                }
            }
        });
        this.imgFlute.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.InstrumentFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (InstrumentFragment.this.flagFlute.intValue() == 0) {
                    InstrumentFragment.this.flagFlute = 1;
                    InstrumentFragment.this.imgFlute.setImageResource(R.drawable.ic_flute_1);
                    InstrumentFragment.this.layoutFlute.setBackgroundResource(R.drawable.bg_select);
                    InstrumentFragment.this.mpFlute.start();
                    InstrumentFragment.this.mpFlute.setLooping(true);
                } else if (InstrumentFragment.this.flagFlute.intValue() == 1) {
                    InstrumentFragment.this.flagFlute = 0;
                    InstrumentFragment.this.imgFlute.setImageResource(R.drawable.ic_flute_2);
                    InstrumentFragment.this.layoutFlute.setBackgroundResource(R.drawable.bg_unselect);
                    InstrumentFragment.this.mpFlute.pause();
                }
            }
        });
        this.imgStone.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.InstrumentFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (InstrumentFragment.this.flagStone.intValue() == 0) {
                    InstrumentFragment.this.flagStone = 1;
                    InstrumentFragment.this.imgStone.setImageResource(R.drawable.ic_stone_1);
                    InstrumentFragment.this.layoutStone.setBackgroundResource(R.drawable.bg_select);
                    InstrumentFragment.this.mpStone.start();
                    InstrumentFragment.this.mpStone.setLooping(true);
                } else if (InstrumentFragment.this.flagStone.intValue() == 1) {
                    InstrumentFragment.this.flagStone = 0;
                    InstrumentFragment.this.imgStone.setImageResource(R.drawable.ic_stone_2);
                    InstrumentFragment.this.layoutStone.setBackgroundResource(R.drawable.bg_unselect);
                    InstrumentFragment.this.mpStone.pause();
                }
            }
        });
        this.imgBowl.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.InstrumentFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (InstrumentFragment.this.flagBowl.intValue() == 0) {
                    InstrumentFragment.this.flagBowl = 1;
                    InstrumentFragment.this.imgBowl.setImageResource(R.drawable.ic_bowl_1);
                    InstrumentFragment.this.layoutBowl.setBackgroundResource(R.drawable.bg_select);
                    InstrumentFragment.this.mpBowl.start();
                    InstrumentFragment.this.mpBowl.setLooping(true);
                } else if (InstrumentFragment.this.flagBowl.intValue() == 1) {
                    InstrumentFragment.this.flagBowl = 0;
                    InstrumentFragment.this.imgBowl.setImageResource(R.drawable.ic_bowl_2);
                    InstrumentFragment.this.layoutBowl.setBackgroundResource(R.drawable.bg_unselect);
                    InstrumentFragment.this.mpBowl.pause();
                }
            }
        });
        this.imgBell.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.InstrumentFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (InstrumentFragment.this.flagBell.intValue() == 0) {
                    InstrumentFragment.this.flagBell = 1;
                    InstrumentFragment.this.imgBell.setImageResource(R.drawable.ic_bell_1);
                    InstrumentFragment.this.layoutBell.setBackgroundResource(R.drawable.bg_select);
                    InstrumentFragment.this.mpBell.start();
                    InstrumentFragment.this.mpBell.setLooping(true);
                } else if (InstrumentFragment.this.flagBell.intValue() == 1) {
                    InstrumentFragment.this.flagBell = 0;
                    InstrumentFragment.this.imgBell.setImageResource(R.drawable.ic_bell_2);
                    InstrumentFragment.this.layoutBell.setBackgroundResource(R.drawable.bg_unselect);
                    InstrumentFragment.this.mpBell.pause();
                }
            }
        });
        this.imgChimes.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.InstrumentFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (InstrumentFragment.this.flagChimes.intValue() == 0) {
                    InstrumentFragment.this.flagChimes = 1;
                    InstrumentFragment.this.imgChimes.setImageResource(R.drawable.ic_chimes_1);
                    InstrumentFragment.this.layoutChimes.setBackgroundResource(R.drawable.bg_select);
                    InstrumentFragment.this.mpChimes.start();
                    InstrumentFragment.this.mpChimes.setLooping(true);
                } else if (InstrumentFragment.this.flagChimes.intValue() == 1) {
                    InstrumentFragment.this.flagChimes = 0;
                    InstrumentFragment.this.imgChimes.setImageResource(R.drawable.ic_chimes_2);
                    InstrumentFragment.this.layoutChimes.setBackgroundResource(R.drawable.bg_unselect);
                    InstrumentFragment.this.mpChimes.pause();
                }
            }
        });
        this.imgWhiteNoise.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.InstrumentFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (InstrumentFragment.this.flagWhiteNoise.intValue() == 0) {
                    InstrumentFragment.this.flagWhiteNoise = 1;
                    InstrumentFragment.this.imgWhiteNoise.setImageResource(R.drawable.ic_white_noise_1);
                    InstrumentFragment.this.layoutWhiteNoise.setBackgroundResource(R.drawable.bg_select);
                    InstrumentFragment.this.mpWhiteNoise.start();
                    InstrumentFragment.this.mpWhiteNoise.setLooping(true);
                } else if (InstrumentFragment.this.flagWhiteNoise.intValue() == 1) {
                    InstrumentFragment.this.flagWhiteNoise = 0;
                    InstrumentFragment.this.imgWhiteNoise.setImageResource(R.drawable.ic_white_noise_2);
                    InstrumentFragment.this.layoutWhiteNoise.setBackgroundResource(R.drawable.bg_unselect);
                    InstrumentFragment.this.mpWhiteNoise.pause();
                }
            }
        });
        this.imgPinkNoise.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.InstrumentFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (InstrumentFragment.this.flagPinkNoise.intValue() == 0) {
                    InstrumentFragment.this.flagPinkNoise = 1;
                    InstrumentFragment.this.imgPinkNoise.setImageResource(R.drawable.ic_pink_noise_1);
                    InstrumentFragment.this.layoutPinkNoise.setBackgroundResource(R.drawable.bg_select);
                    InstrumentFragment.this.mpPinkNoise.start();
                    InstrumentFragment.this.mpPinkNoise.setLooping(true);
                } else if (InstrumentFragment.this.flagPinkNoise.intValue() == 1) {
                    InstrumentFragment.this.flagPinkNoise = 0;
                    InstrumentFragment.this.imgPinkNoise.setImageResource(R.drawable.ic_pink_noise_2);
                    InstrumentFragment.this.layoutPinkNoise.setBackgroundResource(R.drawable.bg_unselect);
                    InstrumentFragment.this.mpPinkNoise.pause();
                }
            }
        });
        this.imgBrownNoise.setOnClickListener(new View.OnClickListener() { // from class: com.example.meditation_app.Fragment.InstrumentFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (InstrumentFragment.this.flagBrownNoise.intValue() == 0) {
                    InstrumentFragment.this.flagBrownNoise = 1;
                    InstrumentFragment.this.imgBrownNoise.setImageResource(R.drawable.ic_brown_noise_1);
                    InstrumentFragment.this.layoutBrownNoise.setBackgroundResource(R.drawable.bg_select);
                    InstrumentFragment.this.mpBrownNoise.start();
                    InstrumentFragment.this.mpBrownNoise.setLooping(true);
                } else if (InstrumentFragment.this.flagBrownNoise.intValue() == 1) {
                    InstrumentFragment.this.flagBrownNoise = 0;
                    InstrumentFragment.this.imgBrownNoise.setImageResource(R.drawable.ic_brown_noise_2);
                    InstrumentFragment.this.layoutBrownNoise.setBackgroundResource(R.drawable.bg_unselect);
                    InstrumentFragment.this.mpBrownNoise.pause();
                }
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        MediaPlayer mediaPlayer = this.mpPiano;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        MediaPlayer mediaPlayer2 = this.mpFlute;
        if (mediaPlayer2 != null) {
            mediaPlayer2.stop();
        }
        MediaPlayer mediaPlayer3 = this.mpStone;
        if (mediaPlayer3 != null) {
            mediaPlayer3.stop();
        }
        MediaPlayer mediaPlayer4 = this.mpBowl;
        if (mediaPlayer4 != null) {
            mediaPlayer4.stop();
        }
        MediaPlayer mediaPlayer5 = this.mpBell;
        if (mediaPlayer5 != null) {
            mediaPlayer5.stop();
        }
        MediaPlayer mediaPlayer6 = this.mpChimes;
        if (mediaPlayer6 != null) {
            mediaPlayer6.stop();
        }
        MediaPlayer mediaPlayer7 = this.mpWhiteNoise;
        if (mediaPlayer7 != null) {
            mediaPlayer7.stop();
        }
        MediaPlayer mediaPlayer8 = this.mpPinkNoise;
        if (mediaPlayer8 != null) {
            mediaPlayer8.stop();
        }
        MediaPlayer mediaPlayer9 = this.mpBrownNoise;
        if (mediaPlayer9 != null) {
            mediaPlayer9.stop();
        }
    }
}
