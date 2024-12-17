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

    public RoleDTOout getRoleById(Integer id) {
        Role role = findRole(id);
        return convertToDTOout(role);
    }

    public List<RoleDTOout> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) throw new ApiException("No roles found");
        return convertToDTOList(roles);
    }

    public List<RoleDTOout> getRolesByEventId(Integer eventId) {
        Event event = findEvent(eventId);
        List<Role> roles = roleRepository.findByEvent(event);
        if (roles.isEmpty()) throw new ApiException("No roles found for this event");
        return convertToDTOList(roles);
    }

    public List<RoleDTOout> getRolesByVolunteerId(Integer volunteerId) {
        Volunteer volunteer = findVolunteer(volunteerId);
        List<Role> roles = roleRepository.findByVolunteer(volunteer);
        if (roles.isEmpty()) throw new ApiException("No roles found for this volunteer");
        return convertToDTOList(roles);
    }

    public List<RoleDTOout> getRolesByName(String name) {
        if (name == null || name.trim().isEmpty()) throw new ApiException("Role name cannot be null or empty");
        List<Role> roles = roleRepository.findByName(name);
        if (roles.isEmpty()) throw new ApiException("No roles found with name: " + name);
        return convertToDTOList(roles);
    }

    public long countRoles() {
        long count = roleRepository.count();
        if (count == 0) throw new ApiException("No roles found in the system");
        return count;
    }

    public boolean roleExistsById(Integer id) {
        if (id == null) throw new ApiException("Role ID cannot be null");
        return roleRepository.existsById(id);
    }

    public List<RoleDTOout> getRolesByDescription(String description) {
        if (description == null || description.trim().isEmpty())
            throw new ApiException("Description cannot be null or empty");
        List<Role> roles = roleRepository.findByDescriptionContaining(description);
        if (roles.isEmpty()) throw new ApiException("No roles found with the specified description");
        return convertToDTOList(roles);
    }

    public List<RoleDTOout> getRolesByEventName(String eventName) {
        if (eventName == null || eventName.trim().isEmpty())
            throw new ApiException("Event name cannot be null or empty");
        List<Role> roles = roleRepository.findByEvent_Name(eventName);
        if (roles.isEmpty()) throw new ApiException("No roles found for event name: " + eventName);
        return convertToDTOList(roles);
    }

    public void addRole(RoleDTO dto) {
        validateRoleDTO(dto);
        Role role = createRoleFromDTO(dto);
        roleRepository.save(role);
    }

    public void addRolesInBulk(List<RoleDTO> roles) {
        if (roles == null || roles.isEmpty()) throw new ApiException("Role list cannot be null or empty");
        for (RoleDTO dto : roles) {
            addRole(dto);
        }
    }

    public void addRoleToEvent(Integer eventId, RoleDTO dto) {
        Event event = findEvent(eventId);
        validateRoleDTO(dto);
        Role role = createRoleFromDTO(dto);
        role.setEvent(event);
        roleRepository.save(role);
    }

    public void updateRoleName(Integer id, String name) {
        Role role = findRole(id);
        if (name == null || name.trim().isEmpty()) throw new ApiException("Role name cannot be null or empty");
        role.setName(name);
        roleRepository.save(role);
    }

    public void updateRoleDescription(Integer id, String description) {
        Role role = findRole(id);
        if (description == null || description.trim().isEmpty())
            throw new ApiException("Description cannot be null or empty");
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
        validateRoleDTO(dto);
        Role role = findRole(id);
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setEvent(findEvent(dto.getEvent_id()));
        role.setVolunteer(findVolunteer(dto.getVolunteer_id()));
        roleRepository.save(role);
    }

    public void deleteRoleById(Integer id) {
        Role role = findRole(id);
        roleRepository.delete(role);
    }

    public void deleteRolesByEvent(Integer eventId) {
        Event event = findEvent(eventId);
        List<Role> roles = roleRepository.findByEvent(event);
        if (roles.isEmpty()) throw new ApiException("No roles found for this event");
        roleRepository.deleteAll(roles);
    }

    public void deleteRolesByVolunteer(Integer volunteerId) {
        Volunteer volunteer = findVolunteer(volunteerId);
        List<Role> roles = roleRepository.findByVolunteer(volunteer);
        if (roles.isEmpty()) throw new ApiException("No roles found for this volunteer");
        roleRepository.deleteAll(roles);
    }

    public void deleteRolesByName(String name) {
        if (name == null || name.trim().isEmpty()) throw new ApiException("Role name cannot be null or empty");
        List<Role> roles = roleRepository.findByName(name);
        if (roles.isEmpty()) throw new ApiException("No roles found with name: " + name);
        roleRepository.deleteAll(roles);
    }

    private Role findRole(Integer id) {
        if (id == null) throw new ApiException("Role ID cannot be null");
        Role role = roleRepository.findRoleById(id);
        if (role == null) throw new ApiException("Role not found");
        return role;
    }

    private Event findEvent(Integer id) {
        if (id == null) throw new ApiException("Event ID cannot be null");
        Event event = eventRepository.findEventById(id);
        if (event == null) throw new ApiException("Event not found");
        return event;
    }

    private Volunteer findVolunteer(Integer id) {
        if (id == null) throw new ApiException("Volunteer ID cannot be null");
        Volunteer volunteer = volunteerRepository.findVolunteerById(id);
        if (volunteer == null) throw new ApiException("Volunteer not found");
        return volunteer;
    }

    private void validateRoleDTO(RoleDTO dto) {
        if (dto == null) throw new ApiException("Role data cannot be null");
        if (dto.getName() == null || dto.getName().trim().isEmpty())
            throw new ApiException("Role name cannot be null or empty");
        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty())
            throw new ApiException("Description cannot be null or empty");
        if (dto.getEvent_id() == null) throw new ApiException("Event ID cannot be null");
        if (dto.getVolunteer_id() == null) throw new ApiException("Volunteer ID cannot be null");
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
        return new RoleDTOout(role.getName(), role.getVolunteer().getName(), role.getEvent().getName(),
                role.getDescription());
    }

    private List<RoleDTOout> convertToDTOList(List<Role> roles) {
        List<RoleDTOout> list = new ArrayList<>();
        for (Role r : roles) {
            list.add(convertToDTOout(r));
        }
        return list;
    }
}
