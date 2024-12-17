package com.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @NotNull(message = "Event cannot be null")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "volunteer_id", referencedColumnName = "id")
    @NotEmpty(message = "Volunteer cannot be empty")
    private Volunteer volunteer;

    @Column(columnDefinition = "Date")
    private LocalTime checkIn;

    @Column(columnDefinition = "Date ")
    private LocalTime checkOut;

    @Column(columnDefinition = "varchar(50) not null")
    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "Checked-in|Checked-out|Absent", message = "Status must be either 'Checked-in', 'Checked-out', or 'Absent'")
    private String status;
}