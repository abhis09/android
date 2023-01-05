package com.example.new_meditation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.new_meditation.Adapter.MainViewPagerAdapter;
import com.example.new_meditation.Adapter.ViewPagerAdapter;
import com.example.new_meditation.R;
import com.example.new_meditation.fragment.InstrumentFragment;
import com.example.new_meditation.fragment.NatureFragment;
import com.example.new_meditation.fragment.RainFragment;


public class RelaxMusicActivity extends AppCompatActivity {
    ImageView imgInstrument;
    ImageView imgNature;
    ImageView imgRain;
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_music);

        findViewById(R.id.ll_back).setOnClickListener(v -> {onBackPressed();});

        this.imgRain = (ImageView) findViewById(R.id.img_rain);
        this.imgNature = (ImageView) findViewById(R.id.img_nature);
        this.imgInstrument = (ImageView) findViewById(R.id.img_instrument);
        this.viewPager = (ViewPager) findViewById(R.id.view_pager);
        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new RainFragment());
        viewPagerAdapter.addFragment(new NatureFragment());
        viewPagerAdapter.addFragment(new InstrumentFragment());
        this.viewPager.setAdapter(viewPagerAdapter);
        this.viewPager.setOffscreenPageLimit(3);
        this.viewPager.setCurrentItem(0);
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    RelaxMusicActivity.this.imgRain.setImageResource(R.drawable.ic_item_three);
                    RelaxMusicActivity.this.imgNature.setImageResource(R.drawable.forest_menu);
                    RelaxMusicActivity.this.imgInstrument.setImageResource(R.drawable.meditation_menu);
                } else if (position == 1) {
                    RelaxMusicActivity.this.imgRain.setImageResource(R.drawable.rain_menu);
                    RelaxMusicActivity.this.imgNature.setImageResource(R.drawable.ic_item_two);
                    RelaxMusicActivity.this.imgInstrument.setImageResource(R.drawable.meditation_menu);
                } else {
                    RelaxMusicActivity.this.imgRain.setImageResource(R.drawable.rain_menu);
                    RelaxMusicActivity.this.imgNature.setImageResource(R.drawable.forest_menu);
                    RelaxMusicActivity.this.imgInstrument.setImageResource(R.drawable.ic_item_one);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        this.imgRain.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RelaxMusicActivity.this.viewPager.setCurrentItem(0);
                RelaxMusicActivity.this.imgRain.setImageResource(R.drawable.ic_item_three);
                RelaxMusicActivity.this.imgNature.setImageResource(R.drawable.forest_menu);
                RelaxMusicActivity.this.imgInstrument.setImageResource(R.drawable.meditation_menu);
            }
        });
        this.imgNature.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RelaxMusicActivity.this.viewPager.setCurrentItem(1);
                RelaxMusicActivity.this.imgRain.setImageResource(R.drawable.rain_menu);
                RelaxMusicActivity.this.imgNature.setImageResource(R.drawable.ic_item_two);
                RelaxMusicActivity.this.imgInstrument.setImageResource(R.drawable.meditation_menu);
            }
        });
        this.imgInstrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelaxMusicActivity.this.viewPager.setCurrentItem(2);
                RelaxMusicActivity.this.imgRain.setImageResource(R.drawable.rain_menu);
                RelaxMusicActivity.this.imgNature.setImageResource(R.drawable.forest_menu);
                RelaxMusicActivity.this.imgInstrument.setImageResource(R.drawable.ic_item_one);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
