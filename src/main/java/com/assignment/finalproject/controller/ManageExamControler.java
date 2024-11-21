package com.assignment.finalproject.controller;

import com.assignment.finalproject.dto.main.AddExamListDTO;
import com.assignment.finalproject.dto.sub.*;
import com.assignment.finalproject.dto.tm.ExamCartTM;
import com.assignment.finalproject.dto.tm.ManageExamTM;
import com.assignment.finalproject.model.mainModel.ExamShedulModel;
import com.assignment.finalproject.model.mainModel.ExamSubjectModel;
import com.assignment.finalproject.model.mainModel.ManageExamModel;
import com.assignment.finalproject.model.subModel.HallModel;
import com.assignment.finalproject.model.subModel.SubjectModel;
import com.assignment.finalproject.util.ClassLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageExamControler implements Initializable {

    ManageExamModel manageExamModel = new ManageExamModel();
    SubjectModel subjectModel = new SubjectModel();
    HallModel hallModel = new HallModel();
    ExamShedulModel examShedulModel = new ExamShedulModel();
    ExamSubjectModel examSubjectModel = new ExamSubjectModel();
    private final ObservableList<ExamCartTM> examCartTMS = FXCollections.observableArrayList();


    @FXML
    private AnchorPane ANKManageExam;

    @FXML
    private Button BUTDelete;

    @FXML
    private Button BUTReset;

    @FXML
    private Button BUTUpdate;

    @FXML
    private ComboBox<String> COBSelectHall;

    @FXML
    private TableColumn<ManageExamTM, PieChart.Data> COLDate;

    @FXML
    private TableColumn<ManageExamTM, String> COLEcamShedulID;

    @FXML
    private TableColumn<ManageExamTM, String> COLExamID;

    @FXML
    private TableColumn<ManageExamTM, String> COLExamName;

    @FXML
    private TableColumn<ManageExamTM, String> COLGrade;

    @FXML
    private TableColumn<ManageExamTM, String> COLHallName;

    @FXML
    private TableColumn<ManageExamTM, String> COLSubjectID;

    @FXML
    private TableColumn<ManageExamTM, Time> COLTime;

    @FXML
    private ComboBox<String> COMSelectClass;

    @FXML
    private ComboBox<String> COMSubject;

    @FXML
    private DatePicker DTPDate;

    @FXML
    private Label LBExamID;

    @FXML
    private Label LBExamShedulID;

    @FXML
    private Label LBSetsubject;

    @FXML
    private TableView<ManageExamTM> TBLSetExam;

    @FXML
    private TextField TXTExamName;

    @FXML
    private TextField TXTTime;

    private void setCellValues() {

        COLEcamShedulID.setCellValueFactory(new PropertyValueFactory<>("examShedulID"));
        COLExamID.setCellValueFactory(new PropertyValueFactory<>("examID"));
        COLHallName.setCellValueFactory(new PropertyValueFactory<>("hallID"));
        COLExamName.setCellValueFactory(new PropertyValueFactory<>("examName"));
        COLGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        COLDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        COLTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        COLSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        BUTDelete.setDisable(true);
        BUTUpdate.setDisable(true);

        try {
            loadExamHall();
            loadGrade( (ComboBox<String>) COMSelectClass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        reset();
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        String examID = LBExamID.getText();
        String examShedulID = LBExamShedulID.getText();

        try {
            boolean isDeleted = manageExamModel.deleteExam(examID, examShedulID);
            if (!isDeleted) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete exam.").show();
                return;
            }

            new Alert(Alert.AlertType.INFORMATION, "Deleted successfully!").show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while deleting. Please try again.").show();
        }
        reset();
        TBLSetExam.refresh();
    }

    @FXML
    void upDateOnAction(ActionEvent event) {
        String examShedulID = LBExamShedulID.getText();
        String examName = TXTExamName.getText();
        String grade = String.valueOf(COMSelectClass.getValue());
        String examID = LBExamID.getText();
        String hallName = String.valueOf(COBSelectHall.getValue());
        String examTime = TXTTime.getText();
        String examDate = DTPDate.getValue().toString();
        String subjectID = String.valueOf(COMSubject.getValue());


        if(examName.isEmpty() || grade.isEmpty() || hallName.isEmpty() || examTime == null|| examDate == null || subjectID.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter All Fields").show();
            return;
        }

        try {
            boolean isUpdated = manageExamModel.updateExamAndSchedule(
                    examID, examName, grade, hallName, examTime, examDate, examShedulID, subjectID
            );

            if (!isUpdated) {
                new Alert(Alert.AlertType.ERROR, "Failed to update Exam details.").show();
                return;
            }

            new Alert(Alert.AlertType.INFORMATION, "Update successful!").show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating. Please try again.").show();
        }
        reset();
        TBLSetExam.refresh();
    }


    @FXML
    void selectGradeOnAction(ActionEvent event) {

        reset();
        String grade = String.valueOf(COMSelectClass.getValue());

        try {
            ArrayList<ManageExamTM> selectExam = manageExamModel.getSelectExam(grade);
            TBLSetExam.setItems(FXCollections.observableArrayList(selectExam));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setCellValues();
        COMSubject.getItems().clear();
        try {
            if (grade.equals("6") || grade.equals("7") || grade.equals("8") || grade.equals("9")) {
                ObservableList<String> observableList = FXCollections.observableArrayList();
                ObservableList<SabjectDTO> sabjectDTOS = subjectModel.get6TO9Subject();
                for (SabjectDTO sabjectDTO : sabjectDTOS) {
                    observableList.add(sabjectDTO.getSubjectId());
                }
                COMSubject.setItems(observableList);

            } else if (grade.equals("10") || grade.equals("11")) {
                ObservableList<String> observableList = FXCollections.observableArrayList();
                ObservableList<SabjectDTO> sabjectDTOS = subjectModel.get10TO11Subject();
                for (SabjectDTO sabjectDTO : sabjectDTOS) {
                    observableList.add(sabjectDTO.getSubjectId());
                }
                COMSubject.setItems(observableList);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tableClickOnAtion(MouseEvent event) {
        ManageExamTM manageExamTM = TBLSetExam.getSelectionModel().getSelectedItem();

        if(manageExamTM != null){
            BUTUpdate.setDisable(false);
            BUTDelete.setDisable(false);
        }

        if (manageExamTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a row").show();
            return;
        }
            LBExamShedulID.setText(manageExamTM.getExamShedulID());
            LBExamID.setText(manageExamTM.getExamID());
            COBSelectHall.setValue(manageExamTM.getHallID());
            TXTExamName.setText(manageExamTM.getExamName());
            COMSubject.setValue(manageExamTM.getSubjectID());
            DTPDate.setValue(LocalDate.parse(manageExamTM.getDate()));
            TXTTime.setText(manageExamTM.getTime());

        String subjectID = TBLSetExam.getSelectionModel().getSelectedItem().getSubjectID();
        try {
            LBSetsubject.setText(subjectModel.getSubjectName(subjectID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadGrade(ComboBox<String> comboBox) {

        comboBox.setItems(FXCollections.observableArrayList(ClassLevel.getAllLabels()));
    }

    private void loadExamHall() throws SQLException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ObservableList<HallDTO> hallDTOS = hallModel.getAllHall();
        for (HallDTO hallDTO : hallDTOS) {
            observableList.add(hallDTO.getHallId());
        }
        COBSelectHall.setItems(observableList);
    }

    private void reset(){
        COMSubject.setValue("");
        LBExamShedulID.setText("");
        LBExamID.setText("");
        TXTExamName.setText("");
        TXTTime.setText("");
        DTPDate.setValue(null);
        COBSelectHall.setValue("");
        LBSetsubject.setText("");
    }
}
