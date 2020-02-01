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
import org.ss.ebooking.wrapper.EntityLayout;

/**
 * User permissions.
 * @author ss
 */
public class UserPermissions {
    /** Data models metadata. */
    private List<EntityMetadata> entityMetadata;
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
     * Data model metadata.
     */
    public static class EntityMetadata {
        /** Data model material icon. */
        private String icon;
        /** Layout. */
        private EntityLayout layout;
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
         * @return the layout
         */
        public EntityLayout getLayout() {
            return layout;
        }
        /**
         * @param layout the layout to set
         */
        public void setLayout(EntityLayout layout) {
            this.layout = layout;
        }
    }
}
