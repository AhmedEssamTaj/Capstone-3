package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Ahmed

public class VolunteerSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotEmpty(message = "description cannot be empty")
//    @Column(columnDefinition = "varchar(50) not null")
//    private String description;

    @Pattern(regexp = "^(Event Planning|Time Management|Crowd Control|Resource Management|Photography|First Aid|Team Leadership|Decision Making|Problem Solving)$"
            ,message = "The skill must be of \n" +
            " These suggestions are just Event Planning - Time Management - Crowd Control - Resource Management - Photography - First Aid - Team Leadership - Decision Making - Problem Solving")
    @Column(columnDefinition = "varchar(30)")
    private String skillType;

    @ManyToOne
    @JsonIgnore
    private Volunteer volunteer;
}
