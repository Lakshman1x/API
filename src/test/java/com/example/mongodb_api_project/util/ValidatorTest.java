package com.example.mongodb_api_project.util;

import com.example.mongodb_api_project.exception_handler.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidatorTest {
    @Test
    void testValidateWithoutErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        assertDoesNotThrow(() -> Validator.validate(bindingResult));
    }

    @Test
    void testValidateWithErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThrows(ValidationException.class, () -> Validator.validate(bindingResult));
    }
}