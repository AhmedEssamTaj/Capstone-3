package com.example.capstone3.Controller;

import com.example.capstone3.DTO.AttendanceDTO;
import com.example.capstone3.DTO.AttendanceDTOout;
import com.example.capstone3.Service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("/add")
    public ResponseEntity addAttendance(@RequestBody @Valid AttendanceDTO attendanceDTO) {
        AttendanceDTO addedAttendance = attendanceService.addAttendance(attendanceDTO);
        return ResponseEntity.status(200).body(addedAttendance);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity updateAttendanceStatus(@PathVariable Integer id, @RequestBody @Valid String status) {
        AttendanceDTO updatedAttendance = attendanceService.updateAttendanceStatus(id, status);
        return ResponseEntity.status(200).body(updatedAttendance);
    }

    @PutMapping("/update-check-in/{volunteer_id}/{event_id}")
    public ResponseEntity updateCheckIn(@PathVariable Integer volunteer_id,@PathVariable Integer event_id, @RequestBody @Valid LocalTime checkIn) {
        AttendanceDTO updatedAttendance = attendanceService.updateAttendanceCheckIn(volunteer_id,event_id, checkIn);
        return ResponseEntity.status(200).body(updatedAttendance);
    }

    @PutMapping("/update-check-out/{volunteer_id}/{event_id}")
    public ResponseEntity updateCheckOut(@PathVariable Integer volunteer_id,@PathVariable Integer event_id, @RequestBody @Valid LocalTime checkOut) {
        AttendanceDTO updatedAttendance = attendanceService.updateAttendanceCheckOut(volunteer_id,event_id, checkOut);
        return ResponseEntity.status(200).body(updatedAttendance);
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
