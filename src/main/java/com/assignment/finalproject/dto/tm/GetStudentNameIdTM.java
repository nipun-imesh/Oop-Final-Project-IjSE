package com.assignment.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class GetStudentNameIdTM {
    private String studentId;
    private String studentName;
}
