package com.assignment.finalproject.dto.sub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ExamDTO {
    private String examId;
    private String examName;
    private String grade;

}
