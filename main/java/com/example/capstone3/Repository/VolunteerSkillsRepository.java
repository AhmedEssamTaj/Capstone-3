package com.example.capstone3.Repository;

import com.example.capstone3.Model.VolunteerSkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerSkillsRepository extends JpaRepository<VolunteerSkills, Integer> {

    VolunteerSkills findVolunteerSkillsById(Integer id);

    List<VolunteerSkills> findVolunteerSkillsByVolunteerId(Integer id);

}
