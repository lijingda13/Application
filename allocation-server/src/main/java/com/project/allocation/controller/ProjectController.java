package com.project.allocation.controller;

import com.project.allocation.dto.StaffProjectDTO;
import com.project.allocation.dto.StudentProjectDTO;
import com.project.allocation.model.User;
import com.project.allocation.service.ProjectService;
import com.project.allocation.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.allocation.model.Project;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    private final JwtUtil jwtUtil;

    @Autowired
    public ProjectController(ProjectService projectService, JwtUtil jwtUtil) {
        this.projectService = projectService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> listAllProjects() {
        List<Project> projects = projectService.listAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/students/{studentId}/available-projects")
    public ResponseEntity<List<StudentProjectDTO>> listAvailableProjects(@PathVariable Long studentId) {
        List<StudentProjectDTO> projects = projectService.listAvailableProjects(studentId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/staff/{staffId}/proposed-projects")
    public ResponseEntity<List<StaffProjectDTO>> listProposedProjects(@PathVariable Long staffId) {
        List<StaffProjectDTO> projects = projectService.listProposedProjects(staffId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

//    @GetMapping("/students/{studentId}/assigned-project")
//    public ResponseEntity<Project> getAssignedProject(@PathVariable Long studentId) {
//        Project assignedProject = projectService.getAssignedProject(studentId);
//        return assignedProject != null ? new ResponseEntity<>(assignedProject, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PostMapping("/staff/{staffId}/create-project")
    public ResponseEntity<Project> createProject(@PathVariable Long staffId, @Valid @RequestBody Project project) {
        Project createdProject = projectService.createProject(project, staffId);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PatchMapping("/projects/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId, @RequestBody Project project) {
        project.setId(projectId);
        Project updatedProject = projectService.updateProject(project);
        return updatedProject != null ? new ResponseEntity<>(updatedProject, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        boolean deleted = projectService.deleteProject(projectId);
        if (deleted) {
            return ResponseEntity.ok("Project deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found.");
        }
    }

    @PostMapping("/projects/{projectId}/register-interest")
    public ResponseEntity<?> registerInterest(@PathVariable Long projectId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        boolean registered = projectService.registerInterest(projectId, userId);
        return registered ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/projects/{projectId}/unregister-interest")
    public ResponseEntity<?> unregisterInterest(@PathVariable Long projectId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        boolean unregistered = projectService.unregisterInterest(projectId, userId);
        return unregistered ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/projects/{projectId}/assign-project")
    public ResponseEntity<?> assignProject(@PathVariable Long projectId, @RequestBody User user) {
        boolean assigned = projectService.assignProject(projectId, user);
        return assigned ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
