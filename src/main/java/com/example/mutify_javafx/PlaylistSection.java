package com.example.mutify_javafx;

import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.SimpleStringProperty;

public class PlaylistSection {
    private final SimpleStringProperty playlistName;
    private final SimpleStringProperty musicName;
    private final SimpleStringProperty fileLocation;

    public PlaylistSection(String playlistName, String musicName, String fileLocation) {
        this.playlistName = new SimpleStringProperty(playlistName);
        this.musicName = new SimpleStringProperty(musicName);
        this.fileLocation = new SimpleStringProperty(fileLocation);
    }

    // Getter and setter for playlistName
    public String getPlaylistName() {
        return playlistName.get();
    }

    public SimpleStringProperty playlistNameProperty() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName.set(playlistName);
    }

    // Getter and setter for musicName
    public String getMusicName() {
        return musicName.get();
    }

    public SimpleStringProperty musicNameProperty() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName.set(musicName);
    }

    // Getter and setter for fileLocation
    public String getFileLocation() {
        return fileLocation.get();
    }

    public SimpleStringProperty fileLocationProperty() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation.set(fileLocation);
    }
}