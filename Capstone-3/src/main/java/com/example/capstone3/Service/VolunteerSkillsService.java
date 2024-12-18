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

// Ahmed

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
    public void  addVolunteerSkills(VolunteerSkills volunteerSkills, Integer volunteerId) {
        Volunteer volunteer = volunteerRepository.findVolunteerById(volunteerId);
        if (volunteer == null) {
            throw new ApiException("no volunteer found");
        }
        volunteerSkills.setVolunteer(volunteer);
        volunteerSkillsRepository.save(volunteerSkills);
    }

    // method to update volunteer skills
    public void updateVolunteerSkills(VolunteerSkills volunteerSkills) {
       VolunteerSkills volunteerSkills1 = volunteerSkillsRepository.findVolunteerSkillsById(volunteerSkills.getId());

        if (volunteerSkills1 == null) {
            throw new ApiException("no volunteer skill was found");
        }
        volunteerSkills1.setSkillType(volunteerSkills.getSkillType());
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

    // method compares the skills of volunteers (Aishtiaq-3)
    public String CompareSkillsOfVolunteers(Integer volunteerId1, Integer volunteerId2) {
        Volunteer volunteer1 = volunteerRepository.findVolunteerById(volunteerId1);
        Volunteer volunteer2 = volunteerRepository.findVolunteerById(volunteerId2);

        if (volunteer1 == null || volunteer2 == null) {
            throw new ApiException("not found volunteer");
        }

        List<VolunteerSkills> volunteerSkills1 = volunteerSkillsRepository.findVolunteerSkillsByVolunteerId(volunteerId1);
        List<VolunteerSkills> volunteerSkills2 = volunteerSkillsRepository.findVolunteerSkillsByVolunteerId(volunteerId2);

        if (volunteerSkills1.isEmpty() && volunteerSkills2.isEmpty()) {
            return "Both volunteers have no skills.";
        } else if (volunteerSkills1.isEmpty()) {
            return "Volunteer " + volunteer1.getId() + " has no skills.";
        } else if (volunteerSkills2.isEmpty()) {
            return "Volunteer " + volunteer2.getId() + " has no skills.";
        }

        for (VolunteerSkills skill1 : volunteerSkills1) {
            boolean found = false;
            for (VolunteerSkills skill2 : volunteerSkills2) {
                if (skill1.getSkillType().equals(skill2.getSkillType())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return "Volunteer " + volunteer1.getId() + " has a unique skill: " + skill1.getSkillType();
            }
        }

        for (VolunteerSkills skill2 : volunteerSkills2) {
            boolean found = false;
            for (VolunteerSkills skill1 : volunteerSkills1) {
                if (skill2.getSkillType().equals(skill1.getSkillType())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return "Volunteer " + volunteer2.getId() + " has a unique skill: " + skill2.getSkillType();
            }
        }

        return "Both volunteers have the same skills.";
    }

}
