package com.example.capstone3.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StadiumDTO {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "City cannot be empty")
    private String city;
    @NotEmpty(message = "Location cannot be empty")
    private String location;
    @PositiveOrZero(message = "The value must be either positive or zero")
    private Integer numberOfGates=1;
    @PositiveOrZero(message = "The value must be either positive or zero")
    private Integer parkingCapacity=1;
    @PositiveOrZero(message = "The value must be either positive or zero")
    private Integer emergencyExits=1;
    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Positive(message = "Value must be a positive number")
    private Integer capacity;

    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "Available|Maintenance", message = "Status must be either 'Available' or 'Maintenance'")
    private String status;
}
