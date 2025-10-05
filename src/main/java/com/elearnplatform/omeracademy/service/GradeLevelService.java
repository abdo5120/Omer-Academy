package com.elearnplatform.omeracademy.service;

import com.elearnplatform.omeracademy.dto.course.GradeLevelDto;
import com.elearnplatform.omeracademy.entity.GradeLevel;
import com.elearnplatform.omeracademy.exception.ResourceAlreadyExistsException;
import com.elearnplatform.omeracademy.exception.ResourceNotFoundException;
import com.elearnplatform.omeracademy.mapper.GradeLevelMapper;
import com.elearnplatform.omeracademy.repository.GradeLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeLevelService
{
    private final GradeLevelRepository gradeLevelRepository;
    private final GradeLevelMapper gradeLevelMapper;

    @Transactional
    public GradeLevelDto createGradeLevel(GradeLevelDto gradeLevelDto)
    {
        if (gradeLevelRepository.existsByGradeLevelName(gradeLevelDto.getName()))
            throw new ResourceAlreadyExistsException("Grade Level already exists");
        GradeLevel gradeLevel = gradeLevelMapper.toGradedLevelEntity(gradeLevelDto);
        gradeLevel = gradeLevelRepository.save(gradeLevel);
        return gradeLevelMapper.toGradeLevelDto(gradeLevel);
    }

    public GradeLevelDto getGradeLevelById(Long id) {
        GradeLevel gradeLevel = gradeLevelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GradeLevel is not found"));
        return gradeLevelMapper.toGradeLevelDto(gradeLevel);
    }

    public GradeLevelDto getGradeLevelByName(String name) {
        GradeLevel gradeLevel = gradeLevelRepository.findByGradeLevelName(name)
                .orElseThrow(() -> new ResourceNotFoundException("GradeLevel is not found"));
        return gradeLevelMapper.toGradeLevelDto(gradeLevel);
    }


    public List<GradeLevelDto> getAllGradeLevels()
    {
        List<GradeLevel> gradeLevels = gradeLevelRepository.findAll();
        return gradeLevelMapper.toGradeLevelDtoList(gradeLevels);
    }

    @Transactional
    public void deleteGradeLevel(Long id)
    {
        if (!gradeLevelRepository.existsById(id)) {
            throw new ResourceNotFoundException("GradeLevel is not found");
        }
        gradeLevelRepository.deleteById(id);
    }


}
