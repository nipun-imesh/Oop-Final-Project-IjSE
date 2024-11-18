package com.assignment.finalproject.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;
import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ExamCartTM {

    private String examShedulID;
    private String examID;
    private String examName;
    private String grade;
    private String examDate;
    private String examTime;
    private String hallName;
    private String subjectID;
    private String SubjectName;
    private Button Delete;


}
