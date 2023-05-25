package com.example.datshopspring2.dto;


import com.example.datshopspring2.validation.constraints.SameValues;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SameValues(fields = {"newPassword", "repeatNewPassword"}, message = "Repeat password not correct")
public class ChangePasswordDto {

    @NotEmpty
    private String oldPassword;

    @NotEmpty
    @Size(min = 8, max = 30)
    private String newPassword;

    @NotEmpty
    @Size(min = 8, max = 30)
    private String repeatNewPassword;
}
