package com.example.capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StadiumDTO {
    private Integer id;
    private String name;
    private String location;
    private Integer capacity;
    private String status;

    // Constructors, getters and setters
}
