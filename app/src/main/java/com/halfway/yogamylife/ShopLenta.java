package com.halfway.yogamylife;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class ShopLenta extends AppCompatActivity {

    private static final String TAG = ShopLenta.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private int currentPageId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_lenta);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.menu_nav);
        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        int fragmentValue = intent.getIntExtra("fragment", 0);
        if(fragmentValue == 33) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, new ProfilFragment())
                    .commit();
            toolbar.setTitle(R.string.profile);
            bottomNavigation.setSelectedItemId(R.id.action_profil);
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, new ShopFragment())
                    .commit();
        }

        setSupportActionBar(toolbar);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (currentPageId == item.getItemId()) {
                    return false;
                } else {
                    currentPageId = item.getItemId();
                    switch (id) {
                        case R.id.action_profil:
                            fragment = new ProfilFragment();
                            toolbar.setTitle(getResources().getString(R.string.profile));
                            break;
                        case R.id.action_shop:
                            fragment = new ShopFragment();
                            toolbar.setTitle(getResources().getString(R.string.yoga_shop));
                            break;
                    }

                    final FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_container, fragment).commit();
                    return true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_lenta_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
