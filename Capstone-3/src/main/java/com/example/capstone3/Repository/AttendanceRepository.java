package com.example.capstone3.Repository;

import com.example.capstone3.Model.Attendance;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    Attendance findAttendanceById(Integer id);


    @Query("SELECT a FROM Attendance a WHERE a.status = ?1")
    List<Attendance> findByStatus(String status);

    @Query("SELECT a FROM Attendance a WHERE a.event.id = ?1")
    List<Attendance> findByEventId(Integer eventId);

    @Query("SELECT a FROM Attendance a WHERE a.volunteer.id = ?1")
    List<Attendance> findByVolunteerId(Integer volunteerId);

    Attendance findAttendanceByEventAndVolunteer(Event event, Volunteer volunteer);

}
