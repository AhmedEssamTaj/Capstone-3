package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.RoleDTO;
import com.example.capstone3.DTO.RoleDTOout;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Role;
import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Repository.EventRepository;
import com.example.capstone3.Repository.RoleRepository;
import com.example.capstone3.Repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final EventRepository eventRepository;
    private final VolunteerRepository volunteerRepository;

    public RoleDTOout getRoleById(Integer id) {
        Role role = roleRepository.findRoleById(id);
        if (role == null) {
            throw new ApiException("Role not found");
        }
        return convertToDTOout(role);
    }

    public List<RoleDTOout> getAllRoleDTOs() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDTOout> roleDTOs = new ArrayList<>();
        for (Role r : roles) {
            roleDTOs.add(convertToDTOout(r));
        }
        return roleDTOs;
    }

    public void addRole(RoleDTO role) {
        Role new_role=new Role();
        new_role.setName(role.getName());
        new_role.setDescription(role.getDescription());
        Event event = eventRepository.findEventById(role.getEvent_id());
        Volunteer volunteer = volunteerRepository.findVolunteerById(role.getVolunteer_id());
        if (volunteer == null || event == null) {
            throw new ApiException("Volunteer or event not found");
        }
        new_role.setEvent(eventRepository.findEventById(role.getEvent_id()));
        new_role.setVolunteer(volunteerRepository.findVolunteerById(role.getVolunteer_id()));
        roleRepository.save(new_role);
    }

    public void deleteRole(Integer id) {
        Role role = roleRepository.findRoleById(id);
        if (role == null) {
            throw new ApiException("Role not found");
        }
        if (role.getVolunteer()!=null){
            throw new ApiException("Role cant be deleted it has volunteer");

        }
        roleRepository.delete(role);
    }

    public void updateRole(Integer id, RoleDTO updatedRole) {
        Role role =  roleRepository.findRoleById(id);
        if (role == null) {
            throw new ApiException("Role not found");
        }

        role.setName(updatedRole.getName());
        role.setDescription(updatedRole.getDescription());
        role.setEvent(eventRepository.findEventById(updatedRole.getEvent_id()));
        role.setVolunteer(volunteerRepository.findVolunteerById(updatedRole.getVolunteer_id()));
        roleRepository.save(role);
    }
    private RoleDTOout convertToDTOout(Role role) {
        return new RoleDTOout(role.getName(),role.getVolunteer().getName(),role.getEvent().getName(),role.getDescription());
    }
}
