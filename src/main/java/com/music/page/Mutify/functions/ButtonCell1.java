package com.music.page.Mutify.functions;

import com.example.mutify_javafx.Mutify_controller;
import com.example.mutify_javafx.Playlist;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class ButtonCell1 extends TableCell<Playlist, Void> {
    private final Button button;
    private static TableView<Playlist> PlaylistTable;
    private static ObservableList<Playlist> playlist;

    private static Label PlayListName;

    private static Tab CreatePlayList;
    private static TabPane MusicTabbbedPane;
    public ButtonCell1(String action, TableView<Playlist>  PlaylistTable, ObservableList<Playlist> playlist, Label PlayListName, Tab CreatePlayList, TabPane MusicTabbbedPane) {
        ButtonCell1.PlaylistTable =  PlaylistTable;
        ButtonCell1.playlist = playlist;
        ButtonCell1.PlayListName = PlayListName;
        ButtonCell1.CreatePlayList = CreatePlayList;
        ButtonCell1.MusicTabbbedPane = MusicTabbbedPane;
        this.button = new Button(action);
        this.button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-size: 10px; -fx-font-weight: bold;");
        // Set the action for the button (e.g., play or delete)
        this.button.setOnAction(event -> {
            Playlist playlist1 =  PlaylistTable.getItems().get(getIndex());
            // Handle the button action here, for example, play or delete
            if (action.equals("Play")) {
                System.out.println("The value of MusicTabbbedPane is " + MusicTabbbedPane);
                System.out.println("The value of CreatePlayList is " + CreatePlayList);
                System .out.println("the value of " + PlaylistTable);
                PlayListName.setText(playlist1.getPlaylistName());
                MusicTabbbedPane.getSelectionModel().select(CreatePlayList);
                String playlistname = PlayListName.getText();
         
                System.out.println("Play button clicked for: " + playlist1.getPlaylistName());

            } else if (action.equals("Delete")) {
                // Delete action
                playlist.remove(playlist1);
                PlaylistTable.setItems(playlist);
                savePlaylistToFile();
                System.out.println("Delete button clicked for: " + playlist1.getPlaylistName());
            }
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || PlaylistTable == null || PlaylistTable.getItems().isEmpty()) {
            setGraphic(null);
        } else if (getGraphic() == null) { // Check if the button is not already set
            setGraphic(button);
        }
    }

    // Create a button cell for the table column with the given action (e.g., play or delete) and the Playlist list
    public static Callback<TableColumn<Playlist, Void>, TableCell<Playlist, Void>> forTableColumn(String action, ObservableList<Playlist> playlist, TableView<Playlist>  PlaylistTable, Label PlayListName, Tab CreatePlayList, TabPane MusicTabbbedPane) {
        return param -> new ButtonCell1(action,  PlaylistTable, playlist,PlayListName,CreatePlayList,MusicTabbbedPane);
    }

    private void savePlaylistToFile() {
        try {
            // Convert Playlist objects to lines and write to the file
            List<String> lines = playlist.stream()
                    .map(p -> String.join(",", p.getPlaylistName(), p.getDateCreated()))
                    .collect(Collectors.toList());

            Files.write(Paths.get("src/main/resources/com/example/mutify_javafx/mymusic1/playlist.txt"), lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
