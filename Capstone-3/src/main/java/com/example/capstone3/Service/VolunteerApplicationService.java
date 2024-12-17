package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Model.VolunteerApplication;
import com.example.capstone3.Repository.EventRepository;
import com.example.capstone3.Repository.VolunteerApplicationRepository;
import com.example.capstone3.Repository.VolunteerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VolunteerApplicationService {

    private final VolunteerApplicationRepository volunteerApplicationRepository;
    private final VolunteerRepository volunteerRepository;
    private final EventRepository eventRepository;

    // method to get ALL the applications
    public List<VolunteerApplication> getAllVolunteerApplications() {
        return volunteerApplicationRepository.findAll();
    }

    // method to get all the applications by a volunteer
    public List <VolunteerApplication> getVolunteerApplications(Integer volunteerId) {
        return volunteerApplicationRepository.findVolunteerApplicationByVolunteerId(volunteerId);
    }

    // method to add a volunteer application
    public void addVolunteerApplication(VolunteerApplication volunteerApplication) {
        Volunteer volunteer = volunteerRepository.findVolunteerById(volunteerApplication.getVolunteer().getId());
        Event event = eventRepository.findEventById(volunteerApplication.getEvent().getId());
        if (volunteer == null || event == null) {
            throw new ApiException("Volunteer or event not found");
        }
        // assign upon creation
        volunteerApplication.setVolunteer(volunteer);
        volunteerApplication.setEvent(event);
        volunteerApplicationRepository.save(volunteerApplication);
    }

    // method to update an application
    public void updateVolunteerApplication(VolunteerApplication volunteerApplication) {
        VolunteerApplication volunteerApplication1 = volunteerApplicationRepository.findVolunteerApplicationById(volunteerApplication.getId());
        if (volunteerApplication1 == null) {
            throw new ApiException("Volunteer not found");
        }
        volunteerApplication1.setApplicationDate(volunteerApplication.getApplicationDate());
        volunteerApplication1.setQuestionnaireUrl(volunteerApplication.getQuestionnaireUrl());
        volunteerApplication1.setStatus(volunteerApplication.getStatus());
    }

    // method to delete the volunteer application
    public void deleteVolunteerApplication(Integer volunteerApplicationId) {
        VolunteerApplication volunteerApplication = volunteerApplicationRepository.findVolunteerApplicationById(volunteerApplicationId);
        if (volunteerApplication == null) {
            throw new ApiException("Volunteer not found");
        }
        volunteerApplicationRepository.delete(volunteerApplication);
    }



}
