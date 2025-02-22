package com.paycraftsystems.cmfb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Rasaq Sulaimon
 * @date 02/02/2025 23:11
 */
public record FilterRequest(
        @NotBlank
        @Size(min=19,max=19, message="from date must be in the format yyyy-MM-dd HH:mm:ss")
        String fromDate,
        @NotBlank
        @Size(min=19,max=19, message="to date must be in the format yyyy-MM-dd HH:mm:ss")
        String toDate,
        //@NotBlank
        //@Size(min=3, message="username must be supplied")
        String searchParam,
        
        @NotBlank
        @Size(min=3, message="status must be supplied")
        String status,
        @NotNull
        @Min(1)
        int pageId,
        @NotNull
        @Min(1)
        int pageSize
        ) {
}
