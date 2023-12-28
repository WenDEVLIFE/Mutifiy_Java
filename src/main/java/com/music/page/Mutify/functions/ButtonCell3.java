package com.music.page.Mutify.functions;

import com.example.mutify_javafx.Albums;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ButtonCell3  extends TableCell<Albums, Void> {
    private final Button button;

    private static ObservableList<Albums> albums;

    private static TableView  <Albums> AlbumTable;

    private static TabPane  MusicTabPane;

    private static Tab AlbumDisplay;

    private static Label Albumname;
    public  ButtonCell3(String action, ObservableList<Albums> albums, TableView<Albums> AlbumTable, TabPane MusicTabPane, Tab AlbumDisplay, Label Albumname) {
        this.button = new Button(action);
        ButtonCell3.albums = albums;
        ButtonCell3.AlbumTable = AlbumTable;
        ButtonCell3.MusicTabPane = MusicTabPane;
        ButtonCell3.AlbumDisplay = AlbumDisplay;
        ButtonCell3.Albumname = Albumname;

        this.button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-size: 10px; -fx-font-weight: bold;");

        // Set the action for the button (e.g., play or delete)
        this.button.setOnAction(event -> {
            System.out.println("Button clicked: " + event.getSource());

            Albums selectedAlbum = getTableView().getItems().get(getIndex());
            System.out.println("Album name: " + selectedAlbum);

            // Handle the button action here, for example, play or delete
            if (action.equals("Open")) {
                System.out.println("Play button clicked for: " + selectedAlbum.getAlbumName());
                MusicTabPane.getSelectionModel().select(AlbumDisplay);
                Albumname.setText(selectedAlbum.getAlbumName());
            } else if (action.equals("Delete")) {
                // Save the album to the file before deleting
                deleteSelectedAlbum(selectedAlbum);
                System.out.println("Delete button clicked for: " + selectedAlbum.getAlbumName());
            }
        });
    }

    private void deleteSelectedAlbum(Albums selectedAlbum) {
        albums.remove(selectedAlbum);

        // Remove the selected album from the table
        AlbumTable.getItems().remove(selectedAlbum);

        // Save the updated list to the file
        saveAlbumsToFile(albums);
    }

    private void saveAlbumsToFile(ObservableList<Albums> albums) {
        try {
            // Specify the file path where albums are stored
            Path filePath = Paths.get("src/main/resources/com/example/mutify_javafx/mymusic1/albums.txt");

            // Create or overwrite the file
            Files.write(filePath, new ArrayList<>()); // Clear the file content

            // Write each album's information to the file
            for (Albums album : albums) {
                String line = album.getAlbumName() + "," + album.getArtistName() + "," + album.getDateCreated();
                Files.write(filePath, Collections.singletonList(line), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || getTableView() == null || getTableView().getItems().isEmpty()) {
            setGraphic(null);
        } else {
            setGraphic(button);
        }
    }

    public static Callback<TableColumn<Albums, Void>, TableCell<Albums, Void>> forTableColumn(String action, ObservableList<Albums> albums, TableView<Albums> AlbumTable, TabPane MusicTabPane, Tab AlbumDisplay, Label Albumname) {
        return param -> new ButtonCell3(action, albums, AlbumTable, MusicTabPane, AlbumDisplay,Albumname);
    }
public void deleteAlbum(List<Albums>  UpdatedAlbums){
    try {
        // Specify the file path where albums are stored
        Path filePath = Paths.get("src/main/resources/com/example/mutify_javafx/mymusic1/albums.txt");

        // Create or overwrite the file
        Files.write(filePath, new ArrayList<>()); // Clear the file content

        // Write each album's information to the file
        for (Albums album :  UpdatedAlbums) {
            String line = album.getAlbumName() + "," + album.getArtistName() + "," + album.getDateCreated();
            Files.write(filePath, Collections.singletonList(line), StandardOpenOption.APPEND);
        }
    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception appropriately in your application
    }
}



}
