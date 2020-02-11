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
package org.ss.ebooking.config.security;

import java.util.List;
import org.ss.ebooking.wrapper.EntityListView;

/**
 * User permissions.
 * @author ss
 */
public class UserPermissions {
    /** Side bar navigation items. */
    private List<EntityListView> sideBarNavItems;
    /** Dashboard tab items. */
    private List<EntityListView> dashboardTabItems;
    /** User full name. */
    private String fullname;
    /**
     * @return the sideBarNavItems
     */
    public List<EntityListView> getSideBarNavItems() {
        return sideBarNavItems;
    }
    /**
     * @param sideBarNavItems the sideBarNavItems to set
     */
    public void setSideBarNavItems(List<EntityListView> sideBarNavItems) {
        this.sideBarNavItems = sideBarNavItems;
    }
    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }
    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    /**
     * @return the dashboardTabItems
     */
    public List<EntityListView> getDashboardTabItems() {
        return dashboardTabItems;
    }
    /**
     * @param dashboardTabItems the dashboardTabItems to set
     */
    public void setDashboardTabItems(List<EntityListView> dashboardTabItems) {
        this.dashboardTabItems = dashboardTabItems;
    }
}
