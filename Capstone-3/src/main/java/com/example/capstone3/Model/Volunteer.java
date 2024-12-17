package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
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

    @NotEmpty
    @Size(min = 3, max = 50)
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @Email
    @NotEmpty
    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;

    @NotEmpty
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String phoneNumber;


    @Column(columnDefinition = "boolean not null default false")
    private boolean isTrained;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private Set<Training> trainings;


//    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
//    private  Set<Attendance>attendances;
//
//    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
//    private Set<VolunteerApplication>volunteerApplications;
//
//    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
//    private Set<VolunteerRating> volunteerRatings;
//
//    @ManyToOne
//    @JsonIgnore
//    private Role role;
//
//    @OneToMany
//    private Set<VolunteerSkills> volunteerSkills;






}
