package com.music.page.Mutify.functions;

import com.example.mutify_javafx.Music;
import com.example.mutify_javafx.Playlist;
import com.example.mutify_javafx.PlaylistSection;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class CustomTableCellFactory2 {

    public static TableCell<PlaylistSection, String> cellFactoryForString(TableColumn<PlaylistSection, String> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setAlignment(Pos.CENTER);
            }
        };
    }

    public static TableCell<PlaylistSection, Integer> cellFactoryForInteger(TableColumn<PlaylistSection, Integer> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.toString());
                setAlignment(Pos.CENTER);
            }
        };
    }

    public static TableCell<PlaylistSection, String> createCenteredStringCell(TableColumn<PlaylistSection, String> column) {
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
