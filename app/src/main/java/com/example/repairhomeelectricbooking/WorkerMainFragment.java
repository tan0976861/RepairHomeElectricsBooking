package com.example.repairhomeelectricbooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repairhomeelectricbooking.photo.Photo;
import com.example.repairhomeelectricbooking.photo.PhotoViewPagerWorkerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class WorkerMainFragment extends Fragment {

    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private List<Photo> mListPhoto;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mViewPager.getCurrentItem() == mListPhoto.size() - 1){
                mViewPager.setCurrentItem(0);
            }else {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worker_main, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager_photo_worker_main);
        mCircleIndicator = (CircleIndicator) view.findViewById(R.id.circle_indicator_photo_worker_main);

        mListPhoto = getListPhotoWorker();
        PhotoViewPagerWorkerAdapter adapter = new PhotoViewPagerWorkerAdapter(mListPhoto);
        mViewPager.setAdapter(adapter);

        mCircleIndicator.setViewPager(mViewPager);

        mHandler.postDelayed(mRunnable,3000);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable,3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private List<Photo> getListPhotoWorker(){
        List<Photo> list = new ArrayList<>();

        list.add(new Photo(R.drawable.photo_worker));
        list.add(new Photo(R.drawable.photo_worker_2));
        list.add(new Photo(R.drawable.photo_worker_3));
        list.add(new Photo(R.drawable.photo_worker_4));
        return list;
    }


    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable,3000);
    }
}