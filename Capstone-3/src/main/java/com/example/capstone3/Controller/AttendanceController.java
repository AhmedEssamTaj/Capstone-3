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

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        return ResponseEntity.status(200).body(attendanceService.getAllAttendances());
    }


    @PutMapping("/Absent/{id}")
    public ResponseEntity updateAttendanceAbsent(@PathVariable Integer id) {
        attendanceService.updateAttendanceAbsent(id);
        return ResponseEntity.status(200).body(new ApiResponse("attendance updated successfully"));
    }

    // endpoint to mark the volunteer check in time  --- Bushra (1)
    @PutMapping("/update-check-in/{volunteer_id}/{event_id}")
    public ResponseEntity updateCheckIn(@PathVariable Integer volunteer_id,@PathVariable Integer event_id, @RequestBody @Valid LocalTime checkIn) {
        attendanceService.updateAttendanceCheckIn(volunteer_id,event_id, checkIn);
        return ResponseEntity.status(200).body(new ApiResponse("record updated"));
    }

    // endpoint to mark the volunteer check out time   --- Bushra (2)
    @PutMapping("/update-check-out/{volunteer_id}/{event_id}")
    public ResponseEntity updateCheckOut(@PathVariable Integer volunteer_id,@PathVariable Integer event_id, @RequestBody @Valid LocalTime checkOut) {
        attendanceService.updateAttendanceCheckOut(volunteer_id,event_id, checkOut);
        return ResponseEntity.status(200).body(new ApiResponse("record updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAttendance(@PathVariable Integer id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.status(200).body("Attendance deleted");
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(attendanceService.getAttendanceById(id));
    }
}
