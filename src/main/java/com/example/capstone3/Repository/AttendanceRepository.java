package com.example.capstone3.Repository;
//bushra

import com.example.capstone3.Model.Attendance;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Volunteer;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("SELECT a FROM Attendance a WHERE a.id = :id")
    Attendance findAttendanceById(Integer id);
    Attendance findAttendanceByVolunteer_IdAndEvent_Id(Integer volunteerId, Integer eventId);

    @Query("SELECT a FROM Attendance a WHERE a.event = :event")
    List<Attendance> findByEvent(Event event);

    @Query("SELECT a FROM Attendance a WHERE a.volunteer = :volunteer")
    List<Attendance> findByVolunteer( Volunteer volunteer);

  List<Attendance> findAttendanceByIdAndStatus(Integer id,String status);

    @Query("SELECT a FROM Attendance a WHERE a.status = :status")
    List<Attendance> findByStatus(String status);



}