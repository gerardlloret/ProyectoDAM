package com.example.proyecto.NavigationController;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyecto.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ControllerActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private ProfileFragment profileFragment;
    private InboxFragment inboxFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);

        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        profileFragment = new ProfileFragment();
        inboxFragment = new InboxFragment();

        //DEFAULT FRAGMENT
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, homeFragment);
        fragmentTransaction.commit();

        /*if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home); // change to whichever id should be default
        }*/

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home :
                        InitializeFragment(homeFragment);
                        return true;

                    case R.id.navigation_inbox :
                        InitializeFragment(inboxFragment);
                        return true;

                    case R.id.navigation_notification :
                        InitializeFragment(notificationFragment);
                        return true;

                    case R.id.navigation_profile :
                        InitializeFragment(profileFragment);
                        return true;

                }

                return false;
            }
        });

    }

    private void InitializeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
