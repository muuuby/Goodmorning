package com.example.goodmorning;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Pageadapter extends FragmentPagerAdapter {
    public Pageadapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new fragment_weather();
        }else if(position==1){
            return new fragment_main();
        }else{
            return new fragment_luck();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
