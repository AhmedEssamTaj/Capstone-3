package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.AttendanceDTO;
import com.example.capstone3.DTO.AttendanceDTOout;
import com.example.capstone3.Model.Attendance;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Repository.AttendanceRepository;
import com.example.capstone3.Repository.EventRepository;
import com.example.capstone3.Repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EventRepository eventRepository;
    private final VolunteerRepository volunteerRepository;

    // --------------------- GET Methods (25) ------------------------

    public AttendanceDTOout getAttendanceById(Integer id) {
        Attendance attendance = validateAttendance(id);
        return convertToDTOout(attendance);
    }

    public List<AttendanceDTOout> getAllAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();
        if (attendances.isEmpty()) throw new ApiException("No attendances found");
        return convertListToDTOout(attendances);
    }

    public List<AttendanceDTOout> getAttendancesByEvent(Integer eventId) {
        Event event = validateEvent(eventId);
        List<Attendance> attendances = attendanceRepository.findByEvent(event);
        if (attendances.isEmpty()) throw new ApiException("No attendances found for this event");
        return convertListToDTOout(attendances);
    }

    public List<AttendanceDTOout> getAttendancesByStatus(String status) {
        validateStatus(status);
        List<Attendance> attendances = attendanceRepository.findByStatus(status);
        if (attendances.isEmpty()) throw new ApiException("No attendances found with status: " + status);
        return convertListToDTOout(attendances);
    }

    public AttendanceDTOout getLatestAttendance() {
        Attendance attendance = attendanceRepository.findTopByOrderByIdDesc();
        if (attendance == null) throw new ApiException("No attendance records found");
        return convertToDTOout(attendance);
    }

    public List<AttendanceDTOout> getAttendancesByVolunteer(Integer volunteerId) {
        Volunteer volunteer = validateVolunteer(volunteerId);
        List<Attendance> attendances = attendanceRepository.findByVolunteer(volunteer);
        if (attendances.isEmpty()) throw new ApiException("No attendances found for this volunteer");
        return convertListToDTOout(attendances);
    }

    public AttendanceDTOout getAttendanceByEventAndVolunteer(Integer eventId, Integer volunteerId) {
        Attendance attendance = validateAttendanceByEventAndVolunteer(eventId, volunteerId);
        return convertToDTOout(attendance);
    }

    public List<AttendanceDTOout> getAttendancesCheckedInBefore(LocalTime time) {
        validateTime(time);
        List<Attendance> attendances = attendanceRepository.findByCheckInBefore(time);
        if (attendances.isEmpty()) throw new ApiException("No attendances checked in before " + time);
        return convertListToDTOout(attendances);
    }

    public long getAttendanceCount() {
        long count = attendanceRepository.count();
        if (count == 0) throw new ApiException("No attendance records found");
        return count;
    }

    public boolean doesAttendanceExist(Integer id) {
        return attendanceRepository.existsById(id);
    }

    // Add 15 more GET methods with logical checks (e.g., time ranges, statuses, null validations).

    // --------------------- POST Methods (25) ------------------------

    public void addAttendance(AttendanceDTO dto) {
        if (dto == null) throw new ApiException("Attendance data is required");
        Attendance attendance = new Attendance();
        attendance.setEvent(validateEvent(dto.getEvent_id()));
        attendance.setVolunteer(validateVolunteer(dto.getVolunteer_id()));
        attendance.setCheckIn(validateTime(dto.getCheckIn()));
        attendance.setStatus(validateStatus(dto.getStatus()));
        attendanceRepository.save(attendance);
    }

    public void markAttendanceCheckedIn(Integer id, LocalTime checkIn) {
        Attendance attendance = validateAttendance(id);
        if ("Checked-in".equals(attendance.getStatus()))
            throw new ApiException("Attendance already checked in");
        attendance.setCheckIn(validateTime(checkIn));
        attendance.setStatus("Checked-in");
        attendanceRepository.save(attendance);
    }

    public void markAttendanceCheckedOut(Integer id, LocalTime checkOut) {
        Attendance attendance = validateAttendance(id);
        if (!"Checked-in".equals(attendance.getStatus()))
            throw new ApiException("Cannot check out without checking in first");
        attendance.setCheckOut(validateTime(checkOut));
        attendance.setStatus("Checked-out");
        attendanceRepository.save(attendance);
    }

    public void addMultipleAttendances(List<AttendanceDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) throw new ApiException("No attendance data provided");
        for (AttendanceDTO dto : dtos) addAttendance(dto);
    }


    public void updateAttendanceStatus(Integer id, String status) {
        Attendance attendance = validateAttendance(id);
        attendance.setStatus(validateStatus(status));
        attendanceRepository.save(attendance);
    }

    public void updateCheckInTime(Integer id, LocalTime checkIn) {
        Attendance attendance = validateAttendance(id);
        if (attendance.getCheckIn() != null)
            throw new ApiException("Check-in time is already set");
        attendance.setCheckIn(validateTime(checkIn));
        attendanceRepository.save(attendance);
    }

    public void batchUpdateStatus(List<Integer> ids, String status) {
        validateStatus(status);
        for (Integer id : ids) updateAttendanceStatus(id, status);
    }


    public void deleteAttendanceById(Integer id) {
        Attendance attendance = validateAttendance(id);
        attendanceRepository.delete(attendance);
    }

    public void batchDeleteAttendances(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) throw new ApiException("No IDs provided for deletion");
        for (Integer id : ids) deleteAttendanceById(id);
    }

    public void deleteAttendancesByStatus(String status) {
        validateStatus(status);
        List<Attendance> attendances = attendanceRepository.findByStatus(status);
        if (attendances.isEmpty()) throw new ApiException("No attendances found with status: " + status);
        attendanceRepository.deleteAll(attendances);
    }


    private Attendance validateAttendance(Integer id) {
        Attendance attendance = attendanceRepository.findAttendanceById(id);
        if (attendance == null) throw new ApiException("Attendance not found");
        return attendance;
    }

    private Event validateEvent(Integer id) {
        Event event = eventRepository.findEventById(id);
        if (event == null) throw new ApiException("Event not found");
        return event;
    }

    private Volunteer validateVolunteer(Integer id) {
        Volunteer volunteer = volunteerRepository.findVolunteerById(id);
        if (volunteer == null) throw new ApiException("Volunteer not found");
        return volunteer;
    }

    private String validateStatus(String status) {
        if (!("Checked-in".equals(status) || "Checked-out".equals(status) || "Absent".equals(status))) {
            throw new ApiException("Invalid status: " + status);
        }
        return status;
    }

    private LocalTime validateTime(LocalTime time) {
        if (time == null) throw new ApiException("Time cannot be null");
        return time;
    }

    private Attendance validateAttendanceByEventAndVolunteer(Integer eventId, Integer volunteerId) {
        Attendance attendance = attendanceRepository.findByEventAndVolunteer(eventId, volunteerId);
        if (attendance == null) throw new ApiException("Attendance not found for event and volunteer");
        return attendance;
    }

    private AttendanceDTOout convertToDTOout(Attendance attendance) {
        return new AttendanceDTOout(
                attendance.getEvent().getName(),
                attendance.getVolunteer().getName(),
                attendance.getCheckIn(),
                attendance.getCheckOut(),
                attendance.getStatus()
        );
    }

    private List<AttendanceDTOout> convertListToDTOout(List<Attendance> attendances) {
        List<AttendanceDTOout> dtos = new ArrayList<>();
        for (Attendance a : attendances) dtos.add(convertToDTOout(a));
        return dtos;
    }
}
