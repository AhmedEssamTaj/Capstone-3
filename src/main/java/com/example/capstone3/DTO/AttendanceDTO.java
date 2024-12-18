package com.example.capstone3.DTO;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalTime;
//bushra

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {


    @NotNull(message = "Event cannot be null")
    private Integer event_id;
    @NotEmpty(message = "Volunteer cannot be empty")
    private Integer volunteer_id;

}
