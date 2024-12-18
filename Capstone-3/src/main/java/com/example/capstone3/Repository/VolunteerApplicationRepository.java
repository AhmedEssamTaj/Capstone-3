package com.example.capstone3.Repository;

import com.example.capstone3.Model.VolunteerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

// Aishtiaq

public interface VolunteerApplicationRepository extends JpaRepository<VolunteerApplication, Integer> {

    VolunteerApplication findVolunteerApplicationById(Integer id);

    List<VolunteerApplication> findVolunteerApplicationByVolunteerId(Integer volunteerId);

    Boolean existsVolunteerApplicationByVolunteerIdAndEventId(Integer volunteerId, Integer eventId);

    List<VolunteerApplication> findVolunteerApplicationByEventId(Integer eventId);

    @Query("SELECT COUNT(va) FROM VolunteerApplication va WHERE va.volunteer.id = :volunteerId AND va.status = 'Approved'")
    Integer countApprovedApplicationsByVolunteerId(Integer volunteerId);
}
