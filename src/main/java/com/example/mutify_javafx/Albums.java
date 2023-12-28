package com.example.mutify_javafx;

import javafx.beans.property.SimpleStringProperty;

public class Albums {
    private final SimpleStringProperty albumName;

    private final SimpleStringProperty artistName;

    private final SimpleStringProperty dateCreated;

    public Albums(String albumName, String artistName, String dateCreated) {
        this.albumName = new SimpleStringProperty(albumName);
        this.artistName = new SimpleStringProperty(artistName);
        this.dateCreated = new SimpleStringProperty(dateCreated);
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

    // Getter and setter for artistName
    public String getArtistName() {
        return artistName.get();
    }

    public SimpleStringProperty artistNameProperty() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName.set(artistName);
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
