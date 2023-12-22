package com.example.mutify_javafx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Mutify_controller {

    // My datatypes and Variables
    private Stage stage;
    @FXML
    private Pane redpanel;

    // These are my tabs
    @FXML
    private Tab Settings;

    @FXML
    private Tab Music;

    @FXML
    private Tab Playlist;

    @FXML
    private Tab  Albums;

    // My tabbedPane
    @FXML
    private TabPane MusicTabbbedPane;


    // Set the stage when initializing the controller
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void mouseExitFunction(MouseEvent event) {
                // Close the application on a single click
                Platform.exit();

    }

    @FXML
    void mouseMinimizeFunction(MouseEvent event) {
        // Minimize the application on a single click
            stage.setIconified(true);

    }


    @FXML
    // When it was focus it will turn into read, meaning alert or exit button
    void redalert(MouseEvent event) {
        redpanel.setStyle("-fx-background-color: red; -fx-text-fill: white;");
    }


    @FXML
    // To restore back to its normal color
    void restorecolor(MouseEvent event) {
        redpanel.setStyle(" -fx-background-color: #1D1D1D;");
    }

    // Side panel Button action events
    @FXML
    void Albumaction(ActionEvent event) {
        MusicTabbbedPane.getSelectionModel().select(Albums);
    }

    @FXML
    void Music_action(ActionEvent event) {
        MusicTabbbedPane.getSelectionModel().select(Music);

    }

    @FXML
    void settings_action(ActionEvent event) {
        MusicTabbbedPane.getSelectionModel().select(Settings);

    }

    @FXML
    void playlist_action(ActionEvent event) {
        MusicTabbbedPane.getSelectionModel().select(Playlist);

    }
    @FXML
    // This function is choose music or import a music on the music table
    void import_music_action(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);

        if (stage != null) {
            showFileDialog(fileChooser, stage);
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
    }

    private void showFileDialog(FileChooser fileChooser, Stage stage) {
        var selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            // Handle the selected file as needed
        } else {
            System.out.println("File selection canceled.");
        }
    }


// This are the actions on the button play, pause, restart
    @FXML
    void Pause_action(ActionEvent event) {
     System.out.println("Pause Music");
    }

    @FXML
    void Play_Action(ActionEvent event) {
        System.out.println("Play Music");
    }

    @FXML
    void Stop_Action(ActionEvent event) {
        System.out.println("Stop Music");
    }

}
