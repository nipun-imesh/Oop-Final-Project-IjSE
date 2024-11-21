package com.assignment.finalproject.controller;

import com.assignment.finalproject.dto.sub.ClassDTO;
import com.assignment.finalproject.dto.sub.ExamNameDTO;
import com.assignment.finalproject.dto.tm.GetResaltTM;
import com.assignment.finalproject.model.mainModel.AddMarkModel;
import com.assignment.finalproject.model.mainModel.StudentResaliViewModel;
import com.assignment.finalproject.model.subModel.ClassModel;
import com.assignment.finalproject.util.ClassLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentResaltViewPageCointroler implements Initializable {

    ClassModel classModel = new ClassModel();
    AddMarkModel addMarkModel = new AddMarkModel();
    StudentResaliViewModel studentResaliViewModel = new StudentResaliViewModel();

    @FXML
    private Button BUTBack;

    @FXML
    private Button BUTSearch;

    @FXML
    private TableColumn<GetResaltTM, Double> COLSubjectMaek;

    @FXML
    private TableColumn<GetResaltTM, String> COLSubjectName;

    @FXML
    private ComboBox<String> COMClass;

    @FXML
    private ComboBox<String> COMExam;

    @FXML
    private ComboBox<String> COMGrade;

    @FXML
    private TextField TXTStudentID;

    @FXML
    private AnchorPane ANKViewResalt;

    @FXML
    private TableView<GetResaltTM> TBLResalt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadClass(COMClass);
        try {
            loadGrade();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValues() {

       COLSubjectMaek.setCellValueFactory(new PropertyValueFactory<>("mark"));
       COLSubjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));

    }

    @FXML
    void goLoginPageOnAction(ActionEvent event) {
        ANKViewResalt.getChildren().clear();
        AnchorPane load = null;
        try {
            load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ANKViewResalt.getChildren().add(load);
    }

    @FXML
    void searchResaltOnaction(ActionEvent event) {
        String studentId = TXTStudentID.getText();
        String classId = String.valueOf(COMClass.getValue());
        String grade = String.valueOf(COMGrade.getValue());
        String examName = String.valueOf(COMExam.getValue());

        try {
           ArrayList<GetResaltTM> getResaltTMS =studentResaliViewModel.searchResalt(studentId, classId, grade, examName);
            TBLResalt.setItems(FXCollections.observableArrayList(getResaltTMS));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void selectClassOnAction(ActionEvent event) {

    }

    @FXML
    void selectExamOnAaction(ActionEvent event) {

    }

    @FXML
    void selectGradeOnAction(ActionEvent event) {
        String classNumber = String.valueOf(COMClass.getValue());
        int grade = Integer.parseInt(classNumber);

        ArrayList<ExamNameDTO> examNameDTOS = null;
        try {
            examNameDTOS = addMarkModel.getExamList(classNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (ExamNameDTO examNameDTO : examNameDTOS) {
            observableList.add(examNameDTO.getExamName());
        }
        COMExam.setItems(observableList);
    }

    private void loadClass(ComboBox<String> comboBox) {

        comboBox.setItems(FXCollections.observableArrayList(ClassLevel.getAllLabels()));
    }

    private void loadGrade() throws SQLException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ObservableList<ClassDTO> classDTOS = classModel.getAllClass();
        for (ClassDTO classDTO : classDTOS) {
            observableList.add(classDTO.getClassId());
        }
        COMGrade.setItems(observableList);
    }

}
