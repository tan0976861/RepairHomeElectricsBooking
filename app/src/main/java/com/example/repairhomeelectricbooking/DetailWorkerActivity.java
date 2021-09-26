package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.repairhomeelectricbooking.photo.Photo;
import com.example.repairhomeelectricbooking.photo.PhotoViewPagerWorkerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class DetailWorkerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private List<Photo> mListPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_worker);

        mViewPager = (ViewPager) findViewById(R.id.view_pager_photo_worker);
        mCircleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator_photo_worker);

        mListPhoto = getListPhotoWorker();
        PhotoViewPagerWorkerAdapter adapter = new PhotoViewPagerWorkerAdapter(mListPhoto);
        mViewPager.setAdapter(adapter);

        mCircleIndicator.setViewPager(mViewPager);
    }

    private List<Photo> getListPhotoWorker(){
        List<Photo> list = new ArrayList<>();

        list.add(new Photo(R.drawable.photo_worker));
        list.add(new Photo(R.drawable.photo_worker_2));
        list.add(new Photo(R.drawable.photo_worker_3));
        list.add(new Photo(R.drawable.photo_worker_4));
        return list;
    }

    public void clickToSearchScreen(View view){
        finish();
    }
}