/*
 * The MIT License
 *
 * Copyright 2020 ss.
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

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.ss.ebooking.anno.ListViewColumn;
import org.ss.ebooking.anno.MaterialIcon;
import org.ss.ebooking.anno.UIGrid;
import org.ss.ebooking.anno.security.StandardRoleAccess;
import org.ss.ebooking.config.security.StandardRole;
import org.ss.ebooking.constants.AppConstants;
import org.ss.ebooking.constants.ListViewColumnAlign;

/**
 * Subscription.
 * @author ss
 */
@Entity
@Table(name = "subscription")
@MaterialIcon(icon = "subscriptions")
@StandardRoleAccess(roles = { StandardRole.ROLE_SUPER_ADMIN })
public class Subscription extends DataModel {
    /** Default UID. */
    private static final long serialVersionUID = 1L;
    // =========================================== FIELDS =============================================================
    /** Organization name. */
    @NotEmpty
    @Size(max = 255)
    @Column(name = "organization_name")
    @UIGrid(xs = "12")
    @ListViewColumn
    private String organizationName;
    /** Started. */
    @JsonFormat(pattern = AppConstants.DEFAULT_DATE_FORMAT)
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "started", nullable = false)
    @UIGrid(xs = "6")
    @ListViewColumn(align = ListViewColumnAlign.right)
    private Date started;
    /** Expiration date. */
    @JsonFormat(pattern = AppConstants.DEFAULT_DATE_FORMAT)
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "expiration_date", nullable = false)
    @UIGrid(xs = "6")
    @ListViewColumn(align = ListViewColumnAlign.right)
    private Date expirationDate;
    /** Subscription admin email. */
    @NotEmpty
    @Email
    @UIGrid(xs = "12")
    @Size(max = 255)
    @Column(name = "admin_email", length = 255, nullable = false)
    private String subscriptionAdminEmail;
    // =========================================== SET & GET ==========================================================
    /**
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }
    /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    /**
     * @return the started
     */
    public Date getStarted() {
        return started;
    }
    /**
     * @param started the started to set
     */
    public void setStarted(Date started) {
        this.started = started;
    }
    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }
    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    /**
     * @return the subscriptionAdminEmail
     */
    public String getSubscriptionAdminEmail() {
        return subscriptionAdminEmail;
    }
    /**
     * @param subscriptionAdminEmail the subscriptionAdminEmail to set
     */
    public void setSubscriptionAdminEmail(String subscriptionAdminEmail) {
        this.subscriptionAdminEmail = subscriptionAdminEmail;
    }
    // ================================================================================================================
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Subscription)) {
            return false;
        }
        Subscription other = (Subscription) object;
        return !((this.getId() == null && other.getId() != null)
                || (this.getId() != null && !this.getId().equals(other.getId())));
    }
    @Override
    public String toString() {
        return "org.ss.ebooking.entity.Subscription[ id=" + getId() + " ]";
    }
}
