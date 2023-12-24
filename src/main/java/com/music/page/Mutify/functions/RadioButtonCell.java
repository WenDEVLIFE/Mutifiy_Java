package com.music.page.Mutify.functions;

import com.example.mutify_javafx.Music;
import com.example.mutify_javafx.Mutify_controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;


public class RadioButtonCell extends TableCell<Music, Boolean> {
    private final RadioButton radioButton;
    private static MediaPlayer mediaPlayer;
    private static Slider musicplay;
    private static Timeline timeline;

    private static Label Set_time_music;

    private static Label SetNowPlaying;

    private static TableView<Music>Musictable1;

    private static Slider music_adjustslider;

    public RadioButtonCell(Slider musicplay, Label Set_time_music, Label SetNowPlaying, TableView<Music> Musictable1, Slider music_adjustslider) {
        // initialize the instance
        RadioButtonCell.musicplay = musicplay;
        RadioButtonCell.Set_time_music = Set_time_music;
        RadioButtonCell.SetNowPlaying = SetNowPlaying;
        RadioButtonCell. Musictable1 =  Musictable1;
        RadioButtonCell.music_adjustslider = music_adjustslider;
        this.radioButton = new RadioButton();
        this.radioButton.setOnAction(event -> {
            Music music = getTableView().getItems().get(getIndex());
            String filePath = music.getFilePath();
            System.out.println("Radio button clicked for: " + filePath);
            if (radioButton.isSelected()) {
                System.out.println("The Set time value is"+ Set_time_music);
                System.out.println("The value is" + musicplay);
                System.out.print("The value of setnow"+ SetNowPlaying);
                System.out.print("The value of volume"+ music_adjustslider);
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
        }
    }

    // This will play the music
    public static void playMusic(Music newMusicVariable) {
        Music music = newMusicVariable;
        String filePath = music.getFilePath();
        System.out.println("Play Music" + filePath);

        try {
            File file = new File(filePath);
            if (!file.exists() || !file.canRead()) {
                throw new FileNotFoundException("Music file not found or inaccessible: " + filePath);
            }

            stopMusic();

            mediaPlayer = getMediaPlayer(music, file);
            mediaPlayer.play();
            System.out.println("Playing music: " + music.getTitle());
            SetNowPlaying.setText("Now Playing:"+music.getTitle());
            setupTimeline();

        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
        }
    }

    // When the music finish it will create the variable and tell the music played complete
    public static MediaPlayer getMediaPlayer(Music music, File file) {
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnEndOfMedia(() -> {
            System.out.println("Music playback completed: " + music.getTitle());
            mediaPlayer.dispose();
            Mutify_controller.flush_music(music);
            SetNowPlaying.setText("");

        });

        mediaPlayer.setOnError(() -> {
            System.out.println("Error during music playback: " + mediaPlayer.getError());
            mediaPlayer.dispose();
        });
        return mediaPlayer;
    }

    private static TableView<Music> Musictable1() {
        // Implement the method logic here
        TableView<Music> Musictable1 = new TableView<>();
        // Set up the table view as needed
        return Musictable1;
    }

    // This will pause the music
    public static void handlePlayPauseButton() {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
    }

    // This will Stop the music
    private static void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
            Music music = null;
            Mutify_controller.set(music);
            stopTimeline();
            SetNowPlaying.setText("");

            System.out.println("Stopping music");
        } else {
            System.out.println("No music is currently playing");
        }
    }

    // This is connected to the slider
    public static void setupTimeline() {
        System.out.println("The value is1" + musicplay);
        if (timeline == null) {
            // Create a new Timeline for updating the slider
            timeline = new Timeline(new KeyFrame(Duration.millis(2000), event -> {
                if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    // Update the slider position based on the current playback time
                    double currentTime = mediaPlayer.getCurrentTime().toMillis();
                    double totalDuration = mediaPlayer.getTotalDuration().toMillis();
                    double progress = currentTime / totalDuration;
                    musicplay.setValue(progress * 100);

                    updateDurationLabel(currentTime, totalDuration);
                }
            }));

            // Add a listener to the musicplay slider to seek the media when the user changes the slider position
            musicplay.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (mediaPlayer != null) {
                    double totalDuration = mediaPlayer.getTotalDuration().toMillis();
                    double newTime = totalDuration * (newValue.doubleValue() / 100.0);
                    mediaPlayer.seek(Duration.millis(newTime));

                }
            });

            // Listener for volume  music_adjuster
            music_adjustslider.valueProperty().addListener((observable, oldValue, newValue) -> {
                double volume = newValue.doubleValue() / 100.0; // Adjust if needed
                RadioButtonCell.setVolume(volume);
            });

            // Set the cycle count to INDEFINITE for continuous updates
            timeline.setCycleCount(Timeline.INDEFINITE);

            // Start the timeline
            timeline.play();
        }
    }

    public static void forward(){
        if (mediaPlayer != null) {
            double totalDuration = mediaPlayer.getTotalDuration().toMillis();
            double currentTime = mediaPlayer.getCurrentTime().toMillis();
            double newTime = currentTime + 5000; // Add 5000 milliseconds (5 seconds), adjust as needed

            // Make sure the new time is within the total duration
            newTime = Math.min(newTime, totalDuration);

            // Set the new time on the slider
            musicplay.setValue((newTime / totalDuration) * 100);

            // Seek the media to the new time
            mediaPlayer.seek(Duration.millis(newTime));
        }
    }

    public static void backward() {
        if (mediaPlayer != null) {
            double currentTime = mediaPlayer.getCurrentTime().toMillis();
            double newTime = currentTime - 5000; // Subtract 5000 milliseconds (5 seconds), adjust as needed

            // Make sure the new time is not negative
            newTime = Math.max(newTime, 0);

            // Set the new time on the slider
            musicplay.setValue((newTime / mediaPlayer.getTotalDuration().toMillis()) * 100);

            // Seek the media to the new time
            mediaPlayer.seek(Duration.millis(newTime));
        }
    }
    public static void stopTimeline() {
        // Stop updating the slider
        if (timeline != null) {
            timeline.stop();
            timeline = null;  // Set the timeline to null to indicate it's not active
        }
    }

    public static void updateSliderValue(double value) {
        if (musicplay != null) {
            musicplay.setValue(value);
        }
    }

    private static void updateDurationLabel(double currentTime, double totalDuration) {
        // Format the time and set it to the label
        String currentTimeStr = formatTime(currentTime);
        String totalDurationStr = formatTime(totalDuration);
        Set_time_music.setText(currentTimeStr + " / " + totalDurationStr);
    }

    private static String formatTime(double millis) {
        // Format milliseconds to HH:mm:ss
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        int hours = (int) ((millis / (1000 * 60 * 60)) % 24);

        return String.format("%02d:%02d", minutes, seconds);
    }

    public static void setVolume(double volume) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            System.out.println("System Media is Playing");
            mediaPlayer.setVolume(volume);
        }
    }
    public static Callback<TableColumn<Music, Boolean>, TableCell<Music, Boolean>> forTableColumn(Slider musicplay, Label Set_time_music, Label SetNowPlaying, TableView<Music>Musictable1, Slider music_adjustslider) {
        return param -> new RadioButtonCell(musicplay, Set_time_music, SetNowPlaying, Musictable1,music_adjustslider);
    }
}
