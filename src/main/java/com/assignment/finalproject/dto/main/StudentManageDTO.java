package com.assignment.finalproject.dto.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class StudentManageDTO {
    private String studentId;
    private String studentName;
    private int age;
    private String dateofBarth;
    private String grade;
    private String S_class;
    private String parentId;
    private String status = "Active";


}
