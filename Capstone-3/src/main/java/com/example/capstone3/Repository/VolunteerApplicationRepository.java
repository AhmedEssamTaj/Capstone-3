package com.example.capstone3.Repository;

import com.example.capstone3.Model.VolunteerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerApplicationRepository extends JpaRepository<VolunteerApplication, Integer> {

    VolunteerApplication findVolunteerApplicationById(Integer id);

    List<VolunteerApplication> findVolunteerApplicationByVolunteerId(Integer volunteerId);

    Boolean existsVolunteerApplicationByVolunteerIdAndEventId(Integer volunteerId, Integer eventId);
}
