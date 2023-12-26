package com.music.page.Mutify.functions;

import com.example.mutify_javafx.PlaylistSection;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ButtonCell2 extends TableCell<PlaylistSection, Void> {
    private final Button button;

    private static TableView<PlaylistSection> MusicTable2;
    private static ObservableList<PlaylistSection> playlistSection;

    public ButtonCell2(String action, TableView<PlaylistSection> MusicTable2, ObservableList<PlaylistSection> playlistSection) {
        this.button = new Button(action);

        this.button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-size: 10px; -fx-font-weight: bold;");

        this.MusicTable2 = MusicTable2;
        this.playlistSection = playlistSection;

        // Set the action for the button (e.g., play or delete)
        this.button.setOnAction(event -> {
            PlaylistSection playlist1 = MusicTable2.getItems().get(getIndex());
            // Handle the button action here, for example, play or delete
            if (action.equals("Play")) {
                System.out.println("Play button clicked for: " + playlist1.getPlaylistName());
            } else if (action.equals("Delete")) {
                // Save the playlist to the file before deleting
                savePlaylistToFile();
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
    public static Callback<TableColumn<PlaylistSection, Void>, TableCell<PlaylistSection, Void>> forTableColumn(String action, ObservableList<PlaylistSection> playlistSection, TableView<PlaylistSection> MusicTable2) {
        return param -> new ButtonCell2(action, MusicTable2, playlistSection);
    }

    private void savePlaylistToFile() {
        try {
            // Convert PlaylistSection objects to lines and write to the file
            Files.write(Paths.get("src/main/resources/com/example/mutify_javafx/mymusic1/playlist.txt"), playlistSection.stream()
                    .map(p -> String.join(",", p.getPlaylistName(), p.getMusicName(), p.getFileLocation()))
                    .collect(java.util.stream.Collectors.toList()));
            System.out.println("Playlist saved to file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
