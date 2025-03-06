package com.paycraftsystems.cmfb.dto;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class CBAAccessToken {
   public String token_type;
   public long expires_in;
   public String expires;
   public String issued;
   public String access_token;
   public LocalDateTime grantTime;

   public boolean isValid() {
      return grantTime != null
              //Add 60 seconds to prevent errors with long-running transactions
              && grantTime.plusSeconds(expires_in - 60).isAfter(LocalDateTime.now());
   }

   public enum TokenType {
      TOKEN, OTHER
   }
}