package com.music.page.Mutify.functions;

import com.example.mutify_javafx.Music;
import com.example.mutify_javafx.Mutify_controller;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;

public class RadioButtonCell extends TableCell<Music, Boolean> {
    private final RadioButton radioButton;
    private String currentFilePath; // Define this variable in your class
    private static MediaPlayer mediaPlayer;

    public RadioButtonCell() {
        this.radioButton = new RadioButton();
        this.radioButton.setOnAction(event -> {
            Music music = getTableView().getItems().get(getIndex());
            // Handle the radio button action here, if needed
            String filePath = music.getFilePath();
            System.out.println("Radio button clicked for: " + filePath);
            if (radioButton.isSelected()) {
                Mutify_controller.set(music);
            } else {
                stopMusic();
            }
        });
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(radioButton);
            // Set the state of the radio button based on the item in your Music object
            // For example, if you have a boolean property named "selected" in Music class:
            // radioButton.setSelected(music.isSelected());
        }
    }

    public static void playMusic(Music newMusicVariable) {
        Music music = newMusicVariable;
        String filePath = music.getFilePath();
        System.out.println("Play Music"+filePath);
        try {
            File file = new File(filePath);
            if (!file.exists() || !file.canRead()) {
                throw new FileNotFoundException("Music file not found or inaccessible: " + filePath);
            }

            mediaPlayer = getMediaPlayer(music, file); // Use the class-level mediaPlayer

            mediaPlayer.play();
            System.out.println("Playing music: " + music.getTitle());
        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
            // Handle the exception appropriately
        }
    }


    private static MediaPlayer getMediaPlayer(Music music, File file) {
        Media media = new Media(file.toURI().toString());
       mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnEndOfMedia(() -> {
            System.out.println("Music playback completed: " + music.getTitle());
            mediaPlayer.dispose(); // Release resources
        });

        mediaPlayer.setOnError(() -> {
            System.out.println("Error during music playback: " + mediaPlayer.getError());
            mediaPlayer.dispose(); // Release resources on error
        });
        return mediaPlayer;
    }

    private void stopMusic() {
        // Stop the music
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
            // to clear the location from the new variable
            Music music = null;
            Mutify_controller.set(music);
            System.out.println("Stopping music");
        } else {
            System.out.println("No music is currently playing");
        }
    }

    public static Callback<TableColumn<Music, Boolean>, TableCell<Music, Boolean>> forTableColumn() {
        return param -> new RadioButtonCell();
    }
}