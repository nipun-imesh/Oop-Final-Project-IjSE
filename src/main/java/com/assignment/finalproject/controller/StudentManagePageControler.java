package com.assignment.finalproject.controller;

import com.assignment.finalproject.dto.main.StudentManageDTO;
import com.assignment.finalproject.dto.sub.ClassDTO;
import com.assignment.finalproject.dto.tm.StudentTM;
import com.assignment.finalproject.model.subModel.ClassModel;
import com.assignment.finalproject.model.mainModel.StudentManageModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;


public class StudentManagePageControler implements Initializable {

    private final ClassModel classModel = new ClassModel();
    private final StudentManageModel studentManageModel = new StudentManageModel();


    @FXML
    private AnchorPane ANKaddStudentpain;

    @FXML
    private Button BUTAddParent;

    @FXML
    private CheckBox CHEC10;

    @FXML
    private CheckBox CHEC6TO9;

    @FXML
    private ComboBox<String> COMClass;

    @FXML
    private DatePicker DATEDateofBarth;

    @FXML
    private TextField TXTGrade;


    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button BUTSave;


    @FXML
    private TableColumn<StudentTM, Integer> COLAge;

    @FXML
    private TableColumn<StudentTM, Date> COLDfB;

    @FXML
    private TableColumn<StudentTM, String> COLGrade;

    @FXML
    private TableColumn<StudentTM, String> COLName;

    @FXML
    private TableColumn<StudentTM, String> COLParentName;

    @FXML
    private TableColumn<StudentTM, String> COLStudentId;

    @FXML
    private TableView<StudentTM> TBLStudent;

    @FXML
    private TextField TXTName;

    @FXML
    private Label LBStudentId;

    @FXML
    private TextField TXTAge;


    @FXML
    private Label TXTStuParentId;

    @FXML
    private Label TXTStuParentName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        COLStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        COLName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        COLAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        COLDfB.setCellValueFactory(new PropertyValueFactory<>("dateofBarth"));
        COLGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        COLParentName.setCellValueFactory(new PropertyValueFactory<>("parentId"));

        try {
            loadNextStudentID();
            loadAllStudent();
            loadClass();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

    }

    @FXML
    void addparentOnAction(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddParentToStudentpage.fxml"));
            Parent load = loader.load();
            AddParentToStudentPageControler addptc = loader.getController();
            addptc.setPatentController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Add Parent to Student");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }

    public Label setTXTStuParentId(){
        return this.TXTStuParentId;
    }

    public Label setTXTStuParentName(){
        return this.TXTStuParentName;
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String studentID = LBStudentId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = false;
            try {
                isDeleted = studentManageModel.deleteStudent(studentID);
                reSet();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Student deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Student...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String namePattern = "^[A-Za-z ]+$";
        String agePattern = "^[0-9]{2}$"; // A simple check for a two-digit number (age between 11-17)
        String gradePattern = "^[6-9]$|^10$|^11$"; // Ensures grade is between 6 and 11 (valid grades)
        String datePattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

        String studentNam = TXTName.getText();
        if (studentNam.isEmpty() || !studentNam.matches(namePattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid name (only letters and spaces)").show();
            return;
        }

        if ( TXTAge.getText().isEmpty() || !TXTAge.getText().matches(datePattern)) {
            System.out.println("Valid date format!");
        } else {
            System.out.println("Invalid date format!");
        }

        String ageText = TXTAge.getText();
        if (ageText.isEmpty() || !ageText.matches(agePattern)) {
            new Alert(Alert.AlertType.ERROR, "Age must be between 11 and 17").show();
            return;
        }
        int ages = Integer.parseInt(ageText);
        if (ages < 11 || ages > 17) {
            new Alert(Alert.AlertType.ERROR, "Age must be between 11 and 17").show();
            return;
        }

        String DateOB = DATEDateofBarth.getValue() != null ? DATEDateofBarth.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
        if (DateOB.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a valid date of birth").show();
            return;
        }

        String grades = TXTGrade.getText();
        if (grades.isEmpty() || !grades.matches(gradePattern)) {
            new Alert(Alert.AlertType.ERROR, "Grade must be between 6 and 11").show();
            return;
        }

        String Studentclass = COMClass.getValue();
        if (Studentclass == null || Studentclass.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a class").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if( optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            if (TXTName.getText().isEmpty() || TXTAge.getText().isEmpty() || DATEDateofBarth.getValue() == null || TXTGrade.getText().isEmpty() || COMClass.getValue() == null || TXTStuParentId.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please enter all fields").show();
            } else {
                String studentID = LBStudentId.getText();
                String studentName = TXTName.getText();
                int age = Integer.parseInt(TXTAge.getText());
                String DOB = DATEDateofBarth.getValue() != null ? DATEDateofBarth.getValue().toString() : ""; // Corrected DOB input
                String grade = TXTGrade.getText();
                String S_class = String.valueOf(COMClass.getValue());
                String parentID = TXTStuParentId.getText();

                StudentManageDTO studentManageDTO = new StudentManageDTO(
                        studentID,
                        studentName,
                        age,
                        DOB,
                        grade,
                        S_class,
                        parentID,
                        "Active"
                );
                boolean isUpdate = studentManageModel.updateStudent(studentManageDTO);
                if (isUpdate) {
                    reSet();
                    new Alert(Alert.AlertType.INFORMATION, "Student update...!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to update Student...!").show();
                }
            }
        }
    }


    @FXML
    void onClickTable(MouseEvent event) {

        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
        BUTSave.setDisable(true);

         StudentTM studentTM = TBLStudent.getSelectionModel().getSelectedItem();
        if (studentTM != null) {
            LBStudentId.setText(studentTM.getStudentId());
            TXTName.setText(studentTM.getStudentName());
            TXTAge.setText(String.valueOf(studentTM.getAge()));
            DATEDateofBarth.setValue(LocalDate.parse(studentTM.getDateofBarth()));
            String grade = studentTM.getGrade();  // Returns "6B"
            String onlyGrade = grade.substring(0, 1); // Extracts "6" only
            TXTGrade.setText(onlyGrade);
            COMClass.setValue(studentTM.getS_class());
            TXTStuParentId.setText(studentTM.getParentId());
        }
    }

    public void reSet(){
        TXTName.setText("");
        TXTAge.setText("");
        DATEDateofBarth.setValue(null);
        TXTGrade.setText("");
        TXTStuParentId.setText("");
        TXTStuParentName.setText("");
        refreshTable();
        try {
            loadNextStudentID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException {
        // Regex for Name (only letters and spaces)
        String namePattern = "^[A-Za-z ]+$";
        String agePattern = "^[0-9]{2}$"; // A simple check for a two-digit number (age between 11-17)
        String gradePattern = "^[6-9]$|^10$|^11$"; // Ensures grade is between 6 and 11 (valid grades)
        String datePattern = "^(0[1-9]|[12][0-9]|3[01])[-.](0[1-9]|1[0-2])[-.]([0-9]{4})$";


        // Get and validate name
        String studentName = TXTName.getText();
        if (studentName.isEmpty() || !studentName.matches(namePattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid name (only letters and spaces)").show();
            return;
        }

        if ( TXTAge.getText().isEmpty() || !TXTAge.getText().matches(datePattern)) {
            System.out.println("Valid date format!");
        } else {
            System.out.println("Invalid date format!");
        }

        // Get and validate age
        String ageText = TXTAge.getText();
        if (ageText.isEmpty() || !ageText.matches(agePattern)) {
            new Alert(Alert.AlertType.ERROR, "Age must be between 11 and 17").show();
            return;
        }
        int age = Integer.parseInt(ageText);
        if (age < 11 || age > 17) {
            new Alert(Alert.AlertType.ERROR, "Age must be between 11 and 17").show();
            return;
        }

        // Get and validate Date of Birth (DatePicker automatically validates the date format)
        String DOB = DATEDateofBarth.getValue() != null ? DATEDateofBarth.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
        if (DOB.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a valid date of birth").show();
            return;
        }

        // Get and validate grade
        String grade = TXTGrade.getText();
        if (grade.isEmpty() || !grade.matches(gradePattern)) {
            new Alert(Alert.AlertType.ERROR, "Grade must be between 6 and 11").show();
            return;
        }

        // Get and validate class (ensure a value is selected from ComboBox)
        String S_class = COMClass.getValue();
        if (S_class == null || S_class.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a class").show();
            return;
        }

        // Create Student DTO (with empty parent ID)
        String studentId = LBStudentId.getText();
        String parentID = TXTStuParentId.getText();  // Parent ID can be left empty if not needed.

        StudentManageDTO studentManageDTO = new StudentManageDTO(
                studentId,
                studentName,
                age,
                DOB,
                grade,
                S_class,
                parentID, // Empty string for now if parent ID is not required
                "Active"
        );

        // Attempt to save the student data
        boolean isSaved = StudentManageModel.saveStudent(studentManageDTO);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Student added successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Student not added").show();
        }
        
        resetOnAction(event);
    }


    @FXML
    void resetOnAction(ActionEvent event) {

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        BUTSave.setDisable(false);

        reSet();
    }

    @FXML
    void selectClassOnaction(ActionEvent event) throws SQLException {
        String selectedClassId = COMClass.getSelectionModel().getSelectedItem();
        ClassDTO classDTO = classModel.findByclass(selectedClassId);

        if (classDTO != null) {
            TXTGrade.setText(classDTO.getClassId());
        }
    }

    private void loadClass() throws SQLException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ObservableList<ClassDTO> classDTOS = classModel.getAllClass();
        for (ClassDTO classDTO : classDTOS) {
            observableList.add(classDTO.getClassId());
        }
        COMClass.setItems(observableList);
    }

    private void loadAllStudent() throws SQLException {
        ArrayList<StudentManageDTO> studentManageDTOS = StudentManageModel.getAllstuden();

        ObservableList<StudentTM> studentTMS = FXCollections.observableArrayList();
        for (StudentManageDTO studentManageDTO : studentManageDTOS) {
            StudentTM studentTM = new StudentTM(
                    studentManageDTO.getStudentId(),
                    studentManageDTO.getStudentName(),
                    studentManageDTO.getAge(),
                    studentManageDTO.getDateofBarth(),
                    studentManageDTO.getGrade() + " " + studentManageDTO.getS_class(),
                    studentManageDTO.getS_class(),
                    studentManageDTO.getParentId()
            );
            studentTMS.add(studentTM);
        }

        TBLStudent.setItems(studentTMS);
    }

    public void loadNextStudentID() throws SQLException {
        String nextStudentId = studentManageModel.getStudentID();
        LBStudentId.setText(nextStudentId);
    }

    public void refreshTable() {
        try {
            loadAllStudent();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
