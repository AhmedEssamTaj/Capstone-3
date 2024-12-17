package com.example.capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {
    private Integer id;
    private Integer eventId;
    private Integer volunteerId;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private String status;
}
