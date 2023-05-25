package com.example.datshopspring2.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException{

    private String message;

    private int code;
}
