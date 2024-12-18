package com.example.capstone3.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
//bushra

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(50) not null")
    @NotEmpty(message = "Title cannot be empty")
    private String name;

    @Column(columnDefinition = "date not null")
    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "Start time cannot be null")
    @Column(columnDefinition = "TIME not null")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    @Column(columnDefinition = "TIME not null")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @PositiveOrZero(message = "the value must be ether positive or zero")
    @Column(columnDefinition = "int not null  default 0")
    private Integer maxCapacity;

    @ManyToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    @NotNull(message = "Stadium cannot be null")
    private Stadium stadium;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<Volunteer> volunteers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<Role> roles;



    @Column(columnDefinition = "varchar(20) default 'Hold'")
    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "^(Run|Ended|Hold|Accept request)+$", message = "Status must be Run or Ended or Hold or Accept request")
    private String status="Hold";

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<VolunteerApplication> volunteerApplications;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<VolunteerRating> volunteerRatings;

}


