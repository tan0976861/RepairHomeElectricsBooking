package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.repairhomeelectricbooking.dto.Item;

public class SearchWorkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_worker);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        Item item = (Item) bundle.get("obj_item");
        TextView tvSearch = (TextView) findViewById(R.id.tv_search);
        tvSearch.setText(item.getTitle());
    }

    public void clickToBackMainScreen(View view){
        finish();
    }
}