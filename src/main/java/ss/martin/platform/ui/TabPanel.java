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
package ss.martin.platform.ui;

import java.util.ArrayList;
import java.util.List;
import ss.martin.platform.constants.RepresentationComponentType;

/**
 * Tab panel.
 * @author ss
 */
public class TabPanel implements RepresentationComponent {
    /** Tabs. */
    private List<RepresentationComponent> tabs = new ArrayList<>();
    /** Icon. */
    private String icon;
    /** Icon color. */
    private String iconColor;
    /** Class name. */
    private String className;
// ========================================== METHODS =================================================================
    @Override
    public RepresentationComponentType type() {
        return RepresentationComponentType.TAB_PANEL;
    }
    /**
     * @return the tabs
     */
    public List<RepresentationComponent> getTabs() {
        return tabs;
    }
    /**
     * @param tabs the tabs to set
     */
    public void setTabs(List<RepresentationComponent> tabs) {
        this.tabs = tabs;
    }
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
     * @return the iconColor
     */
    public String getIconColor() {
        return iconColor;
    }
    /**
     * @param iconColor the iconColor to set
     */
    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }
}
