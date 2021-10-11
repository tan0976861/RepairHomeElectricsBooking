package com.example.repairhomeelectricbooking.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.repairhomeelectricbooking.HistoryFragment;
import com.example.repairhomeelectricbooking.MainUserFragment;
import com.example.repairhomeelectricbooking.NotificationFragment;
import com.example.repairhomeelectricbooking.ProfileFragment;
import com.example.repairhomeelectricbooking.WorkerMainFragment;

public class ViewPagerWorkerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerWorkerAdapter(@NonNull FragmentManager fm, int behavior) { super(fm, behavior); }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new WorkerMainFragment();
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
