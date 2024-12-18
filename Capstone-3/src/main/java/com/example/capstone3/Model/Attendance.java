package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"event_id", "volunteer_id"})
})

// Bushra

public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
//    @NotNull(message = "Event cannot be null")
    private Event event;
    @ManyToOne
//    @JoinColumn(name = "volunteer_id", referencedColumnName = "id")
//    @NotEmpty(message = "Volunteer cannot be empty")
    @JoinColumn(name = "volunteer_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Volunteer volunteer;

    @Column(columnDefinition = "time") // change from Date to time
    private LocalTime checkIn;

    @Column(columnDefinition = "time") // change from Date to time
    private LocalTime checkOut;

    @Column(columnDefinition = "varchar(50)")
    @Pattern(regexp = "Checked-in|Checked-out|Absent", message = "Status must be either 'Checked-in', 'Checked-out', or 'Absent'")
    private String status;
}