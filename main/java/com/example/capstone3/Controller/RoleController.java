package com.example.capstone3.Controller;

import com.example.capstone3.DTO.RoleDTO;
import com.example.capstone3.Model.Role;
import com.example.capstone3.Service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/get-all")
    public ResponseEntity getAllRoles() {
        return ResponseEntity.status(200).body(roleService.getAllRoleDTOs());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getRoleById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(roleService.getRoleById(id));
    }

//    @PostMapping("/add")
//    public ResponseEntity addRole(@RequestBody @Valid RoleDTO role) {
//        roleService.addRole(role);
//        return ResponseEntity.status(200).body("Role added successfully");
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRole(@PathVariable Integer id, @RequestBody @Valid RoleDTO role) {
        roleService.updateRole(id, role);
        return ResponseEntity.status(200).body("Role updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.status(200).body("Role deleted successfully");
    }
}
