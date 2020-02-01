/*
 * The MIT License
 *
 * Copyright 2018 Wisent Media.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.ss.ebooking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.ss.ebooking.anno.MaterialIcon;
import org.ss.ebooking.anno.security.StandardRoleAccess;
import org.ss.ebooking.config.security.StandardRole;

/**
 * SystemUser.
 * @author Alexandr Omeluaniuk
 */
@Entity
@Table(name = "users")
@MaterialIcon(icon = "supervisor_account")
@StandardRoleAccess(roles = { StandardRole.ROLE_SUBSCRIPTION_ADMINISTRATOR, StandardRole.ROLE_SUPER_ADMIN })
public class SystemUser extends TenantEntity {
    /** Default UID. */
    private static final long serialVersionUID = 1L;
// ==================================== FIELDS ====================================================
    /** Email. */
    @Email
    @NotEmpty
    @Size(max = 255)
    @Column(name = "email", length = 255)
    private String email;
    /** Password. */
    @Size(min = 5)
    @NotEmpty
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    /** First name. */
    @NotEmpty
    @Size(max = 255)
    @Column(name = "firstname", nullable = false, length = 255)
    private String firstname;
    /** Last name. */
    @Size(max = 255)
    @NotEmpty
    @Column(name = "lastname", nullable = false, length = 255)
    private String lastname;
    /** Is active. */
    @Column(name = "is_active", nullable = false)
    private boolean active;
    /** Standard role. */
    @Enumerated(EnumType.STRING)
    @Column(name = "standard_role")
    private StandardRole standardRole;
// ==================================== SET & GET =================================================
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    /**
     * @return the lastName
     */
    public String getLastname() {
        return lastname;
    }
    /**
     * @param lastname the lastName to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }
    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * @return the standardRole
     */
    public StandardRole getStandardRole() {
        return standardRole;
    }
    /**
     * @param standardRole the standardRole to set
     */
    public void setStandardRole(StandardRole standardRole) {
        this.standardRole = standardRole;
    }
// ================================================================================================
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SystemUser)) {
            return false;
        }
        SystemUser other = (SystemUser) object;
        return !((this.getId() == null && other.getId() != null)
                || (this.getId() != null && !this.getId().equals(other.getId())));
    }
    @Override
    public String toString() {
        return "org.ss.mvd.entity.User[ id=" + getId() + " ]";
    }
}
