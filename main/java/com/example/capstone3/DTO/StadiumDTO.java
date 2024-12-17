package com.example.capstone3.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StadiumDTO {
    private Integer id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "City cannot be empty")
    private String city;
    @NotEmpty(message = "Location cannot be empty")
    private String location;
    @PositiveOrZero(message = "The value must be either positive or zero")
    private Integer numberOfGates;
    @PositiveOrZero(message = "The value must be either positive or zero")
    private Integer parkingCapacity;
    @PositiveOrZero(message = "The value must be either positive or zero")
    private Integer emergencyExits;
    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Positive(message = "Value must be a positive number")
    private Integer capacity;
    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "Available|Maintenance", message = "Status must be either 'Available' or 'Maintenance'")
    private String status;
}
