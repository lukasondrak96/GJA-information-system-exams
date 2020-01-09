package cz.vutbr.fit.gja.models.teacher;

import cz.vutbr.fit.gja.models.role.Role;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * This class represents entity of Teacher
 */
@Entity
@Table(name = "teacher")
public class Teacher {

    /**
     * First name of Teacher
     */
    @NotNull(message="Prosím vyplňte jméno")
    @Column(name = "first_name")
    private String firstName;

    /**
     * Last name of Teacher
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
     * Email of Teacher
     */
    @Id
    @Length(min=1, message="Prosím vyplňte emailovou adresu")
    @Email(message = "Zadán chybný email")
    @Column(name = "email", unique=true)
    private String email;

    /**
     * Password of Teacher
     */
    @Length(min=5, message="Heslo musí mít minimálně 5 znaků")
    @Column(name = "password")
    private String password;

    /**
     * Repeated password of Teacher
     */
    @Transient
    private String repeatPassword;

    /**
     * Status of Teacher
     */
    @Column(name = "status")
    private String status;

    /**
     * Role of Teacher
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_has_role", joinColumns = @JoinColumn(name = "email"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles;

    public Teacher() {
    }

    /**
     * Creates new Teacher
     *
     * @param firstName first name of Teacher
     * @param lastName last name of Teacher
     * @param degreesBeforeName degrees before name
     * @param degreesBehindName degrees behind name
     * @param email email of Teacher
     * @param password password of Teacher
     * @param repeatPassword repeated password
     * @param status status of Teacher
     * @param roles role of Teacher
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
