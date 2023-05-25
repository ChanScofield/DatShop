package com.example.datshopspring2.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Error Detail")
public class ExceptionDto {

    @Schema(description = "Error text")
    private String message;

    @Schema(description = "HTTP-code status")
    private int status;
}
