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
import java.lang.reflect.RecordComponent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchPasswordValidator implements ConstraintValidator<VerifyPasswordValidator, Object> {

    /*
    private String field;
    private String matchField;

    @Override
    public void initialize(VerifyPasswordValidator constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.matchField = constraintAnnotation.matchField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            // Get the values of the fields from the object
            Field field1 = value.getClass().getDeclaredField(field);
            field1.setAccessible(true);
            Object fieldValue = field1.get(value);

            Field field2 = value.getClass().getDeclaredField(matchField);
            field2.setAccessible(true);
            Object matchFieldValue = field2.get(value);

            // Compare the two field values
            return fieldValue != null && fieldValue.equals(matchFieldValue);
        } catch (Exception e) {
            // Handle reflection errors
            return false;
        }
    }
    
    */
    
    private String field;
    private String matchField;

    @Override
    public void initialize(VerifyPasswordValidator constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.matchField = constraintAnnotation.matchField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            // Using reflection to access record components
            RecordComponent field1 = value.getClass().getRecordComponents()[0];
            
            log.info("-- field1-- "+field1);
            
            RecordComponent field2 = value.getClass().getRecordComponents()[1];
            
            RecordComponent field3 = value.getClass().getRecordComponents()[2];
            log.info("-- field2-- "+field2);
            log.info("-- field3-- "+field3);
            log.info("-- field --  "+field+"---matchField  "+matchField);
            if (field1.getName().equals(field) && field2.getName().equals(matchField)) {
                Object fieldValue = field1.getAccessor().invoke(value);
                Object matchFieldValue = field2.getAccessor().invoke(value);
                log.info("-- fieldValue --"+fieldValue+" -- matchFieldValue-- "+matchFieldValue);
                return fieldValue != null && fieldValue.equals(matchFieldValue);
            }
            else
            {
               System.out.println("FAILED  = field " + field+" - matchField "+matchField);

            return false; // In case the names don't match
            
            }
        } catch (Exception e) {
            return false;  // Handle any errors (e.g., reflection issues)
        }
    }
}
