package ru.test.hello.helpers;

import org.springframework.validation.Errors;
import ru.test.hello.models.ValidationError;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Helpers {
    public static List<ValidationError> getValidationErrors(Errors errors) {
        return errors
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    public static <T> List<T> getListFromIterable(Iterable<T> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
