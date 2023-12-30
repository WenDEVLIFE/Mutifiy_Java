package com.example.mutify_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class Main extends Application {
    private Stage stage;

    @Override

    // dont forget to git remote add origin https://github.com/WenDEVLIFE/Mutifiy_Java.git //
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Mutify Loading!");
            stage.setScene(scene);
            Image icon = new Image(getClass().getResourceAsStream("icons/410623575_1822184534885870_6495627393933065105_n-removebg-preview.png"));
            stage.getIcons().add(icon);
            stage.show();

            Main_Controller controller = fxmlLoader.getController();
            controller.setStage(stage);
            controller.fill();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}