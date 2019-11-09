package cz.vutbr.fit.gja.models;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "auth_user")
public class User {

    @Length(min=1, message="Prosím vyplňte jméno")
    @Column(name = "first_name")
    private String name;

    @Length(min=1, message="Prosím vyplňte příjmení")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "degrees_before_name")
    private String degreesBeforeName;

    @Column(name = "degrees_behind_name")
    private String degreesBehindName;

    @Id
    @Length(min=1, message="Email je povinný")
    @Email(message = "Zadán chybný email")
    @Column(name = "email")
    private String email;

    @Length(min=5, message="Heslo musí mít minimálně 5 znaků")
    @Column(name = "password")
    private String password;

    @Transient
    private String repeatPassword;

    @Column(name = "status")
    private String status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "auth_user_role", joinColumns = @JoinColumn(name = "email"), inverseJoinColumns = @JoinColumn(name = "auth_role_id"))
    private Set<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getDegreesBeforeName() {
        return degreesBeforeName;
    }

    public void setDegreesBeforeName(String degreesBeforeName) {
        this.degreesBeforeName = degreesBeforeName;
    }

    public String getDegreesBehindName() {
        return degreesBehindName;
    }

    public void setDegreesBehindName(String degreesBehindName) {
        this.degreesBehindName = degreesBehindName;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
