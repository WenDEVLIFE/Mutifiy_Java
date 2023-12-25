package com.music.page.Mutify.functions;

import com.example.mutify_javafx.Music;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;


import javafx.scene.control.Button;
import javafx.scene.control.TableCell;


import javafx.scene.control.TableView;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ButtonCell extends TableCell<Music, Void> {
    private final Button button;
    private TableView<Music> Musictable1;
    private ObservableList<Music> MusicList;
    private final String store_filelocations;

    public ButtonCell(String action, ObservableList<Music> MusicList, TableView<Music> Musictable1,String store_filelocations) {
        this.button = new Button(action);
        this.Musictable1 = Musictable1;
        this.MusicList = MusicList;
        this.store_filelocations = store_filelocations;
        this.button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-size: 10px; -fx-font-weight: bold;");
        // Set the action for the button (e.g., play or delete)
        this.button.setOnAction(event -> {
            Music music = Musictable1.getItems().get(getIndex());
            // Handle the button action here, for example, play or delete
            if (action.equals("Play")) {
                // Play action
                System.out.println("Play button clicked for: " + music.getTitle());
            } else if (action.equals("Delete")) {
                // Delete action
                System.out.println("Delete button clicked for: " + music.getTitle());
                if (MusicList != null) {
                    MusicList.remove(music);
                    Musictable1.setItems(MusicList);

                    // After removing from the list, update the file
                    saveMusicToFile(store_filelocations);
                }
            }
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || MusicList == null || MusicList.isEmpty()) {
            setGraphic(null);
        } else {
            setGraphic(button);
        }
    }

    public static Callback<TableColumn<Music, Void>, TableCell<Music, Void>> forTableColumn(String action, ObservableList<Music> MusicList, TableView<Music> Musictable1, String store_filelocations) {
        return param -> new ButtonCell(action, MusicList, Musictable1,store_filelocations);
    }

    private void saveMusicToFile(String filePath) {
        try {
            // Convert Music objects to lines and write to the file
            List<String> lines = MusicList.stream()
                    .map(music -> String.join(",",music.getTitle(), music.getArtist(), music.getYear(), music.getFilePath()))
                    .collect(Collectors.toList());

            Files.write(Paths.get(filePath), lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
