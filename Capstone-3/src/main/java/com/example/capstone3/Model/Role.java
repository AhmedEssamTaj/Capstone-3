package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(255) not null")
//    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Column(columnDefinition = "text not null")
//    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @ManyToOne
//    @JoinColumn(name = "volunteer_id", referencedColumnName = "id")
    @JsonIgnore
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @JsonIgnore
    private Event event;
}