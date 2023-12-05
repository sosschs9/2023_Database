package com.knu.Team8Database.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewReq {
    private String medicineName;
    private String user;
    private String rating;
    private String comment;
    private String medicineID;
    private String date;
}
