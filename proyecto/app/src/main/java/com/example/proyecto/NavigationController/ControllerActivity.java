package com.example.proyecto.NavigationController;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyecto.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//Clase para gestionar la navegacion entre los fragments
public class ControllerActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private ProfileFragment profileFragment;
    private InboxFragment inboxFragment;
    private HomeFragmentTeam homeFragmentTeam;
    private InboxFragmentTeam inboxFragmentTeam;
    private ProfileFragmentTeam profileFragmentTeam;
    private CreateOfertaFragment createOfertaFragment;
    boolean jugador = false;

    //Metodo para obtener el tipo del shared preferences
    private String obtenerTipo() {
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tipo = preferences.getString("tipo", "def");
        Log.d("gla", tipo);
        return tipo;
    }

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
        homeFragmentTeam = new HomeFragmentTeam();
        inboxFragmentTeam = new InboxFragmentTeam();
        profileFragmentTeam = new ProfileFragmentTeam();
        createOfertaFragment = new CreateOfertaFragment();

        if (obtenerTipo().equalsIgnoreCase("jugador")){
            jugador = true;
        }

        //DEFAULT FRAGMENT
        if (jugador){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, homeFragment);
            fragmentTransaction.commit();
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, homeFragmentTeam);
            fragmentTransaction.commit();
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home :
                        if (jugador) {
                            InitializeFragment(homeFragment);
                        } else {
                            InitializeFragment(homeFragmentTeam);
                        }
                        return true;

                    case R.id.navigation_inbox :
                        if (jugador) {
                            InitializeFragment(inboxFragment);
                        } else {
                            InitializeFragment(inboxFragmentTeam);
                        }
                        return true;

                    case R.id.navigation_notification :
                        if (jugador) {
                            InitializeFragment(notificationFragment);
                        }else {
                            InitializeFragment(createOfertaFragment);
                        }
                        return true;

                    case R.id.navigation_profile :
                        if (jugador) {
                            InitializeFragment(profileFragment);
                        } else {
                            InitializeFragment(profileFragmentTeam);
                        }
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
