package com.example.capstone3.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {

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

    @NotEmpty
    @Column(columnDefinition = "varchar(50) not null")
    private String role;

    @NotNull
    @Min(1)
    @Column(columnDefinition = "int not null")
    private Integer experienceYears;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Training training;

}
