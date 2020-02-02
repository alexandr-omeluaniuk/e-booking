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
import org.ss.ebooking.constants.ListViewColumnAlign;

/**
 * User permissions.
 * @author ss
 */
public class UserPermissions {
    /** Data models metadata. */
    private List<EntityMetadata> entityMetadata;
    /** User full name. */
    private String fullname;
    /**
     * @return the entityMetadata
     */
    public List<EntityMetadata> getEntityMetadata() {
        return entityMetadata;
    }
    /**
     * @param entityMetadata the entityMetadata to set
     */
    public void setEntityMetadata(List<EntityMetadata> entityMetadata) {
        this.entityMetadata = entityMetadata;
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
     * Data model metadata.
     */
    public static class EntityMetadata {
        /** Entity class id. */
        private String className;
        /** Data model material icon. */
        private String icon;
        /** List view columns. */
        private List<ListViewColumn> listViewColumns;
        /**
         * @return the icon
         */
        public String getIcon() {
            return icon;
        }
        /**
         * @param icon the icon to set
         */
        public void setIcon(String icon) {
            this.icon = icon;
        }
        /**
         * @return the className
         */
        public String getClassName() {
            return className;
        }
        /**
         * @param className the className to set
         */
        public void setClassName(String className) {
            this.className = className;
        }
        /**
         * @return the listViewColumns
         */
        public List<ListViewColumn> getListViewColumns() {
            return listViewColumns;
        }
        /**
         * @param listViewColumns the listViewColumns to set
         */
        public void setListViewColumns(List<ListViewColumn> listViewColumns) {
            this.listViewColumns = listViewColumns;
        }
    }
    /**
     * List view column.
     */
    public static class ListViewColumn {
        /** Field id. */
        private String id;
        /** Field align. */
        private ListViewColumnAlign align;
        /** Is enum constant. */
        private String enumField;
        /**
         * @return the id
         */
        public String getId() {
            return id;
        }
        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }
        /**
         * @return the align
         */
        public ListViewColumnAlign getAlign() {
            return align;
        }
        /**
         * @param align the align to set
         */
        public void setAlign(ListViewColumnAlign align) {
            this.align = align;
        }
        /**
         * @return the enumField
         */
        public String getEnumField() {
            return enumField;
        }
        /**
         * @param enumField the enumField to set
         */
        public void setEnumField(String enumField) {
            this.enumField = enumField;
        }
    }
}
