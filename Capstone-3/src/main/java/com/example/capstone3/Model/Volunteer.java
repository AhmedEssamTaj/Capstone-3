package com.example.capstone3.Model;

import jakarta.persistence.*;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "volunteer")
    private Set<VolunteerApplication>volunteerApplications;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "volunteer")
    private Set<VolunteerRating> volunteerRatings;

    @OneToMany(cascade = CascadeType.ALL,  mappedBy = "volunteer")
    private Set<VolunteerSkills> volunteerSkills;

}
