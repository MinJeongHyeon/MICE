package com.makguksu.mice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final HomePage homepage = new HomePage();
    private final RecommendationPage recommendationpage = new RecommendationPage();
    private final MyPage mypage = new MyPage();
    private long lastTimeBackPressed;
    public static Activity activity;


    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setSelectedItemId(R.id.HomePage);

        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame,homepage).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.HomePage: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, homepage).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.RecommendationPage: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, recommendationpage).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.MyPage: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, mypage).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - lastTimeBackPressed < 2000) {
            finish();
            return;
        }
        Toast.makeText(this, "버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();


    }
}