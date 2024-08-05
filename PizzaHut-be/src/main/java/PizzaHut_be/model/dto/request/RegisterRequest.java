package PizzaHut_be.model.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;

    private String lastName;

    private String password;

    private String username;

    private String email;

    private String passwordOrOtp;
}
