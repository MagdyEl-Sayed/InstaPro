package com.example.gm7.instaproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.gm7.instaproject.DataManager.Constants;
import com.example.gm7.instaproject.Fragments.HomeFragment;
import com.example.gm7.instaproject.Fragments.ProfileFragment;
import com.example.gm7.instaproject.Fragments.SearchFragment;

public class Home extends AppCompatActivity {
    private BottomNavigationView mBottomNav;
    private int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        selectFragment(mBottomNav.getMenu().getItem(0));
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Constants.SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            //selectFragment(homeItem);
        } else {
            super.onBackPressed();
        }
    }

    private void selectFragment(MenuItem item) {
        Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.menu_home:
                frag = new HomeFragment();
                break;
            case R.id.menu_search:
                frag = new SearchFragment();
                break;
            case R.id.menu_notifications:

                break;
            case R.id.menu_profile:
                frag = new ProfileFragment();
                break;
        }

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag);
            ft.commit();

        }
    }

}