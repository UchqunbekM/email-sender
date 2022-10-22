package uz.pdp.appemailsend.Payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data

public class RegisterDto {
    @NotNull
    @Size(min = 3, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}
