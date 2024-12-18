package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.RoleDTO;
import com.example.capstone3.DTO.VolunteerApplicationOutDTO;
import com.example.capstone3.DTO.VolunteerSkillsDTO;
import com.example.capstone3.Model.*;
import com.example.capstone3.Repository.*;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.hibernate.Hibernate;

@Service
@AllArgsConstructor

// Aishtiaq

public class VolunteerApplicationService {

    private final VolunteerApplicationRepository volunteerApplicationRepository;

    private final VolunteerRepository volunteerRepository;
    private final EventRepository eventRepository;
    private final RoleRepository roleRepository;
    private final AttendanceRepository attendanceRepository;
    private final RoleService roleService;
    private final StadiumRepository stadiumRepository;
    private final AttendanceService attendanceService;

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
        Volunteer volunteer = volunteerRepository.findVolunteerById(volunteerApplication.getVolunteer().getId());
        System.out.println(volunteer.isTrained());
        if (!volunteer.isTrained()) {
            throw new ApiException("Volunteer is not trained");
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

        if (attendanceRepository.existsByEventAndVolunteer(
                volunteerApplication.getEvent(), volunteerApplication.getVolunteer())) {
            throw new ApiException("Attendance record already exists for this volunteer and event");
        }

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

    public List<VolunteerApplicationOutDTO> getVolunteerApplicationsByEventId(Integer eventId) {
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            throw new ApiException("Event not found");
        }
        List<VolunteerApplication> volunteerApplications = volunteerApplicationRepository.findVolunteerApplicationByEventId(eventId);
        if (volunteerApplications == null) {
            throw new ApiException("no Volunteer application for this event was found");
        }

        return convertVolunteerApplication(volunteerApplications);
    }

//    public List <VolunteerSkillsDTO> skillsDto(Volunteer volunteer) {
//        if (volunteer.getVolunteerSkills() == null) {
//            return new ArrayList<>();
//        }
//        List <VolunteerSkillsDTO> skillsDTOS = new ArrayList<>();
//
//
////            List<VolunteerSkills> volunteerSkills =  new ArrayList<>(volunteer.getVolunteerSkills());
//            for (VolunteerSkills v : volunteer.getVolunteerSkills()) {
//                skillsDTOS.add(new VolunteerSkillsDTO(v.getSkillType()));
//
//        }



//        return skillsDTOS;
//    }


    public List<VolunteerApplicationOutDTO> convertVolunteerApplication(Collection<VolunteerApplication> volunteerApplications) {
        List<VolunteerApplicationOutDTO> volunteerApplicationOutDTOS = new ArrayList<>();

        // Create a safe copy of the collection
        List<VolunteerApplication> safeList = new ArrayList<>(volunteerApplications);

        for (int i = 0; i < safeList.size(); i++) {
            VolunteerApplication v = safeList.get(i);
            Volunteer x = v.getVolunteer();

            // Force Hibernate to initialize the volunteer skills collection
            Hibernate.initialize(x.getVolunteerSkills());

            volunteerApplicationOutDTOS.add(new VolunteerApplicationOutDTO(
                    v.getApplicationDate(),
                    v.getStatus(),
                    v.getQuestionnaireUrl(),
                    x.getName(),
                    convert(x.getVolunteerSkills())));
        }
        return volunteerApplicationOutDTOS;
    }





    public List<VolunteerSkillsDTO> convert(Collection<VolunteerSkills> cc) {
        List<VolunteerSkillsDTO> skillsDTOS = new ArrayList<>();

        // Force Hibernate to initialize the collection
        Hibernate.initialize(cc);

        // Now create a safe copy of the collection
        List<VolunteerSkills> safeList = new ArrayList<>(cc);

        for (int i = 0; i < safeList.size(); i++) {
            VolunteerSkills v = safeList.get(i);
            skillsDTOS.add(new VolunteerSkillsDTO(v.getSkillType()));
        }
        return skillsDTOS;
    }





    public Integer getNumberOfApprovedEventsForVolunteer(Integer volunteerId) {

        Integer number = volunteerApplicationRepository.countApprovedApplicationsByVolunteerId(volunteerId);
        return number;
    }
}
