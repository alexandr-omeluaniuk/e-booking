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
    /** Field sets. */
    private List<FieldSet> fieldSets;
    // ====================================== SET & GET ===============================================================
    /**
     * @return the fieldSets
     */
    public List<FieldSet> getFieldSets() {
        return fieldSets;
    }
    /**
     * @param fieldSets the fieldSets to set
     */
    public void setFieldSets(List<FieldSet> fieldSets) {
        this.fieldSets = fieldSets;
    }
    // ====================================== PRIVATE CLASSES =========================================================
    public class FieldSet {
        /** Fieldset legend. */
        private String legend;
        /** Fields. */
        private List<Field> fields;
        /**
         * @return the legend
         */
        public String getLegend() {
            return legend;
        }
        /**
         * @param legend the legend to set
         */
        public void setLegend(String legend) {
            this.legend = legend;
        }
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
    }
    /**
     * Layout field.
     */
    public class Field {
        /** Field name. */
        private String name;
        /** Field type. */
        private String fieldType;
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
    }
}
