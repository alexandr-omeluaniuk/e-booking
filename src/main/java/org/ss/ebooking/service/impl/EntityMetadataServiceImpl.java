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
package org.ss.ebooking.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.Temporal;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.ss.ebooking.anno.ui.FormField;
import org.ss.ebooking.anno.ui.HiddenField;
import org.ss.ebooking.anno.ui.ListViewColumn;
import org.ss.ebooking.anno.ui.MaterialIcon;
import org.ss.ebooking.entity.DataModel;
import org.ss.ebooking.exception.EBookingException;
import org.ss.ebooking.service.EntityMetadataService;
import org.ss.ebooking.wrapper.EntityLayout;
import org.ss.ebooking.wrapper.EntityListView;

/**
 * Entity metadata service implementation.
 * @author ss
 */
@Service
class EntityMetadataServiceImpl implements EntityMetadataService {
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(EntityMetadataService.class);
    /** Layouts cache. */
    private static final Map<Class<? extends DataModel>, EntityLayout> LAYOUTS_CACHE = new ConcurrentHashMap<>();
    /** Excluded fields. */
    private static final Set<String> EXCLUDED_FIELDS = new HashSet<>();
    /**
     * Static initialization.
     */
    static {
        EXCLUDED_FIELDS.add("serialVersionUID");
    }
    @Override
    public EntityLayout getEntityLayout(Class<? extends DataModel> clazz) throws Exception {
        if (LAYOUTS_CACHE.containsKey(clazz)) {
            return LAYOUTS_CACHE.get(clazz);
        }
        LOG.debug("get entity layout [" + clazz.getSimpleName() + "]");
        EntityLayout layout = new EntityLayout();
        layout.setFields(new ArrayList<>());
        for (Field field : getClassFields(clazz)) {
            if (!EXCLUDED_FIELDS.contains(field.getName())) {
                layout.getFields().add(createEntityLayoutField(field));
            }
        }
        LAYOUTS_CACHE.put(clazz, layout);
        return layout;
    }
    @Override
    public EntityListView getEntityListView(Class<? extends DataModel> clazz) throws Exception {
        EntityListView metadata = new EntityListView();
        metadata.setListViewColumns(new ArrayList());
        metadata.setClassName(clazz.getSimpleName());
        MaterialIcon materialIcon = clazz.getAnnotation(MaterialIcon.class);
        if (materialIcon != null) {
            metadata.setIcon(materialIcon.icon());
        }
        for (Field field : clazz.getDeclaredFields()) {
            ListViewColumn listViewColumnAnno = field.getAnnotation(ListViewColumn.class);
            if (listViewColumnAnno != null) {
                EntityListView.ListViewColumn listViewColumn = new EntityListView.ListViewColumn();
                listViewColumn.setId(field.getName());
                listViewColumn.setAlign(listViewColumnAnno.align());
                listViewColumn.setEnumField(field.getType().isEnum() ? field.getType().getSimpleName() : null);
                metadata.getListViewColumns().add(listViewColumn);
            }
        }
        return metadata;
    }
    // =================================================== PRIVATE ====================================================
    /**
     * Create entity layout field from entity field.
     * @param field entity field.
     * @return layout field.
     * @throws Exception error.
     */
    private EntityLayout.Field createEntityLayoutField(Field field) throws Exception {
        Annotation[] annotations = field.getAnnotations();
        if (LOG.isTraceEnabled()) {
            LOG.trace("field [" + field.getName() + "], annotations [" + annotations.length + "]");
        }
        EntityLayout.Field layoutField = new EntityLayout.Field();
        layoutField.setName(field.getName());
        if (Date.class.equals(field.getType())) {
            Temporal temporal = field.getAnnotation(Temporal.class);
            if (temporal == null || temporal.value() == null) {
                throw new EBookingException("wrong date field configuration! Field [" + field.getName()
                        + "] must have @Temporal annotation!");
            } else {
                layoutField.setFieldType(temporal.value().name());
            }
        } else {
            layoutField.setFieldType(field.getType().getSimpleName());
        }
        FormField grid = field.getAnnotation(FormField.class);
        EntityLayout.Grid fieldGridSystem = new EntityLayout.Grid();
        if (grid != null) {
            fieldGridSystem.setLg(grid.lg());
            fieldGridSystem.setMd(grid.lg());
            fieldGridSystem.setSm(grid.sm());
            fieldGridSystem.setXs(grid.xs());
        }
        layoutField.setGrid(fieldGridSystem);
        HiddenField hidden = field.getAnnotation(HiddenField.class);
        layoutField.setHidden(hidden != null);
        layoutField.setValidators(setValidators(field));
        return layoutField;
    }
    /**
     * Set field validators.
     * @param field field.
     * @return field validators.
     * @throws Exception error.
     */
    private List<EntityLayout.Validator> setValidators(Field field) throws Exception {
        List<EntityLayout.Validator> validators = new ArrayList<>();
        NotEmpty vNotEmpty = field.getAnnotation(NotEmpty.class);
        if (vNotEmpty != null) {
            EntityLayout.Validator validator = new EntityLayout.Validator();
            validator.setType(NotEmpty.class.getSimpleName());
            validators.add(validator);
        }
        Size vSize = field.getAnnotation(Size.class);
        if (vSize != null) {
            EntityLayout.Validator validator = new EntityLayout.Validator();
            validator.setType(Size.class.getSimpleName());
            Map<String, String> attributes = new HashMap<>();
            attributes.put("max", String.valueOf(vSize.max()));
            attributes.put("min", String.valueOf(vSize.min()));
            validator.setAttributes(attributes);
            validators.add(validator);
        }
        NotNull vNotNull = field.getAnnotation(NotNull.class);
        if (vNotNull != null) {
            EntityLayout.Validator validator = new EntityLayout.Validator();
            validator.setType(NotNull.class.getSimpleName());
            validators.add(validator);
        }
        Email vEmail = field.getAnnotation(Email.class);
        if (vEmail != null) {
            EntityLayout.Validator validator = new EntityLayout.Validator();
            validator.setType(Email.class.getSimpleName());
            validators.add(validator);
        }
        return validators;
    }
    /**
     * Get class fields (include super classes).
     * @param clazz class.
     * @return class fields.
     * @throws Exception error.
     */
    private List<Field> getClassFields(Class clazz) throws Exception {
        List<Field> result = new ArrayList<>();
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            result.addAll(getClassFields(clazz.getSuperclass()));
        }
        return result;
    }
}
