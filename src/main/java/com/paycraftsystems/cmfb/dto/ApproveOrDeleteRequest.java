package com.paycraftsystems.cmfb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Rasaq Sulaimon
 * @date 02/02/2025 23:11
 */
public record ApproveOrDeleteRequest(
        
        @NotNull
        @Min(1)
        long tid,
        @NotNull
        @Min(1)
        int actionBy,
        @NotBlank
        @Size(min=1, max=100, message="Action by Str must be valid")
        String actionByStr
        ) {
}
