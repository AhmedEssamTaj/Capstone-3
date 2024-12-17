package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    @Id
    private Integer id;

    @NotEmpty
    @Size(min = 3, max = 100)
    @Column(columnDefinition = "varchar(100) not null")
    private String title;

    @NotEmpty
    @Column(columnDefinition = "text not null")
    private String description;

    @NotNull
    @Column(columnDefinition = "date not null")
    private LocalDate startDate;

    @NotNull
    @Column(columnDefinition = "date not null")
    private LocalDate endDate;

    @Column(columnDefinition = "boolean not null default false")
    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "volunteer_id", referencedColumnName = "id")
    private Volunteer volunteer;

    @OneToOne
    @JsonIgnore
    @MapsId
    private Trainer trainer;
}
