package com.example.lab4_fragments.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lab4_fragments.R;

public class HomeFragment extends Fragment {

    private ImageView mapImage;
    private Button viewUbiButton;
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

        viewUbiButton.setOnClickListener(v -> toggleImage());

        mapImage.setOnClickListener(v -> navigateToEdificacionesFragment());
    }

    
}