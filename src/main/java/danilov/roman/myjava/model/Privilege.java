package danilov.roman.myjava.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Data
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @NonNull
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(){
    }

    public Privilege(String name){
        this.name = name;
    }

    public Privilege(String name, Collection<Role> roles){
        this(name);
        this.roles = roles;
    }

}
