package com.example.datshopspring2.validation.constraints;

import com.example.datshopspring2.validation.validators.NotSameValuesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotSameValuesValidator.class)
public @interface SameValues {

    String message() default "{notSameValues}";

    String[] fields();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}