package com.example.intellij_infinite_timer_real;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TimerUI.fxml"));
        primaryStage.setTitle("Infinite Timer");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(new Scene(root, 350, 650));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}