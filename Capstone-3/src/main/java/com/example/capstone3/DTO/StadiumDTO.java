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
public class StadiumDTO {@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

    @Column(columnDefinition = "varchar(50) not null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Column(columnDefinition = "varchar(100) not null")
    @NotEmpty(message = "City cannot be empty")
    private String city;

    @Column(columnDefinition = "varchar(100) not null")
    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @PositiveOrZero(message = "the value must be ether positive or zero")
    @Column(columnDefinition = "int not null  default 1")
    private Integer numberOfGates=1;

    @PositiveOrZero(message = "the value must be ether positive or zero")
    @Column(columnDefinition = "int not null  default 1")
    private Integer parkingCapacity=1;

    @PositiveOrZero(message = "the value must be ether positive or zero")
    @Column(columnDefinition = "int not null  default 1")
    private Integer emergencyExits=1;

    @Column(columnDefinition = "int default 1 not null")
    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Positive(message = "value must be a positive number")
    private Integer capacity;

    @Column(columnDefinition = "varchar(50) not null")
    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "Available|Maintenance", message = "Status must be either 'Available' or 'Maintenance'")
    private String status;
}
