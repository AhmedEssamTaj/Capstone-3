package com.example.capstone3.Controller;

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
        return ResponseEntity.ok(roleService.getAllRoleDTOs());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getRoleById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PostMapping("/add")
    public ResponseEntity addRole(@RequestBody @Valid Role role) {
        roleService.addRole(role);
        return ResponseEntity.ok("Role added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRole(@PathVariable Integer id, @RequestBody @Valid Role role) {
        roleService.updateRole(id, role);
        return ResponseEntity.ok("Role updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted successfully");
    }
}
