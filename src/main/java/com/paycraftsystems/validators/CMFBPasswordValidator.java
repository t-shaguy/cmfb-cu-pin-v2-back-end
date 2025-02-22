/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.validators;

/**
 *
 * @author paycraftsystems-i
 */
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CMFBPasswordValidator implements ConstraintValidator<CMFBUserPassword, String> {

    private static final String HOPPASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{7,20}$";
    private final Pattern pattern = Pattern.compile(HOPPASSWORD_REGEX);
    
    @Override
    public void initialize(CMFBUserPassword constraintAnnotation) {
        // Initialization logic if needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Consider using @NotNull if null values should be invalid
        }
        return pattern.matcher(value).matches();
    }
}
