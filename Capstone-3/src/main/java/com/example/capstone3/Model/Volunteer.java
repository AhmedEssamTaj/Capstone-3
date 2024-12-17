package com.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Volunteer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(50) not null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "volunteer")
    private Set<VolunteerApplication>volunteerApplications;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "volunteer")
    private Set<VolunteerRating> volunteerRatings;

    @OneToMany(cascade = CascadeType.ALL,  mappedBy = "volunteer")
    private Set<VolunteerSkills> volunteerSkills;

}