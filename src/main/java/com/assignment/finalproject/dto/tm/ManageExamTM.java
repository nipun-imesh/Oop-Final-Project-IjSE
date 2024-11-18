package com.assignment.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ManageExamTM {
    private String examShedulID;
    private String examID;
    private String hallID;
    private String examName;
    private String grade;
    private String date;
    private String time;
    private String subjectID;

}
