package com.assignment.finalproject.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class AddMarkCartTM {
    private  String studentID;
    private  String studentName;
    private  String S_class;
    private  String examID;
    private  String subject;
    private  String markId;
    private  double mark;
    private Button  Delete;
}
