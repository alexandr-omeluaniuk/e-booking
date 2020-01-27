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
     * Layout grid system settings.
     */
    public static class Grid {
        /** LG breakpoint value. */
        private String lg = "false";
        /** MD breakpoint value. */
        private String md = "false";
        /** SM breakpoint value. */
        private String sm = "false";
        /** XS breakpoint value. */
        private String xs = "false";
        /** Direction. */
        private String direction = "row";
        /**
         * @return the lg
         */
        public String getLg() {
            return lg;
        }
        /**
         * @param lg the lg to set
         */
        public void setLg(String lg) {
            this.lg = lg;
        }
        /**
         * @return the md
         */
        public String getMd() {
            return md;
        }
        /**
         * @param md the md to set
         */
        public void setMd(String md) {
            this.md = md;
        }
        /**
         * @return the sm
         */
        public String getSm() {
            return sm;
        }
        /**
         * @param sm the sm to set
         */
        public void setSm(String sm) {
            this.sm = sm;
        }
        /**
         * @return the xs
         */
        public String getXs() {
            return xs;
        }
        /**
         * @param xs the xs to set
         */
        public void setXs(String xs) {
            this.xs = xs;
        }
        /**
         * @return the direction
         */
        public String getDirection() {
            return direction;
        }
        /**
         * @param direction the direction to set
         */
        public void setDirection(String direction) {
            this.direction = direction;
        }
    }
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
        /** Grid system. */
        private Grid grid;
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
        /**
         * @return the grid
         */
        public Grid getGrid() {
            return grid;
        }
        /**
         * @param grid the grid to set
         */
        public void setGrid(Grid grid) {
            this.grid = grid;
        }
    }
}
