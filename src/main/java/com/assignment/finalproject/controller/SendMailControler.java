package com.assignment.finalproject.controller;

import com.assignment.finalproject.dto.sub.GetParentIdDTO;
import com.assignment.finalproject.model.mainModel.SendMailModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendMailControler implements Initializable {
    SendMailModel sendMailModel = new SendMailModel();

    @FXML
    private Button BUtSend;

    @FXML
    private ComboBox<String> COMSelectParentID;

    @FXML
    private Label LBMailset;

    @FXML
    private Label LBPArentName;

    @FXML
    private TextArea TXAMAssage;

    @FXML
    private TextField TXTSubject;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            loadParentID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void selectParanrOnAction(ActionEvent event) throws SQLException {
        String parentId = COMSelectParentID.getValue();
        String parentName = sendMailModel.getParentName(parentId);
        String parentEmail = sendMailModel.getParentEmail(parentId);
        LBPArentName.setText(parentName);
        LBMailset.setText( parentEmail);
    }

    @FXML
    void sendMailOnAction(ActionEvent event) {
        String recipientEmail = LBMailset.getText();
        String subject = TXTSubject.getText();
        String body = TXAMAssage.getText();

        if (recipientEmail.isEmpty() || subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields before sending email!").show();
            return;
        }

        final String FROM_EMAIL = "imeshnipun@gmail.com";
        final String PASSWORD = "wthn pbdm rnws xeeb";

        Task<Void> emailTask = new Task<>() {
            @Override
            protected Void call() {
                try {
                    sendEmailWithGmail(FROM_EMAIL, PASSWORD, recipientEmail, subject, body);
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.INFORMATION, "Email sent successfully..!").showAndWait();
                    });
                } catch (MessagingException e) {
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.ERROR, "Failed to send email: " + e.getMessage()).showAndWait();
                    });
                }
                return null;
            }
        };

        // Run the task in a new thread
        Thread emailThread = new Thread(emailTask);
        emailThread.setDaemon(true); // Ensure thread exits when application closes
        emailThread.start();

        TXTSubject.clear();
        TXAMAssage.clear();
    }


    private void loadParentID() throws SQLException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ObservableList<GetParentIdDTO> getParentIdDTOS = sendMailModel.getAllParentID();
        for (GetParentIdDTO getParentIdDTO : getParentIdDTOS) {
            observableList.add(getParentIdDTO.getParentId());
        }
        COMSelectParentID.setItems(observableList);
    }

    private void sendEmailWithGmail(String fromEmail, String password, String toEmail, String subject, String messageBody) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message message = prepareMassage(session, fromEmail, toEmail, subject, messageBody);
        if (message != null) {
            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully..!").showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Email send unsuccessfully..!").showAndWait();
        }
    }

    private Message prepareMassage(Session session, String fromEmail, String toEmail, String subject, String messageBody) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(toEmail)});
            message.setSubject(subject);
            message.setText(messageBody);

            return message;
        } catch (MessagingException e) {
            Logger.getLogger(SendMailControler.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @FXML
    void reportONAction(ActionEvent event) {

    }
}
