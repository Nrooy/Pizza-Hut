package PizzaHut_be.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles;
}
