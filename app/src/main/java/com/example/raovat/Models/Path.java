package com.example.raovat.Models;

public class Path {
    private boolean isSelected;
    private String path;

    public Path(boolean isSelected, String path) {
        this.isSelected = isSelected;
        this.path = path;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void changeStatus() {
        isSelected = !isSelected;
    }

    public String getPath() {
        return path;
    }

}
