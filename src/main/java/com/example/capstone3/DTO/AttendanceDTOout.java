package com.example.capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
//bushra

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTOout {
    private String eventName;
    private String volunteerName;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private String status;
}
