package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"volunteer_id", "event_id"}) // this to make it so that only one rating per event is allowed for a volunteer
})
public class VolunteerRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "rating cannot be null")
    @Min(value = 1, message = "rating cannot be lees the 1")
    @Max(value = 5, message = "rating cannot be more than 5")
    @Column(columnDefinition = "int not null")
    private Integer rating;

    @NotEmpty(message = "feedback cannot be empty")
    @Column(columnDefinition = "varchar(250) not null")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Event event;

    @ManyToOne
    @JoinColumn(name = "volunteer_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Volunteer volunteer;

    // i dont think wee need this relation! since the event is already connected to a stadium

//    @ManyToOne
//    @JsonIgnore
//    private Stadium stadium;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
//    private Set<VolunteerApplication> volunteerApplications;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
//    private Set<VolunteerRating> volunteerRatings;
}
