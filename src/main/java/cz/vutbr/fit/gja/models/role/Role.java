package cz.vutbr.fit.gja.models.role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This class represent the entity of Role
 */

@Entity
@Table(name = "role")
public class Role {
    /**
     * Role ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_role")
    private int idRole;

    /**
     * Type of roll
     */
    @NotNull
    @Column(name = "role_name")
    private String roleName;

    /**
     * Type of roll for system check
     */
    @Column(name = "role_desc")
    private String roleDesc;

    public Role() {
    }

    /**
     * Creates a new Student
     * @param roleName type of role
     * @param roleDesc type of role for system check
     */
    public Role(@NotNull String roleName, String roleDesc) {
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }


}
