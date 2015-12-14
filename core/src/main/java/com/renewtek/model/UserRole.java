//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT©2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

	private static final long serialVersionUID = -7253200324993556230L;

    @Id
    @Column(name = "comp_id")
    private com.renewtek.model.UserRolePK comp_id;

    @ManyToOne()
    private com.renewtek.model.User appUser;

    @ManyToOne()
    private com.renewtek.model.Role role;

    /**
     * full constructor
     */
    public UserRole(com.renewtek.model.UserRolePK comp_id, com.renewtek.model.User appUser, com.renewtek.model.Role role) {
        this.comp_id = comp_id;
        this.appUser = appUser;
        this.role = role;
    }

    /**
     * default constructor
     */
    public UserRole() {
    }

    /**
     * minimal constructor
     */
    public UserRole(com.renewtek.model.UserRolePK comp_id) {
        this.comp_id = comp_id;
    }

    public com.renewtek.model.UserRolePK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(com.renewtek.model.UserRolePK comp_id) {
        this.comp_id = comp_id;
    }

    public com.renewtek.model.User getAppUser() {
        return this.appUser;
    }

    public void setAppUser(com.renewtek.model.User appUser) {
        this.appUser = appUser;
    }

    public com.renewtek.model.Role getRole() {
        return this.role;
    }

    public void setRole(com.renewtek.model.Role role) {
        this.role = role;
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("comp_id", getComp_id())
                .toString();
    }

    public boolean equals(Object other) {
        if (!(other instanceof UserRole)) return false;
        UserRole castOther = (UserRole) other;
        return new EqualsBuilder()
                .append(this.getComp_id(), castOther.getComp_id())
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getComp_id())
                .toHashCode();
    }

}
