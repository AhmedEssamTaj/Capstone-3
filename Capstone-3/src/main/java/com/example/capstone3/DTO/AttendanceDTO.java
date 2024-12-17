package com.example.capstone3.DTO;

import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Volunteer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {


    private Integer id;
    @NotNull(message = "Event cannot be null")
    private Integer event_id;
    @NotEmpty(message = "Volunteer cannot be empty")
    private Integer volunteer_id;
    private LocalTime checkIn;
    private LocalTime checkOut;
    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "Checked-in|Checked-out|Absent", message = "Status must be either 'Checked-in', 'Checked-out', or 'Absent'")
    private String status;
}
