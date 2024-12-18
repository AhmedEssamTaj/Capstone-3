package com.example.capstone3.Controller;

import com.example.capstone3.ApiResponse.ApiResponse;
import com.example.capstone3.DTO.AttendanceDTO;
import com.example.capstone3.DTO.AttendanceDTOout;
import com.example.capstone3.Service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
//bushra

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;


    @GetMapping("/get-all")
    public ResponseEntity getAllAttendances() {
        List<AttendanceDTOout> attendances = attendanceService.getAllAttendances();
        return ResponseEntity.status(200).body(attendances);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getAttendanceById(@PathVariable Integer id) {
        AttendanceDTOout attendance = attendanceService.getAttendanceById(id);
        return ResponseEntity.status(200).body(attendance);
    }

    @GetMapping("/get-by-event/{eventId}")
    public ResponseEntity getAttendancesByEvent(@PathVariable Integer eventId) {
        List<AttendanceDTOout> attendances = attendanceService.getAttendancesByEvent(eventId);
        return ResponseEntity.status(200).body(attendances);
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity getAttendancesByStatus(@PathVariable String status) {
        List<AttendanceDTOout> attendances = attendanceService.getAttendancesByStatus(status);
        return ResponseEntity.status(200).body(attendances);
    }

    @GetMapping("/get-latest")
    public ResponseEntity getLatestAttendance() {
        AttendanceDTOout attendance = attendanceService.getLatestAttendance();
        return ResponseEntity.status(200).body(attendance);
    }

    @GetMapping("/get-by-volunteer/{volunteerId}")
    public ResponseEntity getAttendancesByVolunteer(@PathVariable Integer volunteerId) {
        List<AttendanceDTOout> attendances = attendanceService.getAttendancesByVolunteer(volunteerId);
        return ResponseEntity.status(200).body(attendances);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity checkAttendanceExists(@PathVariable Integer id) {
        boolean exists = attendanceService.doesAttendanceExist(id);
        return ResponseEntity.status(200).body(new ApiResponse("Exists: " + exists));
    }


    @PostMapping("/add")
    public ResponseEntity addAttendance(@RequestBody @Valid AttendanceDTO attendanceDTO) {
        attendanceService.addAttendance(attendanceDTO);
        return ResponseEntity.status(201).body(new ApiResponse("Attendance added successfully"));
    }


    @PostMapping("/mark-check-in/{id}")
    public ResponseEntity markAttendanceCheckIn(@PathVariable Integer id, @RequestBody @Valid LocalTime checkIn) {
        attendanceService.markAttendanceCheckedIn(id);
        return ResponseEntity.status(200).body(new ApiResponse("Attendance marked as checked-in"));
    }

    @PostMapping("/mark-check-out/{id}")
    public ResponseEntity markAttendanceCheckOut(@PathVariable Integer id, @RequestBody @Valid LocalTime checkOut) {
        attendanceService.markAttendanceCheckedOut(id);
        return ResponseEntity.status(200).body(new ApiResponse("Attendance marked as checked-out"));
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity updateAttendanceStatus(@PathVariable Integer id, @RequestBody @Valid String status) {
        attendanceService.updateAttendanceStatus(id, status);
        return ResponseEntity.status(200).body(new ApiResponse("Attendance status updated successfully"));
    }

    @PutMapping("/update-check-in/{id}")
    public ResponseEntity updateCheckInTime(@PathVariable Integer id, @RequestBody @Valid LocalTime checkIn) {
        attendanceService.updateCheckInTime(id, checkIn);
        return ResponseEntity.status(200).body(new ApiResponse("Check-in time updated successfully"));
    }

    @PutMapping("/batch-update-status")
    public ResponseEntity batchUpdateStatus(@RequestBody List<Integer> ids, @RequestBody String status) {
        attendanceService.batchUpdateStatus(ids, status);
        return ResponseEntity.status(200).body(new ApiResponse("Batch update completed"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAttendanceById(@PathVariable Integer id) {
        attendanceService.deleteAttendanceById(id);
        return ResponseEntity.status(200).body(new ApiResponse("Attendance deleted successfully"));
    }

    @DeleteMapping("/batch-delete")
    public ResponseEntity batchDeleteAttendances(@RequestBody List<Integer> ids) {
        attendanceService.batchDeleteAttendances(ids);
        return ResponseEntity.status(200).body(new ApiResponse("Batch deletion completed"));
    }

    @DeleteMapping("/delete-by-status/{status}")
    public ResponseEntity deleteAttendancesByStatus(@PathVariable String status) {
        attendanceService.deleteAttendancesByStatus(status);
        return ResponseEntity.status(200).body(new ApiResponse("Attendances with status " + status + " deleted successfully"));
    }
}
