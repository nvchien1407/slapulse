//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role extends BaseObject implements Serializable, Auditable {
    private static final long serialVersionUID = 3690197650654049848L;

    @Id
    @Column(name = "name", length = 20)
    private String name;
    
    @Column(name = "description", length = 255)
    private String description;
    
    @Column(name = "version")
    private Integer version;
    
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinTable(name = "user_role")
    private Set<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setId(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    /**
     * @param users The users to set.
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * @return Returns the version.
     * @hibernate.version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version The version to set.
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        final Role role = (Role) o;

        if (name != null ? !name.equals(role.name) : role.name != null) return false;

        return true;
    }

    public int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }

    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", this.name).append("description",
                        this.description).toString();
    }

}
