package com.example.lab4_fragments.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab4_fragments.Building;
import com.example.lab4_fragments.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EdificacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EdificacionesFragment extends Fragment {

    private RecyclerView recyclerView;
    private BuildingAdapter buildingAdapter;
    private List<Building> buildingList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_building_list, container, false);

        // Inicializar RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar lista de edificaciones
        buildingList = new ArrayList<>();
        buildingList.add(new Building("Catedral", "Santuario principal de la ciudad ocupando el lado norte de la Plaza de Armas", R.drawable.catedral));
        buildingList.add(new Building("Monasterio de Santa Catalina", "Una pequeña ciudadela que ocupa un área de 20 mil metros cuadrados", R.drawable.santa_catalina));
        buildingList.add(new Building("Molino de Sabandía", "Una construcción colonial donde se molían trigo y maíz", R.drawable.molino_sabandia));

        // Configurar adaptador
        buildingAdapter = new BuildingAdapter(buildingList);
        recyclerView.setAdapter(buildingAdapter);

        return view;
    }
}