package com.example.capstone3.Repository;

import com.example.capstone3.Model.Attendance;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

// Bushra

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    @Query("SELECT a FROM Attendance a WHERE a.id = :id")
    Attendance findAttendanceById(Integer id);

    Attendance findAttendanceByVolunteer_IdAndEvent_Id(Integer volunteerId, Integer eventId);

    @Query("SELECT a FROM Attendance a WHERE a.event.id = ?1")
    List<Attendance> findByEventId(Integer eventId);

    @Query("SELECT a FROM Attendance a WHERE a.event = :event")
    List<Attendance> findByEvent(Event event);

    @Query("SELECT a FROM Attendance a WHERE a.volunteer.id = ?1")
    List<Attendance> findByVolunteerId(Integer volunteerId);


    @Query("SELECT a FROM Attendance a WHERE a.volunteer = :volunteer")
    List<Attendance> findByVolunteer( Volunteer volunteer);

    boolean existsByEventAndVolunteer(Event event, Volunteer volunteer);

    List<Attendance> findAttendanceByIdAndStatus(Integer id,String status);

    @Query("SELECT a FROM Attendance a WHERE a.status = :status")
    List<Attendance> findByStatus(String status);


}
