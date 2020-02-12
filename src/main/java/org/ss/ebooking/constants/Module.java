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
package org.ss.ebooking.constants;

import org.ss.ebooking.entity.Contact;
import org.ss.ebooking.entity.DataModel;

/**
 * Application module.
 * @author ss
 */
public enum Module {
    /** Contacts module */
    CONTACTS(new Class[] {Contact.class}, new Module[0]);
    /** Module data models. */
    private final Class<? extends DataModel>[] dataModel;
    /** Depends on other modules. */
    private final Module[] dependsOn;
    /**
     * Constructor.
     * @param dataModel module data models.
     * @param dependsOn depends on other modules.
     */
    private Module(Class<? extends DataModel>[] dataModel, Module[] dependsOn) {
        this.dataModel = dataModel;
        this.dependsOn = dependsOn;
    }
}
