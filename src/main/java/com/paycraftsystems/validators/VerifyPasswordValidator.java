/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.validators;

/**
 *
 * @author paycraftsystems-i
 */


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Define the custom annotation
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatchPasswordValidator.class)  // Link to the custom validator
public @interface VerifyPasswordValidator {

    String message() default "Fields must match";  // Default error message

    Class<?>[] groups() default {};  // Grouping of constraints (optional)

    Class<? extends Payload>[] payload() default {};  // Metadata (optional)

    // Field names that need to be validated for matching values
    String field();
    String matchField();
}
