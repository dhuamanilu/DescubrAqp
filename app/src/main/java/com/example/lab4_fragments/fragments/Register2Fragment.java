package com.example.lab4_fragments.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lab4_fragments.R;

public class Register2Fragment extends Fragment {

    public Register2Fragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register2, container, false);

        rootView.findViewById(R.id.btnBack).setOnClickListener(v -> goBackToRegister1());
        rootView.findViewById(R.id.btnFinish).setOnClickListener(v -> goBackToStart());

        return rootView;
    }

    private void goBackToRegister1() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void goBackToStart() {
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
