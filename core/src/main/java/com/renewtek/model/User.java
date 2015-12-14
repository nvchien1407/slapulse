//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * User class
 * <p/>
 * This class is used to generate Spring Validation rules
 * as well as the Hibernate mapping file.
 * <p/>
 * <p><a href="User.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Updated by Dan Kibler (dan@getrolling.com)
 */
@Entity
@Table(name = "appuser")
public class User extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3832626162173359411L;

    @Id
    @Column(name = "username", length = 20)
    protected String username;

    @Column(name = "password", length = 20, nullable = false)
    protected String password;

    @Column(name = "first_name", length = 50, nullable = false)
    protected String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    protected String lastName;

    @Column(name = "phone_number", length = 50, nullable = true)
    protected String phoneNumber;

    @Column(name = "email", length = 50, nullable = false)
    protected String email;

    @Column(name = "website", length = 255, nullable = true)
    protected String website;
    
    @Column(name = "password_hint", length = 255, nullable = true)
    protected String passwordHint;

    @Version
    protected Integer version;

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "user_role")
    protected Set<Role> roles = new HashSet<Role>();

    @Column(name = "enabled")
    protected Boolean enabled;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public String getId() {
        return username;
    }

    public void setId(String param) {
        username = param;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public String getPasswordHint() {
        return passwordHint;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Adds a role for the user
     *
     * @param role
     */
    public void addRole(Role role) {
        getRoles().add(role);
    }

    /**
     * Sets the username.
     *
     * @param username The username to set
     * @spring.validator type="required"
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password.
     *
     * @param password The password to set
     * @spring.validator type="required"
     * @spring.validator type="twofields" msgkey="errors.twofields"
     * @spring.validator-args arg1resource="user.password"
     * @spring.validator-args arg1resource="user.confirmPassword"
     * @spring.validator-var name="secondProperty" value="confirmPassword"
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the firstName.
     *
     * @param firstName The firstName to set
     * @spring.validator type="required"
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the lastName.
     *
     * @param lastName The lastName to set
     * @spring.validator type="required"
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * Sets the email.
     *
     * @param email The email to set
     * @spring.validator type="required"
     * @spring.validator type="email"
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the phoneNumber.
     *
     * @param phoneNumber The phoneNumber to set
     * @spring.validator type="mask" msgkey="errors.phone"
     * @spring.validator-var name="mask" value="${phone}"
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the website.
     *
     * @param website The website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @param passwordHint The password hint to set
     * @spring.validator type="required"
     */
    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    /**
     * Sets the roles.
     *
     * @param roles The roles to set
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * @return Returns the updated timestamp.
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version The updated version to set.
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled The enabled to set.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Convert user roles to LabelValue objects for convenience.
     */
    public List<LabelValue> getRoleList() {
        List<LabelValue> userRoles = new ArrayList<LabelValue>();

        if (this.roles != null) {
           for (Role role : roles) {
              // convert the user's roles to LabelValue Objects
              userRoles.add(new LabelValue(role.getName(),
                 role.getName()));
           }
        }

        return userRoles;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        final User user = (User) o;

        if (username != null ? !username.equals(user.getUsername()) : user.getUsername() != null) return false;

        return true;
    }

    public int hashCode() {
        return (username != null ? username.hashCode() : 0);
    }

    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("roles", this.roles)
                .append("firstName", this.firstName).append("lastName",
                        this.lastName)
                .append("passwordHint", this.passwordHint).append("username",
                        this.username).append("fullName", this.getFullName())
                .append("email", this.email).append("phoneNumber",
                        this.phoneNumber).append("password", this.password)
                .append("version", this.getVersion())
                .append("enabled", this.getEnabled()).toString();
    }
}
