package com.example.capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoleDTO {
    private String name;
    private Integer volunteer_id;
    private Integer event_id;
    private String description;


}
