package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.RoleDTO;
import com.example.capstone3.DTO.RoleDTOout;
import com.example.capstone3.Model.Role;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Repository.RoleRepository;
import com.example.capstone3.Repository.EventRepository;
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

    // Get Methods
    public RoleDTOout getRoleById(Integer id) {
        Role role = findRole(id);
        return convertToDTOout(role);
    }

    public List<RoleDTOout> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return convertToDTOList(roles);
    }

    public List<RoleDTOout> getRolesByEventId(Integer eventId) {
        Event event = findEvent(eventId);
        List<Role> roles = roleRepository.findByEvent(event);
        return convertToDTOList(roles);
    }

    public List<RoleDTOout> getRolesByVolunteerId(Integer volunteerId) {
        Volunteer volunteer = findVolunteer(volunteerId);
        List<Role> roles = roleRepository.findByVolunteer(volunteer);
        return convertToDTOList(roles);
    }

    public List<RoleDTOout> getRolesByName(String name) {
        List<Role> roles = roleRepository.findByName(name);
        return convertToDTOList(roles);
    }


    public long countRoles() {
        return roleRepository.count();
    }

    public boolean roleExistsById(Integer id) {
        return roleRepository.existsById(id);
    }

    public List<RoleDTOout> getRolesByDescription(String description) {
        List<Role> roles = roleRepository.findByDescriptionContaining(description);
        return convertToDTOList(roles);
    }

    public List<RoleDTOout> getRolesByEventName(String eventName) {
        List<Role> roles = roleRepository.findByEvent_Name(eventName);
        return convertToDTOList(roles);
    }

    // Post Methods
    public void addRole(RoleDTO dto) {
        Role role = createRoleFromDTO(dto);
        roleRepository.save(role);
    }

    public void addRolesInBulk(List<RoleDTO> roles) {
        for (RoleDTO dto : roles) {
            addRole(dto);
        }
    }

    public void duplicateRole(Integer roleId) {
        Role existingRole = findRole(roleId);
        Role duplicate = new Role();
        duplicate.setName(existingRole.getName() + "_copy");
        duplicate.setDescription(existingRole.getDescription());
        duplicate.setEvent(existingRole.getEvent());
        roleRepository.save(duplicate);
    }

    public void addRoleToEvent(Integer eventId, RoleDTO dto) {
        Event event = findEvent(eventId);
        Role role = createRoleFromDTO(dto);
        role.setEvent(event);
        roleRepository.save(role);
    }

    // Put Methods
    public void updateRoleName(Integer id, String name) {
        Role role = findRole(id);
        role.setName(name);
        roleRepository.save(role);
    }

    public void updateRoleDescription(Integer id, String description) {
        Role role = findRole(id);
        role.setDescription(description);
        roleRepository.save(role);
    }

    public void updateRoleVolunteer(Integer roleId, Integer volunteerId) {
        Role role = findRole(roleId);
        Volunteer volunteer = findVolunteer(volunteerId);
        role.setVolunteer(volunteer);
        roleRepository.save(role);
    }

    public void updateRoleEvent(Integer roleId, Integer eventId) {
        Role role = findRole(roleId);
        Event event = findEvent(eventId);
        role.setEvent(event);
        roleRepository.save(role);
    }

    public void updateRoleDetails(Integer id, RoleDTO dto) {
        Role role = findRole(id);
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setEvent(findEvent(dto.getEvent_id()));
        role.setVolunteer(findVolunteer(dto.getVolunteer_id()));
        roleRepository.save(role);
    }

    // Delete Methods
    public void deleteRoleById(Integer id) {
        Role role = findRole(id);
        roleRepository.delete(role);
    }

    public void deleteRolesByEvent(Integer eventId) {
        Event event = findEvent(eventId);
        List<Role> roles = roleRepository.findByEvent(event);
        roleRepository.deleteAll(roles);
    }

    public void deleteRolesByVolunteer(Integer volunteerId) {
        Volunteer volunteer = findVolunteer(volunteerId);
        List<Role> roles = roleRepository.findByVolunteer(volunteer);
        roleRepository.deleteAll(roles);
    }

    public void deleteRolesByName(String name) {
        List<Role> roles = roleRepository.findByName(name);
        roleRepository.deleteAll(roles);
    }

    // Utility Methods
    private Role findRole(Integer id) {
        Role role = roleRepository.findRoleById(id);
        if (role == null) {
            throw new ApiException("Role not found");
        }
        return role;
    }

    private Event findEvent(Integer id) {
        Event event = eventRepository.findEventById(id);
        if (event == null) {
            throw new ApiException("Event not found");
        }
        return event;
    }

    private Volunteer findVolunteer(Integer id) {
        Volunteer volunteer = volunteerRepository.findVolunteerById(id);
        if (volunteer == null) {
            throw new ApiException("Volunteer not found");
        }
        return volunteer;
    }

    private Role createRoleFromDTO(RoleDTO dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setEvent(findEvent(dto.getEvent_id()));
        role.setVolunteer(findVolunteer(dto.getVolunteer_id()));
        return role;
    }

    private RoleDTOout convertToDTOout(Role role) {
        return new RoleDTOout(role.getName(), role.getVolunteer().getName(), role.getEvent().getName(), role.getDescription());
    }

    private List<RoleDTOout> convertToDTOList(List<Role> roles) {
        List<RoleDTOout> list = new ArrayList<>();
        for (Role r : roles) {
            list.add(convertToDTOout(r));
        }
        return list;
    }
}
