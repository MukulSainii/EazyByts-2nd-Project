package com.RODS.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

// income, for registration

public class LoginDTO {
    @NotBlank(message = "{error.signup.login}")
    private String login;

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$",
            message = "{error.signup.email}")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9._%+-]{2,6}$",
            message = "{error.signup.password}")
    private String password;
}
