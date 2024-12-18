package com.example.capstone3.DTO;

import com.example.capstone3.Model.Stadium;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@AllArgsConstructor
@Data
public class EventDTO {

    private Integer id;
    @NotEmpty(message = "Title cannot be empty")
    private String name;
    @NotNull(message = "Date cannot be null")
    private LocalDate date;
    @NotNull(message = "Start time cannot be null")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @NotNull(message = "End time cannot be null")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    @PositiveOrZero(message = "the value must be ether positive or zero")
    private Integer maxCapacity=1;
    @NotNull(message = "Stadium cannot be null")
    private Integer stadium_id;
    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "^(Run|Ended|Hold|Accept request)+$", message = "Status must be Run or Ended or Hold or Accept request")
    private String status;



}
