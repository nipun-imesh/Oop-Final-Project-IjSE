package com.assignment.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Appinitlizer extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        stage.setResizable(false);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"))));
        stage.setTitle("ABC Exam Management System");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/imege/logo-removebg-preview.png")));
        stage.show();
    }
}
