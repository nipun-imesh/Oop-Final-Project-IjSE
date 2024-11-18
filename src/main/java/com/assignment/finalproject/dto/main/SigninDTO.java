package com.assignment.finalproject.dto.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SigninDTO {

    private String userid;
    private String username;
    private String password;
}
