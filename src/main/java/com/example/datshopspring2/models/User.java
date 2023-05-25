package com.example.datshopspring2.models;


import com.example.datshopspring2.models.enums.AuthenticationProvider;
import com.example.datshopspring2.models.enums.Role;
import com.example.datshopspring2.models.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    private String password;

    @Transient
    private String username;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authenticationProvider;

    public boolean isConfirm() {
        return this.state.equals(State.CONFIRMED);
    }
}
