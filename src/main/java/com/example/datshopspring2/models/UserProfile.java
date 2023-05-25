package com.example.datshopspring2.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @NotNull
    private String email;

    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    @NotNull
    private Integer age;

    @NotNull
    private String sex;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String city;

    @NotNull
    private Integer balance;


}
