package com.project.allocation.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import com.project.allocation.model.User.Role;
import com.project.allocation.service.ProjectService;
import com.project.allocation.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @PostMapping("/propose")
    public ResponseEntity<Project> proposeProject(@RequestBody Project project, @AuthenticationPrincipal User user) {
        if (user.getRole() != Role.STAFF) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Project newProject = projectService.proposeProject(project, user);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Project>> viewProjects() {
        List<Project> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping("/{projectId}/registerInterest")
    public ResponseEntity<Void> registerInterest(@PathVariable Project project, @AuthenticationPrincipal User user) {
        if (user.getRole() != Role.STUDENT) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        projectService.registerInterest(project, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{projectId}/assign/{studentId}")
    public ResponseEntity<Project> assignProjectToStudent(@PathVariable Project project, @AuthenticationPrincipal User student,  @AuthenticationPrincipal User staff) {
        if (staff.getRole() != Role.STAFF || student.getRole() != Role.STUDENT) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Project projectAssign = projectService.assignProject(project, student, staff);
        return projectAssign != null ? new ResponseEntity<>(project, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Additional endpoints like 'getProjectsByStaff', 'getStudentsInterestedInProject'

}
