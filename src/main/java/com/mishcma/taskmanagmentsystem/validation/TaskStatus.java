package com.mishcma.taskmanagmentsystem.validation;


import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = TaskStatusValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskStatus {
    String message() default "Invalid status value. Allowed values are: To do, In progress, Done";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
