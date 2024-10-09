package com.example.lab4_fragments.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lab4_fragments.R;

public class Register1Fragment extends Fragment {

    public Register1Fragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View rootView = inflater.inflate(R.layout.fragment_register1, container, false);

        // Configurar los botones para navegar
        rootView.findViewById(R.id.btnNext).setOnClickListener(v -> goToRegister2());
        rootView.findViewById(R.id.btnBack).setOnClickListener(v -> goBackToStart());

        return rootView;
    }

    private void goToRegister2() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new Register2Fragment())
                .addToBackStack(null)
                .commit();
    }

    private void goBackToStart() {
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
