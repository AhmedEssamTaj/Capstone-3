package com.example.capstone3.Repository;

import com.example.capstone3.Model.Attendance;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("SELECT a FROM Attendance a WHERE a.id = :id")
    Attendance findAttendanceById(@Param("id") Integer id);

    @Query("SELECT a FROM Attendance a WHERE a.event = :event")
    List<Attendance> findByEvent(@Param("event") Event event);

    @Query("SELECT a FROM Attendance a WHERE a.volunteer = :volunteer")
    List<Attendance> findByVolunteer(@Param("volunteer") Volunteer volunteer);

    @Query("SELECT a FROM Attendance a WHERE a.status = :status")
    List<Attendance> findByStatus(@Param("status") String status);

    @Query("SELECT a FROM Attendance a WHERE a.checkIn < :time")
    List<Attendance> findByCheckInBefore(@Param("time") LocalTime time);

    @Query("SELECT COUNT(a) FROM Attendance a")
    long countAllAttendances();

    @Query("SELECT a FROM Attendance a WHERE a.event.id = :eventId AND a.volunteer.id = :volunteerId")
    Attendance findByEventAndVolunteer(@Param("eventId") Integer eventId, @Param("volunteerId") Integer volunteerId);

    @Query("SELECT a FROM Attendance a ORDER BY a.id DESC LIMIT 1")
    Attendance findTopByOrderByIdDesc();


    boolean existsById(Integer id);

}