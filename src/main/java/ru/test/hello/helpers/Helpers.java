package ru.test.hello.helpers;

import org.springframework.validation.Errors;
import ru.test.hello.models.ValidationError;

import java.util.List;
import java.util.stream.Collectors;

public class Helpers {
    public static List<ValidationError> getValidationErrors(Errors errors) {
        return errors
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
