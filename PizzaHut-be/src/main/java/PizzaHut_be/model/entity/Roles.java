package PizzaHut_be.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class Roles {
    @Id
    private String id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Client> users;
}
