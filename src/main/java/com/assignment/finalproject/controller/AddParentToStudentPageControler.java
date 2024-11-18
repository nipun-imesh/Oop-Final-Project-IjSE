package com.assignment.finalproject.controller;

import com.assignment.finalproject.dto.main.AddParentDTO;
import com.assignment.finalproject.dto.tm.AddParentTM;
import com.assignment.finalproject.model.mainModel.AddParentCModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddParentToStudentPageControler implements Initializable {

    AddParentCModel addParentCModel = new AddParentCModel();

    private StudentManagePageControler studentMC;

    @FXML
    private AnchorPane ANKaddStuPErent;

    @FXML
    private Button BUTPerentSelect;

    @FXML
    private Button BUTReset;

    @FXML
    private Button BUTSave;

    @FXML
    private Button BUTSearch;

    @FXML
    private TableColumn<AddParentTM, String> COLParentId;

    @FXML
    private TableColumn<AddParentTM, String> COLParentMail;

    @FXML
    private TableColumn<AddParentTM,String> COLParentName;

    @FXML
    private Label LBPerentID;

    @FXML
    private TableView<AddParentTM> TABParent;

    @FXML
    private TextField TXTParentMail;

    @FXML
    private TextField TXTParentName;

    @FXML
    private VBox Vbox;

    @FXML
    private VBox Vbox1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        COLParentId.setCellValueFactory(new PropertyValueFactory<>("parentId"));
        COLParentName.setCellValueFactory(new PropertyValueFactory<>("parentName"));
        COLParentMail.setCellValueFactory(new PropertyValueFactory<>("parentEmail"));
        try {
            loadNextParentID();
            loadAllParent();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPatentController(StudentManagePageControler studentMC){
        this.studentMC = studentMC;
    }

    private void loadAllParent() throws SQLException {
        ArrayList<AddParentDTO> addParentDTOS = AddParentCModel.getAllParent();

        ObservableList<AddParentTM> addParentTMS = FXCollections.observableArrayList();
        for (AddParentDTO addParentDTO : addParentDTOS) {
            AddParentTM addParentTM = new AddParentTM(
                    addParentDTO.getParentId(),
                    addParentDTO.getParentName(),
                    addParentDTO.getParentEmail()
            );
            addParentTMS.add(addParentTM);
        }

       TABParent.setItems(addParentTMS);
    }

    @FXML
    void parentSelectOnAction(ActionEvent event) throws SQLException, IOException {
        studentMC.setTXTStuParentId().setText(LBPerentID.getText());
        studentMC.setTXTStuParentName().setText(TXTParentName.getText());

        Stage Stage = (Stage) ANKaddStuPErent.getScene().getWindow();
        Stage.close();
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        TXTParentName.setText("");
        TXTParentMail.setText("");
        try {
            loadNextParentID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void saveOnAction(ActionEvent event)  {

        if(LBPerentID.getText().isEmpty() || TXTParentName.getText().isEmpty() || TXTParentMail.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter all fields").show();
        }
        else {
            String parentID = LBPerentID.getText();
            String parentName = TXTParentName.getText();
            String parentMail = TXTParentMail.getText();

            AddParentDTO addParentDTO = new AddParentDTO(
                    parentID,
                    parentName,
                    parentMail,
                    "Active"
            );
        try {
            if (AddParentCModel.saveParent(addParentDTO)) {
                loadAllParent();
                loadNextParentID();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            throw new RuntimeException(e);
        }
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {

    }

    @FXML
    void tabSelectOnaction(MouseEvent event) {
        BUTSave.setDisable(false);
        AddParentTM addParentTM = TABParent.getSelectionModel().getSelectedItem();
        if (addParentTM != null) {
            LBPerentID.setText(addParentTM.getParentId());
            TXTParentName.setText(addParentTM.getParentName());
            TXTParentMail.setText(addParentTM.getParentEmail());

        }
    }

    public void loadNextParentID() throws SQLException {
        String nextParantID = addParentCModel.getParentID();
        LBPerentID.setText(nextParantID);
    }



}
