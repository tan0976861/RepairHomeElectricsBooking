package com.example.repairhomeelectricbooking.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.repairhomeelectricbooking.HistoryFragment;
import com.example.repairhomeelectricbooking.MainUserFragment;
import com.example.repairhomeelectricbooking.NotificationFragment;
import com.example.repairhomeelectricbooking.ProfileFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MainUserFragment();
            case 1:
                return new HistoryFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new ProfileFragment();
            default:
                return new MainUserFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
