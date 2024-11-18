package com.assignment.finalproject.controller;

import com.assignment.finalproject.dto.main.AddExamListDTO;
import com.assignment.finalproject.dto.sub.*;
import com.assignment.finalproject.dto.tm.ExamCartTM;
import com.assignment.finalproject.dto.tm.StudentTM;
import com.assignment.finalproject.model.mainModel.AddExamListModel;
import com.assignment.finalproject.model.mainModel.ExamShedulModel;
import com.assignment.finalproject.model.mainModel.ExamSubjectModel;
import com.assignment.finalproject.model.subModel.HallModel;
import com.assignment.finalproject.model.subModel.SubjectModel;
import com.assignment.finalproject.util.ClassLevel;
import javafx.application.Platform;
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
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddExamListPageControler implements Initializable {

    AddExamListModel addExamListModel = new AddExamListModel();
    HallModel hallModel = new HallModel();
    SubjectModel subjectModel = new SubjectModel();

    private final ObservableList<ExamCartTM> examCartTMS = FXCollections.observableArrayList();

    @FXML
    private AnchorPane ANKAddExampan;

    @FXML
    private Button BUTDelete;

    @FXML
    private Button BUTReset;

    @FXML
    private Button BUTSave;

    @FXML
    private Button BUTUpdate;

    @FXML
    private Button BUTAddAll;

    @FXML
    private ComboBox<String> COBSelectHall;

    @FXML
    private ComboBox<String> COBGrade;

    @FXML
    private ComboBox<String> COMSubjectID;

    @FXML
    private TableColumn<ExamCartTM, String> COLExamID;

    @FXML
    private TableColumn<ExamCartTM, String> COLExamName;

    @FXML
    private TableColumn<ExamCartTM, String> COLGrade;

    @FXML
    private TableColumn<ExamCartTM, String> COLHallName;

    @FXML
    private TableColumn<ExamCartTM, Button> COLDelete;

    @FXML
    private TableColumn<ExamCartTM, Time> COLTime;

    @FXML
    private TableColumn<ExamCartTM, Date> COLDate;

    @FXML
    private TableColumn<ExamCartTM, String> COlEcamShedulID;

    @FXML
    private TableColumn< ExamCartTM, String> COLSubjectID;

    @FXML
    private TableView<ExamCartTM> TBLplasMark;

    @FXML
    private DatePicker DTPDate;

    @FXML
    private Label LBExamID;

    @FXML
    private Label LBExamShedulID;

    @FXML
    private TextField TXTExamName;

    @FXML
    private TextField TXTTime;

    @FXML
    private Label LBTableLabel;

    @FXML
    private Label LBSbjectName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        COBGrade.setPromptText("Grade");
        COBSelectHall.setPromptText("Hall Name");
        COMSubjectID.setPromptText("Subject ID");

        setCellValues();
        getExamShedulID();
        getExamID();
        try {
           loadExamHall();
           loadGrade((ComboBox<String>) COBGrade);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValues() {

        COlEcamShedulID.setCellValueFactory(new PropertyValueFactory<>("ExamShedulID"));
        COLExamID.setCellValueFactory(new PropertyValueFactory<>("ExamID"));
        COLHallName.setCellValueFactory(new PropertyValueFactory<>("HallName"));
        COLExamName.setCellValueFactory(new PropertyValueFactory<>("ExamName"));
        COLGrade.setCellValueFactory(new PropertyValueFactory<>("Grade"));
        COLDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        COLTime.setCellValueFactory(new PropertyValueFactory<>("examTime"));
        COLSubjectID.setCellValueFactory(new PropertyValueFactory<>("SubjectID"));
        COLDelete.setCellValueFactory(new PropertyValueFactory<>("Delete"));

        TBLplasMark.setItems(examCartTMS);
    }

    @FXML
    void resetOnAction(ActionEvent event) {

        resetAddset();
        getExamShedulID();
        getExamID();
    }

    @FXML
    void AddOnAction(ActionEvent event) {
        String examshedulID = LBExamShedulID.getText();
        String examID = LBExamID.getText();
        String examName = TXTExamName.getText();
        String grade = String.valueOf(COBGrade.getValue());
        String date = DTPDate.getValue().toString();
        String time = TXTTime.getText();
        String hallName = String.valueOf(COBSelectHall.getValue());
        String subjectID = String.valueOf(COMSubjectID.getValue());
        String SubjectName = LBSbjectName.getText();

        Button btn = new Button("Remove");

       ExamCartTM examCartTM = new ExamCartTM(
                examshedulID,
                examID,
                examName,
                grade,
                date,
                time,
                hallName,
                subjectID,
                SubjectName,
                btn
        );

        btn.setOnAction(actionEvent -> {

            examCartTMS.remove(examCartTM);
            TBLplasMark.refresh();
        });
       resetAddset();
        examCartTMS.add(examCartTM);
        getExamShedulID();
        getExamID();
    }

    @FXML
    void tableClickOnAtion(MouseEvent event) {
        ExamCartTM markCatTM = TBLplasMark.getSelectionModel().getSelectedItem();
        if (markCatTM != null) {
            LBExamShedulID.setText(markCatTM.getExamShedulID());
            LBExamID.setText(markCatTM.getExamID());
            COBSelectHall.setValue(String.valueOf(markCatTM.getHallName()));
            TXTExamName.setText(markCatTM.getExamName());
            COBGrade.setValue(markCatTM.getGrade());
            DTPDate.setValue(LocalDate.parse(markCatTM.getExamDate()));
            TXTTime.setText(markCatTM.getExamTime());
            COMSubjectID.setValue(markCatTM.getSubjectID());
        }
    }

    @FXML
    void addAllOnAction(ActionEvent event) throws SQLException {

        ArrayList<ExamDTO> examDTOS = new ArrayList<>();
        ArrayList<ExamScheduleDTO> examScheduleDTOS = new ArrayList<>();
        ArrayList<ExamSubjectIdDTO> examSubjectIdDTOS = new ArrayList<>();

        for (ExamCartTM markCatTM : examCartTMS) {
            ExamDTO examDTO = new ExamDTO(
                    markCatTM.getExamID(),
                    markCatTM.getExamName(),
                    markCatTM.getGrade()
            );
            examDTOS.add(examDTO);
            System.out.println(examDTOS);
        }

        for (ExamCartTM examCartTM : examCartTMS) {
            ExamScheduleDTO examScheduleDTO = new ExamScheduleDTO(
                    examCartTM.getExamShedulID(),
                    examCartTM.getExamID(),
                    examCartTM.getHallName(),
                    examCartTM.getExamTime(),
                    examCartTM.getExamDate()
            );
            examScheduleDTOS.add(examScheduleDTO);
            System.out.println(examScheduleDTOS);
        }

        for (ExamCartTM examCartTM : examCartTMS) {
            ExamSubjectIdDTO examSubjectIdDTO = new ExamSubjectIdDTO(
                    examCartTM.getExamID(),
                    examCartTM.getSubjectID()
            );
            examSubjectIdDTOS.add(examSubjectIdDTO);
            System.out.println(examSubjectIdDTOS);
        }

        boolean isSaved = addExamListModel.saveExamAndSchedule(examDTOS, examScheduleDTOS, examSubjectIdDTOS);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Saved...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save...!").show();
        }
    }

    @FXML
    void selectGradeOnAction(ActionEvent event) throws SQLException {

        COMSubjectID.getItems().clear();

        if(getComboboxValues().equals("6") || getComboboxValues().equals("7") || getComboboxValues().equals("8") || getComboboxValues().equals("9")) {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ObservableList<SabjectDTO> sabjectDTOS = subjectModel.get6TO9Subject();
            for ( SabjectDTO sabjectDTO : sabjectDTOS) {
                observableList.add(sabjectDTO.getSubjectId());
            }
            COMSubjectID.setItems(observableList);

        } else if (getComboboxValues().equals("10") || getComboboxValues().equals("11")) {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ObservableList<SabjectDTO> sabjectDTOS = subjectModel.get10TO11Subject();
            for ( SabjectDTO sabjectDTO : sabjectDTOS) {
                observableList.add(sabjectDTO.getSubjectId());
            }
            COMSubjectID.setItems(observableList);
        }
    }

    @FXML
    void selectSubjectOnAction(ActionEvent event) {

        String subjectID = String.valueOf(COMSubjectID.getValue());
        try {
           LBSbjectName .setText(subjectModel.getSubjectName(subjectID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getExamShedulID() {
        String nextExamShedulID = addExamListModel.getExamShedulID();
        LBExamShedulID.setText(nextExamShedulID);
    }

    public void getExamID() {
        String nextExamID = addExamListModel.getExamID();
        LBExamID.setText(nextExamID);
    }

    private void loadExamHall() throws SQLException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ObservableList<HallDTO> hallDTOS = hallModel.getAllHall();
        for (HallDTO hallDTO : hallDTOS) {
            observableList.add(hallDTO.getHallId());
        }
        COBSelectHall.setItems(observableList);
    }

    private void loadGrade(ComboBox<String> comboBox) throws SQLException {

        comboBox.setItems(FXCollections.observableArrayList(ClassLevel.getAllLabels()));
    }

    private String getComboboxValues(){
      String Grade = String.valueOf(COBGrade.getValue());

        return Grade;
    }

    public void resetAddset(){
        LBExamShedulID.setText("");
        LBExamID.setText("");
        COBSelectHall.setValue("");
        TXTExamName.setText("");
        COBGrade.setValue("");
        DTPDate.setValue(null);
        TXTTime.setText("");
        COMSubjectID.setValue("");
        LBSbjectName.setText("");
    }
}
