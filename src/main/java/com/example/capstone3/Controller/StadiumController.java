package com.example.capstone3.Controller;

import com.example.capstone3.ApiResponse.ApiResponse;
import com.example.capstone3.DTO.StadiumDTO;
import com.example.capstone3.DTO.StadiumDTOout;
import com.example.capstone3.Service.StadiumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//bushra
@RestController
@RequestMapping("/api/v1/stadium")
@RequiredArgsConstructor
public class StadiumController {

    private final StadiumService stadiumService;

    @GetMapping("/get-all")
    public ResponseEntity getAllStadiums() {
        return ResponseEntity.status(200).body(stadiumService.getAllStadiums());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity  getStadiumById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(stadiumService.getStadiumById(id));
    }

    @GetMapping("/get-by-city/{city}")
    public ResponseEntity getStadiumsByCity(@PathVariable String city) {
        return ResponseEntity.status(200).body(stadiumService.getStadiumsByCity(city));
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity getStadiumsByStatus(@PathVariable String status) {
        return ResponseEntity.status(200).body(stadiumService.getStadiumsByStatus(status));
    }


    @GetMapping("/get-largest")
    public ResponseEntity getLargestStadium() {
        return ResponseEntity.status(200).body(stadiumService.getLargestStadium());
    }


    @PostMapping("/add")
    public ResponseEntity  addStadium(@RequestBody @Valid StadiumDTO stadiumDTO) {
        stadiumService.addStadium(stadiumDTO);
        return ResponseEntity.status(201).body(new ApiResponse("Stadium added successfully"));
    }

    @PutMapping("/update-name/{id}")
    public ResponseEntity updateStadiumName(@PathVariable Integer id, @RequestBody @Valid String name) {
        stadiumService.updateStadiumName(id, name);
        return ResponseEntity.status(200).body(new ApiResponse("Stadium name updated successfully"));
    }

    @PutMapping("/update-city/{id}")
    public ResponseEntity  updateStadiumCity(@PathVariable Integer id, @RequestBody @Valid String city) {
        stadiumService.updateStadiumCity(id, city);
        return ResponseEntity.status(200).body(new ApiResponse("Stadium city updated successfully"));
    }

    @PutMapping("/update-location/{id}")
    public ResponseEntity updateStadiumLocation(@PathVariable Integer id, @RequestBody @Valid String location) {
        stadiumService.updateStadiumLocation(id, location);
        return ResponseEntity.status(200).body(new ApiResponse("Stadium location updated successfully"));
    }

    @PutMapping("/update-capacity/{id}")
    public ResponseEntity updateStadiumCapacity(@PathVariable Integer id, @RequestBody @Valid Integer capacity) {
        stadiumService.updateStadiumCapacity(id, capacity);
        return ResponseEntity.status(200).body(new ApiResponse("Stadium capacity updated successfully"));
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity updateStadiumStatus(@PathVariable Integer id, @RequestBody @Valid String status) {
        stadiumService.updateStadiumStatus(id, status);
        return ResponseEntity.status(200).body(new ApiResponse("Stadium status updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity  deleteStadiumById(@PathVariable Integer id) {
        stadiumService.deleteStadiumById(id);
        return ResponseEntity.status(200).body(new ApiResponse("Stadium deleted successfully"));
    }

    @DeleteMapping("/delete-by-city/{city}")
    public ResponseEntity deleteStadiumsByCity(@PathVariable String city) {
        stadiumService.deleteStadiumsByCity(city);
        return ResponseEntity.status(200).body(new ApiResponse("Stadiums deleted successfully"));
    }

    @DeleteMapping("/delete-by-status/{status}")
    public ResponseEntity deleteStadiumsByStatus(@PathVariable String status) {
        stadiumService.deleteStadiumsByStatus(status);
        return ResponseEntity.status(200).body(new ApiResponse("Stadiums deleted successfully"));
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity  deleteAllStadiums() {
        stadiumService.deleteAllStadiums();
        return ResponseEntity.status(200).body(new ApiResponse("All stadiums deleted successfully"));
    }
}
