package com.example.capstone3.Service;


import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.VolunteerDTO;
import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;


    public List<Volunteer> getAllVolunteer(){
        return volunteerRepository.findAll();
    }

    public List<VolunteerDTO> getVolunteerDTO(){

        List<Volunteer>volunteers=volunteerRepository.findAll();

        List<VolunteerDTO>volunteerDTOS=new ArrayList<>();

        for (Volunteer v:volunteers){

            VolunteerDTO volunteerDTO=new VolunteerDTO(v.getName(),v.isTrained());

            volunteerDTOS.add(volunteerDTO);
        }
        return  volunteerDTOS;
    }

    public void addVolunteer(Volunteer volunteer){

        volunteerRepository.save(volunteer);
    }

    public void updateVolunteer(Integer id, Volunteer volunteer){

        Volunteer volunteer1=volunteerRepository.findVolunteerById(id);

        if(volunteer1 ==null){

            throw new ApiException("not found Volunteer");
        }
        volunteer1.setName(volunteer.getName());
        volunteer1.setEmail(volunteer.getEmail());
        volunteer1.setPhoneNumber(volunteer.getPhoneNumber());
        volunteer1.setTrained(volunteer.isTrained());

        volunteerRepository.save(volunteer1);
    }

    public void deleteVolunteer(Integer id){

        Volunteer volunteer=volunteerRepository.findVolunteerById(id);

        if(volunteer==null){

            throw new ApiException("not found Volunteer");
        }

        volunteerRepository.delete(volunteer);
    }

    // List of Volunteers Who Did Not Apply for Any Events (Aishtiag-7)

    public List<Volunteer> getVolunteersWithoutApplications() {
        return volunteerRepository.findVolunteersWithoutApplications();
    }

    //Get a list of trained volunteers only (Aishtiaq-8)

    public List<Volunteer> getTrainedVolunteers() {
        List<Volunteer> trainedVolunteers = volunteerRepository.findTrainedVolunteers();
        if (trainedVolunteers.isEmpty()) {
            throw new RuntimeException("No trained volunteers found.");
        }
        return trainedVolunteers;
    }

    //Returns a list of volunteers who did not attend an event (inactive volunteers)  (Aishtiaq-10)

    public List<Volunteer> getVolunteersWithNoAttendance() {
        List<Volunteer> inactiveVolunteers = volunteerRepository.findVolunteersWithNoAttendance();
        if (inactiveVolunteers.isEmpty()) {
            throw new RuntimeException("No inactive volunteers found.");
        }
        return inactiveVolunteers;
    }
}
