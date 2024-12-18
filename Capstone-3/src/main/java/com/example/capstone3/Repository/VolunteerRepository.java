package com.example.capstone3.Repository;

import com.example.capstone3.Model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

// Ahmed

public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {

    Volunteer findVolunteerById(Integer id);

    // List of Volunteers Who Did Not Apply for Any Events (Aishtiag-7)
    @Query("SELECT v FROM Volunteer v WHERE v.id NOT IN (SELECT va.volunteer.id FROM VolunteerApplication va)")
    List<Volunteer> findVolunteersWithoutApplications();

    //Get a list of trained volunteers only (Aishtiaq-8)
    @Query("SELECT v FROM Volunteer v WHERE v.isTrained = true")
    List<Volunteer> findTrainedVolunteers();

    //Returns a list of volunteers who did not attend an event (inactive volunteers)  (Aishtiaq-10)
    @Query("SELECT v FROM Volunteer v WHERE v.id NOT IN (SELECT a.volunteer.id FROM Attendance a)")
    List<Volunteer> findVolunteersWithNoAttendance();


}
