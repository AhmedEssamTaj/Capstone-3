package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.RoleDTO;
import com.example.capstone3.Model.*;
import com.example.capstone3.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VolunteerApplicationService {

    private final VolunteerApplicationRepository volunteerApplicationRepository;

    private final VolunteerRepository volunteerRepository;
    private final EventRepository eventRepository;
    private final RoleRepository roleRepository;
    private final AttendanceRepository attendanceRepository;
    // test
    private final RoleService roleService;
    private final StadiumRepository stadiumRepository;

    // method to get ALL the applications
    public List<VolunteerApplication> getAllVolunteerApplications() {
        return volunteerApplicationRepository.findAll();
    }

    // method to get all the applications by a volunteer
    public List<VolunteerApplication> getVolunteerApplications(Integer volunteerId) {
        return volunteerApplicationRepository.findVolunteerApplicationByVolunteerId(volunteerId);
    }

    // method to add a volunteer application
    public void addVolunteerApplication(VolunteerApplication volunteerApplication, Integer volunteerId,Integer eventId) {
        Volunteer volunteer = volunteerRepository.findVolunteerById(volunteerId);
        Event event = eventRepository.findEventById(eventId);

        if (volunteer == null || event == null) {
            throw new ApiException("Volunteer or event not found");
        }
        Stadium stadium= stadiumRepository.findStadiumById(event.getStadium().getId());
        if (stadium == null) {
            throw new ApiException("Stadium not found");
        }

        if (volunteerApplicationRepository.existsVolunteerApplicationByVolunteerIdAndEventId(volunteerId, eventId)) {
            throw new ApiException("Volunteer already has an application for this event");
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


    // method to accept application
    public void acceptVolunteerApplication(Integer volunteerApplicationId, RoleDTO roleDTO) {
        VolunteerApplication volunteerApplication = volunteerApplicationRepository.findVolunteerApplicationById(volunteerApplicationId);
        if (volunteerApplication == null) {
            throw new ApiException("Volunteer application not found");
        }
        if (volunteerApplication.getStatus().equals("Approved")){
            throw new ApiException("Volunteer application is already approved");
        }
        volunteerApplication.setStatus("Approved");
        volunteerApplicationRepository.save(volunteerApplication);
//        Role role = new Role();
//        role.setName(roleDTO.getName());
//        role.setDescription(roleDTO.getDescription());
//        role.setVolunteer(volunteerApplication.getVolunteer());
//        role.setEvent(volunteerApplication.getEvent());
//        roleRepository.save(role);
        roleService.addRole(roleDTO);

        Attendance attendance = new Attendance();
        attendance.setVolunteer(volunteerApplication.getVolunteer());
        attendance.setEvent(volunteerApplication.getEvent());

        attendanceRepository.save(attendance);

    }

    // method to reject application
    public void rejcetApplication(Integer volunteerApplicationId) {

        VolunteerApplication volunteerApplication = volunteerApplicationRepository.findVolunteerApplicationById(volunteerApplicationId);
        if (volunteerApplication == null) {
            throw new ApiException("Volunteer application not found");
        }
        if (volunteerApplication.getStatus().equals("Approved")){
            throw new ApiException("Volunteer application is already approved");
        }
        volunteerApplication.setStatus("Rejected");
        volunteerApplicationRepository.save(volunteerApplication);
    }


}
