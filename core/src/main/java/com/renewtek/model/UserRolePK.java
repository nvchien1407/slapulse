//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT©2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hibernate CodeGenerator
 */
public class UserRolePK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7715856015152887073L;

	/**
     * identifier field
     */
    private String username;

    /**
     * identifier field
     */
    private String roleName;

    /**
     * full constructor
     */
    public UserRolePK(String username, String roleName) {
        this.username = username;
        this.roleName = roleName;
    }

    /**
     * default constructor
     */
    public UserRolePK() {
    }

    @Column(name = "username", length=20)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "role_name", length=20)
    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("username", getUsername())
                .append("roleName", getRoleName())
                .toString();
    }

    public boolean equals(Object other) {
        if (!(other instanceof UserRolePK)) return false;
        UserRolePK castOther = (UserRolePK) other;
        return new EqualsBuilder()
                .append(this.getUsername(), castOther.getUsername())
                .append(this.getRoleName(), castOther.getRoleName())
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getUsername())
                .append(getRoleName())
                .toHashCode();
    }

}
