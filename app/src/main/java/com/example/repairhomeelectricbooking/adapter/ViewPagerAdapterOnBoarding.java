package com.example.repairhomeelectricbooking.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.repairhomeelectricbooking.OnboardingFragment1;
import com.example.repairhomeelectricbooking.OnboardingFragment2;
import com.example.repairhomeelectricbooking.OnboardingFragment3;
import com.example.repairhomeelectricbooking.OnboardingFragment4;

public class ViewPagerAdapterOnBoarding extends FragmentStatePagerAdapter {
    public ViewPagerAdapterOnBoarding(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new OnboardingFragment1();
            case 1:
                return new OnboardingFragment2();
            case 2:
                return new OnboardingFragment3();
            case 3:
                return new OnboardingFragment4();
            default:
                return new OnboardingFragment1();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
