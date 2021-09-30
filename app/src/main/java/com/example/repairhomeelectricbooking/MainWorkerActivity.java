package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

public class MainWorkerActivity extends AppCompatActivity {
    private ViewPager viewPagerWorker;
    AHBottomNavigation bottomNavigationWorker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_worker);
        viewPagerWorker = findViewById(R.id.viewPagerWorker);
        bottomNavigationWorker = (AHBottomNavigation) findViewById(R.id.bottom_navigation_worker);
        setUpViewPaper();

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab1, R.drawable.home, R.color.bluesky);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab2, R.drawable.task, R.color.bluesky);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab3, R.drawable.notification, R.color.bluesky);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab4, R.drawable.info, R.color.bluesky);


// Add items
        bottomNavigationWorker.addItem(item1);
        bottomNavigationWorker.addItem(item2);
        bottomNavigationWorker.addItem(item3);
        bottomNavigationWorker.addItem(item4);
        bottomNavigationWorker.setColored(false);
        bottomNavigationWorker.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        bottomNavigationWorker.setAccentColor(getResources().getColor(R.color.bluesky));
        bottomNavigationWorker.setInactiveColor(getResources().getColor(R.color.bluesky));

        AHNotification notification = new AHNotification.Builder()
                .setText("10")
                .setBackgroundColor(ContextCompat.getColor(MainWorkerActivity.this, R.color.red))
                .setTextColor(ContextCompat.getColor(MainWorkerActivity.this, R.color.white))
                .build();
        bottomNavigationWorker.setNotification(notification, 2);
        getDataIntent();
        bottomNavigationWorker.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0:
                        viewPagerWorker.setCurrentItem(0);
                        break;
                    case 1:
                        viewPagerWorker.setCurrentItem(1);
                        break;
                    case 2:
                        viewPagerWorker.setCurrentItem(2);
                        break;
                    case 3:
                        viewPagerWorker.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }

    public void setUpViewPaper(){
        ViewPagerWorkerAdapter viewPagerWorkerAdapter = new ViewPagerWorkerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerWorker.setAdapter(viewPagerWorkerAdapter);

        viewPagerWorker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationWorker.setCurrentItem(0);
                        break;
                    case 1:
                        bottomNavigationWorker.setCurrentItem(1);
                        break;
                    case 2:
                        bottomNavigationWorker.setCurrentItem(2);
                        break;
                    case 3:
                        bottomNavigationWorker.setCurrentItem(3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void getDataIntent(){
        String strPhone = getIntent().getStringExtra("phonenumber");
    }
}