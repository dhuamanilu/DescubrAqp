package com.example.lab4_fragments.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.lab4_fragments.LoginActivity;

import com.example.lab4_fragments.R;

public class HomeFragment extends Fragment {

    private ImageView mapImage;
    private Button viewUbiButton;
    private Button logoutButton;
    private boolean isShowingMap = true;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapImage = view.findViewById(R.id.mapImage);
        viewUbiButton = view.findViewById(R.id.viewUbi);
        logoutButton = view.findViewById(R.id.logoutButton);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        String loggedInUser = sharedPreferences.getString("loggedInUser", "Usuario");

        viewUbiButton.setOnClickListener(v -> toggleImage());

        mapImage.setOnClickListener(v -> navigateToDetailFragment());

        logoutButton.setOnClickListener(v -> logout());
    }

    private void toggleImage() {
        if (isShowingMap) {
            mapImage.setImageResource(R.drawable.ubicacion);
            viewUbiButton.setText("Ver mapa");
        } else {
            mapImage.setImageResource(R.drawable.mapimage);
            viewUbiButton.setText("Ver mi ubicación");
        }
        isShowingMap = !isShowingMap;
    }

    private void navigateToDetailFragment() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, new DetailFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void logout() {
        // Eliminar el usuario logueado de SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("loggedInUser");  
        editor.apply();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
