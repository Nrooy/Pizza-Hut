package PizzaHut_be.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "user")
public class UserModel {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String password;

    private String username;

    private String email;

    private LocalDate birthday;

    private String address;

    private String locate;

    private String gender;

    private String phone;

    private String description;

    private String avatar;
}
