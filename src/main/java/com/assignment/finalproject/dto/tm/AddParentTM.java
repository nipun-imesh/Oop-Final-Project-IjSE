package com.assignment.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AddParentTM {

    private String parentId;
    private String parentName;
    private String parentEmail;
}
