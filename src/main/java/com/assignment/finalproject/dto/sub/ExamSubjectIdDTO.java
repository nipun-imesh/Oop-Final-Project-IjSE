package com.assignment.finalproject.dto.sub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ExamSubjectIdDTO {
    private String examId;
    private String subjectId;
}
