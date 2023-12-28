package com.music.page.Mutify.functions;

import com.example.mutify_javafx.Albums;
import com.example.mutify_javafx.Playlist;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class CustomTableCellFactory3 {

    public static TableCell<Albums, String> cellFactoryForString(TableColumn<Albums, String> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setAlignment(Pos.CENTER);
            }
        };
    }

    public static TableCell<Albums, Integer> cellFactoryForInteger(TableColumn<Albums, Integer> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.toString());
                setAlignment(Pos.CENTER);
            }
        };
    }

    public static TableCell<Albums, String> createCenteredStringCell(TableColumn<Albums, String> column) {
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
