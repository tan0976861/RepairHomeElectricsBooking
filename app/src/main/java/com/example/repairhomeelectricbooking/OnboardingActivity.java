package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.relex.circleindicator.CircleIndicator;

public class OnboardingActivity extends AppCompatActivity {

    private TextView txtSkip;
    private ViewPager viewPager;
    private RelativeLayout layout_bottom;
    private CircleIndicator circleIndicator;
    private LinearLayout layoutNext;
    private ViewPagerAdapterOnBoarding viewPagerAdapterOnBoarding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        intUI();
        viewPagerAdapterOnBoarding = new ViewPagerAdapterOnBoarding(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapterOnBoarding);

        circleIndicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 3){
                    txtSkip.setVisibility(View.GONE);
                    layout_bottom.setVisibility(View.GONE);
                }else{
                    txtSkip.setVisibility(View.VISIBLE);
                    layout_bottom.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void intUI(){
        txtSkip = (TextView) findViewById(R.id.txtSkip);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        layout_bottom = (RelativeLayout) findViewById(R.id.layout_bottom);
        circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);
        layoutNext = (LinearLayout) findViewById(R.id.layout_next);

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
            }
        });

        layoutNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() < 3){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });
    }
}