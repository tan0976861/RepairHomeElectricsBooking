package com.example.repairhomeelectricbooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.repairhomeelectricbooking.dto.Worker;
import com.example.repairhomeelectricbooking.photo.Photo;
import com.example.repairhomeelectricbooking.photo.PhotoViewPagerWorkerAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class WorkerMainFragment extends Fragment {

    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private List<Photo> mListPhoto;
    private Handler mHandler = new Handler();
    private Worker worker;
    private TextView tvTest1;
    private TextView edtTest;
    private String emailTest;

//    private Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            if (mViewPager.getCurrentItem() == mListPhoto.size() - 1) {
//                mViewPager.setCurrentItem(0);
//            } else {
//                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
//            }
//        }
//    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_worker_main, container, false);

        tvTest1=(TextView) view.findViewById(R.id.txtNameWorker);

//        mViewPager = (ViewPager) view.findViewById(R.id.view_pager_photo_worker_main);
//        mCircleIndicator = (CircleIndicator) view.findViewById(R.id.circle_indicator_photo_worker_main);
        // edtTest=(TextView) getActivity().findViewById(R.id.TestBundleFragment);
//        mListPhoto = getListPhotoWorker();
//        PhotoViewPagerWorkerAdapter adapter = new PhotoViewPagerWorkerAdapter(mListPhoto);
//        mViewPager.setAdapter(adapter);

//        mCircleIndicator.setViewPager(mViewPager);
//
//        mHandler.postDelayed(mRunnable, 3000);
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                mHandler.removeCallbacks(mRunnable);
//                mHandler.postDelayed(mRunnable, 3000);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

//        tvTest1=(TextView) view.findViewById(R.id.tvTest1);
//        tvTest1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getDataIntent();
//
//            }
//        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
       // getDataIntent();
        worker= new Worker();
        getDataIntent();
//        if(worker== null){
//
//        }else{
//        //    edtTest.setText(worker.getFullName().toString());
//        }
    }
    //    private List<Photo> getListPhotoWorker(){
//        List<Photo> list = new ArrayList<>();
//
//        list.add(new Photo(R.drawable.photo_worker));
//        list.add(new Photo(R.drawable.photo_worker_2));
//        list.add(new Photo(R.drawable.photo_worker_3));
//        list.add(new Photo(R.drawable.photo_worker_4));
//        return list;
//    }

    private void getDataIntent(){
        // worker= (Worker) getArguments().get("worker_ReceiveOrder");
//        if(worker == null){
//
//        }else{
//            tvTest1.setText(getArguments().getString("worker_ReceiveOrder"));
//        }


    }


//    @Override
//    public void onPause() {
//        super.onPause();
////        mHandler.removeCallbacks(mRunnable);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        mHandler.postDelayed(mRunnable,3000);
//    }
}