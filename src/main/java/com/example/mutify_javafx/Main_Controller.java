package com.example.mutify_javafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;


public class Main_Controller {

    @FXML
    private ProgressBar loadingbar;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void fill() throws InterruptedException, IOException {

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(50), // Adjust the duration as needed
                this::handle
        ));

        timeline.setCycleCount(100); // Adjust the cycle count based on your requirements
        timeline.play();
    }

    private void handle(ActionEvent event) {
        double progress = loadingbar.getProgress() + 0.01; // Increment by a small value
        loadingbar.setProgress(progress);

        if (progress >= 1.0) { // Corrected condition to check if progress is complete
            loadToMusic();
        }
    }

    private void loadToMusic() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MutifyUi.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage musicStage = new Stage();
            musicStage.initStyle(StageStyle.UNDECORATED);
            musicStage.setTitle("Mutify");
            musicStage.setScene(scene);

            Image icon = new Image(getClass().getResourceAsStream("icons/410623575_1822184534885870_6495627393933065105_n-removebg-preview.png"));
            musicStage.getIcons().add(icon);
            musicStage.show();

            // Set up mouse event handlers for dragging
            AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
            AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);

            // Set up mouse event handlers for dragging the stage around (continued)
            scene.setOnMousePressed(event -> {
                xOffset.set(event.getSceneX());
                yOffset.set(event.getSceneY());
            });

            // Set up mouse event handlers for dragging the stage around (continued)
            scene.setOnMouseDragged(event -> {
                musicStage.setX(event.getScreenX() - xOffset.get());
                musicStage.setY(event.getScreenY() - yOffset.get());
            });


            // Get the controller and set the stage
            Mutify_controller mutifyController = fxmlLoader.getController();
            mutifyController.setStage(musicStage);

            // Close the current stage
            Stage currentStage = (Stage) loadingbar.getScene().getWindow();
            currentStage.close();

            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     // This method is used to load the main UI of the application
    private void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Mutify");
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("icons/image-from-rawpixel-id-6952101-jpeg-removebg-preview.png"));
        stage.getIcons().add(icon);
        stage.show();
    }
}