package com.example.lab4_fragments.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.example.lab4_fragments.R;
import com.example.lab4_fragments.HomeActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import androidx.fragment.app.FragmentManager;

public class LoginFragment extends Fragment {

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        rootView.findViewById(R.id.btnLogin).setOnClickListener(v -> attemptLogin(rootView));

        rootView.findViewById(R.id.btnBack).setOnClickListener(v -> goBackToStart());

        return rootView;
    }

    private void attemptLogin(View rootView) {
        EditText emailEditText = rootView.findViewById(R.id.emailEditText);
        EditText passwordEditText = rootView.findViewById(R.id.passwordEditText);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (validateCredentials(email, password)) {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateCredentials(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("users.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Se espera que el formato sea: firstName,lastName,dni,phone,email:password
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String fileEmail = parts[0].split(",")[4];
                    String filePassword = parts[1];

                    if (fileEmail.equals(email) && filePassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void goBackToStart() {
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}


