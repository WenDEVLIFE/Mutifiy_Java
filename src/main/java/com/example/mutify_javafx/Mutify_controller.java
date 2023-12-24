package com.example.mutify_javafx;

import com.music.page.Mutify.functions.ButtonCell;
import com.music.page.Mutify.functions.CustomTableCellFactory;
import com.music.page.Mutify.functions.RadioButtonCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import static com.music.page.Mutify.functions.RadioButtonCell.*;

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
    private Tab Albums;

    // My tabbedPane
    @FXML
    private TabPane MusicTabbbedPane;

    // Tables
    @FXML
    private TableView<com.example.mutify_javafx.Music> Musictable1;

    @FXML
    private TableColumn<com.example.mutify_javafx.Music, String> titleColumn;

    @FXML
    private TableColumn<com.example.mutify_javafx.Music, String> artistColumn;

    @FXML
    private TableColumn<com.example.mutify_javafx.Music, String> yearColumn;

    private TableColumn<Music, Boolean> playColumn;

    // This wil Collect the value from the arraylist and store it here
    private ObservableList <Music> MusicList = FXCollections.observableArrayList();

    private RadioButtonCell RadioButtonCell;
    private String currentFilePath;
    // Set the stage when initializing the controller

    // This variable will store the music on the radiobuttoncell
    private static Music newMusicVariable;

    @FXML
    private  Slider musicplay;

    @FXML
    private  Slider music_adjustslider;

    @FXML
    private Label Set_time_music;

    @FXML
    private Label SetNowPlaying;

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
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            System.out.println(filePath);
            currentFilePath = filePath;
            List<Music> newMusicList = readMusicFromFile(new File(selectedFile.getAbsolutePath()), filePath);

            if (!newMusicList.isEmpty()) {
                // store the list here on the observerlist
                MusicList.addAll(newMusicList);
                Musictable1.setItems(MusicList); // Set the items directly
            }
        }
    }


    private static MediaPlayer getMediaPlayer(com.example.mutify_javafx.Music music, File file) {
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

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

    private List<Music> readMusicFromFile(File selectedFile, String filePath) {
        List<Music> musicList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            String filename = selectedFile.getName();

            // You might need to adjust how you extract title, artist, and year based on your filename structure
            String title = filename;
            String artist = "Unknown Artist";
            String year = "Unknown Year";

            Music music = new Music(title, artist, year, filePath, filename);
            musicList.add(music);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return musicList;
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"),
                new FileChooser.ExtensionFilter("WAV Files", "*.wav"),
                new FileChooser.ExtensionFilter("OGG Files", "*.ogg"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
    }
    @FXML
    public void initialize() {



        System.out.println("Initializing the table...");

        titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        titleColumn.setCellFactory(column -> CustomTableCellFactory.createCenteredStringCell(column));
        titleColumn.setPrefWidth(200);
        titleColumn.getStyleClass().add("table-row-cel");

        artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        artistColumn.setCellFactory(column -> CustomTableCellFactory.createCenteredStringCell(column));
        artistColumn.setPrefWidth(100);
        artistColumn.getStyleClass().add("table-row-cel");

        yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        yearColumn.setCellFactory(column -> CustomTableCellFactory.createCenteredStringCell(column));
        yearColumn.setPrefWidth(130);
        yearColumn.getStyleClass().add("table-row-cel");

        TableColumn<Music, String> fileColumn = new TableColumn<>("filepath");
        fileColumn.setCellValueFactory(cellData -> cellData.getValue().filePathProperty());
        fileColumn.setCellFactory(column -> CustomTableCellFactory.createCenteredStringCell(column));
        fileColumn.setPrefWidth(130);
        fileColumn.getStyleClass().add("table-row-cel");

        playColumn = new TableColumn<>("Select");
        playColumn.setCellFactory(RadioButtonCell.forTableColumn(musicplay,Set_time_music,SetNowPlaying,Musictable1,music_adjustslider));
        playColumn.setPrefWidth(100);
        playColumn.getStyleClass().add("table-row-cel");

        TableColumn<Music, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(ButtonCell.forTableColumn("Delete", MusicList, Musictable1));
        deleteColumn.setPrefWidth(100);
        deleteColumn.getStyleClass().add("table-row-cel");

        Musictable1.getColumns().addAll(titleColumn, artistColumn, yearColumn, fileColumn, playColumn, deleteColumn);

        System.out.println("Table initialized.");
    }

    // This are the actions on the button play, pause, restart
    @FXML
    void Pause_action(ActionEvent event) {
        System.out.println("Pause Music");
        handlePlayPauseButton();
    }

    @FXML
    void Play_Action(ActionEvent event) {
        System.out.println("Play Music");
        RadioButtonCell.playMusic(newMusicVariable);


    }
    @FXML
    void Stop_Action(ActionEvent event) {
        System.out.println("Stop Music");

    }

    @FXML
    void backwardfunction(ActionEvent event) {
        System.out.println("Back-ward");
        backward();
    }

    @FXML
    void fowardfunction(ActionEvent event) {
        System.out.println("Forward");


            // Call the forward method
            forward();

    }

    public static void set(Music music){

        // Store the music variable from the radiobuttoncell
        newMusicVariable = music;

        System.out.println("The Location" + newMusicVariable);
    }
    public static void flush_music(com.example.mutify_javafx.Music finalMusic){

        newMusicVariable = finalMusic;

        System.out.println("The music is flush");
    }


}