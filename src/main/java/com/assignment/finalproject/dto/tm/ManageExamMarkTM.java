package com.assignment.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ManageExamMarkTM {
   private String examID;
   private String subjectID;
   private String examName;
   private String mark;
   private String date;
}
