package com.example.project_service.service;

import com.example.project_service.domain.Project;
import com.example.project_service.dto.ProjectRequestDto;
import com.example.project_service.dto.ProjectResponseDto;
import com.example.project_service.dto.UserResponseDto;
import com.example.project_service.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProjectService {

    private final RestTemplate restTemplate;
    private final ProjectRepository projectRepository;

    public List<ProjectResponseDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(ProjectResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }


    /*
    * User Service -> (HTTP) -> Project Service API
    * (UserRepository) FeignClient, RestTemplate or WebClient
    * User-API -> Project-API
    * TODO: Need Replace
    *  */
    public Project createProject(ProjectRequestDto projectRequestDto, String email) {

        String userServiceUrl =
                "http://localhost:8005/api/auth/"+email;

        System.out.println("userServiceUrl = " + userServiceUrl);
        System.out.println("ProjectService.createProject "+email);

        UserResponseDto UserDto = restTemplate.getForObject(userServiceUrl,UserResponseDto.class);

        Project project = new Project();
        project.setTitle(projectRequestDto.getName());
        project.setDescription(projectRequestDto.getDescription());
        project.setGoalAmount(projectRequestDto.getGoalAmount());
        project.setImageUrl(projectRequestDto.getImageUrl());
        project.setTags(projectRequestDto.getTags());

        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProject(Long id, Project updatedProject) {
        return projectRepository.findById(id).map(project -> {
            project.setTitle(updatedProject.getTitle());
            project.setDescription(updatedProject.getDescription());
            project.setGoalAmount(updatedProject.getGoalAmount());
            project.setEndDate(updatedProject.getEndDate());
            project.setTags(updatedProject.getTags());
            return projectRepository.save(project);
        }).orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
