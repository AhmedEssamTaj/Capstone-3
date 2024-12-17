package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.AttendanceDTO;
import com.example.capstone3.DTO.AttendanceDTOout;
import com.example.capstone3.Model.Attendance;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Repository.AttendanceRepository;
import com.example.capstone3.Repository.EventRepository;
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

    public AttendanceDTOout getAttendanceById(Integer id) {
        Attendance attendance = attendanceRepository.findAttendanceById(id);
        if (attendance == null) {
            throw new ApiException("Attendance not found");
        }
        return convertToDTOout(attendance);
    }

    public List<AttendanceDTOout> getAllAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();
        List<AttendanceDTOout> dtos = new ArrayList<>();
        for (Attendance attendance : attendances) {
            dtos.add(convertToDTOout(attendance));
        }
        return dtos;
    }

    public AttendanceDTO addAttendance(AttendanceDTO attendanceDTO) {
        if (attendanceRepository.existsByEventIdAndVolunteerId(attendanceDTO.getEventId(), attendanceDTO.getVolunteerId())) {
            throw new ApiException("Attendance already exists for this volunteer and event");
        }
        Attendance attendance = new Attendance(); // populate from DTO
        attendanceRepository.save(attendance);
        return convertToDTO(attendance);
    }

    public AttendanceDTO updateAttendanceStatus(Integer id, String status) {
        Attendance attendance = attendanceRepository.findAttendanceById(id);
        if (attendance == null) {
            throw new ApiException("Attendance not found");
        }
        if ("Checked-out".equals(attendance.getStatus())) {
            throw new ApiException("Cannot change status after checking out");
        }
        attendance.setStatus(status);
        attendanceRepository.save(attendance);
        return convertToDTO(attendance);
    }

    public AttendanceDTO updateAttendanceCheckIn(Integer volunteer_id,Integer event_id, LocalTime checkIn) {
        Attendance attendance = attendanceRepository.findAttendanceById(volunteer_id);
        Event event=eventRepository.findEventById(event_id);
        if (attendance == null) {
            throw new ApiException("Attendance not found");
        }
        if (event.getStartTime().isBefore(checkIn)){
            throw new ApiException("Check-in time cannot be before event Start time");
        }

        attendance.setCheckIn(checkIn);
        attendanceRepository.save(attendance);
        return convertToDTO(attendance);
    }

    public AttendanceDTO updateAttendanceCheckOut(Integer volunteer_id,Integer event_id, LocalTime checkOut) {
        Attendance attendance = attendanceRepository.findAttendanceById(volunteer_id);
        Event event=eventRepository.findEventById(event_id);
        if (attendance == null) {
            throw new ApiException("Attendance not found");
        }
        if (event.getEndTime().isBefore(checkOut)){
            throw new ApiException("Check-in time cannot be before event end time");
        }
        attendance.setCheckOut(checkOut);
        attendanceRepository.save(attendance);
        return convertToDTO(attendance);
    }

    public void deleteAttendance(Integer id) {
        Attendance attendance = attendanceRepository.findAttendanceById(id);
        if (attendance == null) {
            throw new ApiException("Attendance not found");
        }
        if ("Checked-out".equals(attendance.getStatus())) {
            throw new ApiException("Cannot delete attendance after checking out");
        }
        attendanceRepository.delete(attendance);
    }

    private AttendanceDTOout convertToDTOout(Attendance attendance) {
        return new AttendanceDTOout(attendance.getEvent().getName(),attendance.getVolunteer().getName(),attendance.getCheckIn()
                ,attendance.getCheckOut(),attendance.getStatus());
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        return new AttendanceDTO(attendance.getId(),attendance.getEvent().getId(), attendance.getVolunteer().getId(),
                attendance.getCheckIn(),attendance.getCheckOut(),attendance.getStatus()
        );
    }

}
