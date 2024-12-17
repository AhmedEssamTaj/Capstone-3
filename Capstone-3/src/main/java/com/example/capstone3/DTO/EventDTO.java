package com.example.capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
@AllArgsConstructor
@Data
public class EventDTO {
    private Integer id;
    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private Integer stadiumId;

}
