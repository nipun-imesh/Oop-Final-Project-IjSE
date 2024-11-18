package com.assignment.finalproject.dto.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AddParentDTO {

    private String parentId;
    private String parentName;
    private String parentEmail;
    private String status = "Active";
}
