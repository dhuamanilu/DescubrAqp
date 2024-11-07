package com.example.lab4_fragments;

public class Building {
    private String title;
    private String category; // Nuevo campo
    private String description;
    private int imageResId;

    public Building(String title, String category, String description, int imageResId) {
        this.title = title;
        this.category = category; // Inicializar nuevo campo
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category; // Getter para categoría
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }
}
