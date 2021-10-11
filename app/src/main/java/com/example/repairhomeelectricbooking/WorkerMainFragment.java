package com.example.repairhomeelectricbooking;

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
    private ImageView imgUpdateWorker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worker_main, container, false);


        return view;
    }


}