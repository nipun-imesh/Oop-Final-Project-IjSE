package com.assignment.finalproject.dto.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AddExamListDTO {
    private String examShedulID;
    private String examID;
    private String examName;
    private String grade;
    private String examDate;
    private String examTime;
    private String hallName;
    private String subjectID;

}
