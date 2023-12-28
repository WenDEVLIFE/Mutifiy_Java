package com.music.page.Mutify.functions;

import com.example.mutify_javafx.PlaylistSection;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

public class ButtonCell2 extends TableCell<PlaylistSection, Void> {
    private final Button button;

    private static TableView<PlaylistSection> MusicTable2;
    private static ObservableList<PlaylistSection> playlistSection;

    private static TabPane MusicTabbbedPane;
    private static Tab Music;


    public ButtonCell2(String action, TableView<PlaylistSection> MusicTable2, ObservableList<PlaylistSection> playlistSection, TabPane MusicTabbbedPane, Tab Music) {
        this.button = new Button(action);

        this.button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-size: 10px; -fx-font-weight: bold;");

        this.MusicTable2 = MusicTable2;
        this.playlistSection = playlistSection;

        // Set the action for the button (e.g., play or delete)
        this.button.setOnAction(event -> {
            System.out.println("Button clicked: " + event.getSource());

            PlaylistSection playlist1 = MusicTable2.getItems().get(getIndex());
            System.out.println("Playlist name: " + playlist1);

            // Handle the button action here, for example, play or delete
            if (action.equals("Open it on Music Player")) {
                System.out.println("Open it on Music Player button clicked for: " + playlist1);
                System.out.println("Play button clicked for: " + playlist1.getPlaylistName());
                MusicTabbbedPane.getSelectionModel().select(Music);


            } else if (action.equals("Delete")) {
                // Save the playlist to the file before deleting
               playlistSection.remove(playlist1);
               MusicTable2.getItems().remove(playlist1);

                saveUpdatedPlaylistToFile(playlistSection);

                System.out.println("Delete button clicked for: " + playlist1.getPlaylistName());
            }
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || MusicTable2 == null || MusicTable2.getItems().isEmpty()) {
            setGraphic(null);
        } else if (getGraphic() == null) { // Check if the button is not already set
            setGraphic(button);
        }
    }

    // Create a button cell for the table column with the given action (e.g., play or delete) and the Playlist list
    public static Callback<TableColumn<PlaylistSection, Void>, TableCell<PlaylistSection, Void>> forTableColumn(String action, ObservableList<PlaylistSection> playlistSection, TableView<PlaylistSection> MusicTable2, TabPane MusicTabbbedPane, Tab Music) {
        return param -> new ButtonCell2(action, MusicTable2, playlistSection , MusicTabbbedPane, Music);
    }
    // Save the updated playlist to the file
    private void saveUpdatedPlaylistToFile(ObservableList<PlaylistSection> updatedPlaylist) {
        try {
            Path filePath = Path.of("src/main/resources/com/example/mutify_javafx/mymusic1/saved_details.txt");

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                for (PlaylistSection playlist : updatedPlaylist) {
                    writer.write("Playlist Name: " + playlist.getPlaylistName() + "\n");
                    writer.write("Music Name: " + playlist.getMusicName() + "\n");
                    writer.write("File Location: " + playlist.getFileLocation() + "\n\n");
                }
            }

            System.out.println("Updated details saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately in your application
        }
    }

    }

