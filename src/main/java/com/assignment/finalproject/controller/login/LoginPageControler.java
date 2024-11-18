package com.assignment.finalproject.controller.login;

import com.assignment.finalproject.dto.main.LoginDTO;
import com.assignment.finalproject.model.mainModel.LoginModel;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class LoginPageControler {

    @FXML
    private AnchorPane ANKLoginPage;


    @FXML
    private Button BUTLogin;

    @FXML
    private Button BUTSignin;

    @FXML
    private Button BUTWotchResalt;

    @FXML
    private TextField TXTPAssword;

    @FXML
    private TextField TXTUsername;

    @FXML
    private Label LBchekPassword;

    public void initialize() {
            TXTUsername.setOnAction(event -> TXTPAssword.requestFocus());
            TXTPAssword.setOnAction(event -> BUTLogin.fire());
    }

    @FXML
    void logInAction(ActionEvent event) {

            LoginDTO loginDto = new LoginDTO(TXTUsername.getText(), TXTPAssword.getText());
            LoginModel loginModel = new LoginModel();

            try {
                if (TXTUsername.getText().isEmpty() || TXTPAssword.getText().isEmpty()) {
                    LBchekPassword.setText("Please enter both username and password");
                    showErrorMessageWithFade();
                } else {
                    if (loginModel.chekid(loginDto) >= 1) {
                        Stage currentStage = (Stage) ANKLoginPage.getScene().getWindow();
                        fadeOutAndLoadNewStage(currentStage); // Apply fade-out and load the new stage
                    } else {
                        LBchekPassword.setText("Invalid username or password");
                        showErrorMessageWithFade();
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Error loading UI or connecting to the database").show();
            }
        }

        private void fadeOutAndLoadNewStage(Stage currentStage) {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), currentStage.getScene().getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(event -> {
                currentStage.close();

                try {
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"))));
                    newStage.getIcons().add(new Image(getClass().getResourceAsStream("/imege/logo-removebg-preview.png")));
                    newStage.setTitle("ABC Exam Management System");
                    newStage.setResizable(false);


                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), newStage.getScene().getRoot());
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.5);

                    newStage.show();
                    fadeIn.play();
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Error loading the main UI").show();
                }
            });

            fadeOut.play();
    }

    @FXML
    void signInAction(ActionEvent event) throws IOException {
       ANKLoginPage.getChildren().clear();
       AnchorPane load = FXMLLoader.load(getClass().getResource("/view/SignupPage.fxml"));
       ANKLoginPage.getChildren().add(load);
    }

    @FXML
    void wotchResaltAction(ActionEvent event) throws IOException {
        ANKLoginPage.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/StudentResaltViewPage.fxml"));
        ANKLoginPage.getChildren().add(load);
    }

    private void showErrorMessageWithFade() {
        LBchekPassword.setOpacity(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), LBchekPassword);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
       // fadeIn.setOnFinished(event -> fadeOutErrorMessage()); // Start fade-out after fade-in completes
        fadeIn.play();
    }

//    private void fadeOutErrorMessage() {
//        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), LBchekPassword);
//        fadeOut.setFromValue(1.0);
//        fadeOut.setToValue(0.0);
//        fadeOut.setDelay(Duration.seconds(2));
//        fadeOut.play();
//    }
}
