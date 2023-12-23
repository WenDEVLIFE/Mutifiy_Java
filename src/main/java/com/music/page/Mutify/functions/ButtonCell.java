package com.music.page.Mutify.functions;

import com.example.mutify_javafx.Music;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;


import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class ButtonCell extends TableCell<Music, Void> {
    private final Button button;
    private TableView<com.example.mutify_javafx.Music> Musictable1;
    private ObservableList <Music> MusicList = FXCollections.observableArrayList();
    public ButtonCell(String action, ObservableList<Music> MusicList,  TableView<com.example.mutify_javafx.Music> Musictable1) {
        this.button = new Button(action);
        this.Musictable1 = Musictable1;
        this.MusicList = MusicList;

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


    public static Callback<TableColumn<Music, Void>, TableCell<Music, Void>> forTableColumn(String action, ObservableList<Music> MusicList, TableView<Music> Musictable1) {
        return param -> new ButtonCell(action, MusicList, Musictable1);
    }
}