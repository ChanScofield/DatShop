package com.example.datshopspring2.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeProfileDto {

    @NotNull
    @Size(min = 6, max = 20, message = "inappropriate username")
    private String username;

    @NotNull
    @Size(min = 6, max = 12, message = "inappropriate phone number")
    private String phoneNumber;

    @NotNull
    private String city;

    @NotNull
    private String sex;

    @NotNull
    @Min(6)
    private Integer age;
}
