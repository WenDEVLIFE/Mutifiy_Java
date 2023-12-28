package com.example.mutify_javafx;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

public class AlbumSection {

    private final SimpleStringProperty albumName;

    private final SimpleStringProperty musicName;

    private final SimpleStringProperty fileLocation;

    public AlbumSection(String albumName, String musicName, String fileLocation) {
        this.albumName = new SimpleStringProperty(albumName);
        this.musicName = new SimpleStringProperty(musicName);
        this.fileLocation = new SimpleStringProperty(fileLocation);
    }

    // Getter and setter for albumName
    public String getAlbumName() {
        return albumName.get();
    }

    public SimpleStringProperty albumNameProperty() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName.set(albumName);
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
