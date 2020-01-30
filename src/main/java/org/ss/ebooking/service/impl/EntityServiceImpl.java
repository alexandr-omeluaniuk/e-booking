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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Temporal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.ss.ebooking.anno.UIGrid;
import org.ss.ebooking.anno.UIHidden;
import org.ss.ebooking.dao.CoreDAO;
import org.ss.ebooking.entity.DataModel;
import org.ss.ebooking.exception.EBookingException;
import org.ss.ebooking.wrapper.EntityLayout;
import org.ss.ebooking.service.EntityService;
import org.ss.ebooking.wrapper.EntitySearchRequest;
import org.ss.ebooking.wrapper.EntitySearchResponse;

/**
 * Entity service implementation.
 * @author ss
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
class EntityServiceImpl implements EntityService {
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(EntityService.class);
    /** Excluded fields. */
    private static final Set<String> EXCLUDED_FIELDS = new HashSet<>();
    /**
     * Static initialization.
     */
    static {
        EXCLUDED_FIELDS.add("serialVersionUID");
    }
    /** Core DAO. */
    @Autowired
    private CoreDAO coreDAO;
    @Override
    public EntityLayout getEntityLayout(final Class<? extends DataModel> clazz) throws Exception {
        LOG.debug("get entity layout [" + clazz.getSimpleName() + "]");
        EntityLayout layout = new EntityLayout();
        layout.setFields(new ArrayList<>());
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!EXCLUDED_FIELDS.contains(field.getName())) {
                layout.getFields().add(getLayoutField(field));
            }
        }
        return layout;
    }
    @Override
    public EntitySearchResponse searchEntities(final Class<? extends DataModel> clazz,
            final EntitySearchRequest searchRequest) throws Exception {
        return coreDAO.searchEntities(clazz, searchRequest);
    }
    @Override
    public <T extends DataModel> T createEntity(T entity) throws Exception {
        return coreDAO.create(entity);
    }
    @Override
    public <T extends DataModel> void massDeleteEntities(Set<Long> ids, Class<T> cl) throws Exception {
        coreDAO.massDelete(ids, cl);
    }
    @Override
    public <T extends DataModel> T findEntityByID(Long id, Class<T> cl) throws Exception {
        return coreDAO.findById(id, cl);
    }
    // ==================================== PRIVATE ===================================================================
    /**
     * Get layout field from entity field.
     * @param field entity field.
     * @return layout field.
     * @throws Exception error.
     */
    private EntityLayout.Field getLayoutField(Field field) throws Exception {
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
        UIGrid grid = field.getAnnotation(UIGrid.class);
        EntityLayout.Grid fieldGridSystem = new EntityLayout.Grid();
        if (grid != null) {
            fieldGridSystem.setLg(grid.lg());
            fieldGridSystem.setMd(grid.lg());
            fieldGridSystem.setSm(grid.sm());
            fieldGridSystem.setXs(grid.xs());
        }
        layoutField.setGrid(fieldGridSystem);
        UIHidden hidden = field.getAnnotation(UIHidden.class);
        layoutField.setHidden(hidden != null);
        layoutField.setValidators(setValidators(field));
        return layoutField;
    }
    /**
     * Set validators.
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
        return validators;
    }
}
