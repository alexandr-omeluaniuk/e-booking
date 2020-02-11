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

import java.lang.reflect.Field;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.ss.ebooking.dao.CoreDAO;
import org.ss.ebooking.entity.DataModel;
import org.ss.ebooking.entity.Subscription;
import org.ss.ebooking.entity.SystemUser;
import org.ss.ebooking.service.EntityService;
import org.ss.ebooking.service.SubscriptionService;
import org.ss.ebooking.service.SystemUserService;
import org.ss.ebooking.wrapper.EntitySearchRequest;
import org.ss.ebooking.wrapper.EntitySearchResponse;
import org.ss.ebooking.anno.ui.FormField;

/**
 * Entity service implementation.
 * @author ss
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
class EntityServiceImpl implements EntityService {
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(EntityService.class);
    /** Core DAO. */
    @Autowired
    private CoreDAO coreDAO;
    /** Subscription service. */
    @Autowired
    private SubscriptionService subscriptionService;
    /** System user service. */
    @Autowired
    private SystemUserService systemUserService;
    @Override
    public EntitySearchResponse searchEntities(final Class<? extends DataModel> clazz,
            final EntitySearchRequest searchRequest) throws Exception {
        return coreDAO.searchEntities(clazz, searchRequest);
    }
    @Override
    public <T extends DataModel> T createEntity(T entity) throws Exception {
        if (entity instanceof Subscription) {
            return (T) subscriptionService.createSubscription((Subscription) entity);
        } else if (entity instanceof SystemUser) {
            return (T) systemUserService.createSystemUser((SystemUser) entity);
        } else {
            return coreDAO.create(entity);
        }
    }
    @Override
    public <T extends DataModel> T updateEntity(T entity) throws Exception {
        Class<T> entityClass = (Class<T>) entity.getClass();
        T fromDB = coreDAO.findById(entity.getId(), entityClass);
        setUpdatableFields(entityClass, fromDB, entity);
        return coreDAO.update(fromDB);
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
     * Set values for updatable fields.
     * @param entityClass entity class.
     * @param fromDB entity from database.
     * @param fromUser entity from user.
     * @throws Exception error.
     */
    private void setUpdatableFields(Class entityClass, Object fromDB, Object fromUser) throws Exception {
        for (Field field : entityClass.getDeclaredFields()) {
            FormField formField = field.getAnnotation(FormField.class);
            if (formField != null) {    // field is updatable
                field.setAccessible(true);
                field.set(fromDB, field.get(fromUser));
                field.setAccessible(false);
            }
        }
        if (entityClass.getSuperclass() != null) {
            setUpdatableFields(entityClass.getSuperclass(), fromDB, fromUser);
        }
    }
}
