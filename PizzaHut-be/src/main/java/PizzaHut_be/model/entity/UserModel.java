package PizzaHut_be.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserModel {
    @Id
    private int id;

    private String name;

    private String password;

    private String username;

    private String email;

    private String phone;

    private String description;
}
