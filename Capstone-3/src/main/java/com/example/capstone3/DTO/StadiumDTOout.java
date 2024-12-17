package com.example.capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StadiumDTOout {

    private String name;
    private String location;
    private Integer capacity;
    private String status;

}
