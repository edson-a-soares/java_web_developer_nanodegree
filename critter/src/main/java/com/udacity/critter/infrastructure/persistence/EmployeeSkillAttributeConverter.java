package com.udacity.critter.infrastructure.persistence;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import javax.persistence.Converter;
import javax.persistence.AttributeConverter;
import com.udacity.critter.domain.model.user.EmployeeSkill;

@Converter(autoApply = true)
public class EmployeeSkillAttributeConverter implements AttributeConverter<Set<EmployeeSkill>, String> {

    @Override
    public String convertToDatabaseColumn(Set<EmployeeSkill> skills) {
        Set<String> asStrings = skills
            .stream()
            .map(EmployeeSkill::name)
            .collect(Collectors.toSet());

        return String.join(", ", asStrings);
    }

    @Override
    public Set<EmployeeSkill> convertToEntityAttribute(String skills) {
        if (skills == null)
            return null;

        Set<String> asStrings = Arrays.stream(skills.split("\\s*,\\s*")).collect(Collectors.toSet());
        Set<EmployeeSkill> asEnum = new HashSet<>();
        for (String code : asStrings)
            asEnum.add(EmployeeSkill.valueOf(code.trim()));

        return asEnum;
    }

}
