package com.example.lab4_fragments.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab4_fragments.Building;
import com.example.lab4_fragments.BuildingAdapter;
import com.example.lab4_fragments.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EdificacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EdificacionesFragment extends Fragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private BuildingAdapter buildingAdapter;
    private List<Building> buildingList;

    public static EdificacionesFragment newInstance(String param1, String param2) {
        EdificacionesFragment fragment = new EdificacionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edificaciones, container, false);


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadBuildingsFromAsset(); // Cargar datos desde el archivo



        buildingAdapter = new BuildingAdapter(buildingList ,  new BuildingAdapter.OnBuildingClickListener() {
            @Override
            public void onBuildingClick(int position) {
                Building selectedBuilding = buildingList.get(position);
                int buildingId = position;
                DetailFragment detailFragment = DetailFragment.newInstance(buildingId);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView, detailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        recyclerView.setAdapter(buildingAdapter);

        return view;
    }
    private void loadBuildingsFromAsset() {
        buildingList = new ArrayList<>();
        try {
            InputStream is = getContext().getAssets().open("edificaciones.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String title = parts[0];
                    String description = parts[1];
                    int imageResId = getResourceId(parts[2]);
                    buildingList.add(new Building(title, description, imageResId));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getResourceId(String resourceName) {
        return getResources().getIdentifier(resourceName, "drawable", getContext().getPackageName());
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



}