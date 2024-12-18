package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Aishtiaq

public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "title cannot be empty")
    @Size(min = 3, max = 100)
    @Column(columnDefinition = "varchar(100) not null")
    private String title;

    @NotEmpty(message = "description cannot be empty")
    @Column(columnDefinition = "text not null")
    private String description;

    @NotNull(message = "startDate cannot be empty")
    @Column(columnDefinition = "date not null")
//    @FutureOrPresent
    private LocalDate startDate;

    @NotNull(message = "endDate cannot be empty")
    @Column(columnDefinition = "date not null")
    //    @FutureOrPresent
    private LocalDate endDate;

    @Column(columnDefinition = "boolean not null default false")
    private boolean completed;

//    @ManyToOne
//    @JoinColumn(name = "volunteer_id", referencedColumnName = "id")
//    private Volunteer volunteer;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Volunteer> volunteers =new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Column(columnDefinition = "int not null default 10") // Default capacity for every training = 10
    private Integer capacity = 10;

    @Column(columnDefinition = "int not null default 0")
    private Integer enrolledVolunteers = 0;

}
