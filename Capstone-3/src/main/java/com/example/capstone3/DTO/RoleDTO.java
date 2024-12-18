package com.example.capstone3.DTO;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoleDTO {

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Description cannot be empty")
    private String description;
    @NotNull(message = "volunteer_id cannot be empty")
    private Integer volunteer_id;
    @NotNull(message = "event_id cannot be empty")
    private Integer event_id;
}
