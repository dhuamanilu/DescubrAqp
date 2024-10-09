package com.example.lab4_fragments.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lab4_fragments.R;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // Configurar el botón para regresar al inicio
        rootView.findViewById(R.id.btnBack).setOnClickListener(v -> goBackToStart());

        return rootView;
    }

    private void goBackToStart() {
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
