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
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<VolunteerApplication> volunteerApplications;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<VolunteerRating> volunteerRatings;
}
