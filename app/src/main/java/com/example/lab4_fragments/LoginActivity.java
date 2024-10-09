package com.example.lab4_fragments;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.lab4_fragments.fragments.LoginFragment;
import com.example.lab4_fragments.fragments.Register1Fragment;
import com.example.lab4_fragments.fragments.Register2Fragment;
import com.example.lab4_fragments.fragments.StartFragment;

public class LoginActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private StartFragment startFragment;
    private Register1Fragment register1Fragment;
    private Register2Fragment register2Fragment;
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fragmentManager = getSupportFragmentManager();

        // Cargar el fragmento de inicio inicialmente
        if (savedInstanceState == null) {
            loadFragment(new StartFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.addToBackStack(null);  // Agrega el fragmento a la pila de retroceso
        fragmentTransaction.commit();
    }
}
