package com.example.lab4_fragments;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lab4_fragments.fragments.DetailFragment;
import com.example.lab4_fragments.fragments.EdificacionesFragment;
import com.example.lab4_fragments.fragments.HomeFragment;
import com.example.lab4_fragments.fragments.MapaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;
    private HomeFragment homeFragment = null;
    private EdificacionesFragment  edificacionesFragment = null;
    private MapaFragment mapaFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fragmentManager = getSupportFragmentManager();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_home){
                    homeFragment = HomeFragment.newInstance("","");
                    loadFragment(homeFragment);
                    return true;
                }
                else if(item.getItemId()==R.id.menu_edificaciones){
                    edificacionesFragment = EdificacionesFragment.newInstance("","");
                    loadFragment(edificacionesFragment);
                    return true;
                }
                else return false;
            }
        });
    }
    private void loadFragment(Fragment fragment){
        if(fragmentManager!=null){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView,fragment);
            fragmentTransaction.commit();
        }
    }
}