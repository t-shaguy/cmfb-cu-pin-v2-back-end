/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.resources;

/**
 *
 * @author paycraftsystems-i
 */
import java.util.regex.Pattern;

/**
 *
 * @author root
 */
public class GregorianDateMatcher implements DateMatcher {
 
    private static Pattern DATE_PATTERN = Pattern.compile(
      "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
      + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
      + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
      + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
 
    @Override
    public boolean matches(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}