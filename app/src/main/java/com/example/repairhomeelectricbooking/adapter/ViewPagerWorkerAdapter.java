package com.example.repairhomeelectricbooking.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.repairhomeelectricbooking.HistoryFragment;
import com.example.repairhomeelectricbooking.MainUserFragment;
import com.example.repairhomeelectricbooking.NotificationFragment;
import com.example.repairhomeelectricbooking.ProfileFragment;
import com.example.repairhomeelectricbooking.WorkerHistoryFragment;
import com.example.repairhomeelectricbooking.WorkerMainFragment;
import com.example.repairhomeelectricbooking.WorkerProfileFragment;

public class ViewPagerWorkerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerWorkerAdapter(@NonNull FragmentManager fm, int behavior) { super(fm, behavior); }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new WorkerMainFragment();
            case 1:
                return new WorkerHistoryFragment();
            case 2:
                //return new NotificationFragment();
                return new WorkerProfileFragment();
//            case 3:
//                return new WorkerProfileFragment();
            default:
                return new WorkerMainFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
