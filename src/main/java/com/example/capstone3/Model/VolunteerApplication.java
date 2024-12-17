package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "applicationDate cannot be null")
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDate applicationDate = LocalDate.now();

    @NotEmpty(message = "status cannot be null")
    @Pattern(regexp = "^(Under Review|Approved|Rejected)$")
    @Column(columnDefinition = "varchar(12) not null")
    private String status = "Under Review";

    @NotEmpty(message = "questionnaire cannot be null")
    @Column(columnDefinition = "varchar(50) not null")
    private String questionnaireUrl;

    @ManyToOne
    @JsonIgnore
    private Event event;

    @ManyToOne
    @JsonIgnore
    private Volunteer volunteer;


}
