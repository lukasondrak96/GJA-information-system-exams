package cz.vutbr.fit.gja.models.teacher;

import cz.vutbr.fit.gja.models.role.Role;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * This class represents the entity of teacher
 */
@Entity
@Table(name = "teacher")
public class Teacher {

    /**
     * Teacher first name
     */
    @NotNull(message="Prosím vyplňte jméno")
    @Column(name = "first_name")
    private String firstName;

    /**
     * Teacher last name
     */
    @NotNull(message="Prosím vyplňte příjmení")
    @Column(name = "last_name")
    private String lastName;

    /**
     * Degrees before Teacher's name
     */
    @Column(name = "degrees_before_name")
    private String degreesBeforeName;

    /**
     * Degrees behind Teacher's name
     */
    @Column(name = "degrees_behind_name")
    private String degreesBehindName;

    /**
     * Teacher email
     */
    @Id
    @Length(min=1, message="Prosím vyplňte emailovou adresu")
    @Email(message = "Zadán chybný email")
    @Column(name = "email", unique=true)
    private String email;

    /**
     * Teacher passwor
     */
    @Length(min=5, message="Heslo musí mít minimálně 5 znaků")
    @Column(name = "password")
    private String password;

    /**
     * Repeated teacher password
     */
    @Transient
    private String repeatPassword;

    /**
     * Teacher status
     */
    @Column(name = "status")
    private String status;

    /**
     * Teacher role
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_has_role", joinColumns = @JoinColumn(name = "email"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles;

    public Teacher() {
    }

    /**
     * Creates a new Teacher
     *
     * @param firstName Teacher first name
     * @param lastName Teacher last name
     * @param degreesBeforeName Degrees before name
     * @param degreesBehindName Degrees behind name
     * @param email Teacher email
     * @param password Teacher password
     * @param repeatPassword repeated teacher password
     * @param status Teacher status
     * @param roles Teacher role
     */
    public Teacher(@NotNull(message = "Prosím vyplňte jméno") String firstName,
                   @NotNull(message = "Prosím vyplňte příjmení") String lastName,
                   String degreesBeforeName, String degreesBehindName,
                   @Length(min = 1, message = "Prosím vyplňte emailovou adresu") @Email(message = "Zadán chybný email") String email,
                   @Length(min = 5, message = "Heslo musí mít minimálně 5 znaků") String password,
                   String repeatPassword,
                   String status,
                   Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.degreesBeforeName = degreesBeforeName;
        this.degreesBehindName = degreesBehindName;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.status = status;
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
