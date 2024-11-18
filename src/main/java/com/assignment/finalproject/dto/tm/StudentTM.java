package com.assignment.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class StudentTM {

    private String studentId;
    private String studentName;
    private int age;
    private String dateofBarth;
    private String grade ;
    private String S_class;
    private String parentId;

}
