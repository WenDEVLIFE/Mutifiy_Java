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
    // This is the radio button cell that will be used for the music table view
    private final RadioButton radioButton;
    private static MediaPlayer mediaPlayer;
    private static Slider musicplay;

    private static  Slider musicplay1;
    private static Timeline timeline;

    private static Label Set_time_music;
    private static Label Set_time_music1;

    private static Label SetNowPlaying;
    private static Label SetNowPlaying1;

    private static TableView<Music>Musictable1;

    private static Slider music_adjustslider;

    private  static  Slider SLIDERMUSIC;

    private  static  Slider SLIDERMUSIC1;


    public RadioButtonCell(Slider musicplay, Label Set_time_music, Label SetNowPlaying, TableView<Music> Musictable1, Slider music_adjustslider, Slider SLIDERMUSIC, Slider SLIDERMUSIC1, Label SetNowPlaying1, Slider musicplay1, Label Set_time_music1) {
        // initialize the instance
        // This will set the radio button to the cell when the cell is created and will set the action for the radio button
        RadioButtonCell.musicplay = musicplay;
        RadioButtonCell.Set_time_music = Set_time_music;
        RadioButtonCell.SetNowPlaying = SetNowPlaying;
        RadioButtonCell. Musictable1 =  Musictable1;
        RadioButtonCell.music_adjustslider = music_adjustslider;
        RadioButtonCell.SLIDERMUSIC = SLIDERMUSIC;
        RadioButtonCell.SLIDERMUSIC1 = SLIDERMUSIC1;
        RadioButtonCell.SetNowPlaying1 = SetNowPlaying1;
        RadioButtonCell.musicplay1 = musicplay1;
        RadioButtonCell.Set_time_music1 = Set_time_music1;

        // This will set the radio button to the cell when the cell is created and will set the action for the radio button
        this.radioButton = new RadioButton();
        this.radioButton.setOnAction(event -> {
            if (radioButton.isSelected()) {
                Music music = getTableView().getItems().get(getIndex());
                String filePath = music.getFilePath();
                String fullTitle  = music.getTitle();
                String[] parts = fullTitle.split(",");  // Assuming the title and additional info are separated by a comma

                if (parts.length > 0) {
                    String title = parts[0].trim();
                    System.out.println("Title Name: " + title);
                    System.out.println("Radio button clicked for: " + filePath);
                    // Play action for the radio button when it is selected and will set the action for the radio button
                    System.out.println("The Set time value is"+ Set_time_music);
                    System.out.println("The value is" + musicplay);
                    System.out.print("The value of setnow"+ SetNowPlaying);
                    System.out.print("The value of volume"+ music_adjustslider);
                    System.out.println(" The value of table"+ Musictable1);
                    System.out.println(" The value of Slider music"+ SLIDERMUSIC);
                    System.out.println(" The value of Slider music1"+ SLIDERMUSIC1);
                    System.out.println(" The value of SetPlaying"+ SetNowPlaying1);
                    System.out.println(" The value of Musicplay1"+ musicplay1);
                    System.out.println(" The value of Set_time_music1"+ Set_time_music1);
                    Mutify_controller.set(music,filePath, title);
                }


            } else {
                // Stop action for the radio button when it is not selected and will set the action for the radio button
                stopMusic();
            }
        });
    }

    @Override
    // This will update the item in the table view and set the graphic to the radio button
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
         // Implement the method logic here to play the music and update
        // the UI accordingly (e.g. disable/enable buttons) and update the now playing label
        String filePath = newMusicVariable.getFilePath();
        System.out.println("Play Music" + filePath);

        try {
            File file = new File(filePath);
            if (!file.exists() || !file.canRead()) {
                throw new FileNotFoundException("Music file not found or inaccessible: " + filePath);
            }

            stopMusic();

            mediaPlayer = getMediaPlayer(newMusicVariable, file);
            mediaPlayer.play();
            System.out.println("Playing music: " + newMusicVariable.getTitle());
            SetNowPlaying.setText("Now Playing:"+ newMusicVariable.getTitle());
            SetNowPlaying1.setText(newMusicVariable.getTitle());
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
            String title = null;
            String filelocation = null;
            Mutify_controller.set(music, filelocation, title);
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
                    musicplay1.setValue(progress * 100);

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

            // Listener for volume  music_adjuster slider
            musicplay1.valueProperty().addListener((observable, oldValue, newValue) -> {
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

            SLIDERMUSIC.valueProperty().addListener((observable, oldValue, newValue) -> {
                double volume = newValue.doubleValue() / 100.0; // Adjust if needed
                RadioButtonCell.setVolume(volume);
            });

            SLIDERMUSIC1.valueProperty().addListener((observable, oldValue, newValue) -> {
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

            musicplay1.setValue((newTime / totalDuration) * 100);

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
            // Update the slider position based on the current playback time
            musicplay.setValue(value);
            musicplay1.setValue(value);
        }
    }

    private static void updateDurationLabel(double currentTime, double totalDuration) {
        // Format the time and set it to the label
        String currentTimeStr = formatTime(currentTime);
        String totalDurationStr = formatTime(totalDuration);
        Set_time_music.setText(currentTimeStr + " / " + totalDurationStr);
        Set_time_music1.setText(currentTimeStr + " / " + totalDurationStr);
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
            // Update the volume of the media player if it's currently playing
            System.out.println("System Media is Playing");
            mediaPlayer.setVolume(volume);
        }
    }
    public static Callback<TableColumn<Music, Boolean>, TableCell<Music, Boolean>> forTableColumn(Slider musicplay, Label Set_time_music, Label SetNowPlaying, TableView<Music>Musictable1, Slider music_adjustslider, Slider SLIDERMUSIC, Slider SLIDERMUSIC1, Slider musicplay1, Label SetNowPlaying1, Label Set_time_music1) {
        return param -> new RadioButtonCell(musicplay, Set_time_music, SetNowPlaying, Musictable1,music_adjustslider,SLIDERMUSIC, SLIDERMUSIC1,   SetNowPlaying1, musicplay1,Set_time_music1);
    }

}
