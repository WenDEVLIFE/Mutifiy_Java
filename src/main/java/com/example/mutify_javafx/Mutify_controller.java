package com.example.mutify_javafx;

import com.music.page.Mutify.functions.*;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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


    @FXML
    private Tab fullscreenMUziket;

    @FXML
    private Tab CreatePlayList;

    // My tabbedPane
    @FXML
    private TabPane MusicTabbbedPane;


    // Tables
    @FXML
    private TableView<com.example.mutify_javafx.Music> Musictable1;

    @FXML
    private TableView <PlaylistSection> MusicTable2 ;

    @FXML
    private TableView<com.example.mutify_javafx.Playlist> PlaylistTable;

    @FXML
    private TableColumn<com.example.mutify_javafx.Music, String> titleColumn;

    @FXML
    private TableColumn<com.example.mutify_javafx.Music, String> artistColumn;

    @FXML
    private TableColumn<com.example.mutify_javafx.Music, String> yearColumn;

    private TableColumn<Music, Boolean> playColumn;


    // This wil Collect the value from the arraylist and store it here
    private static ObservableList <Music> MusicList = FXCollections.observableArrayList();

    private  static  ObservableList <Playlist> playlist = FXCollections.observableArrayList();

    private  static ObservableList<PlaylistSection> playlistSection = FXCollections.observableArrayList();
    private RadioButtonCell RadioButtonCell;
    private String currentFilePath;
    // Set the stage when initializing the controller

    // This variable will store the music on the radiobuttoncell
    private static Music newMusicVariable;

    private static String fielocationsmusic;

    private static String titlemusic;

    @FXML
    private  Slider musicplay;
    @FXML
    private  Slider musicplay1;

    @FXML
    private  Slider music_adjustslider;

    @FXML
    private  Slider SLIDERMUSIC;

    @FXML
    private  Slider SLIDERMUSIC1;

    @FXML
    private Label Set_time_music;
    @FXML
    private Label Set_time_music1;

    @FXML
    private Label SetNowPlaying;

    @FXML
    private Label SetNowPlaying1;

    @FXML
    private Label PlayListName;

    private static String store_filelocations;

    // This are the textfield
    @FXML
    private TextField locationtype;

    @FXML
    private TextField PlaylistName;


    @FXML
    private ComboBox<String> AlbumCombo;


    @FXML
    private ComboBox<String> PlaylistCombox;

    public static void set(com.example.mutify_javafx.Music music , String filepath , String title) {
        // This will set the music variable
        newMusicVariable = music;
        fielocationsmusic = filepath;
        titlemusic = title;

        System.out.println("The music is set" + newMusicVariable);
        System.out.println("The music is filelocation" + fielocationsmusic);
        System.out.println("The music is title" + titlemusic);

    }



    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void mouseExitFunction(MouseEvent event) {
        // Close the application on a single click
        Platform.exit();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveMusicToFile(store_filelocations)));

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
        // This will select the music tab when the button is clicked on the side panel button action events
        MusicTabbbedPane.getSelectionModel().select(Music);

    }

    @FXML
    void settings_action(ActionEvent event) {
        // This will select the settings tab when the button is clicked on the side panel button action events
        MusicTabbbedPane.getSelectionModel().select(Settings);

    }

    @FXML
    void playlist_action(ActionEvent event) {
        // This will select the playlist tab when the button is clicked on the side panel button action events
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
        // Read the file and return a list of Music objects from the file contents (if any) or an empty list
        List<Music> musicList = new ArrayList<>();

        // Read the file and return a list of Music objects from the file contents (if any) or an empty list
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            String filename = selectedFile.getName();

            // You might need to adjust how you extract title, artist, and year based on your filename structure
            String title = filename;
            String artist = "Unknown Artist";
            String year = "Unknown Year";

            // Create a new Music object and add it to the list of Music objects
            Music music = new Music(title, artist, year, filePath, filename);
            musicList.add(music);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return musicList;
    }

    private void saveMusicToFile(String filelocation) {
        try {
            // Convert Music objects to lines and write to the file
            List<String> lines = MusicList.stream()
                    .map(music -> String.join(",", music.getTitle(), music.getArtist(), music.getYear(), music.getFilePath()))
                    .collect(Collectors.toList());

            // Write the lines to the file at the specified location
            Files.write(Paths.get(store_filelocations), lines);
            System.out.println("Music saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMusicFromFile(String filePath) {
        try {
            // Read lines from the file
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Parse lines into Music objects
            List<Music> loadedMusic = lines.stream()
                    .map(line -> {
                        String[] parts = line.split(",");
                        if (parts.length >= 4) {
                            return new Music(parts[0], parts[1], parts[2], parts[3], "");
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Add all the loaded Music objects to the observable list and set the items to the table view for display
            MusicList.addAll(loadedMusic);
            Musictable1.setItems(MusicList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        // Set the title of the file chooser dialog window
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"),
                new FileChooser.ExtensionFilter("WAV Files", "*.wav"),
                new FileChooser.ExtensionFilter("OGG Files", "*.ogg"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
    }

    @FXML
    public void initialize() {
        populateComboBox();
        loadPlaylistFromFile();

        locationtype.setText(store_filelocations);
        loadlocation();
        loadPlaylistSectionFromFile();
        loadMusicFromFile(store_filelocations);
        System.out.println("Initializing the table...");

        // Initialize the table
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
        playColumn.setCellFactory(forTableColumn(
                musicplay,
                Set_time_music,
                SetNowPlaying,
                Musictable1,
                music_adjustslider,
                SLIDERMUSIC,
                SLIDERMUSIC1,
                musicplay1,
                SetNowPlaying1, // Pass the initialized label
                Set_time_music1  // Pass the initialized label
        ));
        playColumn.setPrefWidth(100);
        playColumn.getStyleClass().add("table-row-cel");

        TableColumn<Music, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(ButtonCell.forTableColumn("Delete", MusicList, Musictable1,store_filelocations));
        deleteColumn.setPrefWidth(100);
        deleteColumn.getStyleClass().add("table-row-cel");

        //Add all the columns to the table  view and set the table view to
        // the center of the pane layout of the scene builder file (fxml file)
        // and set the table view to be editable and set the items to the observable list
        Musictable1.getColumns().addAll(titleColumn, artistColumn, yearColumn, fileColumn, playColumn, deleteColumn);

        // This will initialize the playlist table
        TableColumn<Playlist, String> PlaylistName = new TableColumn<>("PlaylistName");
        PlaylistName.setCellValueFactory(cellData -> cellData.getValue().playlistNameProperty());
        PlaylistName.setCellFactory(column -> CustomTableCellFactory11.cellFactoryForString(column));
        PlaylistName.setPrefWidth(250);

        TableColumn<Playlist, String> DateCreated = new TableColumn<>("DateCreated");
        DateCreated.setCellValueFactory(cellData -> cellData.getValue().dateCreatedProperty());
        DateCreated.setCellFactory(column -> CustomTableCellFactory11.cellFactoryForString(column));
        DateCreated.setPrefWidth(100);

        TableColumn<Playlist, Void> PlayPlaylist = new TableColumn<>("Play");
        PlayPlaylist.setCellFactory(ButtonCell1.forTableColumn("Play", playlist,PlaylistTable, PlayListName, CreatePlayList, MusicTabbbedPane));
        PlayPlaylist.setPrefWidth(140);

        TableColumn<Playlist, Void> DeletePlaylist = new TableColumn<>("Delete");
        DeletePlaylist.setCellFactory(ButtonCell1.forTableColumn("Delete", playlist,PlaylistTable,PlayListName, CreatePlayList, MusicTabbbedPane));
        DeletePlaylist.setPrefWidth(140);

        PlaylistTable.getColumns().addAll(PlaylistName, DateCreated, PlayPlaylist, DeletePlaylist);

        // This will initialize the playlist section table
        TableColumn<PlaylistSection, String> PlaylistName1 = new TableColumn<>("PlaylistName");
          PlaylistName1.setCellValueFactory(cellData -> cellData.getValue().playlistNameProperty());
            PlaylistName1.setCellFactory(column -> CustomTableCellFactory2.cellFactoryForString(column));
            PlaylistName1.setPrefWidth(200);

        TableColumn<PlaylistSection, String> MusicName = new TableColumn<>("MusicName");
            MusicName.setCellValueFactory(cellData -> cellData.getValue().musicNameProperty());
            MusicName.setCellFactory(column -> CustomTableCellFactory2.cellFactoryForString(column));
            MusicName.setPrefWidth(200);

        TableColumn<PlaylistSection, String> FileLocation = new TableColumn<>("FileLocation");
            FileLocation.setCellValueFactory(cellData -> cellData.getValue().fileLocationProperty());
            FileLocation.setCellFactory(column -> CustomTableCellFactory2.cellFactoryForString(column));
            FileLocation.setPrefWidth(200);

        TableColumn<PlaylistSection, Void> PlayMusic = new TableColumn<>("Open it on Music Player");
            PlayMusic.setCellFactory(ButtonCell2.forTableColumn("Open it on Music Player", playlistSection,MusicTable2, MusicTabbbedPane, fullscreenMUziket));
            PlayMusic.setPrefWidth(100);

        TableColumn<PlaylistSection, Void> DeleteMusic = new TableColumn<>("Delete");
            DeleteMusic.setCellFactory(ButtonCell2.forTableColumn("Delete", playlistSection,MusicTable2, MusicTabbbedPane, fullscreenMUziket));
            DeleteMusic.setPrefWidth(100);

        MusicTable2.getColumns().addAll(PlaylistName1, MusicName, FileLocation, PlayMusic, DeleteMusic);



    }

    // This are the actions on the button play, pause, restart
    @FXML
    void Pause_action(ActionEvent event) {
        // Call the pause method
        System.out.println("Pause Music");
        handlePlayPauseButton();
    }

    @FXML
    void Play_Action(ActionEvent event) {
        // Call the play method
        System.out.println("Play Music");
        RadioButtonCell.playMusic(newMusicVariable);


    }

    @FXML
    void backwardfunction(ActionEvent event) {
        // Call the backward method
        System.out.println("Back-ward");
        backward();
    }

    @FXML
    void fowardfunction(ActionEvent event) {
        System.out.println("Forward");

            // Call the forward method
            forward();

    }
    @FXML
    void fullscreenmusic (ActionEvent event) {
        //This will open the fullscreen music
        System.out.println("Fullscreen1");
        MusicTabbbedPane.getSelectionModel().select(fullscreenMUziket);

    }
    @FXML
    void gobacktosmallmusic (ActionEvent event) {
        //This will open the fullscreen music
        System.out.println("Fullscreen2");
        MusicTabbbedPane.getSelectionModel().select(Music);


    }

    @FXML
    void CreatePlaylistFunction(javafx.event.ActionEvent event) {
        // Get playlist name and current date
        String playlistName = PlaylistName.getText();
        LocalDate dateCreated = LocalDate.now();
        if (playlistName.isEmpty()) {
            // Show an alert if the playlist name is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Playlist Name is Empty");
            alert.setContentText("Please enter a playlist name.");
            alert.showAndWait();
        } else {
             // make sure you have this filter so that it wont ruin your table display
            // Read the existing playlist, add the new entry, and update both the TableView and file
            List<Playlist> existingPlaylist = loadPlaylistFromFile();
            Playlist newPlaylist = new Playlist(playlistName, dateCreated.toString());
            existingPlaylist.add(newPlaylist);
            savePlaylistToFile(existingPlaylist);

            // Refresh the TableView to reflect the changes
            PlaylistTable.setItems(FXCollections.observableArrayList(existingPlaylist));

            // Optionally, you can clear the input field after adding a playlist
            PlaylistName.clear();
        }
    }

    private List<Playlist> loadPlaylistFromFile() {
        try {
            // Read lines from the file
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/com/example/mutify_javafx/mymusic1/playlist.txt"));

            // Parse lines into Playlist objects
            List<Playlist> loadedPlaylist = lines.stream()
                    .map(line -> {
                        String[] parts = line.split(",");
                        if (parts.length >= 2) {
                            return new Playlist(parts[0], parts[1]);
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Set the items to the table view for display
            playlist.clear();
            playlist.addAll(loadedPlaylist);
            PlaylistTable.setItems(FXCollections.observableArrayList(playlist));

            return loadedPlaylist;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private void savePlaylistToFile(List<Playlist> playlist) {
        try {
            String filepath = "src/main/resources/com/example/mutify_javafx/mymusic1/playlist.txt";
            // Format the playlist entries as strings
            List<String> lines = playlist.stream()
                    .map(p -> String.join(",", p.getPlaylistName(), p.getDateCreated()))
                    .collect(Collectors.toList());

            Files.write(Paths.get(filepath), lines);
            System.out.println("Playlist saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void change_directory_action(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Music File");

        // Show open dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Update your UI or perform actions with the selected file
            String filePath = selectedFile.getAbsolutePath();
            String fileName = selectedFile.getName();

            // Perform actions with filePath and fileName as needed
            System.out.println("Selected File Path: " + filePath);
            System.out.println("Selected File Name: " + fileName);

            // Update store_filelocations
            store_filelocations = filePath;

            // Update UI
            locationtype.setText(store_filelocations);

            // Update src.txt file
            updateSrcFile(fileName, store_filelocations);
        }
    }

    private void updateSrcFile(String fileName, String fileLocation) {
        // Update the src.txt file with the new file information
        File srcFile = new File("src/main/resources/com/example/mutify_javafx/mymusic1/src.txt");

        try (PrintWriter writer = new PrintWriter(srcFile)) {
            writer.println(fileLocation);
            System.out.println("Updated src.txt file with new information");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

     public void loadlocation() {
        // This will load the file location from the src.txt file
        File srcFile = new File("src/main/resources/com/example/mutify_javafx/mymusic1/src.txt");

        try (Scanner scanner = new Scanner(srcFile)) {
            if (scanner.hasNext()) {
                // Read the first line of the file and store it in store_filelocations
                store_filelocations = scanner.nextLine();

                // Optionally, you can set the text of the locationtype TextField
                locationtype.setText(store_filelocations);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void flush_music(com.example.mutify_javafx.Music finalMusic){
       // This will flush the music  variable
        newMusicVariable = finalMusic;

        System.out.println("The music is flush");
    }

    private void populateComboBox() {
        ObservableList<String> playlistNames = FXCollections.observableArrayList();

               // Read the file and populate the ComboBox with the playlist names
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\com\\example\\mutify_javafx\\mymusic1\\playlist.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming each line in the file contains a playlist name
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    playlistNames.add(parts[0].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception appropriately in your application
        }

        // Set the items in the ComboBox
        PlaylistCombox.setItems(playlistNames);
    }
    @FXML
    void Savepllaylistinfo(ActionEvent event) {
        String selectedPlaylistName = PlaylistCombox.getSelectionModel().getSelectedItem();

        if (selectedPlaylistName != null) {
            // Get the corresponding Playlist object
            Playlist selectedPlaylist = findPlaylistByName(selectedPlaylistName);

            if (selectedPlaylist != null) {
                // Now you have access to the Playlist details
                String playlistName = selectedPlaylist.getPlaylistName();
                String musicName = titlemusic;  // Assuming titlemusic is a field in your controller
                String fileLocation = fielocationsmusic;  // Assuming fielocationsmusic is a field in your controller

                // Implement your save logic here, for example, print the details
                System.out.println("Saving Playlist Info:");
                System.out.println("Playlist Name: " + playlistName);
                System.out.println("Music Name: " + musicName);
                System.out.println("File Location: " + fileLocation);
                saveDetailsToFile(playlistName, musicName, fileLocation);
            }
        }
    }

    // Method to find a Playlist by name
    private Playlist findPlaylistByName(String playlistName) {
        for (Playlist playlist : this.playlist) {
            if (playlist.getPlaylistName().equals(playlistName)) {
                return playlist;
            }
        }
        return null;
    }

    private void saveDetailsToFile(String playlistName, String musicName, String fileLocation) {
        try {
            // Specify the file path where you want to save the details
            Path filePath = Path.of("src/main/resources/com/example/mutify_javafx/mymusic1/saved_details.txt");

            // Create or append to the file
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                // Write the details to the file
                writer.write("Playlist Name: " + playlistName + "\n");
                writer.write("Music Name: " + musicName + "\n");
                writer.write("File Location: " + fileLocation + "\n\n");
            }

            System.out.println("Details saved to file.");
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception appropriately in your application
        }
    }



    public void loadPlaylistSectionFromFile() {
        try {
            // Read lines from the file
            Path filePath = Path.of("src/main/resources/com/example/mutify_javafx/mymusic1/saved_details.txt");
            List<String> lines = Files.readAllLines(filePath);

            // Parse lines into PlaylistSection objects
            List<PlaylistSection> loadedPlaylistSection = new ArrayList<>();

            for (int i = 0; i < lines.size(); i += 4) {
                String playlistNameLine = lines.get(i);
                String musicNameLine = lines.get(i + 1);
                String fileLocationLine = lines.get(i + 2);

                // Assuming the lines are in the format "Playlist Name: <name>"
                String playlistName = playlistNameLine.substring("Playlist Name: ".length()).trim();
                // Assuming the lines are in the format "Music Name: <name>"
                String musicName = musicNameLine.substring("Music Name: ".length()).trim();
                // Assuming the lines are in the format "File Location: <location>"
                String fileLocation = fileLocationLine.substring("File Location: ".length()).trim();

                PlaylistSection playlistSection = new PlaylistSection(playlistName, musicName, fileLocation);
                loadedPlaylistSection.add(playlistSection);
            }

            // Set the items to the table view for display
            playlistSection.clear();
            playlistSection.addAll(loadedPlaylistSection);
            MusicTable2.setItems(FXCollections.observableArrayList(playlistSection));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}