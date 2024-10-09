package com.example.lab4_fragments.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab4_fragments.Building;
import com.example.lab4_fragments.BuildingAdapter;
import com.example.lab4_fragments.R;

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
        // Asegúrate de que estés inflando el XML correcto
        View view = inflater.inflate(R.layout.fragment_edificaciones, container, false);

        // Inicializa RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializa lista de edificaciones (ejemplo)
        buildingList = new ArrayList<>();
        buildingList.add(new Building("Catedral", "Santuario principal de la ciudad ocupando el lado norte de la Plaza de Armas", R.drawable.catedral));
        buildingList.add(new Building("Monasterio de Santa Catalina", "Una pequeña ciudadela que ocupa un área de 20 mil metros cuadrados", R.drawable.monasterio));
        buildingList.add(new Building("Molino de Sabandía", "Una construcción colonial donde se molían trigo y maíz", R.drawable.molino));

        // Configura el adaptador
        buildingAdapter = new BuildingAdapter(buildingList);
        recyclerView.setAdapter(buildingAdapter);

        return view;
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