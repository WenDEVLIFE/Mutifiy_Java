package com.music.page.Mutify.functions;

import com.example.mutify_javafx.AlbumSection;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class CustomTableCellFactory4 {

    public static TableCell<AlbumSection, String> cellFactoryForString(TableColumn<AlbumSection, String> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setAlignment(Pos.CENTER);
            }
        };
    }

    public static TableCell<AlbumSection, Integer> cellFactoryForInteger(TableColumn<AlbumSection, Integer> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.toString());
                setAlignment(Pos.CENTER);
            }
        };
    }

    public static TableCell<AlbumSection, String> createCenteredStringCell(TableColumn<AlbumSection, String> column) {
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

