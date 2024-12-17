package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Model.VolunteerSkills;
import com.example.capstone3.Repository.VolunteerRepository;
import com.example.capstone3.Repository.VolunteerSkillsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VolunteerSkillsService {
    private VolunteerSkillsRepository volunteerSkillsRepository;
    private final VolunteerRepository volunteerRepository;

    // get ALL volunteers skills
    public List<VolunteerSkills> getAllVolunteerSkills() {
        return volunteerSkillsRepository.findAll();
    }

    // method to get All skills of a volunteer
    public List<VolunteerSkills> getVolunteerSkillsByVolunteerId(Integer volunteerId) {

        Volunteer volunteer = volunteerRepository.findVolunteerById(volunteerId);
        if (volunteer == null) {
            throw new ApiException("no volunteer found");
        }
        return volunteerSkillsRepository.findVolunteerSkillsByVolunteerId(volunteerId);

    }

    // add volunteer skills
    public void  addVolunteerSkills(VolunteerSkills volunteerSkills) {
        Volunteer volunteer = volunteerRepository.findVolunteerById(volunteerSkills.getId());
        if (volunteer == null) {
            throw new ApiException("no volunteer found");
        }
        volunteerSkillsRepository.save(volunteerSkills);
    }

    // method to update volunteer skills
    public void updateVolunteerSkills(VolunteerSkills volunteerSkills) {
       VolunteerSkills volunteerSkills1 = volunteerSkillsRepository.findVolunteerSkillsById(volunteerSkills.getId());

        if (volunteerSkills1 == null) {
            throw new ApiException("no volunteer skill was found");
        }
        volunteerSkills1.setDescription(volunteerSkills.getDescription());
        volunteerSkillsRepository.save(volunteerSkills1);

    }

    // method to delete volunteer skills
    public void deleteVolunteerSkills(Integer volunteerSkillsId) {
        VolunteerSkills volunteerSkills = volunteerSkillsRepository.findVolunteerSkillsById(volunteerSkillsId);
        if (volunteerSkills == null) {
            throw new ApiException("no volunteer skill was found");
        }
        volunteerSkillsRepository.delete(volunteerSkills);
    }

}
