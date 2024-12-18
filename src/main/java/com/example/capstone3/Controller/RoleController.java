package com.example.capstone3.Controller;

import com.example.capstone3.ApiResponse.ApiResponse;
import com.example.capstone3.DTO.RoleDTO;
import com.example.capstone3.DTO.RoleDTOout;
import com.example.capstone3.Service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//bushra
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/get-all")
    public ResponseEntity getAllRoles() {
        List<RoleDTOout> roles = roleService.getAllRoles();
        return ResponseEntity.status(200).body(roles);
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getRoleById(@PathVariable Integer id) {
        RoleDTOout role = roleService.getRoleById(id);
        return ResponseEntity.status(200).body(role);
    }
    @GetMapping("/get-by-event/{eventId}")
    public ResponseEntity getRolesByEvent(@PathVariable Integer eventId) {
        List<RoleDTOout> roles = roleService.getRolesByEventId(eventId);
        return ResponseEntity.status(200).body(roles);
    }
    @GetMapping("/get-by-volunteer/{volunteerId}")
    public ResponseEntity getRolesByVolunteer(@PathVariable Integer volunteerId) {
        List<RoleDTOout> roles = roleService.getRolesByVolunteerId(volunteerId);
        return ResponseEntity.status(200).body(roles);
    }
    @GetMapping("/count")
    public ResponseEntity countRoles() {
        long count = roleService.countRoles();
        return ResponseEntity.status(200).body(count);
    }
    @PostMapping("/add")
    public ResponseEntity addRole(@RequestBody @Valid RoleDTO dto) {
        roleService.addRole(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Role added successfully"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateRoleDetails(@PathVariable Integer id, @RequestBody @Valid RoleDTO dto) {
        roleService.updateRoleDetails(id, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Role updated successfully"));
    }
    @PutMapping("/update-description/{id}")
    public ResponseEntity updateRoleDescription(@PathVariable Integer id, @RequestParam String description) {
        roleService.updateRoleDescription(id, description);
        return ResponseEntity.status(200).body(new ApiResponse("Role description updated successfully"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRole(@PathVariable Integer id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.status(200).body(new ApiResponse("Role deleted successfully"));
    }

}
