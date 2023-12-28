package com.music.page.Mutify.functions;

import com.example.mutify_javafx.AlbumSection;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ButtonCell4 extends TableCell<AlbumSection, Void> {
    private final Button button;

    private static TableView<AlbumSection> AlbumSelection;
    private static ObservableList<AlbumSection> albumSection;

    private static TabPane MusicTabbbedPane;

    private static Tab Music;

    public ButtonCell4(String action, TableView<AlbumSection> AlbumSelection, ObservableList<AlbumSection> albumSection, TabPane MusicTabbbedPane, Tab Music) {
        this.button = new Button(action);
        this.button.setPrefWidth(100);
        ButtonCell4.MusicTabbbedPane = MusicTabbbedPane;
        ButtonCell4.Music = Music;
        ButtonCell4.AlbumSelection = AlbumSelection;
        ButtonCell4.albumSection = albumSection;

        this.button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-size: 10px; -fx-font-weight: bold;");

        this.button.setOnAction(event -> {
            System.out.println("Button clicked: " + event.getSource());

            AlbumSection selectedAlbum = getTableView().getItems().get(getIndex());
            System.out.println("Album name: " + selectedAlbum);

            // Print additional statements to check the flow
            System.out.println("Action: " + action);
            System.out.println("Is MusicTabbbedPane null? " + (MusicTabbbedPane == null));
            System.out.println("Is Music null? " + (Music == null));

            // Handle the button action here, for example, play or delete
            if (action.equals("Open it on a music player")) {
                Platform.runLater(() -> {
                    MusicTabbbedPane.getSelectionModel().select(Music);
                    System.out.println("Play button clicked for: " + selectedAlbum.getAlbumName());
                });
            } else if (action.equals("Delete")) {
                // Save the album to the file before deleting
                albumSection.remove(selectedAlbum);

                // Delete the AlbumSection from the TableView
                AlbumSelection.getItems().remove(selectedAlbum);

                // Save the updated list to the file (optional)
                saveAlbumSectionsToFile(albumSection);
                System.out.println("Delete button clicked for: " + selectedAlbum.getAlbumName());
            }
        });
    }

    private void saveAlbumSectionsToFile(ObservableList<AlbumSection> albumSection) {
        try{
            Path filePath = Paths.get("src/main/resources/com/example/mutify_javafx/mymusic1/saveAlbum.txt");
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                // Write details to the file
                for (AlbumSection albumSection1 : albumSection) {
                    String line = albumSection1.getAlbumName() + "," + albumSection1.getMusicName() + "," + albumSection1.getFileLocation();
                    writer.write(line);
                    writer.newLine();
                }
            }
        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(button);
        }
    }

    public static Callback<TableColumn<AlbumSection, Void>, TableCell<AlbumSection, Void>> forTableColumn(String action, TableView<AlbumSection> AlbumSelection, ObservableList<AlbumSection> albumSection, TabPane MusicTabbbedPane, Tab Music) {
        return param -> new ButtonCell4(action, AlbumSelection, albumSection, MusicTabbbedPane, Music);
    }
}