package com.example.capstone3.Controller;

import com.example.capstone3.DTO.StadiumDTO;
import com.example.capstone3.Model.Stadium;
import com.example.capstone3.Service.StadiumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stadium")
public class StadiumController {
    private final StadiumService stadiumService;

    @GetMapping("/get-all")
    public ResponseEntity getAllStadiums() {
        return ResponseEntity.status(200).body(stadiumService.getAllStadiumDTOs());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getStadiumById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(stadiumService.getStadiumById(id));
    }

    @PostMapping("/add")
    public ResponseEntity addStadium(@RequestBody @Valid StadiumDTO stadium) {
        stadiumService.addStadium(stadium);
        return ResponseEntity.status(200).body("Stadium added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateStadium(@PathVariable Integer id, @RequestBody @Valid StadiumDTO stadium) {
        stadiumService.updateStadium(id, stadium);
        return ResponseEntity.status(200).body("Stadium updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteStadium(@PathVariable Integer id) {
        stadiumService.deleteStadium(id);
        return ResponseEntity.status(200).body("Stadium deleted successfully");
    }
}
