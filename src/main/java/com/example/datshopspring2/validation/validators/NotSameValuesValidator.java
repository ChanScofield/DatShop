package com.example.datshopspring2.validation.validators;

import com.example.datshopspring2.validation.constraints.SameValues;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.data.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NotSameValuesValidator implements ConstraintValidator<SameValues, Object> {

    private String[] fieldsName;

    @Override
    public void initialize(SameValues sameValues) {
        this.fieldsName = sameValues.fields();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        List<String> fieldsValues = new ArrayList<>();

        for (String fieldName: fieldsName) {
            Field field = ReflectionUtils.findRequiredField(object.getClass(), fieldName);
            field.setAccessible(true);

            try {
                fieldsValues.add((String) field.get(object));
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }

        return fieldsValues.size() != fieldsValues.stream().distinct().count();
    }
}
