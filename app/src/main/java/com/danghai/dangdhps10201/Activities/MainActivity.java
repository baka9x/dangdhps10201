package com.danghai.dangdhps10201.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.danghai.dangdhps10201.Fragment.BillFragment;
import com.danghai.dangdhps10201.Fragment.BookFragment;
import com.danghai.dangdhps10201.Fragment.MoreFragment;
import com.danghai.dangdhps10201.Fragment.RevenueFragment;
import com.danghai.dangdhps10201.Fragment.TopBookSellerFragment;
import com.danghai.dangdhps10201.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNav;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ánh xạ
        init();
        //Run RevenueFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new RevenueFragment()).commit();

            bottomNav.setSelectedItemId(R.id.nav_revenue);

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_book:
                            selectFragment = new BookFragment();
                            break;
                        case R.id.nav_bill:
                            selectFragment = new BillFragment();
                            break;
                        case R.id.nav_revenue:
                            selectFragment = new RevenueFragment();
                            break;
                        case R.id.nav_bestseller:
                            selectFragment = new TopBookSellerFragment();
                            break;
                        case R.id.nav_more:
                            selectFragment = new MoreFragment();
                            break;
                    }

                    if (menuItem.getItemId() == R.id.nav_more){
                        toolbar.setVisibility(View.GONE);
                    }else{
                        toolbar.setVisibility(View.VISIBLE);
                    }

                        assert selectFragment != null;
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
                        return true;

                }
            };

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trang chủ");
        bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        auth = FirebaseAuth.getInstance();

    }



}
