package com.example.new_meditation.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> listFragment;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.listFragment = new ArrayList();
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int position) {
        return this.listFragment.get(position);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.listFragment.size();
    }

    public void addFragment(Fragment fragment) {
        this.listFragment.add(fragment);
    }
}
