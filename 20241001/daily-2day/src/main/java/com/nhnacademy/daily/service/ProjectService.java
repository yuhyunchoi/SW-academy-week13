package com.nhnacademy.daily.service;

import com.nhnacademy.daily.model.Project;
import com.nhnacademy.daily.model.ProjectCreateCommand;
import com.nhnacademy.daily.model.ProjectType;
import com.nhnacademy.daily.model.exception.ProjectAlreadyExistsException;
import com.nhnacademy.daily.model.exception.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    @Autowired
    private RedisTemplate<String, Project> redisTemplate;

    private static final String PUBLIC_HASH_NAME = "PublicProject:";
    private static final String PRIVATE_HASH_NAME = "PrivateProject:";

    // 프로젝트 생성
    public void createProject(ProjectCreateCommand projectCreateCommand) {
        String hashName = getProjectHashName(projectCreateCommand.getType());

        Object existingProject = redisTemplate.opsForHash().get(hashName, projectCreateCommand.getCode());
        if (existingProject != null) {
            throw new ProjectAlreadyExistsException("Project with the given code already exists");
        }

        Project project = new Project(
                projectCreateCommand.getCode(),
                projectCreateCommand.getType(),
                LocalDate.now()  // 생성일 설정
        );
        redisTemplate.opsForHash().put(hashName, project.getCode(), project);
    }

    // 프로젝트 리스트 조회 (페이지네이션 적용)
    public List<Project> getProjects(ProjectType projectType, Pageable pageable) {
        String hashName = getProjectHashName(projectType);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(hashName);

        List<Project> projects = new ArrayList<>(entries.size());
        for (Object value : entries.values()) {
            projects.add((Project) value);
        }

        int start = Math.toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()), projects.size());
        if (start > end) {
            return Collections.emptyList();
        }
        return projects.subList(start, end);
    }

    // 특정 프로젝트 조회
    public Project getProject(ProjectType projectType, String code) {
        String hashName = getProjectHashName(projectType);

        Object project = redisTemplate.opsForHash().get(hashName, code);
        if (project == null) {
            throw new ProjectNotFoundException("Project not found");
        }
        return (Project) project;
    }

    // 프로젝트 타입에 따른 Redis HashName 반환
    private String getProjectHashName(ProjectType project

