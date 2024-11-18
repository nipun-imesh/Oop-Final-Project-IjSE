
package com.assignment.finalproject.controller.login;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class MainPageControler {

    @FXML
    private Button BUTStudentManage;

    @FXML
    private Button BUTExamManage;

    @FXML
    private Button BUTMaksManage;

    @FXML
    private Button BUTParentManage;

    @FXML
    private AnchorPane AnkMainPage;

    @FXML
    private AnchorPane ANKMainload;

    @FXML
    void goBackAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) AnkMainPage.getScene().getWindow();

        // Apply fade-out effect to current stage
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), currentStage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            currentStage.close();
            try {
                // Load the login page with a fade-in effect
                Stage newStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.setTitle("ABC Exam Management System");
                newStage.getIcons().add(new Image(getClass().getResourceAsStream("/imege/logo-removebg-preview.png")));

                // Apply fade-in effect to new stage
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), root);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);

                newStage.show();
                fadeIn.play();
            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Error loading the login page").show();
            }
        });

        fadeOut.play();
    }

    @FXML
    void StudentManageAction(ActionEvent event)  {
        navigateTo("/view/StudentManagePage.fxml");
    }

    @FXML
    void AddExamAction(ActionEvent event)  {
        navigateTo("/view/AddExamListPage.fxml");
    }

    @FXML
    void MaksManageAction(ActionEvent event) {
        navigateTo("/view/ManageMarkPage.fxml");
    }

    @FXML
    void ParentManageAction(ActionEvent event) {
        navigateTo("/view/AddPerentPage.fxml");
    }

    @FXML
    void addMarkOnAction(ActionEvent event) {
        navigateTo("/view/AddMarkPage.fxml");
    }

    @FXML
    void ExamManageAction(ActionEvent event) {
        navigateTo("/view/ManageExamList.fxml");
    }



    public void navigateTo(String path) {

        try {
            // Clear existing content from the AnchorPane to avoid overlapping
            ANKMainload.getChildren().clear();

            // Load the new FXML content
            Parent newContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));

            // Add the new content to the AnchorPane (it will be invisible initially)
            ANKMainload.getChildren().add(newContent);

            // Set the initial opacity of the new content to 0 (invisible)
            // newContent.setOpacity(0);

            // Create a fade-in transition for the new content
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), newContent);
            fadeIn.setFromValue(0.0); // Start invisible
            fadeIn.setToValue(1.0);   // Fade to fully visible
            fadeIn.play(); // Start the fade-in effect

        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, "Error loading the page: " + path).show();
        }
    }
}
