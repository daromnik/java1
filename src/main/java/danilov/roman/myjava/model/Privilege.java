package danilov.roman.myjava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = { "roles" })
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NonNull
    private String name;

    @ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
    private Set<Role> roles;

    public Privilege(){
    }

    public Privilege(String name){
        this.name = name;
    }

    public Privilege(String name, Set<Role> roles){
        this(name);
        this.roles = roles;
    }

}
