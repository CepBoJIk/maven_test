package ru.test.hello.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ValidationError {
    private final String fieldName;
    private final String message;
}
