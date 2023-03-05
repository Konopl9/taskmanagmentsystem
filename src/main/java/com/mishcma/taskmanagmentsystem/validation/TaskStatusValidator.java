package com.mishcma.taskmanagmentsystem.validation;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaskStatusValidator implements ConstraintValidator<TaskStatus, String> {

    private final List<String> allowedValues = Arrays.asList("To do", "In progress", "Done");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && allowedValues.contains(value);
    }
    
}
