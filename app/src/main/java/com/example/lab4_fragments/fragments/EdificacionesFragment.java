package com.example.lab4_fragments.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lab4_fragments.Building;
import com.example.lab4_fragments.BuildingAdapter;
import com.example.lab4_fragments.R;
import com.example.lab4_fragments.view_models.SharedViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que muestra la lista de edificaciones.
 */
public class EdificacionesFragment extends Fragment {
    private RecyclerView recyclerView;
    private BuildingAdapter buildingAdapter;
    private List<Building> buildingList;           // Lista original
    private List<Building> filteredBuildingList;   // Lista filtrada

    private EditText searchBar;
    private RadioGroup filterOptions;
    private TextView emptyView;
    private SharedViewModel sharedViewModel;
    public static EdificacionesFragment newInstance() {
        return new EdificacionesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        View view = inflater.inflate(R.layout.fragment_edificaciones, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchBar = view.findViewById(R.id.search_bar);
        filterOptions = view.findViewById(R.id.filter_options);
        emptyView = view.findViewById(R.id.empty_view);

        loadBuildingsFromAsset(); // Cargar datos desde el archivo

        filteredBuildingList = new ArrayList<>(buildingList); // Inicialmente, la lista filtrada es igual a la original

        buildingAdapter = new BuildingAdapter(filteredBuildingList, position -> {
            // Manejar el clic en el elemento
            Building selectedBuilding = filteredBuildingList.get(position);
            int buildingId = buildingList.indexOf(selectedBuilding); // Obtener el índice en la lista original
            DetailFragment detailFragment = DetailFragment.newInstance(buildingId);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView, detailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        recyclerView.setAdapter(buildingAdapter);

        // Configurar el listener para el cambio de texto en la barra de búsqueda
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita implementar
            }
        });

        // Configurar el listener para el cambio de opción en el RadioGroup
        filterOptions.setOnCheckedChangeListener((group, checkedId) -> {
            // Reiniciar el filtrado cada vez que se cambia la opción
            filter(searchBar.getText().toString());
        });

        return view;
    }

    private void filter(String text) {
        String lowerText = text.toLowerCase();
        filteredBuildingList.clear();

        int checkedId = filterOptions.getCheckedRadioButtonId();

        for (Building building : buildingList) {
            boolean matches = false;

            if (checkedId == R.id.rb_title) {
                matches = building.getTitle().toLowerCase().contains(lowerText);
            } else if (checkedId == R.id.rb_category) {
                matches = building.getCategory().toLowerCase().contains(lowerText);
            } else if (checkedId == R.id.rb_description) {
                matches = building.getDescription().toLowerCase().contains(lowerText);
            }

            if (matches) {
                filteredBuildingList.add(building);
            }
        }

        buildingAdapter.notifyDataSetChanged();

        // Mostrar u ocultar el mensaje cuando no hay resultados
        if (filteredBuildingList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    private void loadBuildingsFromAsset() {
        buildingList = new ArrayList<>();
        try {
            InputStream is = getContext().getAssets().open("edificaciones.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) { // Ahora esperamos 4 partes
                    String title = parts[0];
                    String category = parts[1]; // Leer categoría
                    String description = parts[2];
                    int imageResId = getResourceId(parts[3]);
                    buildingList.add(new Building(title, category, description, imageResId));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sharedViewModel.setBuildingList(buildingList);
    }

    private int getResourceId(String resourceName) {
        return getResources()
                .getIdentifier(resourceName, "drawable", getContext().getPackageName());
    }
}
