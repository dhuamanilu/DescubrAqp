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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab4_fragments.Comment;
import com.example.lab4_fragments.CommentAdapter;
import com.example.lab4_fragments.R;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {
    private static final String ARG_BUILDING_ID = "building_id";
    private int buildingId; // Identificador para el edificio seleccionado
    private RecyclerView commentsRecyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

    public static DetailFragment newInstance(int buildingId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BUILDING_ID, buildingId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Inicializa los elementos de la vista
        ImageView imageView = view.findViewById(R.id.image_view);
        TextView titleTextView = view.findViewById(R.id.title_text_view);
        TextView descriptionTextView = view.findViewById(R.id.description_text_view);


        // Inicializa el RecyclerView
        commentsRecyclerView = view.findViewById(R.id.comments_recycler_view);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializa la lista de comentarios
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        commentsRecyclerView.setAdapter(commentAdapter);

        // Cargar datos del edificio según el ID
        loadBuildingData(buildingId, imageView, titleTextView, descriptionTextView);



        return view;
    }

    private void loadBuildingData(int buildingId, ImageView imageView, TextView titleTextView, TextView descriptionTextView) {

        // Ejemplo de datos estáticos
        if (buildingId == 0) {
            titleTextView.setText("Catedral");
            descriptionTextView.setText("Santuario principal de la ciudad ocupando el lado norte de la Plaza de Armas.");
            imageView.setImageResource(R.drawable.catedral);
        } else if (buildingId == 1) {
            titleTextView.setText("Monasterio de Santa Catalina");
            descriptionTextView.setText("Este complejo turístico fue fundado en 1579.");
            imageView.setImageResource(R.drawable.monasterio);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            buildingId = getArguments().getInt(ARG_BUILDING_ID);
        }
    }
}
