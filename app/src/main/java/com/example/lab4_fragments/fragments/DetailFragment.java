package com.example.lab4_fragments.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lab4_fragments.Building;
import com.example.lab4_fragments.Comment;
import com.example.lab4_fragments.CommentAdapter;
import com.example.lab4_fragments.R;
import com.example.lab4_fragments.view_models.SharedViewModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {
    private static final String ARG_BUILDING_ID = "building_id";
    private int buildingId;
    private RecyclerView commentsRecyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private Button btnView360;
    private Button btnViewMansion;
    private EditText commentInput;
    private Button submitCommentButton;
    private RatingBar ratingBar;
    private SharedViewModel sharedViewModel;
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
        if (getArguments() != null) {
            buildingId = getArguments().getInt(ARG_BUILDING_ID);
        }
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);



        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ImageView imageView = view.findViewById(R.id.image_view);
        TextView titleTextView = view.findViewById(R.id.title_text_view);
        TextView descriptionTextView = view.findViewById(R.id.description_text_view);
        btnView360 = view.findViewById(R.id.btn_view_360);
        btnViewMansion = view.findViewById(R.id.btn_view_mansion);
        commentInput = view.findViewById(R.id.comment_input);
        submitCommentButton = view.findViewById(R.id.submit_comment_button);
        ratingBar = view.findViewById(R.id.rating_bar);

        commentsRecyclerView = view.findViewById(R.id.comments_recycler_view);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Observa la lista de edificios en el ViewModel
        sharedViewModel.getBuildingList().observe(getViewLifecycleOwner(), new Observer<List<Building>>() {
            @Override
            public void onChanged(List<Building> buildingList) {
                if (buildingList != null && buildingId < buildingList.size()) {
                    Building building = buildingList.get(buildingId);
                    titleTextView.setText(building.getTitle());
                    descriptionTextView.setText(building.getDescription());
                    imageView.setImageResource(building.getImageResId());
                }
            }
        });

        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        commentsRecyclerView.setAdapter(commentAdapter);


        loadComments();

        btnView360.setOnClickListener(v -> {
            Vista360Fragment vista360Fragment = new Vista360Fragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, vista360Fragment)
                    .addToBackStack(null)
                    .commit();
        });

        btnViewMansion.setOnClickListener(v -> {
            MansionFragment mansionFragment = new MansionFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, mansionFragment)
                    .addToBackStack(null)
                    .commit();
        });

        submitCommentButton.setOnClickListener(v -> addComment());

        return view;
    }

    private void loadComments() {
        File file = new File(getContext().getFilesDir(), "comments_" + buildingId + ".txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 3) {
                        String username = parts[0];
                        String content = parts[1];
                        float rating = Float.parseFloat(parts[2]);
                        commentList.add(new Comment(username, content, rating));
                    }
                }
                commentAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addComment() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        String loggedInUser = sharedPreferences.getString("loggedInUser", "Usuario");

        String commentText = commentInput.getText().toString().trim();
        float rating = ratingBar.getRating();
        if (!commentText.isEmpty()) {
            Comment newComment = new Comment(loggedInUser, commentText, rating);
            commentList.add(newComment);
            commentAdapter.notifyDataSetChanged();

            saveCommentToFile(loggedInUser, commentText, rating);
            commentInput.setText("");
            ratingBar.setRating(0);
        }
    }

    private void saveCommentToFile(String username, String comment, float rating) {
        File file = new File(getContext().getFilesDir(), "comments_" + buildingId + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(username + "|" + comment + "|" + rating);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
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
