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

// Bushra

public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EventRepository eventRepository;
    private final VolunteerRepository volunteerRepository;


    public AttendanceDTOout getAttendanceById(Integer id) {
        Attendance attendance = attendanceRepository.findAttendanceById(id);
        if (attendance == null) {
            throw new ApiException("Attendance not found");
        }
        return convertToDTOout(attendance);
    }

    public Integer getAttendanceCountAbsent(Integer volunteer_id) {

        List<Attendance>volunteerAbsent = attendanceRepository.findAttendanceByIdAndStatus(volunteer_id,"Absent");
        return volunteerAbsent.size();

    }

    public void updateAttendanceStatusAbsent(Integer volunteer_id, Integer event_id) {
        Attendance attendance = attendanceRepository.findAttendanceByVolunteer_IdAndEvent_Id(volunteer_id,event_id);
        if (attendance==null){
            throw new ApiException("Record not found");
        }
        attendance.setStatus("Absent");
        attendanceRepository.save(attendance);
    }

    public List<AttendanceDTOout> getAllAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();
        if (attendances.isEmpty()) throw new ApiException("No attendances found");
        return convertListToDTOout(attendances);
    }

    public void updateAttendanceAbsent(Integer id) {
        Attendance attendance = attendanceRepository.findAttendanceById(id);
        if (attendance == null) {
            throw new ApiException("Attendance not found");
        }
        if ("Checked-out".equals(attendance.getStatus())) {
            throw new ApiException("Cannot change status after checking out");
        }
        attendance.setStatus("Absent");
        attendanceRepository.save(attendance);
    }

    // For CRUD Purposes
    public void addAttendance(AttendanceDTO dto) {
        Event event=eventRepository.findEventById(dto.getEvent_id());
        if (event==null) throw new ApiException("Event not found");
        if (volunteerRepository.findVolunteerById(dto.getVolunteer_id())==null) throw new ApiException("Volunteer not found");
        if (attendanceRepository.findAttendanceByVolunteer_IdAndEvent_Id(dto.getVolunteer_id(),dto.getEvent_id() )!=null)
            throw new ApiException("Attendance already exists");

        Attendance attendance = new Attendance();
        attendance.setEvent(event);
        attendance.setVolunteer(volunteerRepository.findVolunteerById(dto.getVolunteer_id()));
        attendanceRepository.save(attendance);
    }

    public List<AttendanceDTOout> getAttendancesByEvent(Integer eventId) {

        List<Attendance> attendances = attendanceRepository.findByEvent(eventRepository.findEventById(eventId));
        if (attendances.isEmpty()) throw new ApiException("No attendances found for this event");
        return convertListToDTOout(attendances);
    }
    public List<AttendanceDTOout> getAttendancesByVolunteer(Integer volunteerId) {
        List<Attendance> attendances = attendanceRepository.findByVolunteer(volunteerRepository.findVolunteerById(volunteerId));
        if (attendances.isEmpty()) throw new ApiException("No attendances found for this volunteer");
        return convertListToDTOout(attendances);
    }


    public void markAttendanceCheckedIn(Integer volunteerId, Integer eventId) {
        Attendance attendance=attendanceRepository.findAttendanceByVolunteer_IdAndEvent_Id(volunteerId,eventId);
        if ("Checked-in".equals(attendance.getStatus()))
            throw new ApiException("Attendance already checked in");
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isBefore(eventRepository.findEventById(eventId).getStartTime()))
            throw new ApiException("cant checked in before the event start time");
        attendance.setCheckIn(currentTime);
        attendance.setStatus("Checked in");
        attendanceRepository.save(attendance);
    }
    public void markAttendanceCheckedOut(Integer volunteerId, Integer eventId) {
        Attendance attendance=attendanceRepository.findAttendanceByVolunteer_IdAndEvent_Id(volunteerId,eventId);
        if (!"Checked-in".equals(attendance.getStatus()))
            throw new ApiException("Cannot check out without checking in first");
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isBefore(attendance.getCheckIn()))
            throw new ApiException("Check-out time cannot be earlier than check-in time");
        if (currentTime.isBefore(eventRepository.findEventById(eventId).getEndTime()))
            throw new ApiException("cant checked in before the event end time");

        attendance.setCheckOut(currentTime);
        attendance.setStatus("Checked out");
        attendanceRepository.save(attendance);
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

    public void deleteAttendanceById(Integer volunteerId, Integer eventId) {
        Attendance attendance=attendanceRepository.findAttendanceByVolunteer_IdAndEvent_Id(volunteerId,eventId);
        attendanceRepository.delete(attendance);
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


    // Method to find a replacement volunteer for an event (Aishtiaq-2)
    public String findReplacement(Integer eventId, Integer absentVolunteerId) {

        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            throw new ApiException("No event with this ID was found");
        }


        Volunteer absentVolunteer = volunteerRepository.findVolunteerById(absentVolunteerId);
        if (absentVolunteer == null) {
            throw new ApiException("No volunteer with this ID was found");
        }

        List<Attendance> eventAttendances = attendanceRepository.findByEventId(eventId);
        List<Integer> assignedVolunteerIds = new ArrayList<>();

        for (Attendance a: eventAttendances) {
            assignedVolunteerIds.add(a.getVolunteer().getId());
        }

        List<Volunteer> availableVolunteers = new ArrayList<>();
        List<Volunteer> allVolunteers = volunteerRepository.findAll();

        for (Volunteer volunteer : allVolunteers) {
            if (!assignedVolunteerIds.contains(volunteer.getId())) {
                availableVolunteers.add(volunteer);
            }
        }

        if (availableVolunteers.isEmpty()) {
            throw new ApiException("No available volunteers for instant replacement.");
        }

        Volunteer replacement = availableVolunteers.get(0);

        Attendance attendance = new Attendance();
        attendance.setEvent(event);
        attendance.setVolunteer(replacement);
        attendance.setStatus("Replaced");

        attendanceRepository.save(attendance);

        return "Replacement volunteer assigned: " + replacement.getName();
    }

}
