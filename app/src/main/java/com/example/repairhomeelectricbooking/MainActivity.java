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
import com.example.repairhomeelectricbooking.adapter.ViewPagerAdapter;
import com.example.repairhomeelectricbooking.adapter.ItemAdapter;
import com.example.repairhomeelectricbooking.fcm.FcmNotificationsSender;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    AHBottomNavigation bottomNavigation;
    private RecyclerView rcv_item;
    private ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        setUpViewPaper();

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab1, R.drawable.home, R.color.black);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab2, R.drawable.task, R.color.black);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab3, R.drawable.notification, R.color.black);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab4, R.drawable.info, R.color.black);


// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.setColored(false);
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        bottomNavigation.setAccentColor(getResources().getColor(R.color.black));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.black));

        AHNotification notification = new AHNotification.Builder()
                .setText("10")
                .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                .build();
        bottomNavigation.setNotification(notification, 2);
        getDataIntent();
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                    case 3:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
//        FcmNotificationsSender notificationsSender = new FcmNotificationsSender("cC841uQ5RJuyoRxtj30LXe:APA91bGofn0rJWgVUMa4cMC00DeiNPiINuuFHqcRU87KhxR9VUUStfIESuzoBxBcCiX1HibrEZpHQfDp2d7WFg4HeieSoc5LsWglZ45vUKqidpfcIjUdL5BWG2LOmkvDnyr_1kUvjnKp",
//                "TNT3",
//                "Notification",getApplicationContext(),MainActivity.this);
//        notificationsSender.SendNotifications();
    }

    public void setUpViewPaper(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigation.setCurrentItem(0);
                        break;
                    case 1:
                        bottomNavigation.setCurrentItem(1);
                        break;
                    case 2:
                        bottomNavigation.setCurrentItem(2);
                        break;
                    case 3:
                        bottomNavigation.setCurrentItem(3);
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