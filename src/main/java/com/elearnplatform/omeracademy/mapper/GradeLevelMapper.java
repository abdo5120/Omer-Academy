package com.elearnplatform.omeracademy.mapper;

import com.elearnplatform.omeracademy.dto.course.GradeLevelDto;
import com.elearnplatform.omeracademy.entity.GradeLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GradeLevelMapper
{
    public GradeLevelDto toGradeLevelDto(GradeLevel gradeLevel)
    {
        GradeLevelDto gradeLevelDto = new GradeLevelDto();
        gradeLevelDto.setId(gradeLevel.getId());
        gradeLevelDto.setName(gradeLevel.getGradeLevelName());
        return gradeLevelDto;
    }

    public GradeLevel toGradedLevelEntity(GradeLevelDto gradeLevelDto)
    {
        if (gradeLevelDto == null) return null;
        GradeLevel gradeLevel = new GradeLevel();
        gradeLevel.setGradeLevelName(gradeLevelDto.getName());
        return gradeLevel;
    }

    public List<GradeLevelDto> toGradeLevelDtoList(List<GradeLevel> gradeLevels)
    {
        List<GradeLevelDto> gradeLevelDtos = new ArrayList<>();
        for(GradeLevel gradeLevel : gradeLevels)
            gradeLevelDtos.add(toGradeLevelDto(gradeLevel));
        return gradeLevelDtos;
    }
}
