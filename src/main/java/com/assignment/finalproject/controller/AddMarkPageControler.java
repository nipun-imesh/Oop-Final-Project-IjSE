package com.assignment.finalproject.controller;

import com.assignment.finalproject.dto.sub.ClassDTO;
import com.assignment.finalproject.dto.sub.ExamNameDTO;
import com.assignment.finalproject.dto.sub.SabjectDTO;
import com.assignment.finalproject.dto.tm.AddMarkCartTM;
import com.assignment.finalproject.dto.tm.GetStudentNameIdTM;
import com.assignment.finalproject.model.mainModel.AddMarkModel;
import com.assignment.finalproject.model.subModel.ClassModel;
import com.assignment.finalproject.model.subModel.SubjectModel;
import com.assignment.finalproject.util.ClassLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMarkPageControler implements Initializable {

    ClassModel classModel = new ClassModel();
    AddMarkModel addMarkModel = new AddMarkModel();
    SubjectModel subjectModel = new SubjectModel();
    private final ObservableList<AddMarkCartTM> addMarkCartTMS = FXCollections.observableArrayList();


    @FXML
    private AnchorPane ANKMaksManege;

    @FXML
    private Button BUTAddMark;

    @FXML
    private Button BUTSearch;

    @FXML
    private Button BUTpalesallMark;

    @FXML
    private ComboBox<String> COBExamNAme;

    @FXML
    private TableColumn<AddMarkCartTM, Button> COLDelete;

    @FXML
    private TableColumn<AddMarkCartTM, String> COLExamID;

    @FXML
    private TableColumn<AddMarkCartTM, String> COLGrade;

    @FXML
    private TableColumn<AddMarkCartTM, Double> COLMark;

    @FXML
    private TableColumn<AddMarkCartTM, String> COLMarkID;

    @FXML
    private TableColumn<AddMarkCartTM, String> COLStudenID;

    @FXML
    private TableColumn<AddMarkCartTM, String> COLStudent;

    @FXML
    private TableColumn<AddMarkCartTM, String> COLSubjectID;

    @FXML
    private TableColumn<GetStudentNameIdTM, String> COLsetStudeName;

    @FXML
    private TableColumn<GetStudentNameIdTM, String> COLsetStudentID;

    @FXML
    private ComboBox<String> COMGrade;

    @FXML
    private ComboBox<String> COMSetClass;

    @FXML
    private Label LBExamId;

    @FXML
    private Label LBMarkId;

    @FXML
    private Label LBStudentId;

    @FXML
    private Label LBStudentName;

    @FXML
    private ComboBox<String> COMSubjectID;

    @FXML
    private Label LBSubjectName;

    @FXML
    private TableView<GetStudentNameIdTM> TBLStudent;

    @FXML
    private TableView<AddMarkCartTM> TBLMarkTable;

    @FXML
    private TextField TXTMark;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadGrade();
            loadClass((ComboBox<String>) COMSetClass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void palesAllMarkAction(ActionEvent event) {

    }

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException {
        String classId = (String) COMSetClass.getValue();
        String grade = String.valueOf(COMGrade.getValue());

        if (grade.isEmpty() && classId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter all fields").show();
        } else {

            ArrayList<GetStudentNameIdTM> getStudentNameIdDTOS = addMarkModel.getStudentNameId(classId, grade);

            TBLStudent.setItems(FXCollections.observableArrayList(getStudentNameIdDTOS));
            setCellValues();
        }
    }

    private void setMArkValues() {
        COLStudenID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        COLStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        COLGrade.setCellValueFactory(new PropertyValueFactory<>("S_class"));
        COLExamID.setCellValueFactory(new PropertyValueFactory<>("examID"));
        COLSubjectID.setCellValueFactory(new PropertyValueFactory<>("subject"));
        COLMarkID.setCellValueFactory(new PropertyValueFactory<>("markId"));
        COLMark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        COLDelete.setCellValueFactory(new PropertyValueFactory<>("Delete"));
    }

    private void setCellValues() {
        COLsetStudentID.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        COLsetStudeName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
    }

    @FXML
    void selectClassOnAction(ActionEvent event) throws SQLException {

        reSet();
        TBLStudent.getItems().clear();
        COBExamNAme.getItems().clear();

        String classNumber = String.valueOf(COMSetClass.getValue());
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
        COBExamNAme.setItems(observableList);
        TBLMarkTable.getItems().clear();
        loadSubject();
    }

    @FXML
    void selectGradeOnaction(ActionEvent event) {
        TBLMarkTable.getItems().clear();
    }

    @FXML
    void selectSubjectOnAction(ActionEvent event) {
        String subject = COMSubjectID.getValue();
        try {
            LBSubjectName.setText(subjectModel.getSubjectName(subject));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void selectExamNameOnAction(ActionEvent event) {
        String examName = String.valueOf(COBExamNAme.getValue());

        try {
            String examID = addMarkModel.fineExamId(examName);
            LBExamId.setText(examID);
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR, "Exam not found").show();
        }
        TBLMarkTable.getItems().clear();
    }

    @FXML
    void setMArkOnTableAction(ActionEvent event) {

    String subjectID = COMSubjectID.getValue();
        for(int i = 0; i < addMarkCartTMS.size(); i++) {
            String markID = addMarkCartTMS.get(i).getSubject();
            System.out.println(markID);
            if (markID.equals(subjectID)) {
                new Alert(Alert.AlertType.ERROR, "Subject already exist").show();
                return;
            }
        }
        try {
            String studentID = LBStudentId.getText();
            String studentName = LBStudentName.getText();
            String S_class = (String) COMSetClass.getValue();
            String examID = LBExamId.getText();
            String subject = COMSubjectID.getValue();
            String markID = LBMarkId.getText();
            double mark = Double.parseDouble(TXTMark.getText());

            Button btn = new Button("Remove");

            AddMarkCartTM addMarkCartTM = new AddMarkCartTM(
                    studentID,
                    studentName,
                    S_class,
                    examID,
                    subject,
                    markID,
                    mark,
                    btn
            );
            btn.setOnAction(actionEvent -> {

                addMarkCartTMS.remove(addMarkCartTM);
                TBLMarkTable.refresh();
            });
            addMarkCartTMS.add(addMarkCartTM);
            setMArkValues();
            TBLMarkTable.setItems(addMarkCartTMS);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid mark").show();
        }
    }

    @FXML
    void setStudentTBL(MouseEvent event) {
        GetStudentNameIdTM getStudentNameIdTM = TBLStudent.getSelectionModel().getSelectedItem();

        if (getStudentNameIdTM != null) {
            LBStudentId.setText(getStudentNameIdTM.getStudentId());
            LBStudentName.setText(getStudentNameIdTM.getStudentName());
        }
        TBLMarkTable.getItems().clear();
    }

    private void loadGrade() throws SQLException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ObservableList<ClassDTO> classDTOS = classModel.getAllClass();
        for (ClassDTO classDTO : classDTOS) {
            observableList.add(classDTO.getClassId());
        }
        COMGrade.setItems(observableList);
    }

    private void loadClass(ComboBox<String> comboBox) throws SQLException {

        comboBox.setItems(FXCollections.observableArrayList(ClassLevel.getAllLabels()));
    }

    private void loadSubject() throws SQLException {

        String classNumber = String.valueOf(COMSetClass.getValue());

        if (classNumber.equals("6") || classNumber.equals("7") || classNumber.equals("8") || classNumber.equals("9")) {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ObservableList<SabjectDTO> sabjectDTOS = subjectModel.get6TO9Subject();
            for (SabjectDTO sabjectDTO : sabjectDTOS) {
                observableList.add(sabjectDTO.getSubjectId());
            }
            COMSubjectID.setItems(observableList);

        } else if (classNumber.equals("10") || classNumber.equals("11")) {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ObservableList<SabjectDTO> sabjectDTOS = subjectModel.get10TO11Subject();
            for (SabjectDTO sabjectDTO : sabjectDTOS) {
                observableList.add(sabjectDTO.getSubjectId());
            }
            COMSubjectID.setItems(observableList);
        }
    }

    private void reSet(){
        LBStudentId.setText("");
        LBStudentName.setText("");
        LBMarkId.setText("");
        LBExamId.setText("");
        LBSubjectName.setText("");
        TXTMark.setText("");
    }
}


