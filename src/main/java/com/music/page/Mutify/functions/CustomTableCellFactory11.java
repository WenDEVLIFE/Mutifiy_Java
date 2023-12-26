package com.music.page.Mutify.functions;

import com.example.mutify_javafx.Music;
import com.example.mutify_javafx.Playlist;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class CustomTableCellFactory11 {

    public static TableCell<Playlist, String> cellFactoryForString(TableColumn<Playlist, String> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setAlignment(Pos.CENTER);
            }
        };
    }

    public static TableCell<Playlist, Integer> cellFactoryForInteger(TableColumn<Playlist, Integer> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.toString());
                setAlignment(Pos.CENTER);
            }
        };
    }

    public static TableCell<Playlist, String> createCenteredStringCell(TableColumn<Playlist, String> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setAlignment(Pos.CENTER);

            }
        };
    }
}
