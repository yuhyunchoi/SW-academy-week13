package com.nhnacademy.daily.controller;

import com.nhnacademy.daily.model.Project;
import com.nhnacademy.daily.model.ProjectCreateCommand;
import com.nhnacademy.daily.model.ProjectType;
import com.nhnacademy.daily.service.ProjectService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/public-projects")
    public ResponseEntity<?> listPublicProjects(Pageable pageable) {
        List<Project> publicProjects = projectService.getProjects(ProjectType.PUBLIC, pageable);
        return ResponseEntity.ok(publicProjects);
    }

    @GetMapping("/private-projects")
    public ResponseEntity<?> listPrivateProjects(Pageable pageable, Authentication authentication) {
        if (!hasAdminRole(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        List<Project> privateProjects = projectService.getProjects(ProjectType.PRIVATE, pageable);
        return ResponseEntity.ok(privateProjects);
    }

    private boolean hasAdminRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
