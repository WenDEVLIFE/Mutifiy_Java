package com.example.mutify_javafx;

import javafx.beans.property.SimpleStringProperty;

public class Music {
    private final SimpleStringProperty title;
    private final SimpleStringProperty artist;
    private final SimpleStringProperty year;
    private final SimpleStringProperty filePath; // Add a filePath property

    // Constructor
    public Music(String title, String artist, String year, String filePath, String filename) {
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);
        this.year = new SimpleStringProperty(year);
        this.filePath = new SimpleStringProperty(filePath);

    }

    // Getters for JavaFX properties
    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getArtist() {
        return artist.get();
    }

    public SimpleStringProperty artistProperty() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public String getYear() {
        return year.get();
    }

    public SimpleStringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        try {
            this.year.set(year);
        } catch (NumberFormatException e) {

            // Handle the case where the year is not numeric
            System.out.println("Invalid year format: " + year);
        }
    }

    public String getFilePath() {
        return filePath.get();
    }

    public SimpleStringProperty filePathProperty() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath.set(filePath);
    }

    @Override
    public String toString() {
        return "Music{" +
                "title='" + title.get() + '\'' +
                ", artist='" + artist.get() + '\'' +
                ", year=" + year.get() +
                ", filePath=" + filePath.get() +
                '}';
    }
}