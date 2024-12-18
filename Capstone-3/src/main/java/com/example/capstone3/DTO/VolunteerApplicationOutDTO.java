package com.example.capstone3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class VolunteerApplicationOutDTO {

    private LocalDate applicationDate;

    private String status ;

    private String questionnaireUrl;

    private String volunteerName;

    private List<VolunteerSkillsDTO> volunteerSkillsDTOS;

//    private List<VolunteerDTO> volunteerDTO;
//    private List<EventDTO>eventDTOS;



}
