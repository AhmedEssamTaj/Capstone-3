package com.example.capstone3.DTO;
//bushra


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoleDTO {

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Description cannot be empty")
    private String description;
    @NotEmpty(message = "Description cannot be empty")
    private Integer volunteer_id;
    private Integer event_id;
}
