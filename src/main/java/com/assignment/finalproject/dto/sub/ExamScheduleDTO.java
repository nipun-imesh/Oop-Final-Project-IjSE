package com.assignment.finalproject.dto.sub;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class ExamScheduleDTO {
    private String examScheduleId;
    private String examId;
    private String hallId;
    private String examTime;
    private String examDate;


}
