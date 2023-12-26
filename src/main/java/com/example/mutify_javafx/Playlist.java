package com.example.mutify_javafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Playlist {

    private final SimpleStringProperty playlistName;
    private final SimpleStringProperty dateCreated;


    public Playlist(String playlistName, String dateCreated) {

        this.playlistName = new SimpleStringProperty(playlistName);
        this.dateCreated = new SimpleStringProperty(dateCreated);
    }

    // Getter and setter for playlistName
    public  String getPlaylistName() {
        return playlistName.get();
    }

    public SimpleStringProperty playlistNameProperty() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName.set(playlistName);
    }

    // Getter and setter for dateCreated
    public String getDateCreated() {
        return dateCreated.get();
    }

    public SimpleStringProperty dateCreatedProperty() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated.set(dateCreated);
    }
}
