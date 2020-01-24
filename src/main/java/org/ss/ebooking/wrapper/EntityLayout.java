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
package org.ss.ebooking.wrapper;

import java.util.List;

/**
 * Entity layout.
 * @author ss
 */
public class EntityLayout {
    /** Fields. */
    private List<Field> fields;
    // ====================================== SET & GET ===============================================================
    /**
     * @return the fields
     */
    public List<Field> getFields() {
        return fields;
    }
    /**
     * @param fields the fields to set
     */
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
    // ====================================== PRIVATE CLASSES =========================================================
    /**
     * Layout field.
     */
    public static class Field {
        /** Field name. */
        private String name;
        /** Field type. */
        private String fieldType;
        /** Hidden. */
        private boolean hidden;
        /**
         * @return the name
         */
        public String getName() {
            return name;
        }
        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }
        /**
         * @return the fieldType
         */
        public String getFieldType() {
            return fieldType;
        }
        /**
         * @param fieldType the fieldType to set
         */
        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }
        /**
         * @return the hidden
         */
        public boolean isHidden() {
            return hidden;
        }
        /**
         * @param hidden the hidden to set
         */
        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }
    }
}
